package com.timelessname.server.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.timelessname.server.domain.ChannelData;
import com.timelessname.server.domain.Emoticon;
import com.timelessname.server.domain.EmoticonPrice;
import com.timelessname.server.domain.Message;

@Service
public class PricingService {

  @Resource
  Map<String, Emoticon> emoticonMap;

  @Resource
  List<String> emoticonList;

  protected Map<String, List<Long>> emoticonTimes = new HashMap<String, List<Long>>();

  protected Map<String, Map<String, List<Long>>> channelMemeTimes = new HashMap<String, Map<String, List<Long>>>();

  Gson gson = new Gson();

  List<EmoticonPrice> emoticonPrices;

  List<ChannelData> channelDatas;

  public List<EmoticonPrice> getPrices() {
    return emoticonPrices;
  }

  public List<ChannelData> getChannelDatas() {
    return channelDatas;
  }

  public void priceMessage(String json) {

    Type listType = new TypeToken<ArrayList<EmoticonPrice>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    emoticonPrices = gson.fromJson(json, listType);

  }

  public void channelMessage(String json) {

    Type listType = new TypeToken<ArrayList<ChannelData>>() {
      private static final long serialVersionUID = 1L;
    }.getType();
    channelDatas = gson.fromJson(json, listType);

  }

}
