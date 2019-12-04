package com.keylinks.android.aspectJ;

import android.util.Log;

import com.keylinks.android.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect//定义一个切面类
public class ClickBehaviorAspectj {
    private final static String TAG="netase >>>>";

    //1.应用中用到了那些注解,放到当前的切入段进行处理(找到需要处理的切入点)
    //execution 已方法执行时作为切点 触发aspect类
    @Pointcut("execution(@com.keylinks.android.annotation.ClickBehavior * *(..))")
    public void pointCut(){}
    //2.对切入点处理
    @Around("pointCut()")
    public Object jointPoint(ProceedingJoinPoint jointPoint)throws Throwable{
        //获取签名方法
        MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();
        //获取类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //获取方法名
        String methodName = methodSignature.getName();
        //获取注解值
        String funName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();
        Log.i(TAG,"ClickBehavior start");
        Object result = jointPoint.proceed();
        Log.i(TAG,"ClickBehavior end");
        Log.i(TAG, "统计了s%类中的s%方法的s%功能" );

        return result;
    }



}
