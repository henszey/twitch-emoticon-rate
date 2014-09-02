package com.timelessname.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.timelessname.server.domain.EmoticonPrice;

@Service
public class TradingService {

  @Autowired
  PricingService pricingService;
  
  
  boolean marketOpen = false;
  long closedAt = System.currentTimeMillis();
  public boolean isMarketOpen(){
    return marketOpen;
  }
  
  
  @Scheduled(fixedDelay = 50)
  public void checkMarketState() {
    List<EmoticonPrice> prices = pricingService.getPrices();
    if(prices == null || prices.size() == 0){
      marketOpen = false;
      return;
    }
    if(prices.get(0).getPrice() < 100){
      marketOpen = false;
      closedAt = System.currentTimeMillis();
    } else {
      if(!marketOpen){
        if(System.currentTimeMillis() - closedAt > 30000){
          marketOpen = true;
        }
      }
    }
  }
  
  
}
