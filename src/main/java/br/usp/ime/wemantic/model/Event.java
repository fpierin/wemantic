package br.usp.ime.wemantic.model;

import java.io.Serializable;

public class Event implements Serializable {

	private static final long serialVersionUID = -8188776792463135498L;

	private String name;

	public Event() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + "]";
	}

}
