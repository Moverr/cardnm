/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem;

import com.bcn.cardsystem.helper.CORSResponseFilter;
import com.bcn.cardsystem.helper.logfilters.LogInputRequestFilter;
import com.bcn.cardsystem.helper.logfilters.LogOutputResponseFilter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.quartz.Scheduler;

/**
 *
 * @author Mover 11/16/2017
 */
public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private static int orderCounter = 0;
    private static Server jettyServer;
    private static Scheduler scheduler = null;
    public static final int BUILD_NUMBER = 62;

    public static void main(String[] args) {

        init();
        startServer(true);

    }

    private static void startServer(boolean persistent) {
        try {

            jettyServer.start();
            if (persistent) {
                jettyServer.join();
            }

        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (persistent) {
                // stopLogFilesScheduler();
                jettyServer.destroy();

            }
        }

        LOG.log(Level.INFO, " Starting Server  ");
    }

    public static void init() {
        LOG.log(Level.INFO, "Starting Application ");
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setMaxFormContentSize(50000000);
        // this end points dont mind the use of sessions  :: 
//        context.addServlet(
//                getServlet(com.codemovers.scholar.engine.api.v1.accounts.AccountsEndpoint.class, CORSResponseFilter.class
//                ), "/account/v1/*");
 

        int port = 9876;
        jettyServer = new Server(port);

        for (Connector connector : jettyServer.getConnectors()) {
            LOG.log(Level.INFO, "Port: {0}", Integer.toString(((NetworkConnector) connector).getPort()));
        }
        jettyServer.setHandler(context);

    }

    private static ServletHolder getServlet(Class clazz, Class... features) {
        ResourceConfig resourceConfig = new ResourceConfig(clazz);

//        resourceConfig.property("jersey.config.server.tracing.type", "ALL");
//        resourceConfig.property("jersey.config.server.tracing.threshold", "VERBOSE");
        for (Class feature : features) {
            resourceConfig.register(feature);
        }

        // default resources
        resourceConfig.register(LogInputRequestFilter.class);
        resourceConfig.register(JacksonFeature.class);

//        if (!clazz.getPackage().getName().contains("v1b")) {
//            resourceConfig.register(AuthorizationRequestFilter.class);
//        }
        resourceConfig.register(LogOutputResponseFilter.class);

        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder sh = new ServletHolder(servletContainer);
        sh.setInitOrder(orderCounter++);
        return sh;
    }

}
