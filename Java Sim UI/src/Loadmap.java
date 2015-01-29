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
public class LoadMap {
private static final String filePath = "ITB-F2.json";
private static final int offset = 1;
JSONObject jsonObject;
	public  List<float[]> getMap() {
		List<float[]> maplist = new ArrayList<float[]>();  
		try {
			// read the json file
			//FileReader r = new FileReader(main.class.getClassLoader().getResource(filePath).getPath().replaceAll("%20", " "));
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject map =  (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			//JSONObject map = (JSONObject) jsonObject.get("map");
			maplist.add(tools.loadArrayFromJson(map,"outer"));
			for(int i=1; i<(map.size()); i++){
				if ((JSONArray) map.get("intEdge"+Integer.toString(i))!=null){
					maplist.add(tools.loadArrayFromJson(map,"intEdge"+Integer.toString(i)));
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
	public  float[] getBound() {
		float[] boundlist = new float[4];  
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject map  =  (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			//JSONObject map = (JSONObject) jsonObject.get("map");
			boundlist=tools.loadArrayFromJson(map,"size");	
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
}
