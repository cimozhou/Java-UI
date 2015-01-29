import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jmatio.io.MatFileFilter;
import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;


public class LoadParticle {
	final int partcialcount = 2000;
	final int stepcount =3870;
	float [] pointsData;
	float [] colorData;
	List<double[][]> arraylist;
	
	
	public  float[] getPointList(int width,int height, int step,List<Map<String, MLArray>> allpointset,float []edage) {//not used
		pointsData = new float[ partcialcount*2 ];
		/*
		for( int i=0; i < partcialcount; i++ )
	    {
			pointsData[ i ] = ((float)Math.random()*width)/15+step*30;
			pointsData[ i+1 ] = ((float)Math.random()*height)/15+step*30;
	    }
		*/
		MLDouble mlarryx = (MLDouble) allpointset.get(0).get("xvalues");
		double[][] xvalues =mlarryx.getArray();
		
		MLDouble mlarryy = (MLDouble) allpointset.get(1).get("yvalues");
		double[][] yvalues =mlarryy.getArray();
		
		for( int i=0; i < partcialcount*2; i++ )
	    {
			if ( (i & 1) == 0 ) { 
				pointsData[ i ] = (float)((xvalues[step][i/2]-edage[0])/edage[1])*width;
	    		  } else {   
	    	    pointsData[ i ] = (float)((yvalues[step][i/2]-edage[2])/edage[3])*height;	  
	    		  }
	    }
		
		
		return pointsData;
	}
	
	public  float[] getCurrentPointList(int width,int height, int step, List<double[][]> arrayliest,float []edage){
		pointsData = new float[ partcialcount*2 ];
		double[][] xvalues =arrayliest.get(0);
		double[][] yvalues =arrayliest.get(1);
		for( int i=0; i < partcialcount*2; i++ )
	    {
			if ( (i & 1) == 0 ) { 
				pointsData[ i ] = (float)((xvalues[step][i/2]-edage[0])/edage[1])*width;
	    		  } else {   
	    	    pointsData[ i ] = (float)((yvalues[step][i/2]-edage[2])/edage[3])*height;	  
	    		  }
	    }
		return pointsData;
	}
	public  List<double[][]> getAllPointset(List<Map<String, MLArray>> allpointset){
		List<double[][]> arraylist = new ArrayList<double[][]>();
		MLDouble mlarryx = (MLDouble) allpointset.get(0).get("xvalues");
		double[][] xvalues =mlarryx.getArray();
		
		MLDouble mlarryy = (MLDouble) allpointset.get(1).get("yvalues");
		double[][] yvalues =mlarryy.getArray();
		arraylist.add(xvalues);
		arraylist.add(yvalues);
		return arraylist;
		
	}
	public  float[] getPointColorlist() {
		colorData = new float[ partcialcount*3 ];
		for( int i=0; i < partcialcount*3; i++ )
	    {
			colorData[ i ] = (float)0.0;
	    }
		return colorData;
	}
	public  int getStep(){
		return stepcount;
	}
}
