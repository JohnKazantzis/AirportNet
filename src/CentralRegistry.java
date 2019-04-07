import java.util.ArrayList;

public class CentralRegistry {

	private static ArrayList<Airport> airportList = new ArrayList<Airport>();;
	private static ArrayList<Flight> flightList = new ArrayList<Flight>();;
	
	public CentralRegistry(){
		
	}
	
	public static void addFlight(Flight aFlight){
		
		boolean existsInStart = false;
		boolean existsInDest = false;
		
		flightList.add(aFlight);//Adding Flight to the flight list
		
		//Checking if start airport has already this flight company included in its flight companies list
		for(int i=0; i<aFlight.getStart().getFlightCompanies().size(); i++){
			if(aFlight.getStart().getFlightCompanies().get(i).equals(aFlight.getCompany())){
				existsInStart = true;
				break;
			}
			
		}
		//If its not included, we add it
		if(existsInStart == false){
			aFlight.getStart().getFlightCompanies().add(aFlight.getCompany());
		}
		
		//Checking if destination airport has already this flight company included in its flight companies list
		for(int i=0; i<aFlight.getDestination().getFlightCompanies().size(); i++){
			if(!(aFlight.getDestination().getFlightCompanies().get(i).equals(aFlight.getCompany()))){
				existsInDest = true;
				break;
			}
			
		}
		//If its not included, we add it
		if(existsInDest == false){
			aFlight.getDestination().getFlightCompanies().add(aFlight.getCompany());
		}
	}
	
	public static void addAirport(Airport anAirport){//Adding airport to the airport list
		airportList.add(anAirport);
	}
	
	public static Flight getLongestFlight(){//Getting longest flight
		
		int max = 0;
		int posOfmax = -1;
		
		for(int i=0; i<flightList.size(); i++){
			if(flightList.get(i).getFlightDuration() > max){
				posOfmax = i;
				max = flightList.get(i).getFlightDuration();
			}
		}
		
		return flightList.get(posOfmax);
	}
	
	public static Airport getLargestHub(){//Getting Largest hub
		int maxConnAir = 0;
		int numOfConn = 0;
		int posOfLargestHub = -1;
		int j = 0;
		int i = 0;
		
		for(i=0; i<airportList.size(); i++){
			for(j=0; j<CentralRegistry.flightList.size(); j++){
				if((CentralRegistry.flightList.get(j).getStart() == airportList.get(i)) || (CentralRegistry.flightList.get(j).getDestination() == airportList.get(i))){
					numOfConn = numOfConn + 1;
				}
				
			}
			if(numOfConn > maxConnAir){
				maxConnAir = numOfConn;
				posOfLargestHub = i;
			}
			numOfConn = 0;
		}
		
		
		
		return airportList.get(posOfLargestHub);
	}
	
	public static Airport getAirport(String cityName){
		
		//If the city name exists then we return the airport
		for(int i=0; i<airportList.size(); i++){
			if(airportList.get(i).getCity().equals(cityName)){
				return airportList.get(i);
			}
		}
		
		//If the city name is not found we return null
		return null;
	}
	
	public static String getDirectFlightsDetails(Airport a, Airport b){
		String directFlightDet = "";
		int counter = 1;
		
		for(int i=0; i<flightList.size(); i++){
			if((flightList.get(i).getStart() == a && flightList.get(i).getDestination() == b) || (flightList.get(i).getStart() == b && flightList.get(i).getDestination() == a)){
				directFlightDet = directFlightDet + "[" + counter + "]" + flightList.get(i).toString() + "\n";
				counter++;
			}
		}
		
		return directFlightDet;
	}
	
	public static String getInDirectFlightsDetails(Airport a, Airport b){
		String indirectFlightDet = "";
		ArrayList<Airport> indirectFlightList = new ArrayList<Airport>();
		int counter = 1;
		
		indirectFlightList = indirectlyFList(a,b);
		
		for(int i=0; i<indirectFlightList.size(); i++){
			indirectFlightDet = indirectFlightDet + "[" + counter + "]" + indirectFlightList.get(i).getCity() + "," + indirectFlightList.get(i).getCode() + " airport\n";
		}
		
		return indirectFlightDet;
	}
	
	public static ArrayList<Airport> indirectlyFList(Airport a, Airport b){
		ArrayList<Airport> indirectlyFList = new ArrayList<Airport>();
		
		for(int i=0; i<flightList.size(); i++){
			if(flightList.get(i).getStart() == a && flightList.get(i).getDestination() != b){
				for(int j=0; j<flightList.size(); j++){
					if((flightList.get(j).getStart() == flightList.get(i).getDestination() && flightList.get(j).getDestination() == b) || (flightList.get(j).getStart() == b && flightList.get(j).getDestination() == flightList.get(i).getDestination())){
						indirectlyFList.add(flightList.get(i).getDestination());
					}
				}
			}
			else if(flightList.get(i).getStart() != b && flightList.get(i).getDestination() == a){
				for(int j=0; j<flightList.size(); j++){
					if((flightList.get(j).getStart() == flightList.get(i).getStart() && flightList.get(j).getDestination() == b) || (flightList.get(j).getStart() == b && flightList.get(j).getDestination() == flightList.get(i).getStart())){
						indirectlyFList.add(flightList.get(i).getStart());
					}
				}
			}
		}
		
		return indirectlyFList;
	}
	
	//Getter
	public static ArrayList<Flight> getFlightList() {
		return flightList;
	}

	public static ArrayList<Airport> getAirportList() {
		return airportList;
	}
	
	
}
