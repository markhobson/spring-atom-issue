package demo;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AtomController
{
	@GetMapping(produces = "application/atom+xml;type=feed")
	public Feed getFeed()
	{
		return new Feed("atom_1.0");
	}
	
	@GetMapping(path = "/entry", produces = "application/atom+xml;type=entry")
	public Entry getEntry()
	{
		return new Entry();
	}
}
