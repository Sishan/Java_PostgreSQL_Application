package Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Disp {
	private Connection c;
	public Disp(Connection c) {
		this.c = c;
	}
	public void dispMeet(String meetName)  {
		String procedure = "{ call dispMeet(?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.execute();
			System.out.println("\n----------------------------------------------------------------------------------------------------");
			System.out.printf("%10s%15s%15s%15s%20s%15s%10s", "heat_id |", "event_id |", "swimmer_id |", "org_name |", "indivudual_time |", "relay_time |", "rank |");
			System.out.println("\n----------------------------------------------------------------------------------------------------");
			ResultSet rs = statement.getResultSet();
			  while (rs.next()){
				String id = rs.getString("Heat_id");
				String eventId = rs.getString("Event_id");
				String participantId = rs.getString("Participant_id");
				String orgName = rs.getString("Org_Name");
				Float time = rs.getFloat("Individual_Time");
				Float relay_time = rs.getFloat("Relay_Time");
				int rank = rs.getInt("Rank");
				System.out.printf("%10s%15s%15s%15s%20s%15s%10s", id+" |", eventId + " |", participantId + " |", orgName + " |", time + " |", relay_time + " |", rank + " |");
				System.out.println("\n----------------------------------------------------------------------------------------------------");
			}	
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	

	}
	
	public void dispMeetParticipant(String meetName, String participant_id) {
		String procedure = "{ call dispMeetParticipant(?, ?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.setString(2, participant_id);
			statement.execute();
			System.out.println("\n-------------------------------------------------------------------------------------");
			System.out.printf("%10s%15s%15s%20s%15s%10s", "heat_id |", "event_id |", "org_name |", "indivudual_time |", "relay_time |", "rank |");
			System.out.println("\n-------------------------------------------------------------------------------------");
			ResultSet rs = statement.getResultSet();
	        while (rs.next()){
				String id = rs.getString("Heat_id");
				String eventId = rs.getString("Event_id");
				String orgName = rs.getString("Org_Name");
				Float time = rs.getFloat("Individual_Time");
				Float relay_time = rs.getFloat("Relay_Time");
				int rank = rs.getInt("Rank");
				System.out.printf("%10s%15s%15s%20s%15s%10s", id+" |", eventId + " |", orgName + " |", time + " |", relay_time + " |", rank + " |");
				System.out.println("\n-------------------------------------------------------------------------------------");
	        }	
	        rs.close();
	        statement.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}	

	}
	
	public void disMeetSchoolOwn(String meetName, String school_id) {
		String procedure = "{ call dispSchoolOwn(?, ?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.setString(2, school_id);
			statement.execute();
			System.out.println("\n--------------------------------------------------------------------------------------");
			System.out.printf("%10s%15s%15s%20s%15s%10s", "heat_id |", "event_id |", "swimmer_id |", "indivudual_time |", "relay_time |", "rank |");
			System.out.println("\n--------------------------------------------------------------------------------------");
			ResultSet rs = statement.getResultSet();
	        while (rs.next()){
	        	String id = rs.getString("heat_id");
	        	String eventId = rs.getString("event_id");
	        	String participant_id = rs.getString("participant_id");
				Float time = rs.getFloat("Individual_Time");
				Float relay_time = rs.getFloat("Relay_Time");
				int rank = rs.getInt("Rank");
				System.out.printf("%10s%15s%15s%20s%15s%10s", id + " |", eventId + " |", participant_id + " |", time + " |", relay_time + " |", rank + " |");
				System.out.println("\n-----------------------------------------------------------------------------------");
	        }
	        rs.close();
	        statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	

	}
	
	public void disMeetSchoolOpp(String meetName, String school_id) {
		String procedure = "{ call dispSchoolOpp(?, ?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.setString(2, school_id);
			statement.execute();
			System.out.println("\n-----------------------------------------------------------------------------------------------");
			System.out.printf("%10s%15s%15s%15s%20s%10s%10s", "heat_id |", "event_id |", "meet_name |", "swimmer_id |", "swimmer_name |",  "leg |", "org_id |");
			System.out.println("\n-----------------------------------------------------------------------------------------------");
			ResultSet rs = statement.getResultSet();
	        while (rs.next()){
	        	String id = rs.getString("heat_id");
	        	String eventId = rs.getString("event_id");
	        	int leg = rs.getInt("leg");
	        	String participant_id = rs.getString("participant_id");
	        	String participant_name = rs.getString("participant_name");
	        	String schoolId = rs.getString("org_id");
	        	System.out.printf("%10s%15s%15s%15s%20s%10s%10s", id + " |", eventId + " |", meetName + " |", participant_id + " |",
	        			participant_name + " |", leg +  " |", schoolId + " |");
				System.out.println("\n-----------------------------------------------------------------------------------------------");
	        }
	        rs.close();
	        statement.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}	   
	}
	
	public void disMeetEvent(String meetName, String event_id)  {
		String procedure = "{ call dispMeetEvent(?, ?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.setString(2, event_id);
			statement.execute();
			System.out.println("\n---------------------------------------------------------------------------------------------------------");
			System.out.printf("%10s%15s%15s%15s%20s%20s%10s", "heat_id |", "event_id |", "swimmer_id |", "swimmer_name |", "indivudual_time |", "relay_time |", "rank |" );
			System.out.println("\n---------------------------------------------------------------------------------------------------------");
			ResultSet rs = statement.getResultSet();
			while(rs.next()){
				int id = rs.getInt("heat_id");
				String participant_id = rs.getString("participant_id");
				String swimmerName = rs.getString("participant_name");
				Float time = rs.getFloat("Individual_Time");
				Float relay_time = rs.getFloat("Relay_Time");
				int rank = rs.getInt("Rank");
				System.out.printf("%10s%15s%15s%15s%20s%20s%10s", id + " |", event_id + " |", participant_id + " |", swimmerName + " |", time + " |", relay_time + " |", rank + " |" );
				System.out.println("\n---------------------------------------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void disMeetSchoolAll(String meetName) {
		String procedure = "{ call dispAllSchool(?)}";
        CallableStatement statement;
		try {
			statement = c.prepareCall(procedure);
			statement.setString(1, meetName);
			statement.execute();
			System.out.println("\n-----------------------------------");
			System.out.printf("%10s%15s%10s", "Org_id |", "Org_name |", "Score |");
			System.out.println("\n-----------------------------------");
			ResultSet rs = statement.getResultSet();
			while(rs.next()){
				String orgId = rs.getString("org_id");
				String orgName = rs.getString("org_name");
				int score = rs.getInt("score");
				System.out.printf("%10s%15s%10s", orgId + " |", orgName + " |", score + " |");
				System.out.println("\n-----------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}