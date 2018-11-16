package guiDelegate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import model.Model;
import model.Record;

public class Delegate implements Observer {
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 1000;
	private JFrame jF;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JLabel MaxIterLabel;
	private JToolBar toolBar;
	private JTextField inputField;

	private Model model;
	private DrawMandelbrot drawMandelbrot;
	//	private DrawRectangle drawRectangle;
	private Record record;

	public Delegate(Model model) {
		this.model = model;
		this.jF = new JFrame(); 
		toolBar = new JToolBar();
		inputField =new JTextField(String.valueOf(model.getMaxIterations()));
		drawMandelbrot = new DrawMandelbrot(model);
		model.Reset();
		Model m = new Model();
		m.setMinReal(model.getMinReal());
		m.setMinImaginary(model.getMinImaginary());
		m.setMaxReal(model.getMaxReal());
		m.setMaxImaginary(model.getMaxImaginary());
		m.setMaxIterations(model.getMaxIterations());

		record = new Record(m);
		record.addModel(m);
		setupComponents();
		// add the delegate UI component as an observer of the model
		// so as to detect changes in the model and update the GUI view
		// accordingly
		model.addObserver(this);
	}

	private void setupToolbar() {
		button1 = new JButton("Confirm"); //confirm button		
		button1.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){
				model.setMaxIterations(Integer.parseInt(inputField.getText()));
				model.calMandelbrot();

				Model m = new Model();
				m.setMinReal(model.getMinReal());
				m.setMinImaginary(model.getMinImaginary());
				m.setMaxReal(model.getMaxReal());
				m.setMaxImaginary(model.getMaxImaginary());
				m.setMaxIterations(model.getMaxIterations());
				record.addModel(m);
			}
		});	
		button2 = new JButton("Undo"); // undo button
		button2.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){				
				Model temp =record.undo();
				model.setMinReal(temp.getMinReal());
				model.setMaxReal(temp.getMaxReal());
				model.setMinImaginary(temp.getMinImaginary());
				model.setMaxImaginary(temp.getMaxImaginary());
				model.setMaxIterations(temp.getMaxIterations());
				model.calMandelbrot();
			}
		});
		button3 = new JButton("Redo"); // redo button
		button3.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){
				try {
					Model temp = record.redo();
					model.setMinReal(temp.getMinReal());
					model.setMaxReal(temp.getMaxReal());
					model.setMinImaginary(temp.getMinImaginary());
					model.setMaxImaginary(temp.getMaxImaginary());
					model.setMaxIterations(temp.getMaxIterations());
					model.calMandelbrot();
				}catch(Exception e1) {					
				}
			}
		});
		button4 = new JButton("Reset"); // reset button
		button4.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){
				model.Reset();
				model.calMandelbrot();
				inputField.setText(String.valueOf(model.getMaxIterations()));
			}
		});
		button5 = new JButton("Save"); // save button                   
		button5.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){
				record.save();
			}
		});
		button6 = new JButton("Load"); // load button                   
		button6.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){
				try {

					Model temp = record.load();
					model.setMinReal(temp.getMinReal());
					model.setMaxReal(temp.getMaxReal());
					model.setMinImaginary(temp.getMinImaginary());
					model.setMaxImaginary(temp.getMaxImaginary());
					model.setMaxIterations(temp.getMaxIterations());
					model.calMandelbrot();
					inputField.setText(String.valueOf(model.getMaxIterations()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button7 = new JButton("Random colour");
		button7.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
			public void actionPerformed(ActionEvent e){				
				drawMandelbrot.setColoursRandom();
				model.calMandelbrot();
			}
		});
		MaxIterLabel = new JLabel("MaxIteration: ");

		toolBar.add(MaxIterLabel);
		toolBar.add(inputField);
		toolBar.add(button1);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		toolBar.add(button5);
		toolBar.add(button6);
		toolBar.add(button7);

		jF.add(toolBar, BorderLayout.NORTH);
	}

	private void setupMouse() {

		drawMandelbrot.addMouseListener(new MouseAdapter() {	
			double x1,y1,x2,y2,temp1,temp2;
			public void mousePressed(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();

			}
			public void mouseReleased(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();

				temp1 = Math.min(x1, x2);
				temp2 = Math.max(x1, x2);
				x1 = temp1;  
				x2 = temp2;

				temp1 = Math.min(y1, y2);
				temp2 = Math.max(y1, y2);
				y1 = temp1;
				y2 = temp2;

				model.calPosition(x1,y1,x2,y2);
				model.setMinReal(model.getX1());
				model.setMinImaginary(model.getY1());
				model.setMaxReal(model.getX2());
				model.setMaxImaginary(model.getY2());
				model.calMandelbrot();

				Model m = new Model();
				m.setMinReal(model.getMinReal());
				m.setMinImaginary(model.getMinImaginary());
				m.setMaxReal(model.getMaxReal());
				m.setMaxImaginary(model.getMaxImaginary());
				m.setMaxIterations(model.getMaxIterations());
				record.addModel(m);

			}
		});
	}

	private void setupComponents() {
		setupMouse();
		setupToolbar();
		jF.add(drawMandelbrot, BorderLayout.CENTER);
		jF.setTitle("Mandelbrot Explorer");
		jF.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jF.setResizable(false); // make the window size cannot be changed
		jF.setVisible(true);
	}




	@Override
	public void update(Observable o, Object arg) {
		// Tell the SwingUtilities thread to update the GUI components.
		// This is safer than executing outputField.setText(model.getText())
		// in the caller's thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				drawMandelbrot.repaint();
			}
		});

	}

}
