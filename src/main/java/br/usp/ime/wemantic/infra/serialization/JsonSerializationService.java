package br.usp.ime.wemantic.infra.serialization;

import com.google.gson.GsonBuilder;

public class JsonSerializationService implements SerializationService {

	@Override
	public String toJson(final Object object) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(object);
	}

}
