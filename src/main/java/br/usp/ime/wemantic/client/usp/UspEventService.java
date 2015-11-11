package br.usp.ime.wemantic.client.usp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

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
		final Set<String> eventsUris = eventsUrisFromHtml(html);

		final ExecutorService executor = Executors.newFixedThreadPool(50);
		final ListeningExecutorService les = MoreExecutors.listeningDecorator(executor);

		final List<Callable<Event>> eventCalls = createEventCalls(eventsUris);
		final List<Event> resultsFuture = new ArrayList<>();
		try {
			final List<Future<Event>> invokeAll = les.invokeAll(eventCalls);
			for (final Future<Event> future : invokeAll) {
				try {
					resultsFuture.add(future.get());
				} catch (final ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
		}

		executor.shutdown();
		return resultsFuture;
	}

	private List<Callable<Event>> createEventCalls(final Set<String> eventsUris) {
		final List<Callable<Event>> cList = new ArrayList<>();
		for (final String eventUri : eventsUris) {
			cList.add(new Callable<Event>() {
				@Override
				public Event call() throws Exception {
					return eventFromURI(eventUri);
				}

			});
		}
		return cList;
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
		final String eventHtml = extractHtmlFromURI(uri);
		final String titlePattern = "<div class=\"evento-titulo\" style=\"width: 470px;\">(.*?)</div>";
		final Pattern pattern = Pattern.compile(titlePattern, Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(eventHtml);
		if (matcher.find()) {
			final Event e = new Event();
			final String title = matcher.group(1);
			final String name = StringEscapeUtils.unescapeHtml3(title);
			e.setName(name);
			System.out.println(e);
			return e;
		}
		return null;
	}

	public static void main(final String[] args) {
		final RestfullHttpClient restfullHttpClient = new RestfullHttpClientImpl();
		final EventService es = new UspEventService(restfullHttpClient);
		final List<Event> et = es.retrieve();
		System.out.println(et);
	}

}
