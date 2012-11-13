package com.example.gpu_cw1;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.FloatMath;
import android.util.Log;

public class MyGL20Renderer implements GLSurfaceView.Renderer {
	private ArrayList<Triangle> shapes = new ArrayList<Triangle>();
	
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    
    float angle = 0;
	
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		unused.glEnable(GL10.GL_DEPTH_TEST);

		// floor
		shapes.add(new Triangle(
				new float[] {-1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f},
				new float[] {0.5f, 0.5f, 0.5f, 1.0f}));
		shapes.add(new Triangle(
				new float[] {-1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 0.0f},
				new float[] {0.5f, 0.5f, 0.5f, 1.0f}));

		// triangles
		shapes.add(new Triangle(
				new float[] {1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 2.0f},
				new float[] {1.0f, 1.0f, 0.0f, 0.5f}));
		
		shapes.add(new Triangle(
				new float[] {-1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2.0f},
				new float[] {0.0f, 0.0f, 1.0f, 0.5f}));
		
		shapes.add(new Triangle(
				new float[] {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2.0f},
				new float[] {0.0f, 1.0f, 0.0f, 0.5f}));
		
		shapes.add(new Triangle(
				new float[] {1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 2.0f},
				new float[] {1.0f, 0.0f, 0.0f, 0.5f}));
		
	}

	public void onDrawFrame(GL10 unused) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        angle = (angle + 1) % 360;
        float camerah = 5 * FloatMath.sin(angle*(float)Math.PI/180f);
        
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 3.0f, 3.0f, camerah, 0f, 0f, 0f, 0f, 0f, 1.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
        
        Matrix.rotateM(mMVPMatrix, 0, angle, 0, 0, 1);
   

        for (Triangle s : shapes) {
        	s.draw(mMVPMatrix);
        }
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
	    float ratio = (float) width / height;

	    // this projection matrix is applied to object coordinates
	    // in the onDrawFrame() method
	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 2, 150);
	}
	
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("MYGL", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
