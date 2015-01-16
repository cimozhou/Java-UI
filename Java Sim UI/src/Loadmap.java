import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Loadmap {
private static final String filePath = "src/ITB-F2.json";
private static final int offset = 1;
JSONObject jsonObject;
	public  List<float[]> getmap() {
		List<float[]> maplist = new ArrayList<float[]>();  
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			 jsonObject =  (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			JSONObject map = (JSONObject) jsonObject.get("map");
			maplist.add(loadarrayfromjson(map,"outer"));
			for(int i=1; i<(map.size()); i++){
				if ((JSONArray) map.get("intEdge"+Integer.toString(i))!=null){
					maplist.add(loadarrayfromjson(map,"intEdge"+Integer.toString(i)));
				}
			}
			// take the elements of the json array	
			// take each value from the json array separately
			
			// handle a structure into the json object

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return maplist;
	}
	public  float[] getbound() {
		float[] boundlist = new float[4];  
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			 jsonObject =  (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			JSONObject map = (JSONObject) jsonObject.get("map");
			boundlist=loadarrayfromjson(map,"size");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		// get an array from the JSON object
		return boundlist;
	}
	public static  float[] loadarrayfromjson(JSONObject map,String index){
		List<Float> list = new ArrayList<Float>();    
		JSONArray inter= (JSONArray) map.get(index);
		for(int i=0; i<inter.size(); i++){
			//System.out.println("The " + i + " element of the map: "+inter.get(i));
		}
		Iterator i = inter.iterator();
		while (i.hasNext()) {
			JSONArray innerObj = (JSONArray) i.next();
			// take the elements of the json array
			for(int j=0; j<innerObj.size(); j++){
				list.add(Float.parseFloat(innerObj.get(j).toString()));
				//System.out.println("The " + i + " element of the vetx: "+innerObj.get(j));
				Iterator vetex = innerObj.iterator();
			}
			
			
		}
		float[] floatArray = new float[list.size()];
		int t = 0;
		for (Float f : list) {
		    floatArray[t++] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		return floatArray;
	}

}
