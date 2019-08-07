package com.boo.quartz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationUtil
 * @Description 系统应用上下文工具类
 * @Author boo
 * @Date 2019/8/7 9:37
 * @Version 1.0.0
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {
    /**
     * 系统上下文
     */
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtil.applicationContext = applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name bean名称
     * @param <T>  类型
     * @return Object 一个已给名字注册的bean实列
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
}
