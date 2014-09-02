package com.timelessname.server.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.timelessname.server.domain.ChannelData;
import com.timelessname.server.domain.Emoticon;
import com.timelessname.server.domain.EmoticonPrice;
import com.timelessname.server.domain.EmoticonRate;

@Service
public class PricingService {

  @Autowired
  protected MessageSendingOperations<String> messagingTemplate;
    
  @Resource
  protected Map<String, Emoticon> emoticonMap;

  @Resource
  protected List<String> emoticonList;

  protected Gson gson = new Gson();

  List<EmoticonRate> emoticonPrices;

  List<ChannelData> channelDatas;

  Map<String, EmoticonPrice> priceMap = Maps.newConcurrentMap();
  
  public List<EmoticonRate> getPrices() {
    return emoticonPrices;
  }

  public List<ChannelData> getChannelDatas() {
    return channelDatas;
  }

  public void priceMessage(String json) {
    Type listType = new TypeToken<ArrayList<EmoticonRate>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    List<EmoticonRate> newPrices = gson.fromJson(json, listType);
    process(newPrices);
  }

  private void process(List<EmoticonRate> newPrices) {
    //Map<String, EmoticonPrice> newPriceMap = Maps.newHashMap();
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
    Type listType = new TypeToken<ArrayList<ChannelData>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    channelDatas = gson.fromJson(json, listType);
  }

  @Scheduled(fixedDelay=1000)
  public void sendQuotes() {

    
  }
  
}
