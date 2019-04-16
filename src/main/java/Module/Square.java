package Module;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Square implements Shape {

	// private final String CIRCLE = "Circle";
	private final RectangularShape SQUARE = new Rectangle2D.Double();

	@Override
	public Shape clone() {
		return new Square();
	}
	
	public RectangularShape getShape() {
		return SQUARE;
	}
	
}