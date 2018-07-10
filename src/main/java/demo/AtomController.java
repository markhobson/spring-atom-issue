package demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AtomController
{
	@GetMapping(produces = "application/atom+xml;type=feed")
	public String getFeed()
	{
		return "<feed/>";
	}
	
	@GetMapping(produces = "application/atom+xml;type=entry")
	public String getEntry()
	{
		return "<entry/>";
	}
}
