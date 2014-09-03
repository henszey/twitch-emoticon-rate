package com.timelessname.server.conf;

import java.util.UUID;

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

  String priceQueueName = UUID.randomUUID().toString();
  String priceExchangeName = "twitch.rate";

  @Bean
  Queue priceQueue() {
    return new Queue(priceQueueName, false, false, true);
  }

  @Bean
  TopicExchange priceExchange() {
    return new TopicExchange(priceExchangeName, true, false);
  }

  @Bean
  Binding priceBinding(Queue priceQueue, TopicExchange priceExchange) {
    return BindingBuilder.bind(priceQueue).to(priceExchange).with("twitch.rate.emoticons");
  }

  @Bean
  SimpleMessageListenerContainer priceContainer(ConnectionFactory connectionFactory,
      MessageListenerAdapter priceListenerAdapter) {
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

  String channelQueueName = UUID.randomUUID().toString();
  String channelExchangeName = "twitch.rate";

  @Bean
  Queue channelQueue() {
    return new Queue(channelQueueName, false, false, true);
  }

  @Bean
  TopicExchange channelExchange() {
    return new TopicExchange(channelExchangeName, true, false);
  }

  @Bean
  Binding channelBinding(Queue channelQueue, TopicExchange channelExchange) {
    return BindingBuilder.bind(channelQueue).to(channelExchange).with("twitch.rate.channels");
  }

  @Bean
  SimpleMessageListenerContainer channelContainer(ConnectionFactory connectionFactory,
      MessageListenerAdapter channelListenerAdapter) {
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
