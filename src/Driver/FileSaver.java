package Driver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class FileSaver {
	private Connection c ;
	public FileSaver(Connection c) {
		this.c = c;
	}
	
	public void saveFile(String filepath) throws SQLException, IOException{
		CopyManager copyManager = new CopyManager((BaseConnection) this.c);
		
		// Change the output file location according to your system
		//File file = new File("/Users/sishanchen/Desktop/" + fileName + ".csv");
		File file = new File(filepath);
		
		FileOutputStream fileOutputStream = new FileOutputStream(file, true);
		String orgTable = "*Org\n";
		fileOutputStream.write(orgTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM ORG) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);
		
		String meetTable = "*Meet\n";
		fileOutputStream.write(meetTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM MEET) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);

		String participantTable = "*Participant\n";
		fileOutputStream.write(participantTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM PARTICIPANT) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);

		String strokeTable = "*Stroke\n";
		fileOutputStream.write(strokeTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM STROKE) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);
		
		String distanceTable = "*Distance\n";
		fileOutputStream.write(distanceTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM DISTANCE) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);

		String strokeOfTable = "*StrokeOf\n";
		fileOutputStream.write(strokeOfTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM STROKEOF) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);
		
		String eventTable = "*Event\n";
		fileOutputStream.write(eventTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM EVENT) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);

		String heatTable = "*Heat\n";
		fileOutputStream.write(heatTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM HEAT) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);
		
		String swimTable = "*Swim\n";
		fileOutputStream.write(swimTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM SWIM) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);

		String legTable = "*Leg\n";
		fileOutputStream.write(legTable.getBytes());
		copyManager.copyOut("COPY (SELECT * FROM LEG) TO STDOUT WITH (FORMAT CSV)", fileOutputStream);
		
		System.out.println("Saving file completed!\n");
	}
}
