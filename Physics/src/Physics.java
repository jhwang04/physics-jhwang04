import java.awt.Dimension;

import javax.swing.JFrame;

import jhwang04.physics.PhysicsSimulator;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Physics extends PApplet {
	
	// Only updates when mouse is clicked when true
	private boolean debugUpdateMode = true;

	public static void main(String[] args) {
		Physics applet = new Physics();
		PApplet.runSketch(new String[]{""}, applet);
		PSurfaceAWT surf = (PSurfaceAWT) applet.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();
		window.setSize(800, 800);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setVisible(true);
		canvas.requestFocus();
	}

	
	PhysicsSimulator sim ;
	
	public Physics() {
		sim = new PhysicsSimulator();
	}
	
	public void settings() {
		size(800, 800);
	}
	
	public void draw() {
		if(!debugUpdateMode)
			sim.update();
		
		sim.draw(this);
	}
	
	public void mousePressed() {
		if(debugUpdateMode)
			sim.update();
	}
}
