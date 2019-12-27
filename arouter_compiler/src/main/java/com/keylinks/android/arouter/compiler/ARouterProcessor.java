package com.keylinks.android.arouter.compiler;

import com.google.auto.service.AutoService;
import com.keylinks.android.arouter.annotation.ARouter;
import com.keylinks.android.arouter.annotation.model.ARouterBean;
import com.keylinks.android.arouter.compiler.utils.Constants;
import com.keylinks.android.arouter.compiler.utils.EmptyUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)
//允许支持的注解类型
@SupportedAnnotationTypes({Constants.AROUTER_ANNOTATION_TYPES})
//指定的JDK编码
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//注解接收的参数  在gradle里面可以配置
@SupportedOptions({Constants.MODULE_NAME, Constants.APT_PACKAGE})
public class ARouterProcessor extends AbstractProcessor {

    // 操作Element工具类 (类、函数、属性都是Element)
    private Elements elementUtils;

    // type(类信息)工具类，包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;

    // 文件生成器 类/资源，Filter用来创建新的类文件，class文件以及辅助文件
    private Filer filer;

    // 子模块名，如：app/order/personal。需要拼接类名时用到（必传）ARouter$$Group$$order
    private String moduleName;

    // 包名，用于存放APT生成的类文件
    private String packageNameForAPT;

    // 临时map存储，用来存放路由组Group对应的详细Path类对象，生成路由路径类文件时遍历
    // key:组名"app", value:"app"组的路由路径"ARouter$$Path$$app.class"
    private Map<String, List<ARouterBean>> tempPathMap = new HashMap<>();

    // 临时map存储，用来存放路由Group信息，生成路由组类文件时遍历
    // key:组名"app", value:类名"ARouter$$Path$$app.class"
    private Map<String, String> tempGroupMap = new HashMap<>();

    // 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以获取一些列有用的工具类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        Map<String, String> options = processingEnvironment.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            //取参数
            moduleName = options.get(Constants.MODULE_NAME);
            packageNameForAPT = options.get(Constants.APT_PACKAGE);
            // 有坑：Diagnostic.Kind.ERROR，异常会自动结束，不像安卓中Log.e
            messager.printMessage(Diagnostic.Kind.NOTE, "moduleName >>> " + moduleName);
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName >>> " + packageNameForAPT);
        }
        //比传参数判空
        if (EmptyUtils.isEmpty(moduleName) || EmptyUtils.isEmpty(packageNameForAPT)) {
            throw new RuntimeException("注解处理器需要的参数moduleName或者packageName为空，请在对应build.gradle配置参数");
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取所以被ARouter注解的元素
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        if (!EmptyUtils.isEmpty(elementsAnnotatedWith)) {

            //解析元素
            parseElements(elementsAnnotatedWith);


        }

        return false;
    }


    //解析所有被ARouter注解的元素
    private void parseElements(Set<? extends Element> elementsAnnotatedWith) {

        // 通过Element工具类，获取Activity、Callback类型
        TypeElement activityType = elementUtils.getTypeElement(Constants.ACTIVITY);
        TypeElement callType = elementUtils.getTypeElement(Constants.CALL);

        //显示类信息
        TypeMirror activityMirror = activityType.asType();
        TypeMirror callMirror = callType.asType();

        //遍历节点信息
        for (Element element : elementsAnnotatedWith) {
            //获取元素类信息
            TypeMirror elementMirror = element.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "遍历元素信息：" + elementMirror.toString());
            //获取元素类的注解值
            ARouter aRouter = element.getAnnotation(ARouter.class);
            //把路由信息封装成一个实体

            ARouterBean bean = new ARouterBean.Builder()
                    .setGroup(aRouter.group())
                    .setPath(aRouter.path())
                    .setElement(element)
                    .build();
            if (typeUtils.isSubtype(elementMirror, activityMirror)) {
                bean.setType(ARouterBean.Type.ACTIVITY);
            } else if (typeUtils.isSubtype(elementMirror, callMirror)) {
                bean.setType(ARouterBean.Type.CALL);
            } else {
                // 不匹配抛出异常，这里谨慎使用！考虑维护问题
                throw new RuntimeException("@ARouter注解目前仅限用于Activity类之上");
            }


            // 赋值临时map存储，用来存放路由组Group对应的详细Path类对象
            valueOfPathMap(bean);
        }


        //遍历完后,生成文件

        // 获取ARouterLoadGroup、ARouterLoadPath类型（生成类文件需要实现的接口）
        TypeElement groupLoadType = elementUtils.getTypeElement(Constants.AROUTE_GROUP); // 组接口
        TypeElement pathLoadType = elementUtils.getTypeElement(Constants.AROUTE_PATH); // 路径接口

        // 第一步：生成路由组Group对应详细Path类文件，如：ARouter$$Path$$app
        createPathFile(pathLoadType);




    }


    //通过javapoet 组装需要生成的目标文件
    private void createPathFile(TypeElement pathLoadType) {

        TypeName typeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),//Map
                ClassName.get(String.class),//Map<String,
                ClassName.get(ARouterBean.class)// Map<String, ARouterBean>
        );

        for (Map.Entry<String, List<ARouterBean>> entry:tempPathMap.entrySet()) {

            // 方法配置：public Map<String, ARouterBean> loadPath() {
            MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(Constants.PATH_METHOD_NAME)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(typeName);
            // 遍历之前：Map<String, RouterBean> pathMap = new HashMap<>();  1,javapoet语法字符串 2,参数
            methodBuidler.addStatement("$T<$T,$T> $N = new $T<>()"
                    ,ClassName.get(Map.class)
                    ,ClassName.get(String.class)
                    ,ClassName.get(ARouterBean.class)
                    ,Constants.PATH_PARAMETER_NAME
                    ,HashMap.class);

        }


    }


    private void valueOfPathMap(ARouterBean bean) {

        //检查注解上填写的路径格式是否符合规范
        if (checkRouterPath(bean)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean >>> " + bean.toString());
            // 开始赋值Map
            List<ARouterBean> aRouterBeans = tempPathMap.get(bean.getGroup());
            // 如果从Map中找不到key为：bean.getGroup()的数据，就新建List集合再添加进Map
            if (EmptyUtils.isEmpty(aRouterBeans)){
                aRouterBeans=new ArrayList<>();
                aRouterBeans.add(bean);
                tempPathMap.put(bean.getGroup(),aRouterBeans);
            }else{
                aRouterBeans.add(bean);
            }

        }else{
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
        }


    }


    //检查注解上填写的路径格式是否符合规范
    private boolean checkRouterPath(ARouterBean bean) {

        String group = bean.getGroup();
        String path = bean.getPath();

        // @ARouter注解中的path值，必须要以 / 开头
        if (EmptyUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的path值，必须要以 / 开头");
            return false;

        }

        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
            return false;
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        // @ARouter注解中的group有赋值情况
        if (!EmptyUtils.isEmpty(group) && !group.equals(moduleName)) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }

        return true;
    }
}
