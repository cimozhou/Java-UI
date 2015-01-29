import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;

import com.jmatio.types.MLDouble;

import IO.LoadMat;


public class SlideDataPanel extends JPanel {
    private static final int datapanelwidth = 370;
    private static final int datapanelboarder = 12;
    private List<String> panelnamelist;
    private  List<GraphingData> sublist;
    private int startstep;
    private int datapanelH;
    private int datapanelW;
    private static final int datapanelview = 8;
    //private JPanel namePanel;
    public SlideDataPanel(JSlider adjuststep,int panelH,int step){                                    
	        new JPanel(new FlowLayout()); 
	        sublist = new ArrayList<GraphingData>();
	        startstep = step;
	        setPreferredSize(new Dimension(datapanelwidth, 350));// hardCoded sizing
	        setMaximumSize(new Dimension(datapanelwidth, 350));  // hardCoded sizing
	        setMinimumSize(new Dimension(datapanelwidth, 350)); 
	        datapanelH = (panelH/3)-datapanelboarder;
	        datapanelW =  datapanelwidth-datapanelboarder;
	        /*
	        for( int i=0; i < 2; i++ ){
	        	GraphingData test = new GraphingData(datapanelwidth-datapanelboarder,(panelH/3)-datapanelboarder, null, step, 0);
	        	sublist.add(test);
	            add(test);
	        }
			*/

	}
    public void loadData(HashMap<String, List<String>> loaddatadet){
    	panelnamelist = new ArrayList<String>();
    	 for (String key : loaddatadet.keySet()) {
    		 panelnamelist.add(key);
    		 List<String> dataconfig = loaddatadet.get(key);
    		 LoadMat loaddata = new LoadMat(dataconfig.get(0));
    		 loaddata.start();
    		 try {
				loaddata.join();
				List<double[][]> arraylist = new ArrayList<double[][]>();
				MLDouble pointlist = (MLDouble) loaddata.getLines().get(dataconfig.get(1));
				//double[][] pointvalues =pointlist.getArray();
	    		GraphingData test = new GraphingData(key,datapanelW,datapanelH, tools.flatten2darray(pointlist.getArray()), startstep, datapanelview);
		        sublist.add(test);
	    		List<String> value = (List<String>) loaddatadet.get(key);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 

 	        // ...
 	    }
    	 for (GraphingData graph : sublist) {
    		 add(graph);
    	 }
    	
    }
    public void setGraphingData(int step){
    	for (GraphingData graph : sublist) {
    		graph.reSziePoint(step);
    	}
    	
    }
    
}