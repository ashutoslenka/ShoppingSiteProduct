package main.java.shoppingsiteservices.service.Login;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import main.java.shoppingsiteservices.service.resources.JAXBMarshaller;

// This class doesn't need to be annoted as this is to be extended by some other class.
public abstract class LoginController extends JAXBMarshaller {

	abstract protected boolean readAuthenticationData(String strName);
}