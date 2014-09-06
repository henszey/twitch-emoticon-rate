package com.timelessname.server.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import com.timelessname.server.service.WebSocketMessageBrokerStatsMonitor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Autowired
  protected WebSocketMessageBrokerConfigurationSupport webSocketMessageBrokerConfigurationSupport;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/socket").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/queue/", "/topic/");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Bean
  public WebSocketMessageBrokerStatsMonitor statsMonitor() {
    return new WebSocketMessageBrokerStatsMonitor(
        (SubProtocolWebSocketHandler) webSocketMessageBrokerConfigurationSupport.subProtocolWebSocketHandler(),
        webSocketMessageBrokerConfigurationSupport.clientOutboundChannelExecutor());
  }
  


}