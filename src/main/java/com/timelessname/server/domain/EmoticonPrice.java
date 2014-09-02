package com.timelessname.server.domain;

public class EmoticonPrice implements Comparable<EmoticonPrice> {

  String emoticon;
  
  int price;
  
  public EmoticonPrice(){
    
  }

  public EmoticonPrice(String emoticonKey, Integer count) {
    this.emoticon = emoticonKey;
    this.price = count;
  }

  public String getEmoticon() {
    return emoticon;
  }

  public void setEmoticon(String emoticon) {
    this.emoticon = emoticon;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public int compareTo(EmoticonPrice o) {
    return o.price - this.price;
  }
  
  
  
}
