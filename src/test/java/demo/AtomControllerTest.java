package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
			.andExpect(content().string("<feed/>"));
	}
}
