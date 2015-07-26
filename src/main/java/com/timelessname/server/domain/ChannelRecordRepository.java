package com.timelessname.server.domain;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRecordRepository extends CrudRepository<ChannelRecord, Long>  {


  @Cacheable("findByChannelNameAndEmoteName")
	ChannelRecord findByChannelNameAndEmoteName(String channelName, String emoteName);
	

  @Cacheable("findTopChannelByEmote")
	@Query(value = "select * from channel_record where emote_name = ?1 order by emote_count desc limit 1", nativeQuery = true)
	ChannelRecord findTopChannelByEmote(String emote);
  
  
  @Cacheable("findTopChannelsByEmote")
	@Query(value = "select * from channel_record where emote_name = ?1 order by emote_count desc limit 10", nativeQuery = true)
	List<ChannelRecord> findTopChannelsByEmote(String emote);
 
  
  
}
