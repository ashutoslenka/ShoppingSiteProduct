package main.java.shoppingsiteservices.service.Login;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.shoppingsiteservices.service.Login.dto.CustomerDetail;
import main.java.shoppingsiteservices.service.resources.SECURE;

import org.codehaus.jettison.json.JSONObject;

//Declare roles is optional roles can also be controlled by the ibm-ext-bnd.xml 
//Should be defined to allow specific roles to method
@DeclareRoles({ "friend", "User" })
@Path("/LoginService")
public class LoginControllerImpl extends LoginController {

	public boolean readAuthenticationData(String strName) {
		return true;
	}

	@RolesAllowed("User")
	@GET
	@Path("/VerifyUser")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	public String verifyClient(String inputString) {
		System.out.println("Enter LoginControllerImpl.login " + inputString);
		String strJSONString = "";
		try {
			JSONObject inputJson = new JSONObject(inputString);

			System.out.println("JSONArray == " + inputJson);

			strJSONString = new JSONObject().put("Message", "Record Sucessfully Persisted").toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Exit BancsServiceController.login " + strJSONString);
		return strJSONString;
	}

	@Path("/GetUserDetails/{id}")
	@Produces("application/xml")
	@SECURE
	public CustomerDetail getCustomerDetail(@PathParam("id") int id) {
		CustomerDetail customer = new CustomerDetail();
		customer.setId(id);
		customer.setFirstName("ShoppingUser");
		return customer;
	}
}
