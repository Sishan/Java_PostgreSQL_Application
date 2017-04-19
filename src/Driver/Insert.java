package Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Insert {
	private Connection c;
	public Insert(Connection c) {
		this.c = c;
	}
	
	public void insertIntoOrg(String id, String name, boolean is_univ) {
		try{
    		String procedure = "{ call insertOrg(?, ?, ?)}";
    	        CallableStatement statement;
    	        statement = c.prepareCall(procedure);	
    	        statement.setString(1, id);
    	        statement.setString(2, name);
    	        statement.setBoolean(3, is_univ);
    	        statement.execute();
    	        //c.commit();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }
	}
	
	public void insertIntoMeet(String name, Date start_date, int num_days, String org_id) {
		try{
    		String procedure = "{ call insertMeet(?, ?, ?, ?)}";
    	        CallableStatement statement;
    	        statement = c.prepareCall(procedure);	
    	        statement.setString(1, name);
    	        
    	        statement.setDate(2, start_date);
    	        statement.setInt(3 , num_days);
    	        statement.setString(4, org_id);
    	        statement.execute();
//    	        statement
//    	        c.commit();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }
	}
	
	public  void insertIntoParticipant(String id, String gender, String org_id, String name) {
    	try{
    		//Connection conn = getConn();  
    		String procedure = "{ call insertParticipant(?, ?, ?, ?)}";
    	        CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, id);
	        statement.setString(2, gender);
	        statement.setString(3, org_id);
	        statement.setString(4, name);    
    	        //statement.registerOutParameter(1, Types.INTEGER);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
	
	public  void insertIntoStroke(String stroke) {
    	try{
    		//Connection conn = getConn();  
    		String procedure = "{ call insertStroke(?)}";
    	        CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, stroke);
    	        
    	        //statement.registerOutParameter(1, Types.INTEGER);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public  void insertIntoDistance(int distance) {
    	try{
    		String procedure = "{ call insertDistance(?)}";
    	    CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setInt(1, distance);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public  void insertIntoEvent(String id, String gender, int distance) {
    	try{  
    		String procedure = "{ call insertEvent(?, ?, ?)}";
    	    CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, id);
	        statement.setString(2, gender);
	        statement.setInt(3, distance);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public  void insertIntoLeg(int leg) {
    	try{
    		String procedure = "{ call insertLeg(?)}";
    	    CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setInt(1, leg);
    	    statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public  void insertIntoStrokeOf(String event_id, int leg, String stroke) {
    	try{
    		//Connection conn = getConn();  
    		String procedure = "{ call insertStrokeOf(?, ?, ?)}";
    	        CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, event_id);
	        statement.setInt(2, leg);
	        statement.setString(3, stroke);
    	        
    	        //statement.registerOutParameter(1, Types.INTEGER);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public  void insertIntoHeat(String id, String event_id, String meet_name) {
    	try{
    		//Connection conn = getConn();  
    		String procedure = "{ call insertHeat(?, ?, ?)}";
    	        CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, id);
	        statement.setString(2, event_id);
	        statement.setString(3, meet_name);
    	        
    	        //statement.registerOutParameter(1, Types.INTEGER);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    public void insertIntoSwim(String heat_id, String event_id, String participant_id, String meet_name, int leg, Float swimtime) {
    	try{
    		//Connection conn = getConn();  
    		String procedure = "{ call insertSwim(?, ?, ?, ?, ?, ?)}";
    	        CallableStatement statement;
    		statement = c.prepareCall(procedure);	
    		statement.setString(1, heat_id);
	        statement.setString(2, event_id);
	        statement.setString(3, participant_id);
	        statement.setString(4, meet_name);
	        statement.setInt(5, leg);
    	    statement.setFloat(6, swimtime);
    		statement.execute();
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
    }
    
    
}
