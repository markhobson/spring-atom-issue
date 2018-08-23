package demo;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

import static java.util.stream.Collectors.toList;

/**
 * Content negotiation strategy that uses the {@code Accept} header and resolves Atom media types.
 */
public class AtomHeaderContentNegotiationStrategy extends HeaderContentNegotiationStrategy
{
	@Override
	public List<MediaType> resolveMediaTypes(NativeWebRequest request) throws HttpMediaTypeNotAcceptableException
	{
		return super.resolveMediaTypes(request)
			.stream()
			.map(MediaType::toString)
			.map(AtomMediaTypes::parseMediaType)
			.collect(toList());
	}
}
