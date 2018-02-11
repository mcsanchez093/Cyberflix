package edu.txstate.cs3320;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import edu.txstate.cyberflix.data.db.Staff;
import edu.txstate.cyberflix.login.AuthenticationStaffService;

/**
 * Servlet implementation class CyberFlixStaffLogServlet
 */
@WebServlet("/CyberFlixStaffLogServlet")
public class CyberFlixStaffLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CyberFlixStaffLogServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		// getting the input from user
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// simple validation
		if ((email != null && !email.isEmpty()) && (password != null && !password.isEmpty())) {
			System.out.println("email and password are not null");

			// defining the object for AuthenticationService
			AuthenticationStaffService service = new AuthenticationStaffService();

			// validating the user input
			Staff staff = service.validateStaff(email, password);

			// dispatching the result based on the outcome
			if (staff != null) {
				System.out.println("Staff is not null");

				HttpSession session = request.getSession();

				RequestDispatcher dispatcher = request.getRequestDispatcher("staffSuccess.jsp");
				session.setAttribute("staff", staff);

				// setting user details object in session based on the valid outcome
				dispatcher.forward(request, response);

			}
			else {
				System.out.println("Staff is null");

				RequestDispatcher dispatcher = request.getRequestDispatcher("StaffLogin.jsp");

				// setting error message
				request.setAttribute("errorMessage", "you have given invalid email or password :( ");

				dispatcher.forward(request, response);

			}

		}
		else {
			System.out.println("email and password are null");
			RequestDispatcher dispatcher = request.getRequestDispatcher("StaffLogin.jsp");

			// setting error message
			request.setAttribute("errorMessage", "Email: *" + email + "* Password: *" + password + "*");

			dispatcher.forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
