package Module;
/*
 * Create a class GraphFrame extending JFrame and a class GraphComponent extending JComponent. 
 * A GraphFrame will contain a GraphComponent. Instances of GraphComponent will have a 
 * default size of 500x500.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class GraphFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphFrame() {
		final JFrame frame = new JFrame("Graph Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 500, 500);

		// Add a menu allowing to open and close new windows, and quit the program. Use
		// a dialog to confirm before quit.
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newProgram = new JMenuItem("New");
		newProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new GraphFrame();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		JMenuItem closeProgram = new JMenuItem("Close");
		closeProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		JMenuItem exitProgram = new JMenuItem("Exit");
		exitProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onExit();
			}
		});
		file.add(newProgram);
		file.add(closeProgram);
		file.add(exitProgram);
		menuBar.add(file);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);

		// Add a tool bar on the left side. Put in the tool bar buttons allowing to create
		// different shapes : circle, square ... Use a GridLayout to organize buttons in
		// the tool bar.
		JToolBar toolbar = new JToolBar();
		JRadioButton button1 = new JRadioButton("Small Circle");
		JRadioButton button2 = new JRadioButton("Big Circle");
		JRadioButton button3 = new JRadioButton("Small Square");
		JRadioButton button4 = new JRadioButton("Big Square");
	    toolbar.add(button1);
	    toolbar.add(button2);
	    toolbar.add(button3);
	    toolbar.add(button4);
	    ButtonGroup buttonGroup = new ButtonGroup();
	    buttonGroup.add(button1);
	    buttonGroup.add(button2);
	    buttonGroup.add(button3);
	    buttonGroup.add(button4);
	    button1.setSelected(true);
	    button1.setFocusPainted(true);
	    toolbar.setLayout(new GridLayout(4,1));
	    Container contentPane = frame.getContentPane();//set up Container
	    contentPane.add(toolbar, BorderLayout.WEST);
	    button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Small Circle");
		        SelectShape.setSelectShape("circle");
		        GraphComponent.setSmallRadius();
		        GraphComponent.setTheRequestFocus();
			}
		});
	    button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Big Circle");
		        SelectShape.setSelectShape("bigCircle");
		        GraphComponent.setBigRadius();
		        GraphComponent.setTheRequestFocus();
			}
		});
	    button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Small Square");
		        SelectShape.setSelectShape("square");
		        GraphComponent.setSmallRadius();
		        GraphComponent.setTheRequestFocus();
			}
		});
	    button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Big Square");
		        SelectShape.setSelectShape("bigSquare");
		        GraphComponent.setBigRadius();
		        GraphComponent.setTheRequestFocus();
			}
		});

		// GraphComponent
		final GraphComponent component = new GraphComponent();
		frame.add(component);
		frame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				component.requestFocusInWindow();
				component.requestFocus();
			}
		});

		// Add scroll bar to the GraphComponent.
		JScrollPane scrollableComponent = new JScrollPane(component);
		scrollableComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableComponent.setPreferredSize(new Dimension(500, 500));
		frame.getContentPane().add(scrollableComponent);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void onExit() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure to quit the Program?", "Quit", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
