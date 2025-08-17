//package com.palani.learning.security.configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.apache.catalina.connector.Connector;
//
///**
// * Configuration to redirect all HTTP traffic to HTTPS.
// */
//@Configuration
//public class HttpToHttpsRedirectConfig {
//
//    @Value("${server.http.port:8080}")
//    private int httpPort;
//
//    @Value("${server.port:8443}")
//    private int httpsPort;
//
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
//        return server -> {
//            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//            connector.setScheme("http");
//            connector.setPort(httpPort);
//            connector.setSecure(false);
//            connector.setRedirectPort(httpsPort);
//            server.addAdditionalTomcatConnectors(connector);
//        };
//    }
//}
//
