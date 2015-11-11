package br.usp.ime.wemantic.client.usp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import br.usp.ime.wemantic.commons.HttpResponse;
import br.usp.ime.wemantic.commons.RestfullHttpClient;
import br.usp.ime.wemantic.model.Event;

public class UspEventService implements EventService {

	private final RestfullHttpClient restfullHttpClient;

	public UspEventService(final RestfullHttpClient restfullHttpClient) {
		this.restfullHttpClient = restfullHttpClient;
	}

	@Override
	public List<Event> retrieve() {
		final String html = retrieveHtml();
		return eventsInProgress(html);
	}

	private String retrieveHtml() {
		final HttpResponse httpResponse = restfullHttpClient.get("http://www.eventos.usp.br/");
		final String message = httpResponse.getMessage();
		return message;
	}

	private List<Event> eventsInProgress(final String html) {
		final String eventInProgressPattern = "<ul id=\"list-ev-anda\" class=\"front-eve\">(.*?)</ul>";
		final Pattern pattern = Pattern.compile(eventInProgressPattern, Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			final String node = matcher.group(1);
			return eventsFrom(node);
		}
		return null;
	}

	private List<Event> eventsFrom(final String html) {
		final List<Event> el = new ArrayList<>();
		final String eventInProgressPattern = "class=\"hoje-titulo\">(.*?)</a>";
		final Pattern pattern = Pattern.compile(eventInProgressPattern, Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			final Event event = new Event();
			event.setName(unescape(matcher.group(1)));
			el.add(event);
		}
		return el;
	}

	private String unescape(final String word) {
		return StringEscapeUtils.unescapeHtml3(word);
	}

	public static void main(final String[] args) {
		final String string = "&#8220;";
		final String unescape = StringEscapeUtils.unescapeHtml3(string);
		System.out.println(unescape);
	}

}
