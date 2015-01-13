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
  final int mapoffsety = 70;
 
  
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


  private void initArrayData( GL2 gl,int width,int height,List<float[]> arrayset, float[] edage )
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
    		  temppointsData[ i ] = (float)((array[i]-edage[0])/edage[1])*width;
    		  } else { 
    			  
    		temppointsData[ i ] = (float)((edage[3]-array[i])/edage[3])*height-mapoffsety;	  
    		  }
      }
      //pointsData = tools.concatfloat(pointsData, temppointsData);
      for( int i=0; i < colorsets; i++ ){
    	  temcolorsData[ i ] = (float)0.0; 
      }
      //colorsData = tools.concatfloat(colorsData, temcolorsData);
      points = BufferUtil.newFloatBuffer( size );
      points.put( temppointsData, 0, size );
      points.rewind();
      pointsbuffer.add(points);
      colors = BufferUtil.newFloatBuffer( colorsets );
      colors.put( temcolorsData, 0, colorsets );
      colors.rewind();
      colorsbuffer.add(colors);
      
	 }
	  /*buffoffsetcount= tools.setintarrayoffset(sizearray);
      // Points.
	  //totalsize= tools.addintarry(sizearray);
      points = BufferUtil.newFloatBuffer( totalsize );
      points.put( pointsData, 0, totalsize );
      points.rewind();
      gl.glVertexPointer( 2, GL.GL_FLOAT, 0, points );
      // Colors.
      totalcolorsets= tools.addintarry(colorsetsarray);
      colors = BufferUtil.newFloatBuffer( totalcolorsets );
      colors.put( colorsData, 0, totalcolorsets );
      colors.rewind();
      gl.glColorPointer( 3, GL.GL_FLOAT, 0, colors );*/
    }

  //////////////////////// draw /////////////////////////
  private void initArrayDatatest( GL2 gl,int width,int height)
  {
    // Create data points on the surface of a cube.
    int nbValues = nbPoints * 3;
    int n2bValues = nbPoints * 2;
    pointsData = new float[ n2bValues ];
    colorsData = new float[ nbValues ];
    for( int i=0; i < nbPoints; i++ )
    {
	pointsData[ i ] = (float)Math.random()*width;
	pointsData[ i+1 ] = (float)Math.random()*height;

	colorsData[ i ] = (float)Math.random();
	colorsData[ i+1 ] = (float)Math.random();
	colorsData[ i+2 ] = (float)Math.random();
    }
    // Points.
    points = BufferUtil.newFloatBuffer( n2bValues );
    points.put( pointsData, 0, n2bValues );
    points.rewind();
    
    gl.glVertexPointer( 2, GL.GL_FLOAT, 0, points );
    // Colors.
    colors = BufferUtil.newFloatBuffer( nbValues );
    colors.put( colorsData, 0, nbValues );
    colors.rewind();
    gl.glColorPointer( 3, GL.GL_FLOAT, 0, colors );
  }
  private void setparticalbuffer(float[] particallocation, float[] particalcolor){
	  plocation = BufferUtil.newFloatBuffer( particallocation.length );
	  plocation.put( particallocation, 0, particallocation.length );
	  plocation.rewind();
	    // Colors.
	  pcolors = BufferUtil.newFloatBuffer( particalcolor.length );
	  pcolors.put( particalcolor, 0, particalcolor.length );
	  pcolors.rewind();
	  
  }
  
  
  
  public void draw( GL2 gl,float[] particallocation, float[] particalcolor)
    {
	  gl.glClear(GL.GL_COLOR_BUFFER_BIT);

      // draw a triangle filling the window
	
      //gl.glColor3f( 0f, 1f, 0f ); 
//       gl.glBegin( GL.GL_POINTS ); {
// 	for( int i=0; i < nbPoints; i++ ) 
// 	{
// 	  gl.glVertex3fv( pointsData, i*3 );
// // 	  gl.glVertex3f( points.get( i*3 ),
// // 			 points.get( i*3 +1),
// // 			 points.get( i*3 +2) );

// 	  //gl.glArrayElement( i );
// 	}      
//       } gl.glEnd();
	  for (int i = 0; i < shapecount; i++) { 
		   if (sizearray[i] > 0) 
			   gl.glVertexPointer( 2, GL.GL_FLOAT, 0, pointsbuffer.get(i) );
		   		gl.glColorPointer( 3, GL.GL_FLOAT, 0, colorsbuffer.get(i));
			   gl.glDrawArrays( GL.GL_LINE_LOOP, 0, sizearray[i]/2  );
		}
	  setparticalbuffer(particallocation,particalcolor);
	   gl.glVertexPointer( 2, GL.GL_FLOAT, 0, plocation );
 	   gl.glColorPointer( 3, GL.GL_FLOAT, 0, pcolors);
	   gl.glDrawArrays( GL.GL_POINTS, 0, particallocation.length/2  );
	  
      //gl.glDrawArrays( GL.GL_LINE_LOOP, 0, totalsize/2  );
      gl.glFlush();
    }

}
