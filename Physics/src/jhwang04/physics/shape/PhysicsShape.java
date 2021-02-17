package jhwang04.physics.shape;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Abstract class, representing a generic physics shape, containing the necessary variables that apply
 * to all shapes.
 * @author jhwang04
 *
 */
public abstract class PhysicsShape {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                      FIELDS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * X-coordinate of the center of mass of the shape
	 */
	private float x;
	
	/**
	 * Y-coordinate of the center of mass of the shape
	 */
	private float y;
	
	/**
	 * Rotation is in radians, counterclockwise.
	 */
	private float rotation;
	
	/**
	 * vX and vY are in pixels/sec, where positive X is to the right,
	 * and positive Y is downward.
	 */
	private float vX, vY;
	
	/**
	 * Omega is the angular velocity in radians/sec
	 */
	private float omega;
	
	/**
	 * Mass is the objects mass, in no particular unit.
	 */
	private float mass;
	
	/**
	 * Inertia contains the rotational inertia of the shape
	 */
	private float inertia;
	
	/**
	 * Lines contains an ArrayList of lines
	 */
	private ArrayList<Line> lines;
	
	/**
	 * Furthest distance from center of mass to a vertex.
	 */
	private float furthestDistance;
	
	/**
	 * Color that the PhysicsShape will be filled in as.
	 * Set to null for noFill();
	 */
	private Color fillColor;
	
	/**
	 * Color that the PhysicsShape will be outlined as.
	 * Set to null for noStroke();
	 */
	private Color strokeColor;
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                   CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * Creates a new PhysicsShape with all values zeroed out, except mass, which is set to 1, and furthestDistance, which is set to 10,000
	 */
	public PhysicsShape() {
		this(0, 0, 0);
	}
	
