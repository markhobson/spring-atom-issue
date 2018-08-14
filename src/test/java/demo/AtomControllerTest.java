package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class AtomControllerTest
{
	@Autowired
	private MockMvc mvc;
	
	/**
	 * Demonstrates that the Atom media type parameter is ignored during content negotiation.
	 *
	 * @see <a href="https://jira.spring.io/browse/SPR-10903">SPR-10903</a>
	 */
	@Test
	public void canGetFeed() throws Exception
	{
		mvc.perform(get("/").accept("application/atom+xml;type=feed"))
			.andExpect(header().string("Content-Type", "application/atom+xml;type=feed;charset=UTF-8"))
			.andExpect(content().xml("<feed xmlns=\"http://www.w3.org/2005/Atom\"/>"));
	}
	
	@Test
	public void cannotGetFeedAsEntry() throws Exception
	{
		mvc.perform(get("/").accept("application/atom+xml;type=entry"))
			.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void canGetEntry() throws Exception
	{
		mvc.perform(get("/entry").accept("application/atom+xml;type=entry"))
			.andExpect(header().string("Content-Type", "application/atom+xml;type=entry;charset=UTF-8"))
			.andExpect(content().xml("<entry xmlns=\"http://www.w3.org/2005/Atom\"/>"));
	}
	
	@Test
	public void cannotGetEntryAsFeed() throws Exception
	{
		mvc.perform(get("/entry").accept("application/atom+xml;type=feed"))
			.andExpect(status().isNotAcceptable());
	}
}
