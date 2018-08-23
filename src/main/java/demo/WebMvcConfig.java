package demo;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		// Workaround SPR-17040 by replacing default Atom feed message converter with one that can add 'type' parameter
		// to Content-Type header.
		converters.stream()
			.filter(converter -> converter instanceof AtomFeedHttpMessageConverter)
			.findFirst()
			.map(converters::remove);
		converters.add(new AtomFeedWithTypeHttpMessageConverter());
		
		// Add message converter for Atom feed entries (no issue raised yet)
		converters.add(new AtomEntryHttpMessageConverter());
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		// Workaround SPR-10903 by replacing default content negotiation strategy with one that honours Atom media
		// types. This fixes it for produces request conditions only.
		configurer.ignoreAcceptHeader(true)
			.defaultContentTypeStrategy(new AtomHeaderContentNegotiationStrategy());
	}
}
