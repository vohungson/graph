package VoHungSon.GraphEditor;
/**
 * Graph Editor! (OPP-TD)
 * VO HUNG SON - class MINF18 
 */

import javax.swing.JOptionPane;

import Module.*;

public class App {
	public static void main(String[] args) {
		try {
			new GraphFrame();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
