package demo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Demonstrates the various use-cases when using Atom media types.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class AtomControllerTest
{
	@Autowired
	private MockMvc mvc;
	
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
	
	@Test
	public void canPostEntry() throws Exception
	{
		mvc.perform(post("/").contentType("application/atom+xml;type=entry")
				.content("<entry xmlns=\"http://www.w3.org/2005/Atom\"/>")
			)
			.andExpect(status().isCreated())
			.andExpect(content().xml("<entry xmlns=\"http://www.w3.org/2005/Atom\"/>"));
	}
	
	// Currently fails during content negotiation since there's no easy way to configure ConsumesRequestCondition to use
	// Atom media types.
	@Ignore
	@Test
	public void canAddFeed() throws Exception
	{
		mvc.perform(post("/").contentType("application/atom+xml;type=feed")
				.content("<feed xmlns=\"http://www.w3.org/2005/Atom\"/>")
			)
			.andExpect(status().isCreated())
			.andExpect(content().xml("<feed xmlns=\"http://www.w3.org/2005/Atom\"/>"));
	}
}
