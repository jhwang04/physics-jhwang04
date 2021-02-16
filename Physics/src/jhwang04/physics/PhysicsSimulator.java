package jhwang04.physics;

import processing.core.PApplet;

/**
 * Class that holds a list of physics shapes, updates them one by one,
 * and draws them.
 * @author jhwang04
 *
 */
public class PhysicsSimulator {
	
	/**
	 * Processing Applet that all the physics simulation will occur inside.
	 */
	private PApplet applet;
	
	/**
	 * Creates a new PhysicsSimulator
	 * @param p The Processing applet that the PhysicsSimulator should run inside 
	 */
	public PhysicsSimulator(PApplet p) {
		this.applet = p;
	}
	
	/**
	 * Primary method to be called, which ticks the simulation forwards once.
	 */
	public void update() {
		
	}
}
