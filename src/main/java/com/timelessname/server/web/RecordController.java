package com.timelessname.server.web;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timelessname.server.domain.ChannelRecordRepository;

@Controller
public class RecordController {


	@Autowired
	ChannelRecordRepository channelRecordRepository;
	
	@ResponseBody
	@RequestMapping("/api/channels/{channel}/{emote}")
	public Object records(@PathVariable String channel, @PathVariable String emote){
		return channelRecordRepository.findByChannelNameAndEmoteName(channel, emote);
	}
	
	@ResponseBody
	@RequestMapping("/api/emotes/{emote}")
	public Object recordsz(@PathVariable String emote){
		return channelRecordRepository.findTopChannelsByEmote(emote);
	}
	
	
}
