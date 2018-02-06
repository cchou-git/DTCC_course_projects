package edu.dtcc.csc241.lab7.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dtcc.csc241.model.SystemDriver;

/**
 * Servlet implementation class SingleServerSingleQueue
 */
@WebServlet("/SingleServerSingleQueue")
public class SingleServerSingleQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SystemDriver aSystemDriver;
		
    /**
     * Default constructor. 
     */
    public SingleServerSingleQueue() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Double simulationStopTime = null;
		StringBuffer sb = new StringBuffer();
		
		String stopTime = request.getParameter("StopTime");
		if (stopTime != null) {
			simulationStopTime = Double.parseDouble(stopTime);
			aSystemDriver = new SystemDriver();
			aSystemDriver.setAverageServiceTime(60.0);
			aSystemDriver.setAverageTimeBetweenArrival(50.0);
			aSystemDriver.setupSystemRunTimeTo(simulationStopTime);
			aSystemDriver.start();
		}
		
		sb.append("<html lang=\"en-US\">");
		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<title>DTCC CSC 241 Forms Example</title>");
		sb.append("<style type=\"text/css\">");
		sb.append("body {");
		sb.append("margin: 2em 5em;  font-family:Georgia, \"Times New Roman\", Times, serif; }");
		sb.append("h1, legend {");
		sb.append("	font-family:Arial, Helvetica, sans-serif; } ");
		sb.append("label, input, select {");
		sb.append("display:block; }");
		sb.append("input, select { ");
		sb.append("margin-bottom: 1em; }");
		sb.append("input[type=\"checkbox\"] {");
		sb.append("display:inline; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");  
		sb.append("<form method=\"post\"  id=\"simulate\">");
		sb.append("<h1>Enter # of seconds to perform the simulation</h1>");
		sb.append("<fieldset>");
		sb.append("<legend>Simulation Details</legend>");
		sb.append("<div>");
		sb.append("<label>Simulate System till:");
		sb.append("<input id=\"stopTime\" name=\"StopTime\" type=\"text\" placeholder=\"200.0\" required autofocus>");
		sb.append("</label>");
		sb.append("</div>");
		sb.append("<div>");
		sb.append("<button type=submit form=\"simulate\" name=\"sendData\" value=\"Submit\">Start Simulation</button>"); 
		sb.append("</div>");
		if (simulationStopTime != null) {
			sb.append("<div>");
			sb.append("<p>The last time to simulate was: ");
			sb.append(simulationStopTime + "</p>");

			sb.append("<p>The average waiting time was: ");  
			sb.append(aSystemDriver.getCustomerStatitician().getMean() + "</p>");
			sb.append("<p>The server busy percentage was: ");
			sb.append(((aSystemDriver.getServerStatitician().getTotal()) / simulationStopTime) * 100.0 + "</p>");
			sb.append("</div>");
		}
		sb.append("</form>");
		sb.append("</body>");
		sb.append("</html>");
		writer.println(sb.toString());
	}

}