	/**
	 * Creates a new PhysicsShape with position and mass
	 * @param x X-coordinate of the center of mass
	 * @param y Y-coordinate of the center of mass
	 * @param mass Starting mass
	 */
	public PhysicsShape(float x, float y, float mass) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.vX = 0;
		this.vY = 0;
		this.rotation = 0;
		this.furthestDistance = 10000;
		this.omega = 0;
		this.inertia = mass;
		this.fillColor = new Color(200, 200, 200);
		this.strokeColor = new Color(0, 0, 0);
		lines = new ArrayList<Line>();
	}
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                  USEFUL METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Will return an ArrayList of forces applied to this PhysicsShape from the given PhysicsShape
	 * @return New ArrayList of forces applied to this PhysicsShape, [PhysicsShape, x, y, magnitude, direction]
	 */
	public ArrayList<Object[]> allForcesFromShape(PhysicsShape otherShape) {
		ArrayList<Object[]> forces = new ArrayList<Object[]>();
		
		// Exits if the distance between the center of masses is greater than the furthest possible collision (largest protruding vertices touching)
		if(Math.sqrt((x - otherShape.getX()) * (x - otherShape.getX()) + (y - otherShape.getY()) * (y - otherShape.getY())) < (furthestDistance + otherShape.getFurthestDistance())) {
			for(Line line : lines) {
				for(Line otherLine : otherShape.getLines()) {
					if(line.intersects(otherLine)) {
						Float[] magAndDir = findMagnitudeAndDirection(otherShape, line, otherLine);
						forces.add(new Object[] {this, line.getIntersectionX(otherLine), line.getIntersectionY(otherLine), magAndDir[0], magAndDir[1]});
					}
				}
			}
		}
		
		return forces;
	}
	
	/**
	 * Finds the magnitude and direction of a force applied to the implicit object from the given object, at the location
	 * that the two given lines intersect.
	 * @param shape2 The PhysicsShape that the implicit PhysicsShape collides with
	 * @param line1 The Line of the implicit PhysicsShape that is collided with
	 * @param line2 The Line of shape2 that is collided with
	 * @return A Float array, [magnitude, direction], where direction is in radians <clockwise/counterclockwise>
	 */
	private Float[] findMagnitudeAndDirection(PhysicsShape shape2, Line line1, Line line2) {
		PhysicsShape shape1 = this;
		float magnitude = 0;
		float direction = 0;
		// Need to perform actual calculations, but I'll figure that out later.
		return new Float[] {magnitude, direction};
	}
	
	/**
	 * Applies a new vX, vY and rotation based off of the magnitude, direction and location given
	 * @param x The x-coordinate of the collision
	 * @param y The y-coordinate of the collision
	 * @param magnitude The magnitude of the force applied
	 * @param direction The direction of the force applied, where 0 is directly to the right, going counterclockwise in radians (like the unit circle)
	 * @post The implicit object will have a new vX, vY and omega
	 */
	public void applyForce(float x, float y, float magnitude, float direction) {
		// Need to perform actual calculations later to find new translational and rotational velocities.
	}
	
	/**
	 * Moves the PhysicsShape one tick forwards, with the current velocities and positions.
	 */
	public void act() {
		// Assumes FPS is 60
		x += (vX / 60.0f);
		y += (vY / 60.0f);
		rotation += (omega / 60.0f);
		rotate(omega / 60.0f);
	}
	
	/**
	 * Abstract PhysicsShape only sets the colors of the PApplet, must be extended in order to draw the shape.
	 * When overriding, call pushMatrix(), super.draw(applet) to set the colors, run drawing code, then call popMatrix().
	 * @param applet The PApplet that the PhysicsShape will be drawn in
	 * @post PApplet will have a different color and stroke set, but translation/rotation matrix transformations will be unchanged
	 */
	public void draw(PApplet applet) {
		if(fillColor != null)
			applet.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
		else
			applet.noFill();
		
		if(strokeColor != null)
			applet.stroke(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue());
		else
			applet.noStroke();
	}
	
	/**
	 * Rotates all Lines counterclockwise by the given angle, in radians.
	 * @param radians New rotation, coutnerclockwise, in radians
	 */
	protected void rotate(float radians) {
		for(Line line : lines) {
			rotateLine(line, radians);
		}
	}
	
	/**
	 * Adds the given rotation to the given Line about the PhysicsShape's center of mass.
	 * @param line The Line to rotate
	 * @param newRotation New rotation (in radians)
	 * @post The explicit Line will have new start and end points.
	 */
	private void rotateLine(Line line, float newRotation) {
		float newX1 = (float) (x + (line.getX() - x) * Math.cos(newRotation) - (y - line.getY()) * Math.sin(newRotation));
		float newY1 = (float) (y - (line.getX() - x) * Math.sin(newRotation) - (y - line.getY()) * Math.cos(newRotation));

		float newX2 = (float) (x + (line.getX2() - x) * Math.cos(newRotation) - (y - line.getY2()) * Math.sin(newRotation));
		float newY2 = (float) (y - (line.getX2() - x) * Math.sin(newRotation) - (y - line.getY2()) * Math.cos(newRotation));
		
		line.setPoint1(newX1, newY1);
		line.setPoint2(newX2, newY2);
	}
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                 GET/SET METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Sets the new x-coordinate
	 * @param x New x-coordinate
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the new y-coordinate
	 * @param y New y-coordinate
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets the new velocity X
	 * @param vX New velocity X
	 */
	public void setVX(float vX) {
		this.vX = vX;
	}
	
	/**
	 * Sets the new velocity Y
	 * @param vY New velocity Y
	 */
	public void setVY(float vY) {
		this.vY = vY;
	}
	
	/**
	 * Sets the new rotational velocity
	 * @param omega New rotational velocity (clockwise)
	 * @param omega
	 */
	public void setOmega(float omega) {
		this.omega = omega;
	}
	
	/**
	 * Sets the new mass
	 * @param mass The new mass of the object
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	/**
	 * Sets the new inertia
	 * @param inertia The new inertia of the object
	 */
	public void setInertia(float inertia) {
		this.inertia = inertia;
	}
	
	/**
	 * Sets the furthestDistance of the PhysicsShape
	 * @param furthestDistance New furthest distance of the PhysicsShape
	 */
	public void setFurthestDistance(float furthestDistance) {
		this.furthestDistance = furthestDistance;
	}
	
	/**
	 * Sets the new fillColor of the PhysicsShape
	 * @param fillColor New fillColor of the PhysicsShape
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	/**
	 * Sets the new strokeColor of the PhysicsShape
	 * @param strokeColor New strokeColor of the PhysicsShape
	 */
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	/**
	 * Returns the current x-coordinate
	 * @return Current x-coord
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Returns the current y-coordinate
	 * @return Current y-coord
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Returns the current velocity X
	 * @return Current velocity X
	 */
	public float getVX() {
		return vX;
	}
	
	/**
	 * Returns the current velocity Y
	 * @return Current velocity Y
	 */
	public float getVY() {
		return vY;
	}
	
	/**
	 * Returns current rotational velocity
	 * @return Current rotational velocity
	 */
	public float getOmega() {
		return omega;
	}
	
	/**
	 * Returns the current mass of the PhysicsShape
	 * @return Current mass of the PhysicsShape
	 */
	public float getMass() {
		return mass;
	}
	
	/**
	 * Returns the current inertia of the PhysicsShape
	 * @return Current rotational inertia of the PhysicsShape
	 */
	public float getInertia() {
		return inertia;
	}
	
	/**
	 * Returns the furthestDistance of this PhysicsShape
	 * @return The value in the furthestDistance float
	 */
	public float getFurthestDistance() {
		return furthestDistance;
	}
	
	/**
	 * Returns the fillColor of the PhysicsShape
	 * @return The current fillColor
	 */
	public Color getFillColor() {
		return fillColor;
	}
	
	/**
	 * Returns the strokeColor of the PhysicsShape
	 * @return The current strokeColor
	 */
	public Color getStrokeColor() {
		return strokeColor;
	}
	
	/**
	 * Returns the ArrayList of Lines that make up the PhysicsShape
	 * @return ArrayList of Lines
	 */
	public ArrayList<Line> getLines() {
		return lines;
	}
	
	/**
	 * Adds the specified Line to the field "lines"
	 * @param line The line to add to the ArrayList
	 */
	public void addLine(Line line) {
		lines.add(line);
	}
}
