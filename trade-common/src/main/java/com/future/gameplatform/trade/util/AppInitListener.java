package com.future.gameplatform.trade.util;

import com.future.gameplatform.trade.service.NoticeCPTaskService;
import com.future.gameplatform.trade.service.impl.NoticeCPTask;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class AppInitListener implements ServletContextListener {

    private Timer pendingNoticeCPTimer = null;
    private Timer loadNoticeCPTaskTimer = null;
    private NoticeCPTaskService noticeCPTaskService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);


        noticeCPTaskService = (NoticeCPTaskService) applicationContext
                .getBean("noticeCPTaskService");

        initLoadNoticeCPTaskTimer(noticeCPTaskService);

        initNoticeCPTaskTimer(noticeCPTaskService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void initNoticeCPTaskTimer(final NoticeCPTaskService noticeCPTaskService) {
        pendingNoticeCPTimer = new Timer("noticeTimer", true);

        pendingNoticeCPTimer.schedule(
            new TimerTask()
            {
                @Override
                public void run() {
                    NoticeCPTask task = null;
                    do
                    {
                        task = noticeCPTaskService.getTimeToExecuteTask();
                        if (task != null)
                        {
                            task.run();
                        }
                    } while (task != null);
                }

            }, 0, 60 * 1000);
    }

    private void initLoadNoticeCPTaskTimer(final NoticeCPTaskService noticeCPTaskService) {
        loadNoticeCPTaskTimer = new Timer("loadTaskTimer", true);
        loadNoticeCPTaskTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                noticeCPTaskService.loadTaskFromDbGradually();
            }
        }, 0, 60 * 1000);
    }
}
