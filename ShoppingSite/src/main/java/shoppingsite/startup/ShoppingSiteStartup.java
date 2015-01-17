package main.java.shoppingsite.startup;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Servlet implementation class ShoppingSiteStartup
 */
@WebServlet(description = "Load the resources at startup", urlPatterns = { "/ShoppingSiteStartup" })
public class ShoppingSiteStartup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Properties prop;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShoppingSiteStartup() {
		super();
		// TODO Auto-generated constructor stub

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		String args = System.getProperty("ShoppingSite.property");

		initialize(args);
	}

	protected static void initialize(String args) {
		try {
			prop = new Properties();
			FileInputStream file = new FileInputStream(args);
			prop.load(file);
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String name) {
		return prop.getProperty(name);
	}

	public static void setProperty(String name, String value) {
		prop.setProperty(name, value);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
