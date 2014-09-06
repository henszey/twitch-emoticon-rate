package com.timelessname.server.service;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

@ManagedResource
public class WebSocketMessageBrokerStatsMonitor {
 
    private final Map<String, WebSocketSession> webSocketSessions;
    private final ThreadPoolExecutor outboundExecutor;
 
    @SuppressWarnings("unchecked")
    public WebSocketMessageBrokerStatsMonitor(SubProtocolWebSocketHandler webSocketHandler, ThreadPoolTaskExecutor outboundTaskExecutor) {
        this.webSocketSessions = (Map<String, WebSocketSession>) new DirectFieldAccessor(webSocketHandler).getPropertyValue("sessions");
        this.outboundExecutor  = outboundTaskExecutor.getThreadPoolExecutor();
    }
 
    @ManagedAttribute
    public int getCurrentSessions() {
        return webSocketSessions.size();
    }
 
    @ManagedAttribute
    public String getSendBufferSize() {
        int sendBufferSize = 0;
        for (WebSocketSession session : this.webSocketSessions.values()) {
            ConcurrentWebSocketSessionDecorator concurrentSession = (ConcurrentWebSocketSessionDecorator) session;
            sendBufferSize += concurrentSession.getBufferSize();
        }
        return formatByteCount(sendBufferSize);
    }
 
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
 
    private static String formatByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), "KMGTPE".charAt(exp - 1));
    }
 
}