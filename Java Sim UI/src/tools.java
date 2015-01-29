import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class tools {
		public static float[] loadArrayFromJson(JSONObject map,String index){
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
		
		public static double[] flatten2darray(double[][] arr ){
			List<Double> list = new ArrayList<Double>();
			 for (int i = 0; i < arr.length; i++) {
			        // tiny change 1: proper dimensions
			        for (int j = 0; j < arr[i].length; j++) { 
			            // tiny change 2: actually store the values
			            list.add(arr[i][j]); 
			        }
			    }

			    // now you need to find a mode in the list.

			    // tiny change 3, if you definitely need an array
			    double[] vector = new double[list.size()];
			    for (int i = 0; i < vector.length; i++) {
			        vector[i] = list.get(i);
			    }
				return vector;
			}
				
}
