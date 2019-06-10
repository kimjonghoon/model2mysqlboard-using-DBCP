package net.java_school.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java_school.db.dbpool.MySQLConnectionManager;

public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        MySQLConnectionManager dbmgr = new MySQLConnectionManager();
        sc.setAttribute("dbmgr", dbmgr);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        sc.removeAttribute("dbmgr");
    }

}