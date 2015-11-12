package br.usp.ime.wemantic.client.usp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import br.usp.ime.wemantic.commons.HttpResponse;
import br.usp.ime.wemantic.commons.RestfullHttpClient;
import br.usp.ime.wemantic.commons.RestfullHttpClientImpl;
import br.usp.ime.wemantic.model.Event;

public class UspEventService implements EventService {

	private final RestfullHttpClient restfullHttpClient;

	public UspEventService(final RestfullHttpClient restfullHttpClient) {
		this.restfullHttpClient = restfullHttpClient;
	}

	@Override
	public List<Event> retrieve() {
		final String uri = "http://www.eventos.usp.br/";
		final String html = extractHtmlFromURI(uri);
		final List<Event> resultsFuture = new ArrayList<>();
		final Set<String> eventsUris = eventsUrisFromHtml(html);

		int i = 0;
		for (final String eventUri : eventsUris) {
			i++;
			resultsFuture.add(eventFromURI(eventUri));
			if (i == 10) {
				break;
			}
		}
		return resultsFuture;
	}

	private String extractHtmlFromURI(final String uri) {
		final HttpResponse httpResponse = restfullHttpClient.get(uri);
		final String message = httpResponse.getMessage();
		return message;
	}

	private Set<String> eventsUrisFromHtml(final String html) {
		final Set<String> eventUris = new HashSet<String>();
		final String eventInProgressPattern = "href=\"(http://www.eventos.usp.br/\\?events=(.*?))\"";
		final Pattern pattern = Pattern.compile(eventInProgressPattern);
		final Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			final String node = matcher.group(1);
			eventUris.add(node);
		}
		return eventUris;
	}

	private Event eventFromURI(final String uri) {
		final String html = extractHtmlFromURI(uri);
		final String unescapedHtml = StringEscapeUtils.unescapeHtml3(html);
		final String nameRegex = "<div class=\"evento-titulo\" style=\"width: 470px;\">(.*?)</div>";
		final String addressRegex = "var endereco = \\'(.*?)\\'";
		final String dateRegex = "<td>([\\d]{2}/[\\d]{2}/[\\d]{2}) ";

		final Event e = new Event();
		e.setName(regexFromHtml(unescapedHtml, nameRegex));
		e.setAddress(regexFromHtml(unescapedHtml, addressRegex));
		e.setDate(dateFrom(regexFromHtml(unescapedHtml, dateRegex), "dd/MM/yy"));
		return e;
	}

	private Date dateFrom(final String word, final String pattern) {
		final DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(word);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	private String regexFromHtml(final String html, final String regex) {
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			final String group = matcher.group(1);
			if (StringUtils.isNotEmpty(group)) {
				return group.trim();
			}
		}
		return null;
	}

	public static void main(final String[] args) {
		final RestfullHttpClient restfullHttpClient = new RestfullHttpClientImpl();
		final EventService es = new UspEventService(restfullHttpClient);
		final List<Event> et = es.retrieve();
		System.out.println();
		System.out.println(et);
	}

}
