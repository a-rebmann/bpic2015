package bpi2015;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

import bpi2015.model.Activity;
import bpi2015.model.Case;

public class Statistics {
	
	private DataSet ds;

	public Statistics(DataSet ds) {
		super();
		this.setDs(ds);
	}
	
	public Map<String, Integer> countPermitsByType(){
		Map<String,Integer> res = new HashMap<>();
		Map<Long, Case> cases = this.ds.getCases();
		for(Entry<Long,Case> e : cases.entrySet()){
			String[] types = e.getValue().getActivities().get(0).getKindsOfPermit();
			for(int i = 0; i<types.length;i++){
				if(res.containsKey(types[i]))
					res.put(types[i], (res.get(types[i])+1));
				else{
					res.put(types[i], 1);
				}
			}
		}
		return res;
	}
	
	public Map<Long, Map<String,Integer>> countRework(){
		Map<Long, Map<String,Integer>> res = new HashMap<>();
		Map<Long, Case> cases = this.ds.getCases();
		for(Entry<Long,Case> e : cases.entrySet()){
			res.put(e.getKey(), new HashMap<>());
			for (Activity a : e.getValue().getActivities()){
				if(!res.get(e.getKey()).containsKey(a.getActivityNameEN())){
					res.get(e.getKey()).put(a.getActivityNameEN(), 1);
				}else{
					res.get(e.getKey()).put(a.getActivityNameEN(), res.get(e.getKey()).get(a.getActivityNameEN())+1);
				}
			}
			
		}
		return res;
	}
	
	public void printRework(String path){
			Map<String, Integer> absoluteFequencyMoreThan2=new HashMap<>();
			Map<String, Integer> absoluteFequencyMoreThan1=new HashMap<>();
			Map<String, Double> reworkRatio=new HashMap<>();
			try {
				CSVWriter writer=  new CSVWriter(new FileWriter(path), ',');
				String[] header = "CaseId#Activity#Count#Log".split("#");
				writer.writeNext(header);
					Map<Long, Map<String,Integer>> countRework=this.countRework();
					double c = 0d;
					for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
						c++;
						for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(!absoluteFequencyMoreThan1.containsKey(a.getKey())){
								absoluteFequencyMoreThan1.put(a.getKey(), 1);
								reworkRatio.put(a.getKey(), 1d/c);
							}
							else{
								absoluteFequencyMoreThan1.put(a.getKey(), absoluteFequencyMoreThan1.get(a.getKey())+1);
								reworkRatio.put(a.getKey(), new Double(absoluteFequencyMoreThan1.get(a.getKey())/ c
										));
							
							}
							if(a.getValue()>1){
								if(!absoluteFequencyMoreThan2.containsKey(a.getKey()))
									absoluteFequencyMoreThan2.put(a.getKey(), 1);
								else
									absoluteFequencyMoreThan2.put(a.getKey(), absoluteFequencyMoreThan2.get(a.getKey())+1);
								String[] entries = (e.getKey()+","+a.getKey()+","+a.getValue()+","+this.ds.getNumber()).split(",");
								writer.writeNext(entries);
							}
						}	
				}
				 writer.close();
				 System.out.println("Cases with more than 1: "+countRework.size());
				 int moreThan2 = 0;
				 for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
					 for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(a.getValue()>2){
								moreThan2++;
							}
				 }}
				 int moreThan3 = 0;
				 for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
					 for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(a.getValue()>3){
								moreThan3++;
							}
				 }
				 }
				 int moreThan4 = 0;
				 for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
					 for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(a.getValue()>4){
								moreThan4++;
							}
				 }
				 }
				 int moreThan5 = 0;
				 for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
					 for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(a.getValue()>5){
								moreThan5++;
							}
				 }
				 }
				 int moreThan6 = 0;
				 for(Entry<Long,Map<String,Integer>> e : countRework.entrySet()){
					 for(Entry<String,Integer> a : e.getValue().entrySet()){
							if(a.getValue()>6){
								moreThan6++;
							}
				 }
				 }
				 System.out.println("Cases with more than 2: "+moreThan2);
				 System.out.println("Cases with more than 3: "+moreThan3);
				 System.out.println("Cases with more than 4: "+moreThan4);
				 System.out.println("Cases with more than 5: "+moreThan5);
				 System.out.println("Cases with more than 6: "+moreThan6);
				for(Entry<String,Integer> str : absoluteFequencyMoreThan2.entrySet()){
					// if(str.getValue()>100)
						 System.out.println(str.getKey()+" "+str.getValue());
				 }
				for(Entry<String,Double> str : reworkRatio.entrySet()){
					 //if(str.getValue()>1)
						 System.out.println(str.getKey()+" "+str.getValue());
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public Map<String, Long> getAvgDuration(){
		Map<String,Integer> counter = new HashMap<>();
		Map<String,Long> res = new HashMap<>();
		Map<Long, Case> cases = this.ds.getCases();
		for(Entry<Long,Case> e : cases.entrySet()){
			String[] types = e.getValue().getActivities().get(0).getKindsOfPermit();
			for(int i = 0; i<types.length;i++){
				if(counter.containsKey(types[i])){
					counter.put(types[i], (counter.get(types[i])+1));
					res.put(types[i], (res.get(types[i])+e.getValue().getDuration()));
				}
				else{
					counter.put(types[i], 1);
					res.put(types[i], e.getValue().getDuration());
				}
			}
		}
		Map<String, Long> avgs = new HashMap<>();
		for(Entry<String,Long> e : res.entrySet()){
			Long avg = e.getValue() / counter.get(e.getKey());
			avgs.put(e.getKey(), avg);
		}	
		return avgs;
	}

	/**
	 * @return the ds
	 */
	public DataSet getDs() {
		return ds;
	}

	/**
	 * @param ds the ds to set
	 */
	public void setDs(DataSet ds) {
		this.ds = ds;
	}
	
	

}
