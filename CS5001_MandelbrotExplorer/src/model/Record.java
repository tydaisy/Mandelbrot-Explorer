package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;
import java.util.Stack;

public class Record {
	Stack <Model> stack1;
	Stack <Model> stack2;

	Model model;


	public Record(Model model){
		stack1 = new Stack<Model>();
		stack2 = new Stack<Model>();
		this.model = model;
	}

	public void addModel(Model model){
		stack1.push(model);	
	}

	public Model undo(){
		Model model1 = new Model();
		try{
			stack2.push(stack1.pop());
			stack1.peek();
			return stack1.peek();
		} catch(Exception e){
			return model1;
		}
	}

	public Model redo(){
		try{
			stack1.push(stack2.peek());
			stack2.peek();
			stack2.peek();
			return stack2.pop();
		} catch(Exception e){			
			return null;
		}
	}

	public void save() {
		try {
			Model m = stack1.peek();
			BufferedWriter out = new BufferedWriter(new FileWriter("save_record.txt"));
			out.write(String.valueOf(m.getMaxIterations()) + " ");
			out.write(String.valueOf(m.getMaxImaginary()) + " ");
			out.write(String.valueOf(m.getMaxReal()) + " ");
			out.write(String.valueOf(m.getMinImaginary()) + " ");
			out.write(String.valueOf(m.getMinReal()) + " ");
			out.close();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	public Model load() throws IOException {

		try {
			BufferedReader in = new BufferedReader(new FileReader("save_record.txt"));
			String[] input = in.readLine().split(" ");
			Double[] load = new Double[input.length];
			for (int i = 0; i< input.length; i++) {
				load[i] = Double.parseDouble(input[i]);
			}
			Model model = new Model();
			model.setMaxIterations(load[0].intValue());
			model.setMaxImaginary(load[1]);
			model.setMaxReal(load[2]);
			model.setMinImaginary(load[3]);
			model.setMinReal(load[4]);
			return model;
		} catch (FileNotFoundException exc) {
			exc.printStackTrace();
			return null;
		}
	}
}
