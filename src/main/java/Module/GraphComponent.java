package Module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class GraphComponent extends JComponent implements MouseInputListener, KeyListener {
	/**
	 * 
	 */
	private static int smallRadius = 20;
	private static int bigRadius = 30;
	private static int radius = 20;// set default for small circle
	private static int superSmallRadius = 10;
	private final int ARR_SIZE = 5;// set size of Arrow
	private static final long serialVersionUID = 1L;
	private List<RectangularShape> shapes = new ArrayList<>();
	private RectangularShape draggedShape = null;
	private RectangularShape saveLineShape = null;
	private RectangularShape startedLineShape = null;
	private RectangularShape endedLineShape = null;
	private int indexDraggedShape;
	private double deltaDraggedX, deltaDraggedY;
	private boolean isHoldingAltKey = false;
	private static boolean theRequestFocus = true;
	private static boolean theCheckMouseEnteredShape = true;
	private List<Line2D> lines = new ArrayList<>();
	private Line2D line = null;
	private List<Integer> hasPoints = new ArrayList<>();
	private static boolean isHoldingSpaceKey = false;

	public GraphComponent() {
		setForeground(Color.RED);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 500));
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		for (RectangularShape shape : shapes) {
			g2.setColor(new Color(199, 185, 0));
			if (shape.getWidth() == 2 * superSmallRadius) {
				g2.setColor(new Color(223, 102, 102));
			} else if (shape.getWidth() == 2 * smallRadius) {
				g2.setColor(new Color(199, 185, 0));
			} else if (shape.getWidth() == 2 * bigRadius) {
				g2.setColor(new Color(60, 179, 113));
			}
			g2.fill(shape);
			g2.draw(shape);
			g2.setColor(new Color(106, 90, 205));
			g2.drawString(String.valueOf(shapes.indexOf(shape)), (int) shape.getCenterX()-5,
					(int) shape.getCenterY()+5);
		}
		for (Line2D l : lines) {
			// g2.setColor(new Color(208, 0, 182));
			// g2.draw(l);
			g2.setColor(new Color(106, 90, 205));
			g2.drawString("edge " + String.valueOf(lines.indexOf(l)), (int) (l.getX1() + l.getX2()) / 2,
					(int) (l.getY1() + l.getY2()) / 2);
			//get Radius of Point 1
			int R1 = 0;
			for (RectangularShape shape1 : shapes) {
				if (l.getX1() == shape1.getCenterX() && l.getY1() == shape1.getCenterY()) {
					R1 = (int) shape1.getWidth() / 2;
				}
			}
			//get Radius of Point 2
			int R2 = 0;
			for (RectangularShape shape2 : shapes) {
				if (l.getX2() == shape2.getCenterX() && l.getY2() == shape2.getCenterY()) {
					R2 = (int) shape2.getWidth() / 2;
				}
			}
			//Draw the line
				Point2D p1 = Calculate.getPoint(l.getX2(), l.getY2(), l.getX1(), l.getY1(), R1);
				Point2D p2 = Calculate.getPoint(l.getX1(), l.getY1(), l.getX2(), l.getY2(), R2);
				Calculate.drawArrowHead(g2, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY(), ARR_SIZE);
		}
		if (line != null) {
			g2.setColor(new Color(255, 165, 0));
			g2.draw(line);
		}
	}

	public static void setSmallRadius() {
		radius = smallRadius;
	}

	public static void setBigRadius() {
		radius = bigRadius;
	}

	public static void setTheRequestFocus() {
		theRequestFocus = true;
	}

	public void mouseClicked(MouseEvent e) {// Mouse key is pressed/released
		// each time you click inside the circle, the word "win" is written on the
		// standard output
		if (checkMouseEnteredShape(e)) {
			System.out.println("WIN");
		}
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}

	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}

	public void mousePressed(MouseEvent e) {
		for (RectangularShape shape : shapes) {
			Point2D point = new Point2D.Double(e.getX(), e.getY());
			if (shape.contains(point)) {
				draggedShape = shape;
				deltaDraggedX = e.getX() - shape.getX();
				deltaDraggedY = e.getY() - shape.getY();
				indexDraggedShape = shapes.indexOf(shape);
				System.out.println("The index of  draggedShape: " + indexDraggedShape);
			}
		}
		// code for Lines

		if (checkMouseEnteredShape(e)) {
			startedLineShape = saveLineShape;
			if (startedLineShape != null) {
				line = new Line2D.Double(startedLineShape.getCenterX(), startedLineShape.getCenterY(), e.getX(),
						e.getY());
				if (startedLineShape != endedLineShape)
					repaint();
			}
		}
	}

	private void setEndLine(MouseEvent e) {
		if (isHoldingAltKey == true && startedLineShape != null
				|| isHoldingSpaceKey == true && startedLineShape != null) {
			line.setLine(line.getX1(), line.getY1(), e.getX(), e.getY());
			repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {// check leftMouse or rightMouse released.
		if (SwingUtilities.isRightMouseButton(e)) {
			rightMouseReleased(e);
		} else {
			leftMouseReleased(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (isHoldingAltKey == true || isHoldingSpaceKey == true) {
				if (startedLineShape != null) {
					setEndLine(e);
				}
			} else {
				if (draggedShape != null) {
					// More code here to move the lines together with the Shape base on
					// indexDraggedShape
					// we can use the draggedShape and indexDraggedShape to do more code for moving
					// lines here
					Point2D point = new Point2D.Double(draggedShape.getCenterX(), draggedShape.getCenterY());
					movingTheLines(point, e.getX() - deltaDraggedX + draggedShape.getWidth() / 2,
							e.getY() - deltaDraggedY + draggedShape.getWidth() / 2);

					draggedShape.setFrame(e.getX() - deltaDraggedX, e.getY() - deltaDraggedY, draggedShape.getWidth(),
							draggedShape.getWidth());
					shapes.set(indexDraggedShape, draggedShape);
					repaint();
				}
			}
		}
	}

	public void movingTheLines(Point2D point, double newX, double newY) {// TODO
		for (Line2D line : lines) {
			if (point.getX() == line.getX1() && point.getY() == line.getY1()) {
				line.setLine(newX, newY, line.getX2(), line.getY2());
			} else if (point.getX() == line.getX2() && point.getY() == line.getY2()) {
				line.setLine(line.getX1(), line.getY1(), newX, newY);
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (theRequestFocus == true) {
			requestFocus();
			System.out.println("Just called the Request Focus");
			theRequestFocus = false;
		}
		if (checkMouseEnteredShape(e)) {
			if (theCheckMouseEnteredShape == true) {
				System.out.println("Mouse Entered Shape");
				theCheckMouseEnteredShape = false;
			}
		} else {
			if (theCheckMouseEnteredShape == false) {
				theCheckMouseEnteredShape = true;
				System.out.println("Mouse Out of the Shape");
			}
		}
	}

	public void leftMouseReleased(MouseEvent e) {
		if (!checkMouseEnteredShape(e)) {
			RectangularShape shape = SelectShape.createShape(e.getX() - radius, e.getY() - radius, 2 * radius,
					2 * radius);
			if (draggedShape != null) {// the Shape is moving to the new place, set the new line with new shape if you
										// press key ALT
				if (shapes.contains(draggedShape)) {
					if (isHoldingAltKey == true || isHoldingSpaceKey == true) {
						if (isHoldingSpaceKey == true) {// convert to small circle when holding the Space key
							shape = SelectShape.createSmallCircle(e.getX() - superSmallRadius,
									e.getY() - superSmallRadius, 2 * superSmallRadius, 2 * superSmallRadius);
						}
						shapes.add(shape);
						try {
							line.setLine(line.getX1(), line.getY1(), shape.getCenterX(), shape.getCenterY());
							lines.add(line);
						} catch (Exception e1) {
							System.out.println("Please! Try it again!!!!");
						}
					} else {
						// More code here to move lines together with the Shape base on
						// indexDraggedShape
						// we can use the draggedShape and indexDraggedShape to do code for moving lines
						// here
						Point2D point = new Point2D.Double(draggedShape.getCenterX(), draggedShape.getCenterY());
						movingTheLines(point, e.getX() - deltaDraggedX + draggedShape.getWidth() / 2,
								e.getY() - deltaDraggedY + draggedShape.getWidth() / 2);

						shapes.set(indexDraggedShape, shape);
						System.out.println("Print draggedShape");
					}
				}
			} else {// mouse doesn't move, just release and get the Shape
				shapes.add(shape);
			}
		} else {// the ended point of the line is inside of the Shape.
			// code for line
			endedLineShape = saveLineShape;
			if (startedLineShape != endedLineShape) {
				if (startedLineShape != null) {
					line.setLine(line.getX1(), line.getY1(), endedLineShape.getCenterX(), endedLineShape.getCenterY());
					boolean checkExistLine = false;
					for (Line2D lineL : lines) {
						if ((lineL.getX1() == line.getX1()) && (lineL.getX2() == line.getX2())
								&& (lineL.getY1() == line.getY1() && (lineL.getY2() == line.getY2()))) {
							checkExistLine = true;
						} else if ((lineL.getX1() == line.getX2()) && (lineL.getX2() == line.getX1())
								&& (lineL.getY1() == line.getY2() && (lineL.getY2() == line.getY1()))) {
							checkExistLine = true;
						}
					}
					if (!checkExistLine) {
						lines.add(line);
					} else {
						System.out.println("This edge existed, you can't create again");
					}
				} else {
					System.out.println("--->> Please press ALT inside the Shape for drawing the line");
				}
			}
		}
		line = null;
		repaint();
		// end of this method -> set null to all of instances of the RectangularShape;
		// (*_*)
		saveLineShape = null;
		startedLineShape = null;
		endedLineShape = null;
		draggedShape = null;
	}

	public void rightMouseReleased(MouseEvent e) {// Delete the shape.
		if (checkMouseEnteredShape(e)) {
			System.out.println("right click for deletting");
			// Deletion of the Shape
			line = null;
			if (shapes.contains(draggedShape)) {
				Point2D point = new Point2D.Double(draggedShape.getCenterX(), draggedShape.getCenterY());
				// Line2D deleteLine = new Line2D.Double();
				for (Line2D line1 : lines) {
					if (point.getX() == line1.getX1() && point.getY() == line1.getY1()) {
						line1.setLine(point, point);// convert the line that has started point to Point
						hasPoints.add(lines.indexOf(line1));
					} else if (point.getX() == line1.getX2() && point.getY() == line1.getY2()) {
						line1.setLine(point, point);// convert the line that has ended point to Point
						hasPoints.add(lines.indexOf(line1));
					}
				}
				line = null;
				repaint();
				// delete the points in the list of lines from highest index to lowest index
				for (int i = hasPoints.size() - 1; i >= 0; i--) {
					System.out.println("-> Delete line at index: " + hasPoints.get(i));
					lines.remove(lines.get(hasPoints.get(i)));
				}
				hasPoints.clear();// clear hasPoints (*_*)
				shapes.remove(draggedShape);
			}
			draggedShape = null;
			line = null;
			repaint();
		} else {// Outside of the Shape, just delete the line. (ˆ_ˆ)
			// TODO Deletion of the line when right click
			Iterator<Line2D> lineIterator = lines.iterator();
			while (lineIterator.hasNext()) {
				line = lineIterator.next();
				int distance = Calculate.heightOfTriangle(e.getX(), e.getY(), line.getX1(), line.getY1(), line.getX2(),
						line.getY2());
				System.out.println(
						"The distance: " + distance + " < " + e.getX() + " , " + e.getY() + " >  ->   ( " + line.getX1()
								+ " , " + line.getY1() + " ) & ( " + line.getX2() + " , " + line.getY2() + " ) ");
				if (distance <= 5) {
					System.out.println("You select the line with the distance to the edge is: " + distance);
					hasPoints.add(lines.indexOf(line));
				}
			} // end while
			line = null;
			// delete the points in the list of lines from highest index to lowest index
			for (int i = hasPoints.size() - 1; i >= 0; i--) {
				System.out.println("-> Delete line at index: " + hasPoints.get(i));
				lines.remove(lines.get(hasPoints.get(i)));
			}
			hasPoints.clear();// clear hasPoints (*_*)
			line = null;// refresh the screen
			repaint();
		}
	}

	public boolean checkMouseEnteredShape(MouseEvent e) {
		// double distance, x, y, width;
		for (RectangularShape shape : shapes) {
			Point2D point = new Point2D.Double(e.getX(), e.getY());
			if (shape.contains(point)) {
				if (isHoldingAltKey == true || isHoldingSpaceKey == true) {
					saveLineShape = shape;// get the started point of the line.
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			shapes.clear();
			lines.clear();
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
			isHoldingAltKey = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isHoldingSpaceKey = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ALT) {
			System.out.println("Released the button ALT");
			isHoldingAltKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("Released the button SPACE");
			isHoldingSpaceKey = false;
		}
	}
}
