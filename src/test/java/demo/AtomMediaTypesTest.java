package demo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import static demo.AtomMediaTypes.APPLICATION_ATOM_ENTRY_XML;
import static demo.AtomMediaTypes.APPLICATION_ATOM_FEED_XML;
import static demo.AtomMediaTypes.parseMediaType;

public class AtomMediaTypesTest
{
	@Test
	public void atomIncludesAtom()
	{
		assertThat(APPLICATION_ATOM_XML.includes(APPLICATION_ATOM_XML), is(true));
	}
	
	@Test
	public void atomIncludesAtomFeed()
	{
		assertThat(APPLICATION_ATOM_XML.includes(APPLICATION_ATOM_FEED_XML), is(true));
	}
	
	@Test
	public void atomIncludesAtomEntry()
	{
		assertThat(APPLICATION_ATOM_XML.includes(APPLICATION_ATOM_ENTRY_XML), is(true));
	}
	
	@Test
	public void atomFeedDoesNotIncludeAtom()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.includes(APPLICATION_ATOM_XML), is(false));
	}
	
	@Test
	public void atomFeedIncludesAtomFeed()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.includes(APPLICATION_ATOM_FEED_XML), is(true));
	}
	
	@Test
	public void atomFeedDoesNotIncludeAtomEntry()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.includes(APPLICATION_ATOM_ENTRY_XML), is(false));
	}
	
	@Test
	public void atomEntryDoesNotIncludeAtom()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.includes(APPLICATION_ATOM_XML), is(false));
	}
	
	@Test
	public void atomEntryIncludesAtomEntry()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.includes(APPLICATION_ATOM_ENTRY_XML), is(true));
	}
	
	@Test
	public void atomEntryDoesNotIncludeAtomFeed()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.includes(APPLICATION_ATOM_FEED_XML), is(false));
	}

	@Test
	public void atomIsCompatibleWithAtom()
	{
		assertThat(APPLICATION_ATOM_XML.isCompatibleWith(APPLICATION_ATOM_XML), is(true));
	}
	
	@Test
	public void atomIsCompatibleWithAtomFeed()
	{
		assertThat(APPLICATION_ATOM_XML.isCompatibleWith(APPLICATION_ATOM_FEED_XML), is(true));
	}
	
	@Test
	public void atomIsCompatibleWithAtomEntry()
	{
		assertThat(APPLICATION_ATOM_XML.isCompatibleWith(APPLICATION_ATOM_ENTRY_XML), is(true));
	}
	
	@Test
	public void atomFeedIsCompatibleWithAtom()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.isCompatibleWith(APPLICATION_ATOM_XML), is(true));
	}
	
	@Test
	public void atomFeedIsCompatibleWithAtomFeed()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.isCompatibleWith(APPLICATION_ATOM_FEED_XML), is(true));
	}
	
	@Test
	public void atomFeedIsNotCompatibleWithAtomEntry()
	{
		assertThat(APPLICATION_ATOM_FEED_XML.isCompatibleWith(APPLICATION_ATOM_ENTRY_XML), is(false));
	}
	
	@Test
	public void atomEntryIsCompatibleWithAtom()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.isCompatibleWith(APPLICATION_ATOM_XML), is(true));
	}
	
	@Test
	public void atomEntryIsCompatibleWithAtomEntry()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.isCompatibleWith(APPLICATION_ATOM_ENTRY_XML), is(true));
	}
	
	@Test
	public void atomEntryIsNotCompatibleWithAtomFeed()
	{
		assertThat(APPLICATION_ATOM_ENTRY_XML.isCompatibleWith(APPLICATION_ATOM_FEED_XML), is(false));
	}
	
	@Test
	public void canParseAtomFeedMediaType()
	{
		assertThat(parseMediaType("application/atom+xml;type=feed"), is(sameInstance(APPLICATION_ATOM_FEED_XML)));
	}
	
	@Test
	public void canParseAtomEntryMediaType()
	{
		assertThat(parseMediaType("application/atom+xml;type=entry"), is(sameInstance(APPLICATION_ATOM_ENTRY_XML)));
	}
	
	@Test
	public void canParseOtherMediaType()
	{
		assertThat(parseMediaType("text/plain"), is(TEXT_PLAIN));
	}
}
