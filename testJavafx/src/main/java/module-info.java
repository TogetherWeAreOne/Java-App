module com.togetherweareone {
    /*requires javafx.controls;
    requires javafx.fxml;
    requires spring.webflux;
    requires spring.core;
    requires spring.web;
    requires org.eclipse.jetty.client;
    requires org.eclipse.jetty.http;
    requires reactor.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires org.reactivestreams;*/
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.cli;
    requires spring.webflux;
    requires spring.core;
    requires spring.web;
    requires reactor.core;
    requires com.fasterxml.jackson.annotation;
    requires reactor.extra;
    requires reactor.netty.http;
    requires org.slf4j;
    requires org.eclipse.jetty.client;
    requires org.reactivestreams;
    requires com.fasterxml.jackson.core;
    //requires org.controlsfx.controls;
    
    opens com.togetherweareone.request.authRequest;
    opens com.togetherweareone.request.projectRequest;
    opens com.togetherweareone.request.checklistRequest;
    opens com.togetherweareone.request.columnRequest;
    opens com.togetherweareone.request.optionRequest;
    opens com.togetherweareone.request.stickerRequest;
    opens com.togetherweareone.request.taskRequest;
    opens com.togetherweareone to javafx.fxml;
    opens com.togetherweareone.fx.controllers to javafx.fxml;

    exports com.togetherweareone to javafx.graphics, javafx.fxml;
    exports com.togetherweareone.controller;
    exports com.togetherweareone.models;
    exports com.togetherweareone.fx to javafx.fxml, javafx.graphics;
    exports com.togetherweareone.api;

}