package bpi2015;

import java.util.Map;
import java.util.Map.Entry;

public class Main {
	public static String dir="/Users/adrianrebmann/Dropbox/PMFallstudie/Datasets/CSV/";
	public static String[] filenames={"BPIC15_1.csv","BPIC15_2.csv","BPIC15_3.csv","BPIC15_4.csv","BPIC15_5.csv"};
	public static String[] writefilenames={"BPIC15_1_new.csv","BPIC15_2_new.csv","BPIC15_3_new.csv","BPIC15_4_new.csv","BPIC15_5_new.csv"};
	
	public static void main(String args[]){
		String[] paths = new String[5];
		for(int i = 0;i<filenames.length;i++){
			paths[i]=dir+filenames[i];
		}
		Reader read = new Reader(paths);
		
		DataSet[] sets = read.read();
		String[] newpaths = new String[5];
		for(int i = 0;i<writefilenames.length;i++){
			newpaths[i]=dir+writefilenames[i];
		}
		Writer write = new Writer(sets,newpaths);
		write.write();
		
		/*
		for(int i = 0; i<sets.length;i++){
			System.out.println("Log: "+sets[i].getNumber() + " "+ sets[i].getCases().size()+" "+sets[i].getMeanDuration());
			Statistics s = new Statistics(sets[i]);
			
			Map<String,Integer> count=s.countPermitsByType();*/
			/*for(Entry<String,Integer> e : count.entrySet()){
				System.out.println(e.getKey()+" "+e.getValue());
			}*/
			/*Map<String, Long> avgs = s.getAvgDuration();
			System.out.println("----------------------------------");
			for(Entry<String,Long> e : avgs.entrySet()){
				System.out.println(e.getKey()+" "+e.getValue());
			}
		}**/
	}
	
}
