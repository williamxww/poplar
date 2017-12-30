package com.bow;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wwxiang
 * @since 2017/12/27.
 */
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        // 服务器的监听端口
        Server server = new Server(9000);
        // 关联一个已经存在的上下文
        WebAppContext context = new WebAppContext();
        context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("./src/main/webapp");
        context.setContextPath("/poplar");
        context.setParentLoaderPriority(true);
        server.setHandler(context);


        try {
            server.start();
            LOGGER.info("Server started.");
            server.join();
        } catch (Exception e) {
            LOGGER.error("Fatal error.", e);
        }
    }
}
