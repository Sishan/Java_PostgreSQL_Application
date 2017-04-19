package Driver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class FileOperater {
	private Connection c ;
	public FileOperater(Connection c) {
		this.c = c;
	}
	
	public void loadFile(String path) throws FileNotFoundException{
		BufferedReader br = null;
			br = new BufferedReader(new FileReader(path));
	    	
	    	String temp = "";
	    	String data = "";
	    	Insert in = new Insert(c);
	    	try {
				while((data = br.readLine()) != null) {
					String tbLine[]  = data.split(",");
					switch(tbLine[0]){
					case "*Org":
						temp = "Org";
						break;
					case "*Meet":
						temp = "Meet";
						break;
					case "*Participant":
						temp = "Participant";
						break;
					case "*Event":
						temp = "Event";
						break;
					case "*Stroke":
						temp = "Stroke";
						break;
					case "*StrokeOf":
						temp = "StrokeOf";
						break;
					case "*Distance":
						temp = "Distance";
						break;
					case "*Heat":
						temp = "Heat";
						break;
					case "*Swim":
						temp = "Swim";
						break;
					case "*Leg":
						temp = "Leg";
						break;
					default:
						String array[] = data.split(",");
						switch(temp) {
							case "Org":
								in.insertIntoOrg(array[0], array[1], Boolean.parseBoolean(array[2]));
								break;
							case "Meet":
								in.insertIntoMeet(array[0], java.sql.Date.valueOf(array[1]), Integer.parseInt((array[2])), array[3]);
								break;
							case "Participant":
								in.insertIntoParticipant(array[0], array[1], array[2], array[3]);
								break;
							case "Stroke":
								in.insertIntoStroke(array[0]);
								break;
							case "Distance":
								in.insertIntoDistance(Integer.parseInt(array[0]));
								break;
							case "Event":
								in.insertIntoEvent(array[0], array[1], Integer.parseInt(array[2]));
								break;
							case "Leg":
								in.insertIntoLeg(Integer.parseInt(array[0]));
								break;
							case "StrokeOf":
								in.insertIntoStrokeOf(array[0], Integer.parseInt(array[1]), array[2]);
								break;
							case "Heat":
								in.insertIntoHeat(array[0], array[1], array[2]);
								break;
							case "Swim":
								int leg = 0;
								if (!array[4].equals("")) {
									leg = Integer.parseInt(array[4]);
								}
								in.insertIntoSwim(array[0], array[1], array[2], array[3], leg, Float.parseFloat(array[5]));
								break;
						}
						
					}
				}
				System.out.println("Loading file completed!\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
