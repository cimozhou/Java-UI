import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class main {
	 public static void main( String args[] )
	    { 	
	    	//Instantiates class
	    	Maininterface app = new Maininterface();
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
