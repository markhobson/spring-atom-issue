package demo;

import org.springframework.http.MediaType;

import static java.util.Collections.singletonMap;

/**
 * Media types for Atom documents that honour the type parameter.
 *
 * @see <a href="https://tools.ietf.org/html/rfc5023#section-12">The Atom Publishing Protocol</a>
 */
public final class AtomMediaTypes
{
	private static class AtomMediaType extends MediaType
	{
		private static final long serialVersionUID = 4509221754183311927L;
		
		private static final String TYPE_PARAMETER = "type";
		
		AtomMediaType(String typeParameter)
		{
			super("application", "atom+xml", singletonMap(TYPE_PARAMETER, typeParameter));
		}
		
		@Override
		public boolean includes(MediaType other)
		{
			if (getType().equals(other.getType())
				&& getSubtype().equals(other.getSubtype())
				&& getParameters().containsKey(TYPE_PARAMETER))
			{
				String type = getParameter(TYPE_PARAMETER);
				String otherType = other.getParameter(TYPE_PARAMETER);
				
				return type.equals(otherType);
			}
			
			return super.includes(other);
		}
		
		@Override
		public boolean isCompatibleWith(MediaType other)
		{
			if (getType().equals(other.getType())
				&& getSubtype().equals(other.getSubtype())
				&& getParameters().containsKey(TYPE_PARAMETER)
				&& other.getParameters().containsKey(TYPE_PARAMETER))
			{
				String type = getParameter(TYPE_PARAMETER);
				String otherType = other.getParameter(TYPE_PARAMETER);
				
				return type.equals(otherType);
			}
			
			return super.isCompatibleWith(other);
		}
	}
	
	public static final String APPLICATION_ATOM_FEED_XML_VALUE = "application/atom+xml;type=feed";
	
	public static final MediaType APPLICATION_ATOM_FEED_XML = new AtomMediaType("feed");
	
	public static final String APPLICATION_ATOM_ENTRY_XML_VALUE = "application/atom+xml;type=entry";
	
	public static final MediaType APPLICATION_ATOM_ENTRY_XML = new AtomMediaType("entry");
	
	private AtomMediaTypes()
	{
		throw new AssertionError();
	}
	
	public static MediaType parseMediaType(String mediaType)
	{
		if (APPLICATION_ATOM_FEED_XML_VALUE.equals(mediaType))
		{
			return APPLICATION_ATOM_FEED_XML;
		}
		
		if (APPLICATION_ATOM_ENTRY_XML_VALUE.equals(mediaType))
		{
			return APPLICATION_ATOM_ENTRY_XML;
		}
		
		return MediaType.parseMediaType(mediaType);
	}
}
