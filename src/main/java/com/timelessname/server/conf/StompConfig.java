package com.timelessname.server.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import com.timelessname.server.service.WebSocketMessageBrokerStatsMonitor;

@Configuration
public class StompConfig extends AbstractMessageBrokerConfiguration {

  

  
  @Bean
  public AnnotationMBeanExporter annotationMBeanExporter() {
      return new AnnotationMBeanExporter();
  }
  
  
}