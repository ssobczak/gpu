package com.example.gpu_cw1;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OpenGLES20 extends Activity {
	private GLSurfaceView mGLView;
	
	private final String vertexShaderCode =
	    "attribute vec4 vPosition;" +
	    "void main() {" +
	    "  gl_Position = vPosition;" +
	    "}";

	private final String fragmentShaderCode =
	    "precision mediump float;" +
	    "uniform vec4 vColor;" +
	    "void main() {" +
	    "  gl_FragColor = vColor;" +
	    "}";
	
	public static int loadShader(int type, String shaderCode) {
	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);

	    return shader;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
}
