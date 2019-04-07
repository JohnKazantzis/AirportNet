import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Main extends JFrame{
	
	private static JFrame searchFrame;

	public static void main(String[] args) {
		
		//Creation of airports
		Airport a1 = new Airport("Orly", "ORY", "Paris", "France");
		Airport a2 = new Airport("Fiumicino", "FCO", "Rome", "Italy");
		Airport a3 = new Airport("Venizelos", "ATH", "Athens", "Greece");
		Airport a4 = new Airport("Macedonia", "SKG", "Thessaloniki", "Greece");
		Airport a5 = new Airport("MunichAirport", "MUC", "Munich", "Germany");
		Airport a6 = new Airport("Charleroi", "CRL", "Brussels", "Belgium");
		
		//Creation of flights
		Flight f1 = new Flight(a1, a2, 120, "Air France");
		Flight f2 = new Flight(a1, a5, 90, "Lufthansa");
		Flight f3 = new Flight(a1, a6, 40, "Air France");
		Flight f4 = new Flight(a4, a5, 130, "EasyJet");
		Flight f5 = new Flight(a3, a5, 150, "Olympic");
		Flight f6 = new Flight(a4, a5, 120, "Aegean");
		Flight f7 = new Flight(a2, a5, 110, "Alitalia");
		Flight f8 = new Flight(a3, a4, 30, "Aegean");
				
		//Addition to Central Registry
		CentralRegistry.addAirport(a1);
		CentralRegistry.addAirport(a2);
		CentralRegistry.addAirport(a3);
		CentralRegistry.addAirport(a4);
		CentralRegistry.addAirport(a5);
		CentralRegistry.addAirport(a6);
		
		CentralRegistry.addFlight(f1);
		CentralRegistry.addFlight(f2);
		CentralRegistry.addFlight(f3);
		CentralRegistry.addFlight(f4);
		CentralRegistry.addFlight(f5);
		CentralRegistry.addFlight(f6);
		CentralRegistry.addFlight(f7);		
		CentralRegistry.addFlight(f8);
		
		//Creation of the "Find Airport" Frame
		searchFrame = new JFrame("Find Airport");
		
		Container contentPane1 = searchFrame.getContentPane();
		JTextField searchTextF = new JTextField("Please enter CITY name");
		JButton searchButton = new JButton("Find");
		JButton visualizeNet = new JButton("Visualize Network");
		
		searchButton.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				
				
				String text;
				String s = ""; //String for the companies text area
				int flag = 0;
				
				text = searchTextF.getText();
				
				for(int i=0; i<CentralRegistry.getAirportList().size(); i++){
					
					if(CentralRegistry.getAirportList().get(i).getCity().equals(text)){
						
						searchFrame.setVisible(false);
						
						//Create Airport Frame
						flag =1;
						
						JFrame airportPage = new JFrame("Airport Page");
						Container airPageCon = airportPage.getContentPane();
						
						JTextField nameF = new JTextField(12);
						JTextField codeF = new JTextField(12);
						JTextField cityF = new JTextField(12);
						JTextField countryF = new JTextField(12);
						JTextArea companies = new JTextArea(5,10);
						JTextField dest = new JTextField(12);
						JButton findFl = new JButton("Find Flights");
						JTextArea dirFd = new JTextArea(12,40);
						JTextArea indirFd = new JTextArea(12,40);
						JButton back = new JButton("Back to Search Screen");
						
						//Setting Airport's info labels
						nameF.setText(CentralRegistry.getAirportList().get(i).getName());
						codeF.setText(CentralRegistry.getAirportList().get(i).getCode());
						cityF.setText(CentralRegistry.getAirportList().get(i).getCity());
						countryF.setText(CentralRegistry.getAirportList().get(i).getCountry());
						
						//Setting companies Textarea
						for(int k=0; k< CentralRegistry.getAirportList().get(i).getFlightCompanies().size(); k++){
							s = s + CentralRegistry.getAirportList().get(i).getFlightCompanies().get(k) + "\n";
						}
						companies.setText(s);
						
						
						
						findFl.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								String destination,start,output,country,name,code,filename;
								int destNum = 0;
								int startNum = 0;
								
								destination = dest.getText();
								start = cityF.getText();
								country = countryF.getText();
								name = nameF.getText();
								code = codeF.getText();
								
								for(int l=0; l<CentralRegistry.getAirportList().size(); l++){
									if(CentralRegistry.getAirportList().get(l).getCity().equals(destination)){
										destNum = l;
									}
									if(CentralRegistry.getAirportList().get(l).getCity().equals(cityF.getText())){
										startNum = l;
									}
								}
								
								
								//Setting Dir/Indir Flight details
								dirFd.setText("DIRECT FLIGHTS DETAILS\n" + CentralRegistry.getDirectFlightsDetails(CentralRegistry.getAirportList().get(startNum),CentralRegistry.getAirportList().get(destNum)));
								indirFd.setText("INDIRECT FLIGHTS through...\n" + CentralRegistry.getInDirectFlightsDetails(CentralRegistry.getAirportList().get(startNum),CentralRegistry.getAirportList().get(destNum)));
								
								output = "City: " + start + ", " + country + "\nAirport: " +  name + " (" + code + ")\n\nDestination: " + destination + "\n\n" + dirFd.getText() + "\n\n\n" + indirFd.getText();
								
								//Creating the text file
								filename = cityF + "To" + destination + ".txt";
								try {
									FileOutputStream fileOut = new FileOutputStream("output.txt");//I named the file "output". The name should have been cityF + "To" + destination + ".txt" but i was getting a java.io.filenotfoundexception (file name too long)
									ObjectOutputStream out = new ObjectOutputStream(fileOut);
									
									out.writeObject(output);
									out.close();
									fileOut.close();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						});
						
						
						back.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent e) {
								
								airportPage.setVisible(false);
								searchFrame.setVisible(true);
								
							}
							
						});						
						
						
						//1st row
						airPageCon.add(nameF);
						airPageCon.add(codeF);
						airPageCon.add(cityF);
						airPageCon.add(countryF);
						airPageCon.add(companies);
						
						//2nd row
						airPageCon.add(dest);
						airPageCon.add(findFl);
						
						//3rd row
						airPageCon.add(dirFd);
						airPageCon.add(indirFd);
						
						//4th row
						airPageCon.add(back);
						
						airportPage.setLayout(new FlowLayout());
						airportPage.pack();
						airportPage.setSize(800,700);
						airportPage.setVisible(true);
						
						
					}
					
				}
				if(flag == 0){
					//Gui if there is no airport in the city entered
					JFrame noAirFr = new JFrame("Message");
					Container noAirCon = noAirFr.getContentPane();
					
					ImageIcon noAirIcon = new ImageIcon("/home/john/Downloads/3ass/images/mark2.jpg");
					JLabel imageLabel = new JLabel(noAirIcon);
					JLabel noAirLabel = new JLabel(text + " does not have an airport");
					JButton okB = new JButton("OK");
					
					okB.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
							
							noAirFr.setVisible(false);
							
						}
						
					});
					
					noAirCon.add(imageLabel);
					noAirCon.add(noAirLabel);
					noAirCon.add(okB);
					
					noAirFr.setLayout(new FlowLayout());
					noAirFr.pack();
					noAirFr.setSize(300,150);
					noAirFr.setVisible(true);
					
				}
			}
			
		});
		
		visualizeNet.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UndirectedSparseGraph graph = new UndirectedSparseGraph();
				graph.addVertex("Athens");
			    graph.addVertex("Paris");
			    graph.addVertex("Thessaloniki");
			    graph.addVertex("Munich");
			    graph.addVertex("Rome");
			    graph.addVertex("Brussels");
			    
			    graph.addEdge("Edge1", "Paris", "Rome");
			    graph.addEdge("Edge2", "Paris", "Munich");
			    graph.addEdge("Edge3", "Paris", "Brussels");
			    graph.addEdge("Edge4", "Thessaloniki", "Munich");
			    graph.addEdge("Edge5", "Athens", "Munich");
			    graph.addEdge("Edge6", "Rome", "Munich");
			    graph.addEdge("Edge7", "Athens", "Thessaloniki");
			   
			    
			    VisualizationImageServer vs = new VisualizationImageServer( new CircleLayout(graph), new Dimension(700, 600));
			    vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			 
			    JFrame frame = new JFrame("City Airport Connections Network");
			    JLabel label = new JLabel("Diameter = 3.0");
			    Container cont = new Container();
			    cont = frame.getContentPane();
			    cont.add(vs);
			    frame.add(label,BorderLayout.PAGE_START);
			    frame.setLayout(new BorderLayout());
			    frame.add(label, BorderLayout.PAGE_END);
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.pack();
			    frame.setSize(700,650);
			    frame.setVisible(true);
				
			}
			
		});
		
		contentPane1.add(searchTextF);
		contentPane1.add(searchButton);
		contentPane1.add(visualizeNet);
		
		contentPane1.setLayout(new FlowLayout());
		searchFrame.pack();
		searchFrame.setSize(300,150);
		searchFrame.setVisible(true);
		
		
		
		
	}
}

