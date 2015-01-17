package main.java.shoppingsiteservices.service.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Type;

@Provider
@Produces("application/xml")
public class JAXBMarshaller implements MessageBodyWriter {
	protected Providers providers;

	@Override
	public long getSize(Object arg0, Class arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean isWriteable(Class type, Type genericType, Annotation annotations[], MediaType mediaType) {
		// TODO Auto-generated method stub
		return type.isAnnotationPresent(XmlRootElement.class);
	}

	@Context
	@Override
	public void writeTo(Object target, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream outputStream) throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		try {
			JAXBContext ctx = JAXBContext.newInstance(type);
			Marshaller m = ctx.createMarshaller();
			boolean pretty = false;
			for (Annotation ann : annotations) {
				if (ann.annotationType().equals(SECURE.class)) {
					pretty = true;
					break;
				}
			}
			if (pretty) {
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			}
			// USE OF THIS WILL BE ABLE TO DECODE ONLY SINGLE TYPE.
			// m.marshal(target, outputStream);

			ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
			if (resolver != null) {
				ctx = resolver.getContext(type);
			}
			if (ctx == null) {
				// create one ourselves
				ctx = JAXBContext.newInstance(type);
			}
			ctx.createMarshaller().marshal(target, outputStream);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}
}
