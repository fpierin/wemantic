package br.usp.ime.wemantic.guestbook;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventServlet extends HttpServlet {

	private static final long serialVersionUID = -2874602184469205527L;

	@Override
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		// final RestfullHttpClient restfullHttpClient = new
		// RestfullHttpClientImpl();
		// final HttpResponse httpResponse =
		// restfullHttpClient.get("http://www.eventos.usp.br/");

		resp.setContentType("text/plain");
		resp.getWriter().println(getExample());
		// resp.getWriter().println(httpResponse.getMessage());
	}

	private String getExample() {
		return "    <div vocab=\"http://schema.org/\" typeof=\"MusicEvent\">\n" + "      <div property=\"location\" typeof=\"MusicVenue\">\n" + "        <meta property=\"name\" content=\"Chicago Symphony Center\"/>\n"
				+ "        <link property=\"sameAs\" href=\"http://en.wikipedia.org/wiki/Symphony_Center\"/>\n" + "        <meta property=\"address\" content=\"220 S. Michigan Ave, Chicago, Illinois, USA\"/>\n" + "      </div>\n"
				+ "      <div property=\"offers\" typeof=\"Offer\">\n" + "        <link property=\"url\" href=\"/examples/ticket/12341234\"/>\n" + "        <meta property=\"priceCurrency\" content=\"USD\" />$\n"
				+ "        <meta property=\"price\" content=\"40\"/>40.00\n" + "        <link property=\"availability\" href=\"http://schema.org/InStock\"/>\n" + "      </div>\n" + "      <h2 property=\"name\">Shostakovich Leningrad</h2>\n"
				+ "      <div>\n" + "        <div property=\"startDate\" content=\"2014-05-23T20:00\">May<span>23</span></div>\n" + "        <div>8:00 PM</div>\n" + "        <div>\n" + "          <strong>Britten, Shostakovich</strong>\n"
				+ "        </div>\n" + "      </div>\n" + "      <div>\n" + "        <p>Jaap van Zweden conducts two World War II-era pieces showcasing the glorious sound of the CSO.</p>\n" + "      </div>\n" + "      <div>\n"
				+ "        <h3>Program</h3>\n" + "        <ul>\n" + "          <li property=\"workPerformed\" typeof=\"CreativeWork\">\n" + "            <link href=\"http://en.wikipedia.org/wiki/Peter_Grimes\" property=\"sameAs\"/>\n"
				+ "            <span property=\"name\"><strong>Britten</strong> Four Sea Interludes and Passacaglia from <em property=\"name\">Peter Grimes</em></span>\n" + "      </li>\n"
				+ "          <li property=\"workPerformed\" typeof=\"CreativeWork\">\n" + "          <link href=\"http://en.wikipedia.org/wiki/Symphony_No._7_(Shostakovich)\" property=\"sameAs\"/>\n"
				+ "          <span property=\"name\"><strong>Shostakovich</strong> Symphony No. 7 <em>(Leningrad)</em></span>\n" + "      </li>\n" + "        </ul>\n" + "      </div>\n" + "      <div>\n" + "        <h3>Performers</h3>\n"
				+ "        <div property=\"performer\" typeof=\"MusicGroup\">\n" + "          <img src=\"/examples/cso_c_logo_s.jpg\" alt=\"Chicago Symphony Orchestra\"/>\n"
				+ "          <link href=\"http://cso.org/\" property=\"sameAs\"/>\n" + "          <link href=\"http://en.wikipedia.org/wiki/Chicago_Symphony_Orchestra\" property=\"sameAs\"/>\n"
				+ "          <a property=\"name\" href=\"examples/Performer?id=4434\">Chicago Symphony Orchestra</a>\n" + "        </div>\n" + "        <div property=\"performer\" typeof=\"Person\">\n"
				+ "          <link href=\"http://www.jaapvanzweden.com/\" property=\"sameAs\"/>\n" + "          <img src=\"/examples/jvanzweden_s.jpg\" alt=\"Jaap van Zweden\" property=\"image\"/>\n"
				+ "          <a property=\"name\" href=\"/examples/Performer.aspx?id=11324\">Jaap van Zweden</a>\n" + "          <div>conductor</div>\n" + "        </div>\n" + "      </div>\n" + "    </div>";
	}
}