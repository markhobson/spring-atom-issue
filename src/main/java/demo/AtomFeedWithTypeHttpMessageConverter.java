package demo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.util.StringUtils;

import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;

import static java.util.Collections.singletonList;

import static demo.AtomMediaTypes.APPLICATION_ATOM_FEED_XML;

/**
 * Spring HTTP message converter for an Atom feed.
 */
public class AtomFeedWithTypeHttpMessageConverter extends AtomFeedHttpMessageConverter
{
	public AtomFeedWithTypeHttpMessageConverter()
	{
		setSupportedMediaTypes(singletonList(APPLICATION_ATOM_FEED_XML));
	}

	// Workaround: https://jira.spring.io/browse/SPR-17040
	// modified from AbstractWireFeedHttpMessageConverter.writeInternal
	@Override
	protected void writeInternal(Feed wireFeed, HttpOutputMessage outputMessage) throws IOException,
		HttpMessageNotWritableException
	{
		String wireFeedEncoding = wireFeed.getEncoding();
		
		if (!StringUtils.hasLength(wireFeedEncoding))
		{
			wireFeedEncoding = DEFAULT_CHARSET.name();
		}
		
		MediaType contentType = outputMessage.getHeaders().getContentType();
		
		if (contentType != null)
		{
			Charset wireFeedCharset = Charset.forName(wireFeedEncoding);
//			contentType = new MediaType(contentType.getType(), contentType.getSubtype(), wireFeedCharset);
			contentType = new MediaType(contentType, wireFeedCharset);
			outputMessage.getHeaders().setContentType(contentType);
		}

		WireFeedOutput feedOutput = new WireFeedOutput();
		try
		{
			Writer writer = new OutputStreamWriter(outputMessage.getBody(), wireFeedEncoding);
			feedOutput.output(wireFeed, writer);
		}
		catch (FeedException ex)
		{
			throw new HttpMessageNotWritableException("Could not write WireFeed: " + ex.getMessage(), ex);
		}
	}
}
