package br.usp.ime.wemantic.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.ime.wemantic.client.usp.EventService;
import br.usp.ime.wemantic.client.usp.UspEventService;
import br.usp.ime.wemantic.commons.RestfullHttpClient;
import br.usp.ime.wemantic.commons.RestfullHttpClientImpl;
import br.usp.ime.wemantic.infra.serialization.JsonSerializationService;
import br.usp.ime.wemantic.infra.serialization.SerializationService;
import br.usp.ime.wemantic.model.Event;

public class EventServlet extends HttpServlet {

	private static final long serialVersionUID = -2874602184469205527L;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final RestfullHttpClient restfullHttpClient = new RestfullHttpClientImpl();
		final EventService es = new UspEventService(restfullHttpClient);
		final List<Event> et = es.retrieve();

		final SerializationService ss = new JsonSerializationService();
		final String json = ss.toJson(et);

		response.setContentType("application/json; charset=UTF-8");
		final PrintWriter printout = response.getWriter();
		printout.print(json);
		printout.flush();
	}

}