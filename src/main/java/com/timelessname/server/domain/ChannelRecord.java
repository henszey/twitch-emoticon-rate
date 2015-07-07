package com.timelessname.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.Length;

@Entity
public class ChannelRecord {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	@Column
	@Length(max=50)
	String channelName;
	
	@Column
	@Length(max=50)
	String emoteName;
	
	@Column
	int emoteCount;
	
	@Column
	Date date;

	
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getEmoteName() {
		return emoteName;
	}

	public void setEmoteName(String emoteName) {
		this.emoteName = emoteName;
	}

	public int getEmoteCount() {
		return emoteCount;
	}

	public void setEmoteCount(int emoteCount) {
		this.emoteCount = emoteCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	
	
	
}
