package jhwang04.physics.shape;
import processing.core.PApplet;

/**
 * Line class, can be drawn and checked if it intersects with another Line.
 * @author jhwang04
 * @version 2/15
 *
 */

public class Line{
	private float x, y, x2, y2;
	
	/**
	 * Creates a new line with the given coordinates.
	 * @param x X-coordinate of the first point
	 * @param y Y-coordinate of the first point
	 * @param x2 X-coordinate of the second point
	 * @param y2 Y-coordinate of the second point
	 */
	public Line(float x, float y, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/**
	 * Sets the location of the first point of the line
	 * @param x1 The new x-coordinate of the second point
	 * @param y1 The new y-coordinate of the second point
	 */
	public void setPoint1(float x1, float y1) {
		this.x = x1;
		this.y = y1;
	}
	
	/**
	 * Sets the location of the second point of the line
	 * @param x2 The new x-coordinate of the second point
	 * @param y2 The new y-coordinate of the second point
	 */
	public void setPoint2(float x2, float y2) {
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/**
	 * Draws the line
	 * @param p The PApplet object being used to draw the Line.
	 * @pre Use p.stroke() to set the color of the Line
	 * @post The PApplet ends up with the same style it starts with.
	 */
	public void draw(PApplet p) {
		p.pushStyle();
		p.noFill();
		p.line(x, y, x2, y2);
		p.popStyle();
	}
	
	/**
	 * Returns the x coordinate of the intersection of two Lines, if they were to be continued forever.
	 * Returns NaN if there is no intersection point (lines are parallel)
	 * @param other Other Line to check for intersection with
	 * @return The x coordinate of the intersection point.
	 */
	public float getIntersectionX(Line other) {
		float x3 = other.x;
		float x4 = other.x2;
		float y3 = other.y;
		float y4 = other.y2;
		
		float num = (x*y2 - y*x2)*(x3-x4) - (x-x2)*(x3*y4 - y3*x4);
		float denom = (x-x2)*(y3-y4) - (y-y2)*(x3-x4);
		return num/denom;
	}
	
	/**
	 * Returns the y coordinate of the intersection of two Lines, if they were to be continued forever.
	 * Returns NaN if there is no intersection point (lines are parallel)
	 * @param other Other Line to check for intersection with
	 * @return The y coordinate of the intersection point.
	 */
	public float getIntersectionY(Line other) {
		float x3 = other.x;
		float x4 = other.x2;
		float y3 = other.y;
		float y4 = other.y2;
		
		float num = (x*y2 - y*x2)*(y3-y4) - (y-y2)*(x3*y4 - y3*x4);
		float denom = (x-x2)*(y3-y4) - (y-y2)*(x3-x4);
		
		return num/denom;
	}
	
	/**
	 * Checks if two line segments intersect or overlap.
	 * Will return true if the lines are collinear
	 * @param other Other line to check for intersection with
	 * @return True if the line segments touch, false if they do not.
	 */
	public boolean intersects(Line other) {
		boolean xGood, yGood;
		float x, y;
		x = Math.round(this.getIntersectionX(other) * 1000.0)/1000.0f;
		y = Math.round(this.getIntersectionY(other) * 1000.0)/1000.0f;
		
		//System.out.println(this);
		//System.out.println(other);
		//System.out.println("x = " + x + " , y = " + y);
		
		if(isBetween(x, this.x, x2) && isBetween(x, other.x, other.x2) && isBetween(y, this.y, y2) && isBetween(y, other.y, other.y2))
			return true;
		else if(Float.isNaN(x)) {
			Line line1 = new Line(getX(), getY(), other.x2, other.y2);
			Line line2 = new Line(other.getX(), other.getY(), x2, y2);
			if(Double.isNaN(line1.getIntersectionX(line2))) 
				if((isBetween(getX(), other.getX(), other.x2) || isBetween(x2, other.getX(), other.x2)) && (isBetween(getY(), other.getY(), other.y2) || isBetween(y2, other.getY(), other.y2)))
					return true;
		}
		return false;
	}
	
	/**
	 * Debug method for intersects()
	 * Checks if two line segments intersect or overlap, and prints some debug info to the console
	 * Will return true if the lines are collinear
	 * @param other Other line to check for intersection with
	 * @return True if the line segments touch, false if they do not.
	 */
	public boolean debugIntersects(Line other) {
		boolean xGood, yGood;
		float x, y;
		x = Math.round(this.getIntersectionX(other) * 1000.0)/1000.0f;
		y = Math.round(this.getIntersectionY(other) * 1000.0)/1000.0f;
		
		System.out.println("Intersection X = " + x);
		System.out.println("Intersection Y = " + y);
		
		System.out.println(this);
		System.out.println(other);
		//System.out.println("x = " + x + " , y = " + y);
		
		System.out.println(isBetween(x, this.x, x2) && isBetween(x, other.x, other.x2) && isBetween(y, this.y, y2) && isBetween(y, other.y, other.y2));
		
		if(isBetween(x, this.x, x2) && isBetween(x, other.x, other.x2) && isBetween(y, this.y, y2) && isBetween(y, other.y, other.y2))
			return true;
		else if(Float.isNaN(x)) {
			Line line1 = new Line(getX(), getY(), other.x2, other.y2);
			Line line2 = new Line(other.getX(), other.getY(), x2, y2);
			if(Double.isNaN(line1.getIntersectionX(line2))) 
				if((isBetween(getX(), other.getX(), other.x2) || isBetween(x2, other.getX(), other.x2)) && (isBetween(getY(), other.getY(), other.y2) || isBetween(y2, other.getY(), other.y2)))
					return true;
		}
		
		return false;
	}
	
	/**
	 * Prints the line in the format "x1, x2, y1, y2"
	 * @return Returns the generated string
	 */
	public String toString() {
		return "x1 = " + x + " , x2 = " + x2 + " , y1 = " + y + " , y2 = " + y2;
	}
	
	private boolean isBetween(float x, float first, float second) {
		if(first <= second) {
			if(first <= x && x <= second) {
				return true;
			}
			return false;
		} else {
			return this.isBetween(x, second, first);
		}
	}
	
	/**
	 * Creates a new Line using a point, an angle and a length
	 * @param x The starting x coordinate
	 * @param y The starting y coordiante
	 * @param angle The angle, in degrees, rotated counterclockwise from the right side of the circle (the 3-oclock position)
	 * @param length The length of the Line
	 * @return A new Line object with the given values
	 */
	public static Line newLineWithAngle(float x, float y, float angle, float length) {
		float changeX = (float) (length * Math.cos(angle*Math.PI/180.0));
		float changeY = (float) (length * Math.sin(angle*Math.PI/180.0) * -1);
		return new Line(x, y, x + changeX, y + changeY);
	}
	
	
	/**
	 * Gets the x-coordinate of the first point of the Line
	 * @return The x-coord of the first point of the Line
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y-coordinate of the first ponit of the Line
	 * @return the y-coord of the first point of the Line
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Gets the x-coordinate of the second point of the Line
	 * @return The x-coord of the second point of the Line
	 */
	public float getX2() {
		return x2;
	}
	
	
	/**
	 * Gets the y-coordinate of the second point of the Line
	 * @return The y-coord of the second point of the Line
	 */
	public float getY2() {
		return y2;
	}

}
