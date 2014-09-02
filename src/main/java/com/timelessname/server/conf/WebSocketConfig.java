package com.timelessname.server.conf;

import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

import com.timelessname.server.socket.WebSocketHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig extends SpringBootServletInitializer implements WebSocketConfigurer {

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler(), "/socket");//.withSockJS();
  }
  
  @Bean
  public PerConnectionWebSocketHandler webSocketHandler() {
    //return new EchoWebSocketHandler();
    return new PerConnectionWebSocketHandler(WebSocketHandler.class);
  }



}