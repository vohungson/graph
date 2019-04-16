package Module;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Calculate {
	
	/*
	 * Calculate the height of the Triangle when we know 3 vertices of the triangle
	 * A(x1, y1); B(x2, y2); C(x3, y3) -> Calculate the height from Vertice A to the edge BC
	 * 
	 *		 A 
	 * 	     *
	 * 		/|\
	 *a(AB)/ | \ b(AC)
	 * 	  /	 |  \
	 * B /___|___\ C
	 *       H  c(BC)
	 *       h(AH)
	 */
	public static int heightOfTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		double a = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		double b = Math.sqrt((x1-x3)*(x1-x3) + (y1-y3)*(y1-y3));
		double c = Math.sqrt((x3-x2)*(x3-x2) + (y3-y2)*(y3-y2));
		//Apply Heron's Formula for Area
		double S = (a + b + c)/2;
		double Area = Math.sqrt(S*(S-a)*(S-b)*(S-c));
		double h = 2*Area/c;
		return (int)h;
	}
	
	
	/*
	 * There are two points: A(x1,y1) and B(x2,y2) with R is radius of B
	 * Calculate thePoint cross the line AB with circle of B
	 */
	public static Point2D getPoint(double x1, double y1, double x2, double y2, int R) {
		Point2D thePoint = new Point2D.Double();
		double x3 = (x1 + x2) / 2;
		double y3 = (y1 + y2) / 2;
		double d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
		int times = 0;
		while ((int) d > R) {
			x1 = x3;
			y1 = y3;
			x3 = (x3 + x2)/2;
			y3 = (y3 + y2)/2;
			d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
			while((int) d < R && times < 10) {//set times to catch error here.
				x3 = (x3 + x1)/2;
				y3 = (y3 + y1)/2;
				d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				times++;
			}
		}
		thePoint.setLocation(x3, y3);
		return thePoint;
	}
	
	public static void drawArrowHead(Graphics g2, int x1, int y1, int x2, int y2, int ARR_SIZE) {
		Graphics2D g = (Graphics2D) g2.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
				4);
	}
}
