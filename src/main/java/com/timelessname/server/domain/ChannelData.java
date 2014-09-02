package com.timelessname.server.domain;

public class ChannelData {
  String channel;
  int topEmoteCount;
  String topEmote;

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public int getTopEmoteCount() {
    return topEmoteCount;
  }

  public void setTopEmoteCount(int topEmoteCount) {
    this.topEmoteCount = topEmoteCount;
  }

  public String getTopEmote() {
    return topEmote;
  }

  public void setTopEmote(String topEmote) {
    this.topEmote = topEmote;
  }

}