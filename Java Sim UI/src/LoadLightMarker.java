import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;


public class LoadLightMarker {
	final int stepcount =3870;
	private static final String filePath = "ITB-F2.json";
	 final int mapoffsety = 0;
	 final float[] defaultcolor = {0.7f, 0.7f,0.7f};
	 final float[] oncolor = {1.0f, 1.0f,0.0f};
	public  int getStep(){
		return stepcount;
	}
	
	public  float[] getLightVertexList(){
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject map  =  (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			//JSONObject map = (JSONObject) jsonObject.get("map");
			float[] loadinlight = tools.loadArrayFromJson(map,"light");	
			 return loadinlight;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return null;
	}
	public  List<float[]> getfileVertexList(float[] orignllist, int width,int height,float[] edge){
		//float[] temppointsData = new float[orignllist.length];
		List<float[]> lightlist = new ArrayList<float[]>();
		 for( int i=0; i < (orignllist.length)/2; i++ )
	      {
			 	float[] temppointsData = new float[2];
	    		  temppointsData[0]= (float)((orignllist[i*2]-edge[0])/edge[1])*width;
	    		  temppointsData[1] = (float)((edge[3]-(orignllist[i*2+1]))/edge[3])*height-mapoffsety;	  
	    		  lightlist.add(temppointsData);
	      }
		 return lightlist;
	}
	public  List<float[]> getCurrentLightList(float[] Vetrx, double[][]allsteps, int curentstep){
		List<float[]> colorlist = new ArrayList<float[]>();
		int lenght = allsteps.length;
		double templighton = -1;
		for( int i=0; i < lenght; i++ ){
			if (allsteps[i][0] == curentstep){
				if (allsteps[i][1]>0){
					templighton = allsteps[i][1];
					break;
				}
			}		
		}
		
		for( int i=0; i < (Vetrx.length)/2; i++ ){
			float[] tempcolorData = new float[3];
			tempcolorData = defaultcolor;
			colorlist.add(tempcolorData);
		}
		if (templighton != -1){
			colorlist.set((int) templighton, oncolor);
		}
		return colorlist;
		
	}
	
	
	public  double[][] getAllLightset(List<Map<String, MLArray>> allpointset){
		MLDouble mlarrylight = (MLDouble) allpointset.get(0).get("t");
		double[][] lvalues =mlarrylight.getArray();
		return lvalues;
		
	}
	
}
