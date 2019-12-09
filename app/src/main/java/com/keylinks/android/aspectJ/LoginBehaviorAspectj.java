package com.keylinks.android.aspectJ;

import android.content.Context;
import android.content.Intent;

import com.keylinks.android.LoginActivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect//定义一个切面类
public class LoginBehaviorAspectj {
    private final static String TAG = "netase >>>>";
    //1.应用中用到了那些注解,放到当前的切入段进行处理(找到需要处理的切入点)
    //execution 已方法执行时作为切点 触发aspect类
    @Pointcut("execution(@com.keylinks.android.annotation.LoginBehavior * *(..))")
    public void pointCut() {
    }

    //2.对切入点处理
    @Around("pointCut()")
    public Object jointPoint(ProceedingJoinPoint jointPoint) throws Throwable {
        Context context = (Context) jointPoint.getThis();
        //获取令牌,判断是否登入
        if (false) {
            //检测到登入,继续执行
            Object result = jointPoint.proceed();
            return result;
        }
        //未登入   跳转到登录页
        context.startActivity(new Intent(context, LoginActivity.class));
        return null;
    }


}
