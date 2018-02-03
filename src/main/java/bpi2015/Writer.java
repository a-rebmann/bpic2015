package bpi2015;

/**
 * Class for writing to the filesystem
 */
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	
	public void writeCases(String path){
		try {
			CSVWriter writer=  new CSVWriter(new FileWriter(path), ',');
			String[] header = "CaseId#Resouces#Duration#Start#End#Type#Status#Monitoring#Responsible#Log".split("#");
			writer.writeNext(header);
			for (int i =0;i<this.paths.length;i++){
				SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
					DataSet d = this.ds[i];
					Map<Long,Case> cases = d.getCases();
					for(Entry<Long,Case> e : cases.entrySet()){
						Long caseID = e.getKey();
						String resources = "";
						List<Long> reso = new ArrayList<>();
		
						List<Long> moni = new ArrayList<>();
						Long duration = e.getValue().getDuration();
						Long dur = duration/3600000;
						String typesOfPermit = "";
						String status = "";
						String monitoring = "";
						String responsible = "";
						for(Activity a : e.getValue().getActivities()){
							if(!reso.contains(a.getResource()))
								reso.add(a.getResource());
							if(!moni.contains(a.getMonitoring()))
								moni.add(a.getMonitoring());
							status=a.getStatus();
							typesOfPermit=a.getKindsOfPermitString();
							responsible=a.getResponsible().toString();
						}
						for(Long l : reso){
							resources += l.toString()+"#";
						}
						for (Long l :moni){
							monitoring += l.toString()+"#";
						}
						String s = parser.format(new Date(+e.getValue().getStart()));
						String en = parser.format(new Date(+e.getValue().getEnd()));
						String[] entries = (caseID+","+resources+","+dur+","+s+","+en+","+typesOfPermit+","+status+","+monitoring+","+responsible+","+i).split(",");
						writer.writeNext(entries);
					}	
				}
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
