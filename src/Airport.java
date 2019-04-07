import java.util.ArrayList;

public class Airport {
	
	private String name;
	private String code;
	private String city;
	private String country;
	private ArrayList<String> flightCompanies;


	public Airport(String name,String code,String city,String country){// Constructor
		
		this.name = name;
		this.code = code;
		this.city = city;
		this.country = country;
		this.flightCompanies = new ArrayList<String>();
	}
	
	public boolean isDirectlyConnectedTo(Airport anAirport){// A function that returns true if a target airport is directly connected
		
		boolean connected = false;
		
		for(int i=0; i<CentralRegistry.getFlightList().size(); i++){
			if(CentralRegistry.getFlightList().get(i).getStart() == anAirport && CentralRegistry.getFlightList().get(i).getDestination() == (this)){
				return connected = true;
			}
			else if(CentralRegistry.getFlightList().get(i).getStart() == this && CentralRegistry.getFlightList().get(i).getDestination() == anAirport){
				return connected = true;
			}
		}
		
		return connected;
	}
	
	public boolean isInDirectlyConnectedTo(Airport anAirport){//Creates a list with the airports directly connected to it.If one of these airports is directly connected to the target airport the function returns true
		
		boolean connected = false;
		ArrayList<Airport> directlyConAirports = new ArrayList<Airport>();
		
		for(int i=0; i<CentralRegistry.getFlightList().size(); i++){//Creates a list with the airports directly connected to it
			if(CentralRegistry.getFlightList().get(i).getStart() == this){
				directlyConAirports.add(CentralRegistry.getFlightList().get(i).getDestination());
			}
			else if(CentralRegistry.getFlightList().get(i).getDestination() == this){
				directlyConAirports.add(CentralRegistry.getFlightList().get(i).getStart());
			}
		}
		
		for(int i=0; i<directlyConAirports.size(); i++){//Checks if the target is directly connecting to any of the airports in our list
			for(int j=0; j<CentralRegistry.getFlightList().size(); j++){
				if(CentralRegistry.getFlightList().get(j).getStart() == directlyConAirports.get(i) && CentralRegistry.getFlightList().get(j).getDestination() == anAirport){
					return connected = true;
				}
				else if(CentralRegistry.getFlightList().get(j).getStart() == anAirport && CentralRegistry.getFlightList().get(j).getDestination() == directlyConAirports.get(i)){
					return connected = true;
				}
			}
		}
		
		return connected;
	}
	

	public ArrayList<Airport> getCommonConnections(Airport anAirport){
		
		ArrayList<Airport> commonAirports = new ArrayList<Airport>();
		ArrayList<Airport> thisObjAirportsList = new ArrayList<Airport>();
		ArrayList<Airport> anAirportsList = new ArrayList<Airport>();
		
		for(int i=0; i<CentralRegistry.getFlightList().size(); i++){//Creates a list with the airports directly connected to it
			if(CentralRegistry.getFlightList().get(i).getStart() == this){
				thisObjAirportsList.add(CentralRegistry.getFlightList().get(i).getDestination());
			}
			else if(CentralRegistry.getFlightList().get(i).getDestination() == this){
				thisObjAirportsList.add(CentralRegistry.getFlightList().get(i).getStart());
			}
		}
		
		for(int i=0; i<CentralRegistry.getFlightList().size(); i++){//Creates a list with the airports directly connected to it
			if(CentralRegistry.getFlightList().get(i).getStart() == anAirport){
				anAirportsList.add(CentralRegistry.getFlightList().get(i).getDestination());
			}
			else if(CentralRegistry.getFlightList().get(i).getDestination() == anAirport){
				anAirportsList.add(CentralRegistry.getFlightList().get(i).getStart());
			}
		}
		
		for(int i=0; i<thisObjAirportsList.size(); i++){//Compares to see if there are any common airports. If there are, they are added to the common airport list 
			for(int j=0; j<anAirportsList.size(); j++){
				if(thisObjAirportsList.get(i) == anAirportsList.get(j)){
					commonAirports.add(thisObjAirportsList.get(i));
				}
			}
		}
		
		return commonAirports;
	}
	
	public void printCompanies(){//Printing Flight companies
		
		for(int i=0; i<this.flightCompanies.size(); i++){
			System.out.println(this.flightCompanies.get(i));
		}
	}
	
	//Getters
	public ArrayList<String> getFlightCompanies() {
		return flightCompanies;
	}

	public String getCity() {
		return city;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getCountry() {
		return country;
	}
}
