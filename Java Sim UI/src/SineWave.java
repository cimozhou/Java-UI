import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import IO.LoadMat;

import com.jmatio.types.MLArray;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;



public class SineWave extends JFrame //Inherits JFrame class
{
	//Declares objects from predefined classes
	String strReadClientInfo[];
	JTextField userName;
	JPasswordField userPassword;
	JButton logInButton, newAccountButton;
	int mapszie;
	List<float[]> map;
	float[] boundlist;
	private static final int boundentry = 4;
	private static final int startingstep = 0;
	private static final int windowsW= 1200;
	private static final int windowsH = 800;
	private static final int panelW= 1100;
	private static final int panelH = 700;
    int stepcount;
    private static final String mathPathx = "src/xvalues.mat";
    private static final String mathPathy = "src/yvalues.mat";
    List<LoadMat> threads;
    List<Map<String, MLArray>> allpointset;
	//List<VertexArray> shapelist;
    public SineWave() 
    {
    	super ("Welcome to a big headache");//Adds a title
    	
    	stepcount=startingstep; //set the starting step
    	threads = new ArrayList<LoadMat>();
    	
	    getContentPane().setLayout(new FlowLayout());//Sets layout
	    getContentPane().setBackground( Color.black );//Sets background
      	
      	//Creates an image
      	Icon logoCompany = new ImageIcon( "carcLogoBig.JPG" );
      	JLabel logoPicture = new JLabel(logoCompany);
      	getContentPane().add( logoPicture );//Adds to container   	
		
		SineDraw sines = new SineDraw();
		JSlider adjustCycles = new JSlider(1, 30, 5);
		
		
		JPanel panelLogIn = new JPanel(new BorderLayout());
    	panelLogIn.setPreferredSize(new Dimension(panelW, panelH));
    	panelLogIn.setBorder(BorderFactory.createLineBorder( Color.orange.darker() ));
    	panelLogIn.setBackground( Color.orange );
    	//getContentPane().add( panelLogIn );
		JPanel panelLogIn2 = new JPanel(new BorderLayout());
    	panelLogIn2.setPreferredSize(new Dimension(panelW, panelH));
    	//panelLogIn2.setBorder(BorderFactory.createLineBorder( Color.orange.darker() ));
    	//panelLogIn2.setBackground( Color.blue );
    	
    	//getContentPane().add( panelLogIn2 );
		panelLogIn.add(sines);
		
		loadpartical currentset = new loadpartical();  //test points
		//LoadMat matfile = new LoadMat(mathPath);
		
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
			
		
		
		JSlider adjuststep = new JSlider(startingstep, currentset.getstep(), startingstep);
		adjustCycles.addChangeListener(new ChangeListener() {
		  public void stateChanged(ChangeEvent e) {
			sines.setCycles(((JSlider) e.getSource()).getValue());
		  }
		});
		
		panelLogIn.add(adjustCycles, BorderLayout.SOUTH);

		Loadmap mymap= new Loadmap(); //load map
		map=  new ArrayList<float[]>();
		map=mymap.getmap();
		boundlist = new float[boundentry];
		boundlist=mymap.getbound();
		mapszie= map.size();

		GLProfile glprofile = GLProfile.getDefault();
	    GLCapabilities glcapabilities = new GLCapabilities( glprofile );
	    final GLCanvas glcanvas = new GLCanvas( glcapabilities );
	    glcanvas.setBackground(Color.white);
	    VertexArray teststuff= new VertexArray();
	    
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
            	stepcount=stepcount+10;
            	teststuff.draw(glautodrawable.getGL().getGL2(),currentset.getpointlist(panelW, panelH,stepcount,allpointset,boundlist),currentset.getpointcolorlist());
            }
        });
	    adjuststep.addChangeListener(new ChangeListener() {
			  public void stateChanged(ChangeEvent e) {
				  stepcount= ((JSlider) e.getSource()).getValue();
				  glcanvas.display();
					//sines.setCycles(((JSlider) e.getSource()).getValue());
				  }
				});
	    panelLogIn2.add(adjuststep, BorderLayout.SOUTH);
	    //glcanvas.setSize(600, 400);
	    panelLogIn2.add(glcanvas);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.addTab("Tab 1", null, panelLogIn,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Tab 2", null, panelLogIn2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setBackground(Color.white);
        tabbedPane.setBackgroundAt(1, Color.white);
        tabbedPane.setBackgroundAt(0, Color.white);
		getContentPane().add (tabbedPane);
 	 		
 		//Sets screen size and shows screen
    	setSize( windowsW, windowsH );
    	this.setLocationRelativeTo(null);
		this.setVisible(true);
		//animation
		Animator animator = new Animator(glcanvas);
        animator.start();
    	//show();
    }
}