package bpi2015;

/**
 * Class for writing to the filesystem
 */
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
 * @author  
 *
 */
public class Writer {
	
	private DataSet[] ds;
	private String [] paths;
	
	public Writer(DataSet[] ds, String[] paths) {
		this.ds = ds;
		this.paths=paths;
	}
	
	/**
	 * writes the logs only with some fields and removes the 3 digits from the activity to get to a higher level of abstraction
	 */
	public void write(){
		for (int i =0;i<this.paths.length;i++){
			CSVWriter writer;
			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			try {
				writer = new CSVWriter(new FileWriter(paths[i]), ',');
				DataSet d = this.ds[i];
				Map<Long,Case> cases = d.getCases();
				String[] header = "CaseId#Acitvity#Resouce#Timestamp#Type#ActivityName#Status#Log".split("#");
				 writer.writeNext(header);
				for(Entry<Long,Case> e : cases.entrySet()){
					for(Activity a : e.getValue().getActivities()){
						String[] newActivity = a.getName().split("_");
						String withoutPhase = newActivity[0]+"_"+newActivity[1];
						String date = parser.format(new Date(a.getTimestamp()));
						String[] entries = (a.getCaseId()+","+withoutPhase+","+a.getResource()+","+date+","+a.getKindsOfPermitString()+","+a.getActivityNameEN()+","+a.getStatus()+","+i).split(",");
						writer.writeNext(entries);
					}
				}
			     writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Combines the Logs 
	 * @param path
	 */
	public void combine(String path){
		try {
			CSVWriter writer=  new CSVWriter(new FileWriter(path), ',');
			String[] header = "CaseId#Acitvity#Resouce#Timestamp#Type#ActivityName#Status#Log#Monitoring#Responsible".split("#");
			writer.writeNext(header);
			for (int i =0;i<this.paths.length;i++){
				SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
					DataSet d = this.ds[i];
					Map<Long,Case> cases = d.getCases();
					for(Entry<Long,Case> e : cases.entrySet()){
						for(Activity a : e.getValue().getActivities()){
							String[] newActivity = a.getName().split("_");
							String withoutPhase = newActivity[0]+"_"+newActivity[1];
							String date = parser.format(new Date(a.getTimestamp()));
							String[] entries = (a.getCaseId()+","+a.getName()+","+a.getResource()+","+date+","+a.getKindsOfPermitString()+","+a.getActivityNameEN()+","+a.getStatus()+","+(((i+1)*100000000000L)+a.getCaseId())+","+a.getMonitoring()+","+a.getResponsible()).split(",");
							writer.writeNext(entries);
						}
					}	
				}
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
