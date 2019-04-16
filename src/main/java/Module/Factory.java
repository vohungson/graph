package Module;

import java.util.HashMap;
import java.util.Map;

public class Factory {
	private static final Map<String, Shape> prototypes = new HashMap<>();
	static {
		prototypes.put("circle", new Circle());
		prototypes.put("bigCircle", new Circle());
		prototypes.put("square", new Square());
		prototypes.put("bigSquare", new Square());
	}

	public static Shape getPrototype(String type) {
		try {
			return prototypes.get(type).clone();
		} catch (NullPointerException ex) {
			System.out.println("Prototype with name: " + type + ", doesn’t exist");
			return null;
		}
	}
}
