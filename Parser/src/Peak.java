import java.util.ArrayList;
public class Peak {
	private ArrayList<Integer> ID;
	private ArrayList<Double> mass;
	private ArrayList<Double> RT;
	private ArrayList<Double> intensity;
	private String metaData;
	public Peak(String m) {
		metaData = m;
		ID = new ArrayList<Integer>();
		mass = new ArrayList<Double>();
		RT = new ArrayList<Double>();
		intensity = new ArrayList<Double>();
	}
	public String getMeta() {
		return metaData;
	}
	
	public void  addPeak(Integer a,Double m,Double r,Double i) {
		ID.add(a);
		mass.add(m);
		RT.add(r);
		intensity.add(i);
	}
	public int indexOf(int i) {
		return ID.indexOf(i);
	}
	public int size() {	
		return ID.size();
	}
	public Integer getID(int i) {
		return ID.get(i);
	}
	
	public Object getMass(int i) {
		return mass.get(i);
	}
	
	public Object getRT(int i) {
		return RT.get(i);
	}
	
	public Object getIntensity(int i) {
		return intensity.get(i);
	}
	
	
	
	
}
