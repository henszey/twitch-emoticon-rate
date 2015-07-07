package com.timelessname.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timelessname.server.domain.Channel;
import com.timelessname.server.domain.ChannelRecord;
import com.timelessname.server.domain.ChannelRecordRepository;
import com.timelessname.server.domain.ChannelRepository;
import com.timelessname.server.domain.ChannelStats;

@Service
@Transactional
public class RecordService {

	@Autowired
	ChannelRepository channelRepository;

	@Autowired
	ChannelRecordRepository channelRecordRepository;

	static long lastRun = System.currentTimeMillis();

	public void processChannelStats(List<ChannelStats> stats) {

		if (System.currentTimeMillis() - lastRun > 1000) {

			long time = System.currentTimeMillis();
			for (ChannelStats channelStat : stats) {
				Channel channel = channelRepository.findOne(channelStat.getChannel());

				if (channel == null) {
					channel = new Channel();
					channel.setName(channelStat.getChannel());
					channel.setFirstSeen(new Date());
					channel.setRecords(new HashMap<String, ChannelRecord>());
				}
				channel.setLastSeen(new Date());

				Map<String, ChannelRecord> records = channel.getRecords();

				ChannelRecord record = records.get(channelStat.getTopEmote());
				if (record == null) {
					record = new ChannelRecord();
					record.setChannelName(channelStat.getChannel());
					record.setEmoteName(channelStat.getTopEmote());
					records.put(channelStat.getTopEmote(), record);
				}

				if (channelStat.getTopEmoteCount() > record.getEmoteCount()) {
					record.setEmoteCount(channelStat.getTopEmoteCount());
					record.setDate(new Date());
					channelRepository.save(channel);
					System.out.println(channel.getName());
				}
			}
			System.out.println(System.currentTimeMillis() - time);
			lastRun = System.currentTimeMillis();
		}

	}

}
