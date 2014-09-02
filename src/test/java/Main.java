import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timelessname.server.conf.EmoticonConfig;
import com.timelessname.server.domain.Emoticon;


public class Main {

  
  
  public static void main(String[] args) throws Exception {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    String json = IOUtils.toString(EmoticonConfig.class.getResourceAsStream("/emoticons.json"));

    Map<String, Object> data = gson.fromJson(json, Map.class);

    List<Map<String, Object>> emoticons = (List<Map<String, Object>>) data.get("emoticons");

    //System.out.println(emoticons.size());

    //for (Map<String, Object> emo : emoticons) {
    for (Iterator iterator = emoticons.iterator(); iterator.hasNext();) {
      Map<String, Object> emo = (Map<String, Object>) iterator.next();
     
    
      String regex = (String) emo.get("regex");

      List<Map<String, Object>> images = (List<Map<String, Object>>) emo.get("images");

      Map<String, Object> image = (Map<String, Object>) images.get(0);

      if (image.get("emoticon_set") == null && regex.matches("[a-zA-Z0-9]+")) {

      } else {
        iterator.remove();
      }
      
      

    }

    //System.out.println(emoticons.size());
    
    System.out.println(gson.toJson(data));
    
    
  }
  
}
