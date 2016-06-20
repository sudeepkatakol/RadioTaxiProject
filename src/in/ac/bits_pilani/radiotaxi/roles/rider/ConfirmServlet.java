package in.ac.bits_pilani.radiotaxi.roles.rider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.ac.bits_pilani.radiotaxi.CabType;

/**
 * Servlet implementation class ConfirmServlet
 */
@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String rider,origin,dest,distance, duration; float[] originCoord, destCoord; 
    private static CabType cab;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null) {
			PrintWriter out = response.getWriter();
			Booking b = new Booking();
			try {
				b.bookTrip(rider, origin, dest, cab, distance, duration, originCoord, destCoord); // add driver later when confirmed
			} catch(Exception e) {
				request.getRequestDispatcher("html/error.html").include(request, response);
				out.println("Database error");
			}
			request.getRequestDispatcher("html/html-top-common.html").include(request, response);
			request.getRequestDispatcher("html/riderbookings-layout-1.html").include(request, response);
			request.getRequestDispatcher("html/html-bottom-common.html").include(request, response);
		}
		else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null) {
			PrintWriter out = response.getWriter();
			rider = (String) session.getAttribute("user");
			origin = request.getParameter("origin");
			dest = request.getParameter("dest");
			distance = request.getParameter("distance");
			duration = request.getParameter("time");
			try{
				float[] testoriginCoord = {Float.parseFloat(request.getParameter("orig_lat")),
						Float.parseFloat(request.getParameter("orig_lng"))};
				float[] testdestCoord = {Float.parseFloat(request.getParameter("dest_lat")),
						Float.parseFloat(request.getParameter("dest_lng"))};
				originCoord=testoriginCoord; destCoord=testdestCoord;
				}catch(Exception e){
						out.println("<p><center><font color=red>Origin and Destination couldn't be gathered. Please try again<center></font></p>");
						request.getRequestDispatcher("/book").forward(request,response);
					}
			String cabtype=request.getParameter("cabtype");
			switch(cabtype){
			case "regular": cab=CabType.Regular; break;
			case "extended":cab=CabType.Extended; break;
			case "double": cab=CabType.Double; break;
			default: break;
			}
			response.setContentType("text/html");
			out.println("<html>");
			out.println("<body>");
			out.println("<div class=\"row\">");
		    out.println("<div class=\"col s12\">");
		    out.println("Origin: "+origin);
		    out.println("</div>");
		    out.println("</div>");
			out.println("<div class=\"row\">");
		    out.println("<div class=\"col s12\">");
		    out.println("Destination: "+dest);
		    out.println("</div>");
		    out.println("</div>");
		    out.println("<div class=\"row\">");
		    out.println("<div class=\"col s12\">");
			out.println("Cab type: "+cab.toString());
		    out.println("</div>");
		    out.println("</div>");
		    out.println("<p>Display Rate Card and Estimated Fare here</p>");
		    out.println("</body>");
			out.println("</html>");
			request.getRequestDispatcher("html/html-top-common.html").include(request, response);
			request.getRequestDispatcher("html/confirm.html").include(request, response);
			request.getRequestDispatcher("html/html-bottom-common.html").include(request, response);
	}
		else {
			response.sendRedirect("index.html");
		}
}
}