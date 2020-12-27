package com.packtpub.javaee8.tracing;

import com.uber.jaeger.Configuration;
import io.opentracing.util.GlobalTracer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class OpenTracingContextInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration configuration = Configuration.fromEnv();
        GlobalTracer.register(configuration.getTracer());
    }
}
