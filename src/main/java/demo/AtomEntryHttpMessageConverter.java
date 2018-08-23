package demo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;

import org.jdom2.JDOMException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.io.impl.Atom10Generator;
import com.rometools.rome.io.impl.Atom10Parser;

import static demo.AtomMediaTypes.APPLICATION_ATOM_ENTRY_XML;

/**
 * Spring HTTP message converter for an Atom feed entry.
 */
public class AtomEntryHttpMessageConverter extends AbstractHttpMessageConverter<Entry>
{
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public AtomEntryHttpMessageConverter()
	{
		super(APPLICATION_ATOM_ENTRY_XML);
	}
	
	@Override
	protected boolean supports(Class<?> clazz)
	{
		return Entry.class.isAssignableFrom(clazz);
	}
	
	@Override
	protected Entry readInternal(Class<? extends Entry> clazz, HttpInputMessage inputMessage)
		throws IOException, HttpMessageNotReadableException
	{
		try
		{
			return Atom10Parser.parseEntry(new XmlReader(inputMessage.getBody()), null, Locale.US);
		}
		catch (FeedException | JDOMException exception)
		{
			throw new HttpMessageNotReadableException("Could not read Entry: " + exception.getMessage(), exception);
		}
	}
	
	@Override
	protected void writeInternal(Entry entry, HttpOutputMessage outputMessage)
		throws IOException, HttpMessageNotWritableException
	{
		String encoding = DEFAULT_CHARSET.name();
		
		MediaType contentType = outputMessage.getHeaders().getContentType();
		
		if (contentType != null)
		{
			Charset charset = Charset.forName(encoding);
			contentType = new MediaType(contentType, charset);
			outputMessage.getHeaders().setContentType(contentType);
		}
		
		try
		{
			Writer writer = new OutputStreamWriter(outputMessage.getBody(), encoding);
			Atom10Generator.serializeEntry(entry, writer);
		}
		catch (FeedException exception)
		{
			throw new HttpMessageNotWritableException("Could not write Entry: " + exception.getMessage(), exception);
		}
	}
}
