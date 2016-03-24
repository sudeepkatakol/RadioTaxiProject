
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Row;

/**
 * Servlet implementation class displayQueuedRides
 */
@WebServlet("/displayQueuedRides")
public class DisplayQueuedRidesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayQueuedRidesServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if(session.getAttribute("type") != null) {
			if(session.getAttribute("type").toString().compareTo("driver") != 0) {
				response.sendRedirect("error.jsp");
			} else {
				response.setContentType("text/html");
				//PrintWriter out = response.getWriter();

				DisplayQueuedRides rides = new DisplayQueuedRides();
				List<Row> results = rides.getRides();

				request.setAttribute("results", results);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/rideQueue.jsp");
				rd.forward(request, response);
			}
		} else {
			response.sendRedirect("index.html");
		}
	}
}
