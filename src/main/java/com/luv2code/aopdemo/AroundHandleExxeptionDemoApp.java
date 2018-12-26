package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.service.TrafficFortuneService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class AroundHandleExxeptionDemoApp {
    private static Logger myLogger = Logger.getLogger(AroundHandleExxeptionDemoApp.class.getName());
    public static void main(String[] args) {

        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        // get the bean from spring container
        AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);
        TrafficFortuneService trafficFortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);
        myLogger.info("\nMain Program: AroundDemoApp" );
        myLogger.info("Calling getFortune");
        boolean tripWire = true;
        String data = trafficFortuneService.getFortune(tripWire);
        myLogger.info("\nMy Fortune is: " + data);
        myLogger.info("Finished");


        // close the context
        context.close();
    }

}










