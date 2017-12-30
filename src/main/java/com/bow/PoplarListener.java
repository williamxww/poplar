package com.bow;

import com.bow.component.SpringUtil;
import com.bow.service.core.impl.Manager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author vv
 * @since 2017/12/30.
 */
@Component
public class PoplarListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 容器启动后初始化配置管理器
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if( event.getApplicationContext().getParent() == null){
            Manager manager = SpringUtil.getBean(Manager.class);
            manager.init();
        }
    }
}
