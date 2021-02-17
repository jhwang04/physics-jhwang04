package jhwang04.physics.shape;

import java.awt.Color;

import processing.core.PApplet;

public class PhysicsRectangle extends PhysicsShape {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                      FIELDS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Width of the PhysicsRectangle
	 */
	private float width;
	
	/**
	 * Height of the PhysicsRectangle
	 */
	private float height;
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                 CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Creates a new PhysicsRectangle with width 100, height 100, mass 100, at the origin.
	 */
	public PhysicsRectangle() {
		super(0, 0, 100);
		addLine(new Line(-50, -50, 50, -50));
		addLine(new Line(50, -50, 50, 50));
		addLine(new Line(50, 50, -50, 50));
		addLine(new Line(-50, 50, -50, -50));
		this.width = 100;
		this.height = 100;
		setFurthestDistance(70.72f);
	}

	/**
	 * Creates a new PhysicsRectangle with position, mass, width, and height
	 * @param x X-coordinate of the center of mass (not the top left corner!)
	 * @param y Y-coordinate of the center of mass (not the top left corner!);
	 * @param mass Starting mass
	 * @param width Width of the PhysicsRectangle
	 * @param height Height of the PhysicsRectangle
	 */
	public PhysicsRectangle(float x, float y, float mass, float width, float height) {
		super(x, y, mass);
		addLine(new Line(x - width/2, y - height/2, x + width/2, y - height/2));
		addLine(new Line(x + width/2, y - height/2, x + width/2, y + height/2));
		addLine(new Line(x + width/2, y + height/2, x - width/2, y + height/2));
		addLine(new Line(x - width/2, y + height/2, x - width/2, y - height/2));
		this.width = width;
		this.height = height;
		setFurthestDistance((float) Math.sqrt((width/2) * (width/2) + (height/2) * (height/2)));
	}
	
	/**
	 * Creates a new PhysicsRectangle with position, mass, width, height, and colors
	 * @param x X-coordinate of the center of mass (not the top left corner!)
	 * @param y Y-coordinate of the center of mass (not the top left corner!);
	 * @param mass Starting mass
	 * @param width Width of the PhysicsRectangle
	 * @param height Height of the PhysicsRectangle
	 * @param fillColor The fill color of the PhysicsRectangle
	 * @param strokeColor The color of the lines of the PhysicsRectangle
	 */
	public PhysicsRectangle(float x, float y, float mass, float width, float height, Color fillColor, Color strokeColor) {
		this(x, y, mass, width, height);
		setFillColor(fillColor);
		setStrokeColor(strokeColor);
	}
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                  USEFUL METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public void draw(PApplet applet) {
		super.draw(applet);
		
		applet.beginShape();
		for(Line line : getLines()) {
			applet.vertex(line.getX(), line.getY());
		}
		applet.endShape(PApplet.CLOSE);
		
		for(Line line : getLines()) {
			line.draw(applet);
		}
	}
}
