package com.timelessname.server.domain;

public class EmoticonRate implements Comparable<EmoticonRate> {

  protected String emoticon;

  protected int perMinute;
  


  public EmoticonRate(String emoticonKey, Integer count) {
    this.emoticon = emoticonKey;
    this.perMinute = count;
  }

  public String getEmoticon() {
    return emoticon;
  }

  public void setEmoticon(String emoticon) {
    this.emoticon = emoticon;
  }
  
  

  public int getPerMinute() {
    return perMinute;
  }

  public void setPerMinute(int perMinute) {
    this.perMinute = perMinute;
  }

  @Override
  public int compareTo(EmoticonRate o) {
    return o.perMinute - this.perMinute;
  }
  
  
  
}
