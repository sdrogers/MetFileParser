import java.io.*;
import java.util.ArrayList;
//A comment
//another comment
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
	
	public static ArrayList<Peak> readMultiple(String listName) {
		ArrayList<Peak> allPeaks = new ArrayList<Peak>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(listName));
			String line;
			while((line = reader.readLine())!=null) {
				System.out.println("../" + line);
				allPeaks.add(ParseFile("../" + line));
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
				headers += "," + i;
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
		//Peak p = ParseFile("../data_28_weeks_controls_Q00225_3_NEGATIVE.csv");
		ArrayList<Peak> p = readMultiple("../fileList.txt");
		ArrayList<Integer> ID = createGlobalID(p);
		writeFile(p,ID,"test.csv");
	}
}
