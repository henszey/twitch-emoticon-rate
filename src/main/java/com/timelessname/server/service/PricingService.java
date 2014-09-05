package com.timelessname.server.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.timelessname.server.domain.ChannelStats;
import com.timelessname.server.domain.Emoticon;
import com.timelessname.server.domain.EmoticonPrice;
import com.timelessname.server.domain.EmoticonRate;
import com.timelessname.server.domain.Message;

@Service
public class PricingService {

  @Autowired
  protected MessageSendingOperations<String> messagingTemplate;
    
  @Resource
  protected Map<String, Emoticon> emoticonMap;

  @Resource
  protected List<String> emoticonList;

  protected Gson gson = new Gson();

  Map<String, EmoticonPrice> priceMap = Maps.newConcurrentMap();
  
  List<EmoticonRate> prices;
  
  List<ChannelStats> channelStats;
  

  public void priceMessage(String json) {
    Type listType = new TypeToken<ArrayList<EmoticonRate>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    List<EmoticonRate> newPrices = gson.fromJson(json, listType);
    process(newPrices);
    prices = newPrices;
  }

  private void process(List<EmoticonRate> newPrices) {
    for (EmoticonRate emoticonRate : newPrices) {     
      EmoticonPrice emoticonPrice = priceMap.get(emoticonRate.getEmoticon());
      if(emoticonPrice == null){
        emoticonPrice = new EmoticonPrice();
        emoticonPrice.setEmoticon(emoticonRate.getEmoticon());
        priceMap.put(emoticonPrice.getEmoticon(), emoticonPrice);
      } 
      if(emoticonPrice.getPrice() != emoticonRate.getPerMinute()){
        emoticonPrice.setPrice(emoticonRate.getPerMinute());
        messagingTemplate.convertAndSend("/topic/price.stock." + emoticonPrice.getEmoticon(), emoticonPrice);
      }
    }
  }

  public void channelMessage(String json) {
    Type listType = new TypeToken<ArrayList<ChannelStats>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    List<ChannelStats> newStats = gson.fromJson(json, listType);
    if(channelStats == null){
      channelStats = newStats;
      return;
    }
    
    boolean changes = false;
    for (int i = 0; i < newStats.size() && i < channelStats.size(); i++) {
      if(!newStats.get(i).equals(channelStats.get(i))){
        changes = true;
        break;
      }
    }
    if(changes){
      messagingTemplate.convertAndSend("/topic/channelstats", newStats);
    }

    channelStats = newStats;
  }
  
  public void chatMessage(String json){
    Message message = gson.fromJson(json, Message.class);
    if(channelStats != null && channelStats.size() > 0){
      ChannelStats channelStat = channelStats.get(0);
      if(message.getChannel().equalsIgnoreCase(channelStat.getChannel())){
        messagingTemplate.convertAndSend("/topic/chat."+message.getChannel()+".message", message);
      }
    }

    
  }

  public List<EmoticonRate> getPrices() {
    return prices;
  }


  
}
