package com.keylinks.android.arouter.annotation.model;

import javax.lang.model.element.Element;

/**
 * 路由路径Path的最终实体封装类
 * 比如：app分组中的MainActivity对象，这个对象有更多的属性
 */
public class ARouterBean {

    public enum Type{
        ACTIVITY,
        CALL
    }
    // 枚举类型：Activity
    private Type type;
    // 类节点
    private Element element;
    // 注解使用的类对象
    private Class<?> clazz;
    // 路由地址
    private String path;
    // 路由组
    private String group;


    private ARouterBean(Builder builder) {
        this.element = builder.element;
        this.path = builder.path;
        this.group = builder.group;
    }

    private ARouterBean(Type type, Class<?> clazz, String path, String group) {
        this.type = type;
        this.clazz = clazz;
        this.path = path;
        this.group = group;
    }


    public static ARouterBean create(Type type, Class<?> clazz, String path, String group){
        return new ARouterBean(type,clazz,path,group);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /*
    * 构建者模式
    * */
    public static class Builder{

        // 类节点
        private Element element;
        // 路由地址
        private String path;
        // 路由组
        private String group;

        public Builder setElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public ARouterBean build(){
            if (path==null&&path.length()==0){
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new ARouterBean(this);
        }

    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "path='" + path + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

}
