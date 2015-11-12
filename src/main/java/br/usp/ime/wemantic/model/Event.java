package br.usp.ime.wemantic.model;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private static final long serialVersionUID = -8188776792463135498L;

	private String name;
	private String address;
	private Date date;

	public Event() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", address=" + address + ", date=" + date + "]";
	}

}
