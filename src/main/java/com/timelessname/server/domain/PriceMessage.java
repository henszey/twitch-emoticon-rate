package com.timelessname.server.domain;

import java.util.List;

public class PriceMessage {

  long time;

  List<EmoticonPrice> prices;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public List<EmoticonPrice> getPrices() {
    return prices;
  }

  public void setPrices(List<EmoticonPrice> prices) {
    this.prices = prices;
  }

}
