package com.timelessname.server.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

@Entity
public class Channel {
	
	@Id
	@Length(max=50)
	String name;
	
	@Column
	Date firstSeen;
	
	@Column
	Date lastSeen;
	
	@ElementCollection()
  @MapKeyColumn(length=50,name = "emoteName")
	@OneToMany(cascade=CascadeType.ALL , mappedBy="channelName")
	Map<String, ChannelRecord> records = new HashMap<String, ChannelRecord>();;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(Date firstSeen) {
		this.firstSeen = firstSeen;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	public Map<String, ChannelRecord> getRecords() {
		return records;
	}

	public void setRecords(Map<String, ChannelRecord> records) {
		this.records = records;
	}
	
	

}
