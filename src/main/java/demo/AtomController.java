package demo;

import java.net.URI;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping(consumes = "application/atom+xml;type=entry", produces = "application/atom+xml;type=entry")
	public ResponseEntity<Entry> postEntry(Entry entry)
	{
		return ResponseEntity.created(URI.create("/entry"))
			.body(entry);
	}
	
	@PostMapping(consumes = "application/atom+xml;type=feed", produces = "application/atom+xml;type=feed")
	public ResponseEntity<Feed> addFeed(Feed feed)
	{
		return ResponseEntity.created(URI.create("/"))
			.body(feed);
	}
}
