package jhwang04.physics.shape;

import java.awt.Color;
import java.util.ArrayList;

import jhwang04.physics.PhysicsSimulator;
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
						//System.out.println(line + ", " + otherLine);
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
	 * @return A Float array, [magnitude, direction], where direction is in radians counterclockwise
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
		//System.out.println("\n\nforce x = " + x + ", force y = " + y + ", magnitude = " + magnitude + ", direction = " + direction);
		//distance to COM, in meters
		float distanceToCOM = (float) Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y)) / PhysicsSimulator.METER;
		//System.out.println("distanceToCOM = " + distanceToCOM);
		
		//angle that the force applied deviates from being towards the center of mass
		float deviationAngle = 0;
		
		
		
		// range of thetaCOM (theta towards center of mass) is [-PI/2, 3PI/2)
		float difXCOM = x - this.x;
		float difYCOM = this.y - y;
		float thetaCOM = (float) Math.atan(difYCOM/difXCOM);
		//System.out.println("difXCom = " + difXCOM + ", difYCom = " + difYCOM);
		
		//System.out.println("initial thetaCOM = " + thetaCOM);
		
		// Ensures that thetaCOM is oriented correctly if line is vertical
		if(thetaCOM == (float) (Math.PI/2.0))
			if(this.y - y > 0)
				thetaCOM = 0 - (float) (Math.PI / 2.0);
			else
				thetaCOM = (float) (Math.PI / 2.0);
		
		//System.out.println("fixed thetaCOM = " + thetaCOM);
		
		
		
		if(distanceToCOM >= 0.001f) { //if force is not being applied to the center of mass (if it is, then deviationAngle stays 0)
			
			if(difXCOM > 0) // makes sure that thetaCOM is rotated towards COM, and not away
				thetaCOM = (float) (thetaCOM + Math.PI);
			
			// normalizes direction to have the same range as thetaCOM [-PI/2, 3PI/2)
			while(direction < (0 - Math.PI/2) || direction >= (3 * Math.PI / 2)) {
				System.out.println(direction);
				if(direction < (0 - Math.PI/2))
					direction += (2 * Math.PI);
				else if(direction >= (3 * Math.PI / 2))
					direction -= (2 * Math.PI);
			}
			
			deviationAngle = Math.abs(direction - thetaCOM);
			
			if(deviationAngle > Math.PI)
				deviationAngle = (float)  (Math.PI * 2) - deviationAngle;
			
		}
		//System.out.println("devationAngle = " + deviationAngle);
		
		boolean isTorqueNegative = (thetaCOM - direction) < 0 && (thetaCOM - direction) > (0 - Math.PI);
		
		//TODO: Make Tau positive/negative depending on the direction, so that deltaL can be neg, and omega_f can be less than omega_i
		
		//
		// CHANGE IN ROTATION
		//
		
		float torque = magnitude * distanceToCOM * (float) Math.sin(deviationAngle); // Tau = Force * radius * sin(theta)
		
		if(isTorqueNegative)
			torque *= -1;
		
		float deltaL = torque * (1/60.0f); // Change in L (angular momentum) = Tau * time (at 60fps)
		
		// Finding final angular velocity
		//
		// L_i = inertia * omega_i                              Initial angular momentum = I * w
		// L_f = L_i + deltaL  						 			Final angular momentum = Initial L + Delta L
		// inertia * omega_i + deltaL = inertia * omega_f		Substitute variables
		// omega_f = (inertia * omega_i + deltaL) / inertia		Isolate for final omega
		
		float omega_f = (inertia * omega + deltaL) / inertia;
		
		//System.out.println("inertia, omega_i = " + inertia + ", " + omega);
		//System.out.println("torque, deltaL, omega_f = " + torque + ", " + deltaL + ", " + omega_f);
		
		
		
		//
		// CHANGE IN TRANSLATION
		//
		
		float translationForce = magnitude * (float) Math.abs(Math.cos(deviationAngle)); // i'm totally guessing on this, but it seems reasonable
		// EDIT 2/24 - This method of finding translation force is actually incorrect, but this method will probably not be used.
		float translationForceX = translationForce * (float) Math.cos(direction);
		float translationForceY = translationForce * (float) Math.sin(direction);
		
		float deltaPX = translationForceX * (1/60.0f); // Change in P (linear momentum) = Force * time (at 60fps)
		float deltaPY = translationForceY * (1/60.0f);
		
		// Finding final linear velocity
		//
		// P_ix = vx_i * mass									Momentum = velocity * mass
		// P_fx = P_ix + deltaPX								Final momentum = Initial P + Delta P
		// mass * vx_f = mass * vi_x + deltaPX					Substitute for variables
		// vx_f = (mass * vx_i + deltaPX) / mass				Isolate for v_fx (final velocity x)
		
		float vx_f = (mass * vX + deltaPX) / mass;
		float vy_f = (mass * vY + deltaPY) / mass;

		//System.out.println("translationForce, translationForceX, translationForceY = " + translationForce + ", " + translationForceX + ", " + translationForceY);
		//System.out.println("deltaPX, deltaPY = " + deltaPX + ", " + deltaPY);
		
		//System.out.println("Before vx, vy, omega = " + vX + ", " + vY + ", " + omega);
		// Applying values calculated above
		vX = vx_f;
		vY = vy_f;
		omega = omega_f;
		//System.out.println("After vx, vy, omega = " + vX + ", " + vY + ", " + omega);
		
	}
	
	/**
	 * Moves the PhysicsShape one tick forwards, with the current velocities and positions.
	 */
	public void act() {
		// Assumes FPS is 60
		x += (vX * PhysicsSimulator.METER / 60.0f);
		y -= (vY * PhysicsSimulator.METER / 60.0f); //flipped because positive vY should cause upward motion
		rotation += (omega / 60.0f);
		
		translateLines(0, (vX * PhysicsSimulator.METER /60.0f));
		translateLines(1, 0 - (vY * PhysicsSimulator.METER /60.0f)); //flipped because positive vY should cause upward motion
		rotateLines(omega / 60.0f);
		
		//System.out.println("time = " + System.currentTimeMillis() + ", x = " + x + ", y = " + y + ", rotation = " + rotation);
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
	protected void rotateLines(float radians) {
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
	
	/**
	 * Translates all Lines along the given axis
	 * @param axis Axis to translate along (0 = x axis, 1 = y axis)
	 * @param amount Number of pixels to translate
	 */
	protected void translateLines(int axis, float amount) {
		for(Line line : lines) {
			translateLine(line, axis, amount);
		}
	}
	
	/**
	 * Adds the given rotation to the given Line about the PhysicsShape's center of mass.
	 * @param line The Line to rotate
	 * @param axis Axis of translation (0 = x axis, 1 = y axis)
	 * @param amount Number of pixels to be translated along the given axis
	 * @post The explicit Line will have new start and end points, translated
	 */
	private void translateLine(Line line, float axis, float amount) {
		if(axis == 0) { // x-axis
			line.setPoint1(line.getX() + amount, line.getY());
			line.setPoint2(line.getX2() + amount, line.getY2());
		} else if(axis == 1) { // y-axis
			line.setPoint1(line.getX(), line.getY() + amount);
			line.setPoint2(line.getX2(), line.getY2() + amount);
		}
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
