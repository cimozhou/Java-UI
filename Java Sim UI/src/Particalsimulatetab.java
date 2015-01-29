import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import IO.LoadMat;

import com.jmatio.types.MLArray;
import com.jogamp.opengl.util.Animator;


public class ParticalSimulateTab{
	private  final JPanel mainpanel;
    private static final String mathPathx = "xvalues.mat";
    private static final String mathPathy = "yvalues.mat";
    List<LoadMat> threads;
    List<Map<String, MLArray>> allpointset;
	int mapszie;
	//List<float[]> map;
	List<double[][]> arraylist;
	//float[] boundlist;
	private static final int boundentry = 4;
	private static final int startingstep = 1;
	private static final int defulautset = 5;
    int stepcount;
    double invercount;
    double stepintervel;
    boolean enableplayer = false;
	public ParticalSimulateTab(int panelW, int panelH, List<float[]> map,float[] boundlist) {
    	stepintervel =defulautset;
    	stepcount=startingstep; //set the starting step
    	invercount= (double)startingstep;
    	threads = new ArrayList<LoadMat>();
    	LoadParticle currentset = new LoadParticle();
	    this.mainpanel = new JPanel(new BorderLayout());
	    this.mainpanel.setPreferredSize(new Dimension(panelW, panelH));
	    threads.add(new LoadMat(mathPathx));
		threads.add(new LoadMat(mathPathy));
		for (LoadMat t : threads) {
		    t.start();
		  }
		allpointset = new  ArrayList<Map<String, MLArray>>();
		for (LoadMat t : threads) {
		    try {
				t.join();
				allpointset.add(t.getLines());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  }
		arraylist  = new ArrayList<double[][]>();
		arraylist = currentset.getAllPointset(allpointset);
	 JSlider adjuststep = new JSlider(startingstep, currentset.getStep(), startingstep);
	 /*LoadMap mymap= new LoadMap(); //load map
		map=  new ArrayList<float[]>();
		map=mymap.getMap();
		boundlist = new float[boundentry];
		boundlist=mymap.getBound();*/
		mapszie= map.size();
	  
	GLProfile glprofile = GLProfile.getDefault();
    GLCapabilities glcapabilities = new GLCapabilities( glprofile );
    final GLCanvas glcanvas = new GLCanvas( glcapabilities );
    glcanvas.setBackground(Color.white);
    VertexArray teststuff= new VertexArray();
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
        	//update();
        	//render(glautodrawable);
        	
        	if (enableplayer ==true){
        		if (stepcount < currentset.getStep()){
        			adjuststep.setValue(stepcount);
        			invercount = invercount+stepintervel;
        			stepcount=(int) invercount;
        			
        		}else{
        			if (animator.isAnimating()){
        				animator.stop();
        			}
        		}
        	}
        	if (stepcount < currentset.getStep()){
        		teststuff.drawwithparticle(glautodrawable.getGL().getGL2(),currentset.getCurrentPointList(glcanvas.getWidth(), glcanvas.getHeight(),stepcount,arraylist,boundlist),currentset.getPointColorlist());
        	}
        	}
    });
    adjuststep.addChangeListener(new ChangeListener() {
		  public void stateChanged(ChangeEvent e) {
			  if (enableplayer ==false){
			  stepcount= ((JSlider) e.getSource()).getValue();
			  invercount =(double)stepcount;
			  glcanvas.display();
			  }
				//sines.setCycles(((JSlider) e.getSource()).getValue());
			  }
			});
    this.mainpanel.add(adjuststep, BorderLayout.SOUTH);
    this.mainpanel.add(glcanvas);
    showCombobox(this.mainpanel,animator,glcanvas,adjuststep);
	}
	public JPanel getpanel(){
		return this.mainpanel;
	}
    private void showCombobox(JPanel parentpanel,  Animator animator,GLCanvas glcanvas,JSlider adjuststep){                                    
	      final DefaultComboBoxModel setpcount = new DefaultComboBoxModel();
	      setpcount.addElement((double)0.01);
	      setpcount.addElement((double)0.02);
	      setpcount.addElement((double)0.04);
	      setpcount.addElement((double)0.06);
	      setpcount.addElement((double)0.1);
	      setpcount.addElement((double)1);
	      final JComboBox setCombo = new JComboBox(setpcount);    
	      setCombo.setSelectedIndex(4);
	      ;
	      JScrollPane setListScrollPane = new JScrollPane(setCombo);    
	      JButton Runsimulator = new JButton("Runsimulator");
	      Runsimulator.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) { 
	        	 double data = (double)0.1;
	            if (setCombo.getSelectedIndex() != -1) {                     
	               data = (double) setCombo.getItemAt
	                    (setCombo.getSelectedIndex());          
	            } 
	            //invercount=(double)1;
	            //stepcount=1;
	            glcanvas.display();
	            stepintervel= data;
	            enableplayer = true;
	            animator.start();
	            adjuststep.setValue(1);
	         }
	      }); 
	      JButton Stopsimulator = new JButton("Stopsimulator");
	      Stopsimulator.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) { 
		        	 animator.stop();
		        	 enableplayer = false;
		        	 invercount=(double)1;
		        	 stepcount=1;
		        	 glcanvas.display();
		        	 stepintervel =(double)0.1;
		        	 setCombo.setSelectedIndex(0);
		        	 adjuststep.setValue(1);
		         }});
	      JButton Pauseimulator = new JButton("Pauseimulator");
	      Pauseimulator.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) { 
		        	 animator.pause();
		        	 enableplayer = false;
		         }});
	      JButton Resumesimulator = new JButton("Resumesimulator");
	      Resumesimulator.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) { 
		        	 animator.resume();
		        	 enableplayer = true;
		         }});
	       JPanel namePanel = new JPanel(new FlowLayout());
	       JLabel tatusLabel = new JLabel("Step size",JLabel.CENTER);  
	        namePanel.setPreferredSize(new Dimension(150, 150));// hardCoded sizing
	        namePanel.setMaximumSize(new Dimension(250, 150));  // hardCoded sizing
	        namePanel.setMinimumSize(new Dimension(150, 150)); 
	        namePanel.add(tatusLabel);
	        namePanel.add(setListScrollPane);
	        namePanel.add(Runsimulator);
	        namePanel.add(Stopsimulator);
	        namePanel.add(Pauseimulator);
	        namePanel.add(Resumesimulator);
	        parentpanel.add(namePanel,BorderLayout.EAST);
  }
}
