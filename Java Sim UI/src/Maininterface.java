import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import IO.LoadMat;

import com.jmatio.types.MLArray;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

public class MainInterface extends JFrame //Inherits JFrame class
{
	//private static final int windowsW= 1200;
	//private static final int windowsH = 800;
	//private static final int panelW= 1100;
	//private static final int panelH = 700;
	List<float[]> map;
	float[] boundlist;
	private static final int boundentry = 4;
	private static final int boarder = 50;
	private static final int boarderinter = 120;
	private int windowsW;
	private int windowsH;
    public MainInterface() 
    {
    	super ("Java simulator");//Adds a title
    	windowsW = Toolkit.getDefaultToolkit().getScreenSize().width;
    	windowsH = Toolkit.getDefaultToolkit().getScreenSize().height;
    	
	    getContentPane().setLayout(new FlowLayout());//Sets layout
	    getContentPane().setBackground( Color.black );//Sets background
      	
      	//Creates an image
      	Icon logoCompany = new ImageIcon( "carcLogoBig.JPG" );
      	JLabel logoPicture = new JLabel(logoCompany);
      	getContentPane().add( logoPicture );//Adds to container   	
		
		SineDraw sines = new SineDraw();
		JSlider adjustCycles = new JSlider(1, 30, 5);
		JPanel panelLogIn = new JPanel(new BorderLayout());
    	//panelLogIn.setPreferredSize(new Dimension(panelW, panelH));
		panelLogIn.setSize(windowsW-boarder, windowsH-boarderinter);
    	panelLogIn.setBorder(BorderFactory.createLineBorder( Color.orange.darker() ));
    	panelLogIn.setBackground( Color.orange );
		panelLogIn.add(sines);
		adjustCycles.addChangeListener(new ChangeListener() {
		  public void stateChanged(ChangeEvent e) {
			sines.setCycles(((JSlider) e.getSource()).getValue());
		  }
		});
		panelLogIn.add(adjustCycles, BorderLayout.SOUTH); 
		
		LoadMap mymap= new LoadMap(); //load map
		map=  new ArrayList<float[]>();
		map=mymap.getMap();
		boundlist = new float[boundentry];
		boundlist=mymap.getBound();
		
		ParticalSimulateTab p1 = new ParticalSimulateTab(windowsW-boarder, windowsH-boarderinter,map,boundlist);
		LightSimulateTab p2 = new LightSimulateTab(windowsW-boarder, windowsH-boarderinter,map,boundlist);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addTab("Tab 1", null, panelLogIn,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Tab 2", null, p1.getpanel(),
                "Particalsim");
        tabbedPane.addTab("Tab 3", null, p2.getpanel(),
                "Lightsim");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setBackground(Color.white);
        //tabbedPane.setBackgroundAt(1, Color.white);
        //tabbedPane.setBackgroundAt(0, Color.white);
        tabbedPane.setSize(windowsW-boarder, windowsH-boarder);
		getContentPane().add (tabbedPane);
		setSize(windowsW, windowsH-boarder);
		
		//setExtendedState(Frame.MAXIMIZED_BOTH);
		
    	//setSize( windowsW, windowsH );
    	this.setLocationRelativeTo(null);
		this.setVisible(true);
    }
}