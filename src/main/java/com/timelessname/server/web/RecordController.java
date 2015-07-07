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
	@RequestMapping("/api/records/{channel}/{emote}")
	public Object records(@PathVariable String channel, @PathVariable String emote){
		System.out.println(channel);
		System.out.println(emote);
		System.out.println(channelRecordRepository.findByChannelNameAndEmoteName(channel, emote));
		return channelRecordRepository.findByChannelNameAndEmoteName(channel, emote);
	}
	
}
