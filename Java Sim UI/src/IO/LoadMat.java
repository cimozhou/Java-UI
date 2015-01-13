package IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.jmatio.io.MatFileFilter;
import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;

public class LoadMat extends Thread {
	private  final String filePath;
	Map<String, MLArray> pointset;
	 public LoadMat(String filePath) {
		    this.filePath = filePath;
		  }
	  @Override
	  public void run() {
	    // Read file here (populate `lines`)..
		  try {
				//MatFileFilter filter = new MatFileFilter();
				//filter.addArrayName( "compress_heading" );
				MatFileReader mfr = new MatFileReader( filePath);
				pointset = mfr.getContent();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  }
	  public Map<String, MLArray> getLines() {
		    return pointset;
		  }
	}
