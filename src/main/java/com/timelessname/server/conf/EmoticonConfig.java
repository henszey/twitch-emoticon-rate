package com.timelessname.server.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.timelessname.server.domain.Emoticon;

@Configuration
public class EmoticonConfig {

  @Autowired
  Gson gson;

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Bean
  public Map<String, Emoticon> emoticonMap() throws Exception {

    Map<String, Emoticon> map = new HashMap();

    String json = IOUtils.toString(EmoticonConfig.class.getResourceAsStream("/public/emoticons.json"));

    Map<String, Object> data = gson.fromJson(json, Map.class);

    List<Map<String, Object>> emoticons = (List<Map<String, Object>>) data.get("emoticons");

    System.out.println(emoticons.size());

    for (Map<String, Object> emo : emoticons) {
      String regex = (String) emo.get("regex");

      List<Map<String, Object>> images = (List<Map<String, Object>>) emo.get("images");

      Map<String, Object> image = (Map<String, Object>) images.get(0);


        Emoticon emoticon = new Emoticon();
        emoticon.setRegex(regex);
        emoticon.setUrl((String) image.get("url"));

        map.put(emoticon.getRegex(), emoticon);

    }

    return map;
  }

  @Bean
  public List<String> emoticonList() throws Exception {
    Map<String, Emoticon> emoticonMap = emoticonMap();

    List<String> list = Lists.newArrayList();

    for (String key : emoticonMap.keySet()) {
      list.add(key.toLowerCase());
    }

    return list;
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

}
