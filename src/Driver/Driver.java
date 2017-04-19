package Driver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {  
    public static void main(String args[]) throws SQLException, IOException {  
        Connection c = null;  
        Statement stmt = null;  
        while(true){
        	System.out.println("Please input your local database setting parameters, separate using comma!");
        	System.out.println("Input order: Server,port,dataBase,username,password");
        	System.out.println("Sample input: localhost,5432,postgres,ricedb,postgres");
        	System.out.println("-------Input your parameters below:--------");
        	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String settings = null;
    		try {
    			settings = br.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		String server = null;
    		String dataBase = null;
    		String port = null;
    		String username = null;
    		String password = null;
    		
    		String[] set = settings.split(",");
    		if (set.length != 5){
    			continue;
    		}
    		else {
    			server = set[0].trim();
    			port = set[1].trim();
    			dataBase = set[2].trim();
    			username = set[3].trim();
    			password = set[4].trim();
    		}
    		
            try {  
                Class.forName("org.postgresql.Driver");  
                c = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + dataBase, username,  password);
                System.out.println("Connected to database successfully...");  
                
                stmt = c.createStatement();
                String sql = "SELECT * FROM Distance";
                ResultSet rs = stmt.executeQuery(sql);
                 while (rs.next())
                {
                	 System.out.print(rs.getInt( 1 ));
                } 
                rs.close();      
            } catch (Exception e) {  
                System.err.println(e.getClass().getName() + ": " + e.getMessage());  
                System.exit(0);  
                continue;
            }
            while (true) {
            	System.out.println("-------Input your command below:--------");
            	BufferedReader brin=new BufferedReader(new InputStreamReader(System.in));
                String line = null;
        		try {
        			line = brin.readLine();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		String[] list = line.split(":");
        		if (list.length == 1) {
        			String cmd = list[0].trim();
        			switch(cmd) {
        			case "-h" :
        				System.out.println("************************Command Manuel****************************");
        				System.out.println("-h                                                    help");
        				System.out.println("-l:filename                                           load file");
        				System.out.println("-s:filename                                           save to file");	
        				System.out.println("-i:tablename,[att]                                    insert a row of data");
        				System.out.println("-disp:-meet:[meet_name]                               display the heatsheet of a meet");
        				System.out.println("-disp:-meet_partcipant:[meet_name,participant_id]     display the participant's result in a meet");
        				System.out.println("-disp:-meet_school_own:[meet_name,school_id]          display the school's participant in a meet");
        				System.out.println("-disp:-meet_school_opp:[meet_name,school_id]          display the school's opposite participant in a meet");
        				System.out.println("-disp:-meet_event:[meet_name,event_id]                display one event of a meet");
        				System.out.println("-disp:-meet_allschool:[meet_name]                     display all school's rank in a meet");
        				System.out.println("******************************************************************");
        				break;
        			default:
    					System.out.println("Syntax error, input -h to see help manuel.");
    					break;
        				
        			}
        		} else if (list.length == 2) {
        			String cmd = list[0].trim();
        			switch(cmd) {
    	    			case "-l" :
    	    				try{
        	    			System.out.println("Loading data from: " + list[1].trim() + "...");
        	    			FileOperater fo = new FileOperater(c);
        	    			fo.loadFile(list[1].trim());
        	    			}catch (FileNotFoundException e) {
        	    					e.printStackTrace();
        	    					continue;
        	    			}
    	    				break;
    	    			case "-s" :
    	    				try{
    	    					System.out.println("Saving data into: " + list[1].trim() + ".csv");
        	    				FileSaver fs = new FileSaver(c);
        	    				fs.saveFile(list[1].trim());
    	    				}catch(SQLException e1){
    	    					e1.printStackTrace();
    	    				}catch(IOException e2){
    	    					e2.printStackTrace();
    	    				}

    	    				break;
    	    			case "-i" :
    	    				String tableName = list[1].trim();
    	    				
    	    				switch(tableName){
    		    				case "Org":
    		    					System.out.println("Table Org!\n"
    		    										+ "Input type: id VARCHAR(50), name VARCHAR(50), is_univ BOOLEAN\n"
    		    										+ "Sample input: U111,UCLA,TRUE\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istOrg = new Insert(c);
    		    		        	BufferedReader brinOrg=new BufferedReader(new InputStreamReader(System.in));
    		    		            String orgInput = null;
    		    		    		try {
    		    		    			orgInput = brinOrg.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] orgPara = orgInput.split(",");
    		    		    		if (orgPara.length != 3) {
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else {
    		    		    			istOrg.insertIntoOrg(orgPara[0].trim(), orgPara[1].trim(), Boolean.parseBoolean(orgPara[2].trim()));
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Participant":
    		    					System.out.println("Table Participant!\n"
    		    										+ "Type: id VARCHAR(50), gender CHAR (1), org_id VARCHAR(50), name VARCHAR(50)\n"
    		    										+ "Sample input: P123321,M,U430,Rany\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istParticipant = new Insert(c);
    		    		        	BufferedReader brinParticipant=new BufferedReader(new InputStreamReader(System.in));
    		    		            String participantInput = null;
    		    		    		try {
    		    		    			participantInput = brinParticipant.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] participantPara = participantInput.split(",");
    		    		    		if(participantPara.length != 4){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else {
    		    		    			istParticipant.insertIntoParticipant(participantPara[0].trim(), participantPara[1].trim(), participantPara[2].trim(), participantPara[3].trim());
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Meet":
    		    					System.out.println("Table Meet!\n"
    		    										+ "Type: name VARCHAR(50), start_date DATE, num_days INT, org_id VARCHAR(50)\n"
    		    										+ "Sample input: RICE_Summer,2007-05-05,4,U430\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istMeet = new Insert(c);
    		    					BufferedReader brinMeet=new BufferedReader(new InputStreamReader(System.in));
    		    		            String meetInput = null;
    		    		    		try {
    		    		    			meetInput = brinMeet.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] meetPara = meetInput.split(",");
    		    		    		if(meetPara.length != 4){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else {
    		    		    			istMeet.insertIntoMeet(meetPara[0].trim(), java.sql.Date.valueOf(meetPara[1].trim()), Integer.parseInt(meetPara[2].trim()), meetPara[3].trim());
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Stroke":
    		    					System.out.println("Table Stroke!\n"
    		    										+ "Type: stroke VARCHAR(50)\n"
    		    										+ "Sample input: testStyle\n"
    		    										+ "-------Input your parameter below:--------");
    		    					Insert istStroke = new Insert(c);
    		    					BufferedReader brinStroke=new BufferedReader(new InputStreamReader(System.in));
    		    		            String strokeInput = null;
    		    		    		try {
    		    		    			strokeInput = brinStroke.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] strokePara = strokeInput.split(",");
    		    		    		if(strokePara.length != 1){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else {
    		    		    			istStroke.insertIntoStroke(strokePara[0].trim());
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Distance":
    		    					System.out.println("Table Distance!\n"
    		    										+ "Type: distance INT\n"
    		    										+ "Sample input: 1000\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istDistance = new Insert(c);
    		    					BufferedReader brinDistance=new BufferedReader(new InputStreamReader(System.in));
    		    		            String distanceInput = null;
    		    		    		try {
    		    		    			distanceInput = brinDistance.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] distancePara = distanceInput.split(",");
    		    		    		if(distancePara.length != 1){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else{
    		    		    			istDistance.insertIntoDistance(Integer.parseInt(distancePara[0].trim()));
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Event":
    		    					System.out.println("Table Event!\n"
    		    										+ "Type: id VARCHAR(50), gender CHAR(1), distance INT\n"
    		    										+ "Sample input: E1234,M,100\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istEvent = new Insert(c);
    		    					BufferedReader brinEvent=new BufferedReader(new InputStreamReader(System.in));
    		    		            String eventInput = null;
    		    		    		try {
    		    		    			eventInput = brinEvent.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] eventPara = eventInput.split(",");
    		    		    		if(eventPara.length != 3){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else{
    		    		    			istEvent.insertIntoEvent(eventPara[0].trim(), eventPara[1].trim(), Integer.parseInt(eventPara[2].trim()));
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Leg":
    		    					System.out.println("Table Leg!\n"
    		    										+ "Type: leg INT\n"
    		    										+ "Sample input: 5\n"
    		    										+ "-------Input your parameter below:--------");
    		    					Insert istLeg = new Insert(c);
    		    					BufferedReader brinLeg=new BufferedReader(new InputStreamReader(System.in));
    		    		            String legInput = null;
    		    		    		try {
    		    		    			legInput = brinLeg.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] legPara = legInput.split(",");
    		    		    		if(legPara.length != 1){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else{
    		    		    			istLeg.insertIntoLeg(Integer.parseInt(legPara[0].trim()));
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "StrokeOf":
    		    					System.out.println("Table StrokeOf!\n"
    		    										+ "Type: event_id VARCHAR(50), leg INT, stroke VARCHAR(50)\n"
    		    										+ "Sample input: E1234,2,Freestyle\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istStrokeOf = new Insert(c);
    		    					BufferedReader brinStrokeOf=new BufferedReader(new InputStreamReader(System.in));
    		    		            String strokeOfInput = null;
    		    		    		try {
    		    		    			strokeOfInput = brinStrokeOf.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] strokeOfPara = strokeOfInput.split(",");
    		    		    		if(strokeOfPara.length != 3){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else{
    		    		    			istStrokeOf.insertIntoStrokeOf(strokeOfPara[0].trim(), Integer.parseInt(strokeOfPara[1].trim()), strokeOfPara[2].trim());
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Heat":
    		    					System.out.println("Table Heat!\n"
    		    										+ "Type: id VARCHAR(50), event_id VARCHAR(50), meet_name VARCHAR(50)\n"
    		    										+ "Sample input: 1,E0107,RICE_Summer\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istHeat = new Insert(c);
    		    					BufferedReader brinHeat=new BufferedReader(new InputStreamReader(System.in));
    		    		            String heatInput = null;
    		    		    		try {
    		    		    			heatInput = brinHeat.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] heatPara = heatInput.split(",");
    		    		    		if(heatPara.length != 3){
    		    		    			System.out.println("Wrong input length!");
    		    		    		}
    		    		    		else{
    		    		    			istHeat.insertIntoHeat(heatPara[0].trim(), heatPara[1].trim(), heatPara[2].trim());
    		    		    			System.out.println("Insertion successfully!");
    		    		    		}
    		    					break;
    		    				case "Swim":
    		    					System.out.println("Table Swim!\n"
    		    										+ "Type: heat_id VARCHAR(50), event_id VARCHAR(50), meet_name VARCHAR(50), participant_id VARCHAR(50), leg INT, swimtime FLOAT\n"
    		    										+ "Sample input: 2,E0307,SouthConfed,P246719,2,15.222222\n"
    		    										+ "-------Input your parameters below:--------");
    		    					Insert istSwim = new Insert(c);
    		    					BufferedReader brinSwim=new BufferedReader(new InputStreamReader(System.in));
    		    		            String swimInput = null;
    		    		    		try {
    		    		    			swimInput = brinSwim.readLine();
    		    		    		} catch (IOException e) {
    		    		    			e.printStackTrace();
    		    		    		}
    		    		    		String[] swimPara = swimInput.split(",");
    		    		    		int leg = 0;
    		    		    		if(swimPara.length != 6){
    		    		    			System.out.println("Wrong input length!");
    									if (!swimPara[4].equals("")) {
    										leg = Integer.parseInt(swimPara[4].trim());
    									}
    		    		    		}
    								else{
    									istSwim.insertIntoSwim(swimPara[0].trim(), swimPara[1].trim(), swimPara[2].trim(), swimPara[3].trim(), leg, Float.parseFloat(swimPara[5].trim()));
    									System.out.println("Insertion successfully!");
    								}
    		    					break;
    		    				default:
    		    					System.out.println("Syntax error, check input table name and attributes.");
    		    					break;
    	    				}
    	    				break;
    	    			default:
    						System.out.println("Syntax error, input -h to see help manuel.");
    						break;
        				
        			}
        		} else if (list.length == 3) {
        			String cmd1 = list[0].trim();
        			String cmd2 = list[1].trim();
        			switch(cmd1) {
        			case "-disp" :{
        				switch(cmd2) {
        				case "-meet" :{
        					System.out.println("For Meet = " + list[2].trim() + " display the Heat Sheet:");
        					Disp dp = new Disp(c);
        					dp.dispMeet(list[2].trim());
        				}
        					break;
        				case "-meet_participant" :{
        					String[] para = list[2].split(",");
        					System.out.println("For a Participant_id = " + para[1].trim() + " and Meet = " + para[0].trim() + ", display the Heat Sheet:");
        					Disp dp = new Disp(c);
        					dp.dispMeetParticipant(para[0].trim(), para[1].trim());
        				}
        					break;
        				case "-meet_school_own" :{
        					String[] para = list[2].split(",");
        					System.out.println("For School_id = " + para[1].trim() + " and Meet = " + para[0].trim() + ", display the Heat Sheet limited to just that School's swimmers:");
        					Disp dp = new Disp(c);
        					dp.disMeetSchoolOwn(para[0].trim(), para[1].trim());
        				}
        					break;
        				case "-meet_school_opp" :{
        					String[] para = list[2].split(",");
        					System.out.println("For School_id = " + para[1].trim() + " and Meet = " + para[0].trim() + ", display the Heat Sheet limited to School's competing swimmers:");
        					Disp dp = new Disp(c);
        					dp.disMeetSchoolOpp(para[0].trim(), para[1].trim());
        				}
        					break;
        				case "-meet_event" :{
        					String[] para = list[2].split(",");
        					System.out.println("For Event_id = " + para[1].trim() + " and Meet = " + para[0].trim() + ", display all results sorted by time:");
        					Disp dp = new Disp(c);
        					dp.disMeetEvent(para[0].trim(), para[1].trim());
        				}
        					break;
        				case "-meet_allSchool" :{
        					String para = list[2].trim();
        					System.out.println("For Meet = " + para + ", display the scores of each school, sorted by scores:");
        					Disp dp = new Disp(c);
        					dp.disMeetSchoolAll(para);
        				}
        					break;
        				default:
        					System.out.println("Syntax error, input -h to see help manuel.");
        					break;
        				}	
        			}
        				break;
        			}
        		}
            }
        }

    }
}  