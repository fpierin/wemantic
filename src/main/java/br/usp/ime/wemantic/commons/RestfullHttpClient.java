package br.usp.ime.wemantic.commons;

public interface RestfullHttpClient {

	HttpResponse post(String endpoint, String body);

	HttpResponse get(String endpoint);

	void setEncoding(String encoding);

	void setContentType(String contentType);

}
