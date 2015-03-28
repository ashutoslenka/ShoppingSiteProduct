package main.shoppingsite.buyinguser.model;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterBuyingUserServlet
 */
@WebServlet("/RegisterBuyingUserServlet")
public class RegisterBuyingUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String userName;
	private String password;
	private String name;
	private String phone;
	private String street;
	private String city;
	private String country;
	private String website;
	private String gender;
	private String birthDate;
	private String nationality;
	private int isHavingChildren;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterBuyingUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		
		userName = request.getParameter("Email");
		password = request.getParameter("Password");
		
		System.out.print(userName);
		System.out.print(password);
	}

}
