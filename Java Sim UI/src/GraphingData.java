

	import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
	 
	public class GraphingData extends JPanel {
		double[] data;
		double[] alldata;
	    int[] test = {
		        21, 14, 18, 03, 86, 88, 74, 87, 54, 77,
		        61, 55, 48, 60, 49, 36, 38, 27, 20, 18
		    };
	    final int PAD = 20;
		private double[]pointset;
		private int pointviewsize =8;
		private String lablename;
	    public GraphingData(String lable ,int windowsW,int windowsH,double[] pts, int startstep, int viewszie) {
			pointset = pts;
			pointviewsize= viewszie;
			alldata = extentCurrentarray(pts);
			lablename = lable;
			//data = makeCurrentarray(alldata,startstep);
			//setCurrentDisplay(startstep);
			setPreferredSize(new Dimension( windowsW, windowsH ));
			//data =  Arrays.copyOf(data, data.length - startstep);
			//setBorder(BorderFactory.createLineBorder(Color.black));
			reSziePoint(startstep);
		  }
	    public void reSziePoint(int startstep){
	    	//data =  Arrays.copyOf(test,startstep);
	    	data = makeCurrentarray(alldata,startstep);
	    	repaint();
	    	
	    }
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        int w = getWidth();
	        int h = getHeight();
	        // Draw ordinate.
	        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
	        // Draw abcissa.
	        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
	        // Draw labels.
	        Font font = g2.getFont();
	        FontRenderContext frc = g2.getFontRenderContext();
	        LineMetrics lm = font.getLineMetrics("0", frc);
	        float sh = lm.getAscent() + lm.getDescent();
	        // Ordinate label.
	        String s = lablename;
	        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
	        for(int i = 0; i < s.length(); i++) {
	            String letter = String.valueOf(s.charAt(i));
	            float sw = (float)font.getStringBounds(letter, frc).getWidth();
	            float sx = (PAD - sw)/2;
	            g2.drawString(letter, sx, sy);
	            sy += sh;
	        }
	        // Abcissa label.
	        s = "x axis";
	        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
	        float sw = (float)font.getStringBounds(s, frc).getWidth();
	        float sx = (w - sw)/2;
	        g2.drawString(s, sx, sy);
	        // Draw lines.
	        double xInc = (double)(w - 2*PAD)/(data.length-1);
	        double scale = (double)(h - 2*PAD)/getMax();
	        g2.setPaint(Color.green.darker());
	        for(int i = 0; i < data.length-1; i++) {
	            double x1 = PAD + i*xInc;
	            double y1 = h - PAD - scale*data[i];
	            double x2 = PAD + (i+1)*xInc;
	            double y2 = h - PAD - scale*data[i+1];
	            g2.draw(new Line2D.Double(x1, y1, x2, y2));
	        }
	        // Mark data points.
	        g2.setPaint(Color.red);
	        for(int i = 0; i < data.length; i++) {
	            double x = PAD + i*xInc;
	            double y = h - PAD - scale*data[i];
	            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
	        }
	    }
	 
	    private double getMax() {
	    	double max =0;
	        for(int i = 0; i < data.length; i++) {
	            if(data[i] > max)
	                max = data[i];
	        }
	        return max;
	    }
	    private double[] extentCurrentarray(double [] old) {
	    	double [] newArray = new double[old.length+pointviewsize];
	    	double [] boarder = new double[pointviewsize/2];
	    	System.arraycopy( boarder, 0, newArray, 0, boarder.length);
	    	System.arraycopy( old, 0, newArray, boarder.length, old.length);
	    	System.arraycopy( boarder, 0, newArray, boarder.length+old.length, boarder.length);
	        return newArray;
	    }
	    private double[] makeCurrentarray(double[] ptsmy,int startstep) {
	    	double [] max  = new double[pointviewsize];
	    	max = Arrays.copyOfRange(ptsmy, startstep-1, (startstep+pointviewsize)-1);
	        return max;
	    }
}
