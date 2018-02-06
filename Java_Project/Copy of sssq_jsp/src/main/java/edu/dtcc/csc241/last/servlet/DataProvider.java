package edu.dtcc.csc241.last.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dtcc.csc241.model.SystemDriver;
import edu.edu.csc241.aircraft.Airplane;
import edu.edu.csc241.aircraft.ScheduledTask;

/**
 * Servlet implementation class SingleServerSingleQueue
 */
@WebServlet("/GetSampleCount")
public class DataProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SystemDriver aSystemDriver;
	
	
	static ScheduledTask updTask;

	public void init(ServletConfig config)
          throws ServletException {
	
		double systemTime = (double) System.currentTimeMillis();
		flyingTask = new ScheduledTask(1000, systemTime);
		Airplane anAirplane = new Airplane(systemTime);
		updTask.addObserver(anAirplane);
		Thread t = new Thread(updTask); 
		
		t.start();
		
		while (updTask.isRunning()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Main program waking up!");
		}
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
		Double simulationStopTime = 1.0; 
		aSystemDriver = new SystemDriver();
		String stopTime = request.getParameter("StopTime");
		if (stopTime != null) {
			simulationStopTime = Double.parseDouble(stopTime);
			aSystemDriver = new SystemDriver();
			aSystemDriver.setAverageServiceTime(60.0);
			aSystemDriver.setAverageTimeBetweenArrival(50.0);
			aSystemDriver.setupSystemRunTimeTo(simulationStopTime);
			aSystemDriver.start();
		} 
		 
		writer.println((aSystemDriver.getServerStatitician().getTotal() / simulationStopTime) *100.0);
	}

}
