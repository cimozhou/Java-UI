import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class main {
	 public static void main( String args[] )
	    { 	
	    	//Instantiates class
		 	//MainInterface app = new MainInterface();
		 	Config app = new Config();
			//Adds window listener
	        app.addWindowListener(
	        	new WindowAdapter() 
	        	{
	            	public void windowClosing( WindowEvent e )
	            	{
	               		System.exit( 0 );//Closes window
	            	}
	        	}
	     	);
		} 
}
