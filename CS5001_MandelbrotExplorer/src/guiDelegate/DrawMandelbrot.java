package guiDelegate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import model.Model;

@SuppressWarnings("serial")
public class DrawMandelbrot extends JPanel {
	private Model model;
	int[][] mData ;
	private int red;
	private int green;
	private int blue;
	Random rand;
	
	public DrawMandelbrot(Model model) {		
		this.model = model;
		model.calMandelbrot();
		red = 255;
		green = 20;
		blue = 40;
		rand = new Random();
	}

	//	@Override
	public void paint(Graphics g) {
		mData = model.getMandelbrotData();
		BufferedImage b = new BufferedImage(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < mData.length; y++) {
			for (int x = 0; x < mData[y].length; x++) 
			{		
				if (mData[y][x] == model.getMaxIterations()) {
					g.setColor(Color.BLACK);
				} else {
					b.setRGB(x, y, (new Color((int)(red/model.getMaxIterations() * mData[y][x]), (int)(green/model.getMaxIterations() * mData[y][x]), (int)(blue/model.getMaxIterations() * mData[y][x])).getRGB()));
				}
				g.drawLine(x, y, x, y);
			}				
		}
		g.drawImage(b, 0, 0, null);
	}
	
	public void setColoursRandom() {
		red = rand.nextInt(255);
		green = rand.nextInt(255);
		blue = rand.nextInt(255);
		System.out.println(red);
	}
}
