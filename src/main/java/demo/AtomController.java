package demo;

import java.net.URI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

import static demo.AtomMediaTypes.APPLICATION_ATOM_ENTRY_XML_VALUE;
import static demo.AtomMediaTypes.APPLICATION_ATOM_FEED_XML_VALUE;

@SpringBootApplication
@RestController
public class AtomController
{
	@GetMapping(produces = APPLICATION_ATOM_FEED_XML_VALUE)
	public Feed getFeed()
	{
		return new Feed("atom_1.0");
	}
	
	@GetMapping(path = "/entry", produces = APPLICATION_ATOM_ENTRY_XML_VALUE)
	public Entry getEntry()
	{
		return new Entry();
	}
	
	@PostMapping(consumes = APPLICATION_ATOM_ENTRY_XML_VALUE, produces = APPLICATION_ATOM_ENTRY_XML_VALUE)
	public ResponseEntity<Entry> postEntry(Entry entry)
	{
		return ResponseEntity.created(URI.create("/entry"))
			.body(entry);
	}
	
	@PostMapping(consumes = APPLICATION_ATOM_FEED_XML_VALUE, produces = APPLICATION_ATOM_FEED_XML_VALUE)
	public ResponseEntity<Feed> addFeed(Feed feed)
	{
		return ResponseEntity.created(URI.create("/"))
			.body(feed);
	}
}
