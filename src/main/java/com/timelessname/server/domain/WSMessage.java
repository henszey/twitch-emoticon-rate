package com.timelessname.server.domain;

public class WSMessage {

  String channel;
  Object message;
  
  public WSMessage(){
    
  }
  
  
  public WSMessage(String channel, Object message) {
    this.channel = channel;
    this.message = message;
  }


  public String getChannel() {
    return channel;
  }
  public void setChannel(String channel) {
    this.channel = channel;
  }
  public Object getMessage() {
    return message;
  }
  public void setMessage(Object message) {
    this.message = message;
  }
  
  
  
}
