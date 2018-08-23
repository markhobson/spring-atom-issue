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
		// Replace default Atom feed message converter with one that can add 'type' parameter to Content-Type header
		converters.stream()
			.filter(converter -> converter instanceof AtomFeedHttpMessageConverter)
			.findFirst()
			.map(converters::remove);
		converters.add(new AtomFeedWithTypeHttpMessageConverter());
		
		// Add new Atom entry message converter
		converters.add(new AtomEntryHttpMessageConverter());
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		// Replace default content negotiation strategy with one that honours Atom media types
		configurer.ignoreAcceptHeader(true)
			.defaultContentTypeStrategy(new AtomHeaderContentNegotiationStrategy());
	}
}
