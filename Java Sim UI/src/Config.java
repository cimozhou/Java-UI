import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Config  extends JFrame {
	private static final int windowsW= 600;
	private static final int windowsH = 400;
	 public Config() {
		 	super ("config page");//Adds a title
		    getContentPane().setLayout(new FlowLayout());//Sets layout
		    getContentPane().setBackground( Color.black );//Sets background
	      	
	      	//Creates an image
	      	Icon logoCompany = new ImageIcon( "carcLogoBig.JPG" );
	      	JLabel logoPicture = new JLabel(logoCompany);
	      	getContentPane().add( logoPicture );//Adds to container   	

			JPanel holder= showConfig(this);
			getContentPane().add (holder);
			
	      	setSize( windowsW, windowsH );
	    	this.setLocationRelativeTo(null);
			this.setVisible(true);
	 }
	 
	 private JPanel showConfig(JFrame parent){
		  JPanel namePanel = new JPanel(new FlowLayout());
		  JButton RunSimulator = new JButton("Resumesimulator");
		  RunSimulator.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) { 
		        	 MainInterface app = new MainInterface();
		        	 parent.dispose();
		         }});
		  namePanel.add(RunSimulator);
		  return namePanel;  
	 }
}
