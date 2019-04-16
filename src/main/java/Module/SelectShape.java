package Module;

import java.awt.geom.RectangularShape;

public class SelectShape {
	private static String selectShape = "circle";

	public static void setSelectShape(String selectShape) {
		SelectShape.selectShape = selectShape;
	}

	public static RectangularShape createShape(double x, double y, double w, double h) {
		RectangularShape makeShape = null;
		Shape prototype = Factory.getPrototype(selectShape);
		makeShape = (RectangularShape) prototype.getShape();
		makeShape.setFrame(x, y, w, w);
		return makeShape;
	}
	
	public static RectangularShape createSmallCircle(double x, double y, double w, double h) {
		RectangularShape makeShape = null;
		Shape prototype = Factory.getPrototype("circle");
		makeShape = (RectangularShape) prototype.getShape();
		makeShape.setFrame(x, y, w, w);
		return makeShape;
	}
}
