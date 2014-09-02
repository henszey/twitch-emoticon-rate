package com.timelessname.server.socket;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.timelessname.server.domain.Message;
import com.timelessname.server.domain.WSMessage;
import com.timelessname.server.service.PricingService;
import com.timelessname.server.service.TradingService;

public class WebSocketHandler extends TextWebSocketHandler {


  @Autowired
  PricingService pricingService;
  
  @Autowired
  TradingService tradingService;
  
  @Autowired
  Gson gson;
  

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException{
    
    WSMessage counts = new WSMessage("prices",pricingService.getPrices());
    session.sendMessage(new TextMessage(gson.toJson(counts)));

    WSMessage channelDatas = new WSMessage("channelDatas",pricingService.getChannelDatas());
    session.sendMessage(new TextMessage(gson.toJson(channelDatas)));

    WSMessage marketStatus = new WSMessage("marketStatus",tradingService.isMarketOpen());
    session.sendMessage(new TextMessage(gson.toJson(marketStatus)));
  }
  
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

  }
  
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    try {
      session.close(CloseStatus.SERVER_ERROR);
    } catch (Exception e) {

    }
  }
  
  




}
