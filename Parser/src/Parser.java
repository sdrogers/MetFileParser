import java.io.*;
import java.util.ArrayList;

public class Parser {


	
	public static Peak ParseFile(String fileName) {
		Peak peaks = new Peak(fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			System.out.println("Opened " + fileName);
			
			
			String line = reader.readLine(); // Reads the headers
			while((line = reader.readLine())!=null) {
				String[] bits = line.split(",");
				peaks.addPeak(Integer.parseInt(bits[0]),
						Double.parseDouble(bits[1]),
						Double.parseDouble(bits[2]),
						Double.parseDouble(bits[3]));
			}
			
			System.out.println("Read " + peaks.size() + " peaks.");
			
			reader.close();
			
		} catch(IOException e) {
			System.err.println("Can't open file");
		}
		
		return peaks;
	}
	
	public static ArrayList<Peak> readMultiple(String path) {
		ArrayList<Peak> allPeaks = new ArrayList<Peak>();
		try {
			String fname = path + "fileList.txt";
			BufferedReader reader = new BufferedReader(new FileReader(fname));
			String line;
			while((line = reader.readLine())!=null) {
				System.out.println(path + line);
				allPeaks.add(ParseFile(path + line));
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("Unable to open");
			System.exit(0);
		}
		return allPeaks;
	}
	
	public static ArrayList<Integer> createGlobalID(ArrayList<Peak> allPeaks) {
		Peak current = allPeaks.get(0);
		ArrayList<Integer> globalID = new ArrayList<Integer>();
		for(int i=0;i<current.size();i++) {
			globalID.add(current.getID(i));
		}
		for(int i=1;i<allPeaks.size();i++) {
			current = allPeaks.get(i);
			for(int j=0;j<current.size();j++) {
				int a = globalID.indexOf(current.getID(j));
				if(a==-1) {
					globalID.add(current.getID(j));
				}
			}
			System.out.println("Length: " + globalID.size());	
		}
		
		return globalID;
	}
	public static void writeFile(ArrayList<Peak> p,ArrayList<Integer> ID,String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			String headers = "ID";
			for(int i=0;i<p.size();i++) {
				//System.out.println(p.get(i).getMeta());
				String temp = p.get(i).getMeta();
				if(temp.contains("cases")) {
					headers += "," + 1;
				} else {
					headers += "," + 0;
				}
			}
			writer.write(headers + "\n");
			headers = "ID";
			for(int i=0;i<p.size();i++) {
				String temp = p.get(i).getMeta();
				if(temp.contains("28_weeks")) {
					headers += "," + 1;
				} else {
					headers += "," + 0;
				}
			}
			writer.write(headers + "\n");
			for(int i=0;i<ID.size();i++){
				writer.write("" + ID.get(i));
				Integer pID = ID.get(i);
				for(int j=0;j<p.size();j++) {
					Integer pos = p.get(j).indexOf(pID);
					if(pos == -1) {
						writer.write(",nan");
					} else {
						writer.write("," + p.get(j).getIntensity(pos));
					}
				}
				writer.write("\n");
			}
			writer.close();
		}catch(IOException e) {
			System.out.println("Failed to open file");
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(args.length);
		String path = args[0];
		//Peak p = ParseFile("../data_28_weeks_controls_Q00225_3_NEGATIVE.csv");
		String fname = path + "fileList.txt";
		System.out.println(fname);
		ArrayList<Peak> p = readMultiple(path);
		ArrayList<Integer> ID = createGlobalID(p);
		writeFile(p,ID,"/Users/simonrogers/Dropbox/Meta_clustering/Human_PE/Positive_processed.csv");
	}
}
