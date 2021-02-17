import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import jhwang04.physics.PhysicsSimulator;
import jhwang04.physics.shape.PhysicsRectangle;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Physics extends PApplet {
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

	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                      FIELDS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Only updates when mouse is clicked when true
	private boolean debugUpdateMode = true;
	
	PhysicsSimulator sim;
	
	
	
	public Physics() {
		sim = new PhysicsSimulator();
		sim.addShape(new PhysicsRectangle(200, 200, 10, 100, 150, Color.RED, Color.BLACK));
	}
	
	public void settings() {
		size(800, 800);
	}
	
	public void draw() {
		background(255);
		if(!debugUpdateMode)
			sim.update();
		
		sim.draw(this);
		fill(0);
		text("x: " + mouseX + ", y " + mouseY, mouseX, mouseY);
	}
	
	public void mousePressed() {
		if(debugUpdateMode)
			sim.update();
	}
}
