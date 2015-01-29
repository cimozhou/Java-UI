import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import IO.LoadConfig;
import IO.LoadMat;

import com.jmatio.types.MLArray;
import com.jogamp.opengl.util.Animator;


public class LightSimulateTab {
	private  final JPanel mainpanel;
	int mapszie;
	private static final int startingstep = 1;
	private static final String lightfile = "estlight.mat";
    List<LoadMat> threads;
    List<Map<String, MLArray>> alLlighTset;
    float[] lightVertexList;
    double[][] getAllLightset;
    int stepcount;
    private static final int datapanelwidth = 370;
    private static final int datapanelboarder = 12;
    
	public LightSimulateTab(int panelW, int panelH, List<float[]> map,float[] boundlist) {
		stepcount=startingstep; //set the starting step
		this.mainpanel = new JPanel(new BorderLayout());
	    this.mainpanel.setPreferredSize(new Dimension(panelW, panelH));
	    LoadLightMarker currentset = new LoadLightMarker();
	    JSlider adjuststep = new JSlider(startingstep, currentset.getStep(), startingstep);
		mapszie= map.size();
		GLProfile glprofile = GLProfile.getDefault();
	    GLCapabilities glcapabilities = new GLCapabilities( glprofile );
	    final GLCanvas glcanvas = new GLCanvas( glcapabilities );
	    glcanvas.setBackground(Color.white);
	    VertexArray teststuff= new VertexArray();
	    threads = new ArrayList<LoadMat>();
	    threads.add(new LoadMat(lightfile));
	    LoadLightMarker currentLightVertex = new LoadLightMarker();
	    lightVertexList =new  float[currentset.getStep()];
		for (LoadMat t : threads) {
		    t.start();
		  }
		alLlighTset = new  ArrayList<Map<String, MLArray>>();
		for (LoadMat t : threads) {
		    try {
				t.join();
				alLlighTset.add(t.getLines());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  }
		getAllLightset = currentLightVertex.getAllLightset(alLlighTset);
		
		lightVertexList = currentLightVertex.getLightVertexList();
	    Animator animator = new Animator(glcanvas);
	    glcanvas.addGLEventListener( new GLEventListener() {
	        @Override
	        public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
	            teststuff.init(glautodrawable.getGL().getGL2(), width, height,map,boundlist);
	        }
	        
	        @Override
	        public void init( GLAutoDrawable glautodrawable ) {
	        }
	        
	        @Override
	        public void dispose( GLAutoDrawable glautodrawable ) {
	        }
	        @Override
	        public void display( GLAutoDrawable glautodrawable ) {
	        		List<float[]> lightlist= currentLightVertex.getfileVertexList(lightVertexList, glcanvas.getWidth(), glcanvas.getHeight(), boundlist);
	        		List<float[]> colorlist= currentLightVertex.getCurrentLightList(lightVertexList,getAllLightset,stepcount);
	        		teststuff.drawwithlight(glautodrawable.getGL().getGL2(),lightlist,colorlist);
	        	
	        }	
	    });
	    /*
	    HashMap<String, List<String>> newMap = new HashMap<String, List<String>>();
	    List<String> Lightdata = new ArrayList<String>();
	    Lightdata.add("light.mat");
	    Lightdata.add("light");
	    newMap.put("Light", Lightdata);
	    List<String> Stepdata = new ArrayList<String>();
	    Stepdata.add("stepevent.mat");
	    Stepdata.add("step");
	    newMap.put("Step", Stepdata);
	    */
	    LoadConfig loadconfig = new LoadConfig("config.json");
	    HashMap<String, List<String>> loadMap = new HashMap<String, List<String>>();
	    loadMap = loadconfig.returnlist();
	    
	    
	    SlideDataPanel lightdatapanel = new SlideDataPanel(adjuststep,panelH,stepcount);
	    lightdatapanel.loadData(loadMap);
	    //lightdatapanel.showCombobox(this.mainpanel,adjuststep,panelH,stepcount);
	    
	    
	    adjuststep.addChangeListener(new ChangeListener() {
			  public void stateChanged(ChangeEvent e) {
				  stepcount= ((JSlider) e.getSource()).getValue();
				  glcanvas.display();
				  lightdatapanel.setGraphingData(stepcount);
					//sines.setCycles(((JSlider) e.getSource()).getValue());
				  }
				});
	    this.mainpanel.add(lightdatapanel,BorderLayout.EAST);
	    this.mainpanel.add(adjuststep, BorderLayout.SOUTH);
	    this.mainpanel.add(glcanvas);
	    

	    
	    
	}
	public JPanel getpanel(){
		return this.mainpanel;
	}

}
