package model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JTextField;

/**
 * The model class contains many parameters which are used when calculate Mandelbrot set.
 * @author y
 * @version 1.0
 */
public class Model extends Observable {

	double minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
	double maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
	double minImaginary = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
	double maxImaginary = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
	int maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
	double radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;

	private int width = 1000;
	private int height = 1000;
	private int[][] mData;
	private double x1,y1,x2,y2;
	private MandelbrotCalculator m;

	public Model() {
		this.m = new MandelbrotCalculator();
	}

	/**
	 * calMandelbrot method is to calculate and update Mandelbrot set
	 */
	public void calMandelbrot() {
		mData =m.calcMandelbrotSet(width, height, minReal, maxReal, minImaginary, maxImaginary, maxIterations,
				radiusSquared);	
		System.out.println(this.getMaxIterations());
		setChanged();
		notifyObservers();
	}

	/**
	 * when mouse is dragged in the window, the values of coordinates need to be recalculated  
	 * @param x1 x coordinate (smaller one)
	 * @param y1 y coordinate (smaller one)
	 * @param x2 x coordinate (bigger one)
	 * @param y2 y coordinate (bigger one)
	 */
	public void calPosition(double x1, double y1, double x2, double y2){
		this.x1 = (x1/width) * (maxReal-minReal) + minReal;
		this.y1 = (y1/height)*(maxImaginary-minImaginary) + minImaginary;

		this.x2 = (x2/width) * (maxReal-minReal) + minReal;
		this.y2 = (y2/height)*(maxImaginary-minImaginary) + minImaginary;	
	}


	public void Reset() {
		minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
		maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
		minImaginary = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
		maxImaginary = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
		maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
		radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;

	}
	public int[][] getMandelbrotData() {
		return mData;
	}

	public double getX1() {
		return this.x1;
	}

	public double getY1(){
		return this.y1;
	}

	public double getX2() {
		return this.x2;
	}

	public double getY2(){
		return this.y2;
	}
	public int getWidth() {
		return width;
	}

	public int getHeight(){
		return height;
	}


	public double getMinReal() {
		return minReal;
	}

	public double getMaxReal() {
		return maxReal;
	}

	public double getMinImaginary() {
		return minImaginary;
	}

	public double getMaxImaginary() {
		return maxImaginary;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMinReal(double minReal) {
		this.minReal = minReal;
	}

	public void setMaxReal(double maxReal) {
		this.maxReal = maxReal;
	}

	public void setMinImaginary(double minImaginary) {
		this.minImaginary = minImaginary;
	}

	public void setMaxImaginary(double maxImaginary) {
		this.maxImaginary = maxImaginary;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

}
