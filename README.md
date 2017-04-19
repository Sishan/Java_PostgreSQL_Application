# Simple-Java-Postgres-application

***********************************************************
This README file includes all useful sample input, 
Keep it opened and copy/paste all commands makes life easy
***********************************************************

Before running this application, make sure you have load the sql file using:

					\i /Users/sishanchen/Desktop/CreateTables.sql

Instruction for using this application:
There is an runable .jar file in the package, it should be able to directly run using following command:

Open your terminal, type:

					java -jar /Users/sishanchen/Desktop/Assignment-6/hw6_sc.jar (please replace with the right path)

Once the application lunched,  
0) Connect to your local database:
	*** IMPORTANT *** 
	Input your local database environment parameters using command line:
	Input order: Server,port,dataBase,username,password
	Sample input:
					localhost,5432,postgres,ricedb,postgres
					
					
1) Set load file directory accordingly:
	*** IMPORTANT *** 
	Type: "-i:filepath"
	Sample input: 
					-l:/Users/sishanchen/Desktop/Assignment-6/sampleInputFile.csv


2) Set save SQL tables directory accordingly:
	*** IMPORTANT *** 
	Type: "-s:filepath"
	Sampel input:
					-s:/Users/sishanchen/Desktop/testOutput.csv


Now you will be able to run Driver.java which is the entrance for this application:

3) To get help:
	Type "-h" in the console
	Sample input:
					-h
					
					
4) To insert or update data for specific table:
	Type "-i:tableName", and the application will show what is the required type for input parameters
	Then type "[att]", replace att with table name and attributes
	Sample input:
				   -i:Org							//insert into Org table
				   and then:
				   U111,UCLA,TRUE									
				   
				   -i:Participant					//insert into Participant table
				   and then:
				   P123321,M,U430,Rany								
				   
				   -i:Meet							//insert into Meet table
				   and then:
				   RICE_Summer,2007-05-05,4,U430					
				   
				   -i:Stroke						//insert into Stroke
				   and then:
				   testStyle										
				   
				   -i:Distance						//insert into Distance
				   and then:
				   1000												
				   
				   -i:Event						//insert into Event
				   and then:
				   E1234,M,100										
				   
				   -i:Leg							//insert into Leg
				   and then:
				   5												
				   
				   -i:StrokeOf						//insert into StrokeOf
				   and then:								
				   E1234,2,Freestyle
				   
				   -i:Heat							//insert into Heat
				   and then:
				   1,E0107,RICE_Summer								
				   
				   -i:Swim							//insert into Swim
				   and then:
				   2,E0307,SouthConfed,P246719,2,15.222222			
	
	
Homework query requirements:

5) For a Meet, display a Heat Sheet:
   Type "-disp:-meet:[meet_name]", replace meet_name with the target meet name
   Sample input: 
					-disp:-meet:UT_Meet


6) For a Participant and Meet, display a Heat Sheet limited to just that swimmer, 
   including any relays they are in:
   Type "-disp:-meet_partcipant:[meet_name,participant_id]", replace meet_name and 
   participant with target meet name and participant id
   Sample input: 
					-disp:-meet_participant:SouthConfed,P363719
	
	
7) For a School and Meet, display a Heat Sheet limited to just that School’s swimmers:
   Type "-disp:-meet_school_own:[meet_name,school_id]", replace meet_name and 
   school_id with target meet name and school id
   Sample input:
					-disp:-meet_school_own:SouthConfed,U430


8) For a School and Meet, display just the names of the competing swimmers:
   Type "-disp:-meet_school_opp:[meet_name,school_id]", replace meet_name and 
   school_id with target meet name and school id
   Sample input:   
   					-disp:-meet_school_opp:SouthConfed,U430
					
					
9) For an Event and Meet, display all results sorted by time. Include the heat, 
   lane, swimmer(s) name(s), and rank:
   Type "-disp:-meet_event:[meet_name,event_id]", replace meet_name and event_id
   with meet name and event id
   Sample input:    
   					-disp:-meet_event:SouthConfed,E0307
					
					
10) For a Meet, display the scores of each school, sorted by scores:
	Type: "-disp:-meet_allSchool:[meet-name]", replace meet-name with target meet-name
	Sample input: 
				    -disp:-meet_allSchool:SouthConfed
