package com.end2end.ansimnuri.util.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringProvider implements ApplicationContextAware {

    public static ApplicationContext Spring;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringProvider.Spring = applicationContext;
    }
}