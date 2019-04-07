
public class Flight {

	private Airport start;
	private Airport destination;
	private int flightDuration;
	private String company;

	public Flight(Airport start,Airport destination,int flightDuration,String company){// Constructor
		
		this.start = start;
		this.destination = destination;
		this.flightDuration = flightDuration;
		this.company = company;
	}
	
	public Airport getAirportA(){//Get airport A
		return this.start;
	}
	
	public Airport getAirportB(){//Get airport B
		return this.destination;
	}
	
	public String toString(){//toString method
		
		return "Flight operated by " + this.company + ", duration " + this.flightDuration + " minutes";
	}

	//Getters
	public Airport getStart() {
		return start;
	}

	public Airport getDestination() {
		return destination;
	}

	public String getCompany() {
		return company;
	}

	public int getFlightDuration() {
		return flightDuration;
	}
	
}
