package br.usp.ime.wemantic.commons;

import java.io.Serializable;

public class HttpResponse implements Serializable {

	private static final long serialVersionUID = -7137686405843917330L;

	private final int code;
	private final String message;

	public HttpResponse(final int code, final String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "HttpResponse [code=" + code + ", message=" + message + "]";
	}

}