package edu.dtcc.csc241.last.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
import edu.edu.csc241.aircraft.ScheduledTask;
import edu.edu.csc241.aircraft.SystemDriver;

/**
 * Servlet implementation class SingleServerSingleQueue
 */
@WebServlet("/GetSampleCount")
public class DataProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SystemDriver TheSystemDriver;
	
	
	static ScheduledTask updTask;

	public void init(ServletConfig config)
          throws ServletException {
	
		TheSystemDriver = new SystemDriver();
		
		TheSystemDriver.initSystem(1000, 600.0, 45.0); 
		
		Thread t = new Thread(TheSystemDriver);
		t.start();
		 
	}
	
    /**
     * Default constructor. 
     */
    public DataProvider() {
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
		
		String command = request.getParameter("setHeading");
		if (command != null) {
			Double newHeading = new Double(Double.parseDouble(command)); 
			TheSystemDriver.setHeading(newHeading);
		}
		
		command = request.getParameter("setSpeed");
		if (command != null) {
			Double newSpeed = new Double(Double.parseDouble(command)); 
			TheSystemDriver.setSpeed(newSpeed);
		}	
		
		command = request.getParameter("sysinit");
		if (command != null) {
			
			TheSystemDriver.resetAirplane();
		}	
		  
		writer.println(TheSystemDriver.getStatus());
	}

}
