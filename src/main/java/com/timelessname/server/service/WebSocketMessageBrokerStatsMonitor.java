package com.timelessname.server.service;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

@ManagedResource
public class WebSocketMessageBrokerStatsMonitor {

  @Autowired
  protected GaugeService gaugeService;
  
  @Autowired
  protected WebSocketMessageBrokerStats brokerStats;
  
  private final Map<String, WebSocketSession> webSocketSessions;
  private final ThreadPoolExecutor outboundExecutor;

  @SuppressWarnings("unchecked")
  public WebSocketMessageBrokerStatsMonitor(SubProtocolWebSocketHandler webSocketHandler,
      ThreadPoolTaskExecutor outboundTaskExecutor) {
    this.webSocketSessions = (Map<String, WebSocketSession>) new DirectFieldAccessor(webSocketHandler)
        .getPropertyValue("sessions");
    this.outboundExecutor = outboundTaskExecutor.getThreadPoolExecutor();
  }
  
  @Scheduled(fixedDelay=1000)
  public void gaugeCron() {
    gaugeService.submit("gauge.websockets.currentsessions",  getCurrentSessions());
    //gaugeService.submit("gauge.websockets.sendbuffersize",  getSendBufferSize());
    gaugeService.submit("gauge.websockets.outbound.poolsize",  getOutboundPoolSize());
    gaugeService.submit("gauge.websockets.outbound.largestpoolsize",  getOutboundLargestPoolSize());
    gaugeService.submit("gauge.websockets.outbound.activethreads",  getOutboundActiveThreads());
    gaugeService.submit("gauge.websockets.outbound.queuedtaskcount",  getOutboundQueuedTaskCount());
    gaugeService.submit("gauge.websockets.outbound.completedtaskcount",  getOutboundCompletedTaskCount());
  }

  @ManagedAttribute
  public int getCurrentSessions() {
    return webSocketSessions.size();
  }

//  @ManagedAttribute
//  public int getSendBufferSize() {
//    int sendBufferSize = 0;
//    for (WebSocketSession session : this.webSocketSessions.values()) {
//      ConcurrentWebSocketSessionDecorator concurrentSession = (ConcurrentWebSocketSessionDecorator) session;
//      sendBufferSize += concurrentSession.getBufferSize();
//    }
//    return sendBufferSize;
//  }

  @ManagedAttribute
  public int getOutboundPoolSize() {
    return outboundExecutor.getPoolSize();
  }

  @ManagedAttribute
  public int getOutboundLargestPoolSize() {
    return outboundExecutor.getLargestPoolSize();
  }

  @ManagedAttribute
  public int getOutboundActiveThreads() {
    return outboundExecutor.getActiveCount();
  }

  @ManagedAttribute
  public int getOutboundQueuedTaskCount() {
    return outboundExecutor.getQueue().size();
  }

  @ManagedAttribute
  public long getOutboundCompletedTaskCount() {
    return outboundExecutor.getCompletedTaskCount();
  }

}