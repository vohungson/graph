package Module;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public class Circle implements Shape {

	// private final String CIRCLE = "Circle";
	private final RectangularShape CIRCLE = new Ellipse2D.Double();

	@Override
	public Shape clone() {
		return new Circle();
	}
	
	public RectangularShape getShape() {
		return CIRCLE;
	}
	
}