package com.timelessname.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.timelessname.server.service.PricingService;

@Controller
public class PriceController {

  //private static final Log logger = LogFactory.getLog(PriceController.class);
  
  @Autowired
  protected PricingService pricingService;
  
  @SubscribeMapping("/prices")
  public Object getPrices() throws Exception {
    return pricingService.getPrices();
  }

  @MessageMapping("/trade")
  public void executeTrade() {
    System.out.println("Hitttt &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
  }

  @MessageExceptionHandler
  @SendToUser("/queue/errors")
  public String handleException(Throwable exception) {
    return exception.getMessage();
  }
  
}
