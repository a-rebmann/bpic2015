package bpi2015;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

import bpi2015.model.Activity;
import bpi2015.model.Case;

/**
 * 
 * @author Adrian Rebmann 
 *
 */
public class Writer {
	
	private DataSet[] ds;
	private String [] paths;
	
	public Writer(DataSet[] ds, String[] paths) {
		this.ds = ds;
		this.paths=paths;
	}
	
	public void write(){
		for (int i =0;i<this.paths.length;i++){
			CSVWriter writer;
			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			try {
				writer = new CSVWriter(new FileWriter(paths[i]), ',');
				DataSet d = this.ds[i];
				Map<Long,Case> cases = d.getCases();
				String[] header = "CaseId#Acitvity#Resouce#Timestamp#Type#ActivityName".split("#");
				 writer.writeNext(header);
				for(Entry<Long,Case> e : cases.entrySet()){
					for(Activity a : e.getValue().getActivities()){
						String[] newActivity = a.getName().split("_");
						String withoutPhase = newActivity[0]+"_"+newActivity[1];
						String date = parser.format(new Date(a.getTimestamp()));
						String[] entries = (a.getCaseId()+","+withoutPhase+","+a.getResource()+","+date+","+a.getKindsOfPermitString()+","+a.getActivityNameEN()).split(",");
						writer.writeNext(entries);
					}
				}
			     writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
