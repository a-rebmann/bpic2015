package bpi2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
