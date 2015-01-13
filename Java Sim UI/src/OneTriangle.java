import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.sun.prism.impl.BufferUtil;

public class OneTriangle {
    protected static void setup( GL2 gl2, int width, int height ) {
        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();

        // coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluOrtho2D( 0.0f, width, 0.0f, height );

        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();

        gl2.glViewport( 0, 0, width, height );
    }

    protected static void render( GL2 gl2, int width, int height ) {
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );

        // draw a triangle filling the window
        gl2.glLoadIdentity();
        /*gl2.glBegin( GL.GL_LINE_LOOP );
        gl2.glColor3f( 1, 0, 0 );
        gl2.glVertex2f( 0, 0 );
        gl2.glColor3f( 0, 1, 0 );
        gl2.glVertex2f( width, 0 );
       
        gl2.glVertex2f( width / 2, height );
        gl2.glEnd();*/
        /*
         float[] x= {40, 50, 60, 70, 10, 20, 60, 50, 
           	 0, 10, 50, 40, 0, 30, 20, 10, 
           	 0, 40, 70, 30, 20, 30, 70, 60};
         ByteBuffer byteBuf = ByteBuffer.allocateDirect(x.length * 4); //4 bytes per float
         byteBuf.order(ByteOrder.nativeOrder());
         FloatBuffer buffer = byteBuf.asFloatBuffer();
         buffer.put(x);
         buffer.position(0);
         gl2.glColor3f( 0, 0, 1 );
         gl2.glDrawElements(GL.GL_LINE_LOOP, 24, GL.GL_UNSIGNED_INT, buffer);
         */
         int indices[] = new int[]
        	      { 0, 100, 300, 400 };
        	      IntBuffer indicesBuf = BufferUtil.newIntBuffer(indices.length);
        	      for (int i = 0; i < indices.length; i++)
        	        indicesBuf.put(indices[i]);
        	      indicesBuf.rewind();
        	      gl2.glColor3f( 1, 1, 1 );
        	      gl2.glDrawElements(GL.GL_LINE_LOOP, 4, GL.GL_UNSIGNED_INT, indicesBuf);
        	      gl2.glFlush();
         
    }
}