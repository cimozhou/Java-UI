import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.prism.impl.BufferUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class VertexArray {

  //////////////// Constants /////////////////////////

  // Number of Points in the array.
  final int nbPoints = 20;

  //////////////// Variables /////////////////////////

  // We use a buffer and an array for the vertex data
  // to more compatible with C.
  List <FloatBuffer>  pointsbuffer;
  List <FloatBuffer>  colorsbuffer;
  FloatBuffer points;
  float[] pointsData;
  FloatBuffer colors;
  float[] colorsData;
  int[] sizearray;
  int[] colorsetsarray;
  int[] buffoffsetcount;
  int totalcolorsets;
  int shapecount;
  int totalsize;
  FloatBuffer plocation;
  FloatBuffer pcolors;
  final int mapoffsety = 0;
  final int circlesegment= 100; 
  final int circlesize= 5; 
  
  Random random = new Random();

  ///////////////// Functions /////////////////////////

  public void init ( GL2 gl ,int width,int height,List<float[]> array,float[] edage )
    {
      gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
      gl.glEnableClientState( GL2.GL_COLOR_ARRAY );
      //gl.glEnableClientState( GL2.GL_NORMAL_ARRAY ); 
      gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
      
      gl.glMatrixMode( GL2.GL_PROJECTION );
      gl.glLoadIdentity();

      // coordinate system origin at lower left with width and height same as the window
      GLU glu = new GLU();
      glu.gluOrtho2D( 0.0f, width, height ,0.0f );

      gl.glMatrixMode( GL2.GL_MODELVIEW );
      gl.glLoadIdentity();

      gl.glViewport( 0, 0, width, height );
     
      initArrayData( gl,width, height,array,edage );
      //initArrayDatatest(gl,width, height);
    }
  
  ///////////////// create data ///////////////////////


  private void initArrayData( GL2 gl,int width,int height,List<float[]> arrayset, float[] edge )
    {
      // Create data points on the surface of a cube.
	  shapecount= arrayset.size();
	  sizearray = new int[shapecount];
	  colorsetsarray = new int[shapecount];
	  pointsData =new float[0];
	  colorsData =new float[0];
	  pointsbuffer =new ArrayList <FloatBuffer>();
	  colorsbuffer =new ArrayList <FloatBuffer>();
	  for( int t=0; t < shapecount; t++ )
	 { 
	  int size =0;
      int colorsets=0;
	  size =arrayset.get(t).length;
	  sizearray[t]=size;
	  float[] array= arrayset.get(t);
	  colorsets =(size/2)*3;
	  colorsetsarray[t]=colorsets;
      //int nbValues = nbPoints * 3;
      //int nb2dValues = nbPoints * 2;
	  float[] temppointsData = new float[size];
	  float[] temcolorsData= new float[colorsets];

      for( int i=0; i < size; i++ )
      {
    	  
    	  if ( (i & 1) == 0 ) { 
    		  temppointsData[ i ] = (float)((array[i]-edge[0])/edge[1])*width;
    		  } else { 
    			  
    		temppointsData[ i ] = (float)((edge[3]-array[i])/edge[3])*height-mapoffsety;	  
    		  }
      }
      for( int i=0; i < colorsets; i++ ){
    	  temcolorsData[ i ] = (float)0.0; 
      }
      points = BufferUtil.newFloatBuffer( size );
      points.put( temppointsData, 0, size );
      points.rewind();
      pointsbuffer.add(points);
      colors = BufferUtil.newFloatBuffer( colorsets );
      colors.put( temcolorsData, 0, colorsets );
      colors.rewind();
      colorsbuffer.add(colors);
      
	 }
    }

  //////////////////////// draw /////////////////////////

  public void drawwithparticle( GL2 gl,float[] particallocation, float[] particalcolor)
    {
	  gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	  for (int i = 0; i < shapecount; i++) { 
		   if (sizearray[i] > 0) 
			   gl.glVertexPointer( 2, GL.GL_FLOAT, 0, pointsbuffer.get(i) );
		   		gl.glColorPointer( 3, GL.GL_FLOAT, 0, colorsbuffer.get(i));
			   gl.glDrawArrays( GL.GL_LINE_LOOP, 0, sizearray[i]/2  );
		}
	  setParticalBuffer(particallocation,particalcolor);
	   gl.glVertexPointer( 2, GL.GL_FLOAT, 0, plocation );
 	   gl.glColorPointer( 3, GL.GL_FLOAT, 0, pcolors);
	   gl.glDrawArrays( GL.GL_POINTS, 0, particallocation.length/2  );
      gl.glFlush();
    }
  public void drawwithlight( GL2 gl,List<float[]> lightvertexlist, List<float[]> lightcolorlist)
  {
	  gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	  for (int i = 0; i < shapecount; i++) { 
		   if (sizearray[i] > 0) 
			   gl.glVertexPointer( 2, GL.GL_FLOAT, 0, pointsbuffer.get(i) );
		   		gl.glColorPointer( 3, GL.GL_FLOAT, 0, colorsbuffer.get(i));
			   gl.glDrawArrays( GL.GL_LINE_LOOP, 0, sizearray[i]/2  );
		}
	  for(int ii = 0; ii < lightvertexlist.size(); ii++) {
	  DrawCircle(gl,lightcolorlist.get(ii),lightvertexlist.get(ii)[0], lightvertexlist.get(ii)[1],circlesize,circlesegment);
	  }
    gl.glFlush();
  }

private void setParticalBuffer(float[] particallocation, float[] particalcolor){
	  plocation = BufferUtil.newFloatBuffer( particallocation.length );
	  plocation.put( particallocation, 0, particallocation.length );
	  plocation.rewind();
	    // Colors.
	  pcolors = BufferUtil.newFloatBuffer( particalcolor.length );
	  pcolors.put( particalcolor, 0, particalcolor.length );
	  pcolors.rewind();
	  
}





private void DrawCircle(GL2 gl, float[] innerColor,float cx, float cy, float r, int num_segments) 
{ 
	float theta = (float) (2 * 3.1415926 / (float)num_segments); 
	float c = (float) Math.cos(theta);//precalculate the sine and cosine
	float s = (float) Math.sin(theta);
	float t;
	float x = r;//we start at angle = 0 
	float y = 0; 
	gl.glBegin(GL.GL_TRIANGLE_FAN);
	gl.glColor3fv(innerColor,0 );
	for(int ii = 0; ii < num_segments; ii++) 
	{ 
		
		gl.glVertex2f(x + cx, y + cy);//output vertex 
        
		//apply the rotation matrix
		t = x;
		x = c * x - s * y;
		y = s * t + c * y;
	} 
	gl.glEnd(); 
}
}