package com.zj.caoshangfei.common.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent cse) {
        JtaTransactionManager jtaTransactionManager = cse.getApplicationContext().getBean(JtaTransactionManager.class);
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
    }
}
