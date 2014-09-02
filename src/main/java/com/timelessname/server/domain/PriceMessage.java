package com.timelessname.server.domain;

import java.util.List;

public class PriceMessage {

  long time;

  List<EmoticonRate> prices;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public List<EmoticonRate> getPrices() {
    return prices;
  }

  public void setPrices(List<EmoticonRate> prices) {
    this.prices = prices;
  }

}
