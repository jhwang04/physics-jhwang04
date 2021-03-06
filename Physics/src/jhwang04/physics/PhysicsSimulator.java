package jhwang04.physics;

import java.awt.Color;
import java.util.ArrayList;

import jhwang04.physics.shape.PhysicsRectangle;
import jhwang04.physics.shape.PhysicsShape;
import processing.core.PApplet;

/**
 * Class that holds a list of physics shapes, updates them one by one,
 * and draws them.
 * @author jhwang04
 *
 */
public class PhysicsSimulator {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                      CONSTANTS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final float METER = 100;
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                      FIELDS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * ArrayList of all the shapes that this PhysicsSimulator contains
	 */
	private ArrayList<PhysicsShape> shapes;
	
	/**
	 * Rectangle that represents the ground
	 */
	private PhysicsRectangle ground;
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                   CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Creates a new empty PhysicsSimulator
	 */
	public PhysicsSimulator() {
		shapes = new ArrayList<PhysicsShape>();
		
		ground = new PhysicsRectangle(400, 800, 1000000, 1000, 200, new Color(200, 200, 200), new Color(0));
		shapes.add(ground);
		
	}
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                  USEFUL METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Primary method to be called, which ticks the simulation forwards once.
	 */
	public void update() {
		
		// Calculating all forces to apply to the physics shapes
		ArrayList<Object[]> forces = new ArrayList<Object[]>();
		for(PhysicsShape shape : shapes) {
			for(PhysicsShape shape2 : shapes) {
				if(!(shape == shape2))
					forces.addAll(shape.allForcesFromShape(shape2));
			}
		}

		// Adding force of gravity to each shape, directly downward.
		for(PhysicsShape shape : shapes) {
			if(!shape.equals(ground))
				forces.add(new Object[] {shape, shape.getX(), shape.getY(), shape.getMass() * 9.81f, 0 - (float) (Math.PI / 2.0) } );
		}
		
		// tester, applies sideways force to the left
		if(shapes.get(1).getY() == 200.0f) {
			forces.add(new Object[] {shapes.get(1), 300f, 125f, 1000f, (float) Math.PI});
		}
		
		// Applying each force to the shapes to set new vX, vY and omega values for this tick.
		// Force array is in format [PhysicsShape, x, y, magnitude, direction]
		for(Object[] force : forces) {
			
			PhysicsShape shape = (PhysicsShape) force[0];
			float x = (float) force[1];
			float y = (float) force[2];
			float magnitude = (float) force[3];
			float direction = (float) force[4];
			
			shape.applyForce(x, y, magnitude, direction);
		}
		
		// Tells each PhysicsShape to move one tick forwards with the new vX, vY and omega values
		for(PhysicsShape shape : shapes) {
			shape.act();
		}
		
	}
	
	/**
	 * Draws each shape in the shapes ArrayList
	 * @param applet The PApplet that all the PhysicsShapes will be drawn in
	 * @post The given applet will have shapes drawn in it, but the style and matrix transformations will not be changed
	 */
	public void draw(PApplet applet) {
		applet.pushStyle();
		
		for(PhysicsShape shape : shapes) {
			shape.draw(applet);
		}
		
		applet.popStyle();
	}
	
	/**
	 * Adds the given PhysicsShape to the ArrayList shapes
	 * @param shape PhysicsShape to add to the the shapes field
	 */
	public void addShape(PhysicsShape shape) {
		shapes.add(shape);
	}
	
	/**
	 * Removes the given PhysicsShape from the ArrayList shapes
	 * @param shape PhysicsShape to remove from the the shapes field
	 */
	public void removeShape(PhysicsShape shape) {
		shapes.remove(shape);
	}
	
	
	
	
	
	
	
	

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                  GET/SET METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Returns the current ArrayList of shapes
	 * @return The shapes field
	 */
	public ArrayList<PhysicsShape> getShapes() {
		return shapes;
	}
}
