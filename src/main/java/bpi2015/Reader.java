package bpi2015;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVReader;

import bpi2015.model.Activity;
import bpi2015.model.Case;

public class Reader {
	private String[] paths;
	
	public Reader(String[] paths){
		this.paths=paths;
	}
	
	public DataSet[] read(){
		DataSet[] res = new DataSet[5];
		SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		for(int i=0; i<paths.length;i++){
			DataSet currentLog = new DataSet(i+1);
			Map<Long, List<Activity>> cases = new HashMap<>();
			String currentPath = paths[i];
			try {
				CSVReader reader = new CSVReader(new FileReader(currentPath), ',');
				String [] nextLine;
				String[] header = reader.readNext();
			     while ((nextLine = reader.readNext()) != null) { 
			    	 Long caseId = Long.parseLong(nextLine[0]);
			    	 String name = nextLine[1];
			    	 Long resource = Long.parseLong(nextLine[2]);
			    	 String time = nextLine[3];
			    	 Date date=null;
			    	 try {
						date = parser.parse(time);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    	 String actionCode = nextLine[4];
			    	 String activityNameEN = nextLine[19];
			    	 String[] kindOfPermit = nextLine[15].split(",");
			    	 Activity a = new Activity(caseId,name,resource,date.getTime(),actionCode,activityNameEN);
			    	 a.setKindsOfPermit(kindOfPermit);
			    	 if(cases.containsKey(caseId))
			    		 cases.get(caseId).add(a);
			    	 else{
			    		 cases.put(caseId, new ArrayList<>());
			    		 cases.get(caseId).add(a);
			    	 }
			     }
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(Entry<Long, List<Activity>> e : cases.entrySet()){
				Case c = new Case(e.getKey());
				c.setActivities(e.getValue());
				currentLog.addCase(c);
			}
			res[i]=currentLog;
		}
		System.out.println(res.length);
		for(int i = 0; i<res.length;i++){
			System.out.println(res[i].toString());
		}
		return res;
	}

}
