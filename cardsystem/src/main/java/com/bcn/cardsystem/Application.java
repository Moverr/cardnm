/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcn.cardsystem;

import com.codemovers.scholar.engine.helper.CORSResponseFilter;
import com.codemovers.scholar.engine.helper.logfilters.LogInputRequestFilter;
import com.codemovers.scholar.engine.helper.logfilters.LogOutputResponseFilter;
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

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.accounts.AccountsEndpoint.class, CORSResponseFilter.class
                ), "/account/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.users.UsersEndpoint.class, CORSResponseFilter.class
                ), "/user/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.roles.RolesEndpoint.class, CORSResponseFilter.class
                ), "/roles/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.classes.ClassEndpoint.class, CORSResponseFilter.class
                ), "/classes/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.streams.StreamsEndpoint.class, CORSResponseFilter.class
                ), "/streams/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.curriculum.CurriculumEndpoint.class, CORSResponseFilter.class
                ), "/curriculum/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.subjects.SubjectEndpoint.class, CORSResponseFilter.class
                ), "/subjects/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.subjects.papers.SubjectPapersEndpoint.class, CORSResponseFilter.class
                ), "/subjects/papers/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.grading.GradingEndpoint.class, CORSResponseFilter.class
                ), "/grading/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.grading.details.GradingDetailsEndpoint.class, CORSResponseFilter.class
                ), "/grading/details/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.studyear.StudyYearEndpoint.class, CORSResponseFilter.class
                ), "/studyyear/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.terms.TermEndpoint.class, CORSResponseFilter.class
                ), "/terms/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.departments.DepartmentsEdnpoint.class, CORSResponseFilter.class
                ), "/departments/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.staff.StaffEdnpoint.class, CORSResponseFilter.class
                ), "/staff/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.permissions.PermissionsEndpoint.class, CORSResponseFilter.class
                ), "/permissions/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.exams.ExamsEndpoint.class, CORSResponseFilter.class
                ), "/exams/v1/*");
        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.exams.classes.ExamsClassEndpoint.class, CORSResponseFilter.class
                ), "/exams/classes/v1/*");
        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.exams.terms.ExamsTermEndpoint.class, CORSResponseFilter.class
                ), "/exams/terms/v1/*");
         context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.timetable.exams.ExamTimetableEndpoint.class, CORSResponseFilter.class
                ), "/exams/timetable/v1/*");
        

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.students.admissions.AdmissionEndpoint.class, CORSResponseFilter.class
                ), "/admissions/v1/*");

        context.addServlet(
                getServlet(com.codemovers.scholar.engine.api.v1.students.registration.terms.TermRegistrationEndpoint.class, CORSResponseFilter.class
                ), "/registration/term/v1/*");

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
