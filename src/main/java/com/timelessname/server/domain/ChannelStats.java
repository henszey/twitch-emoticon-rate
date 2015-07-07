package com.timelessname.server.domain;

public class ChannelStats {
  String channel;
  int topEmoteCount;
  String topEmote;
  
  ChannelRecord channelRecord;
  
  ChannelRecord topChannelRecord;

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
  
  public ChannelRecord getChannelRecord() {
		return channelRecord;
	}

	public void setChannelRecord(ChannelRecord channelRecord) {
		this.channelRecord = channelRecord;
	}

	public ChannelRecord getTopChannelRecord() {
		return topChannelRecord;
	}

	public void setTopChannelRecord(ChannelRecord topChannelRecord) {
		this.topChannelRecord = topChannelRecord;
	}

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((channel == null) ? 0 : channel.hashCode());
    result = prime * result + ((topEmote == null) ? 0 : topEmote.hashCode());
    result = prime * result + topEmoteCount;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ChannelStats other = (ChannelStats) obj;
    if (channel == null) {
      if (other.channel != null)
        return false;
    } else if (!channel.equals(other.channel))
      return false;
    if (topEmote == null) {
      if (other.topEmote != null)
        return false;
    } else if (!topEmote.equals(other.topEmote))
      return false;
    if (topEmoteCount != other.topEmoteCount)
      return false;
    return true;
  }

}