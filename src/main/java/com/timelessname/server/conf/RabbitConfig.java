package com.timelessname.server.conf;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timelessname.server.service.PricingService;

@Configuration
public class RabbitConfig {



  String priceQueueName = "twitch.main.consumer";
  String priceExchangeName = "twitch.prices";


  @Bean
  Queue priceQueue() {
    return new Queue(priceQueueName,false,false,true);
  }

  @Bean
  FanoutExchange priceExchange() {
    return new FanoutExchange(priceExchangeName,false,false);
  }

  @Bean
  Binding priceBinding(Queue priceQueue, FanoutExchange priceExchange) {
    return BindingBuilder.bind(priceQueue).to(priceExchange);
  }
  

  @Bean
  SimpleMessageListenerContainer priceContainer(ConnectionFactory connectionFactory, MessageListenerAdapter priceListenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(priceQueueName);
    container.setMessageListener(priceListenerAdapter);
    container.setAcknowledgeMode(AcknowledgeMode.NONE);
    return container;
  }

  @Bean
  MessageListenerAdapter priceListenerAdapter(PricingService coreService) {
    return new MessageListenerAdapter(coreService, "priceMessage");
  }
  
  
  
  String channelQueueName = "twitch.main.consumer";
  String channelExchangeName = "twitch.channelData";


  @Bean
  Queue channelQueue() {
    return new Queue(channelQueueName,false,false,true);
  }

  @Bean
  FanoutExchange channelExchange() {
    return new FanoutExchange(channelExchangeName,false,false);
  }

  @Bean
  Binding channelBinding(Queue channelQueue, FanoutExchange channelExchange) {
    return BindingBuilder.bind(channelQueue).to(channelExchange);
  }
  

  @Bean
  SimpleMessageListenerContainer channelContainer(ConnectionFactory connectionFactory, MessageListenerAdapter channelListenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(channelQueueName);
    container.setMessageListener(channelListenerAdapter);
    container.setAcknowledgeMode(AcknowledgeMode.NONE);
    return container;
  }

  @Bean
  MessageListenerAdapter channelListenerAdapter(PricingService coreService) {
    return new MessageListenerAdapter(coreService, "channelMessage");
  }

  
  
  

  
  
}
