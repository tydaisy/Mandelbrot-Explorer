package main;

import guiDelegate.Delegate;
import model.Model;

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
		Delegate delegate = new Delegate(model); // pass the model object to the delegate, so that it can observe, display and change the model

	}

}
