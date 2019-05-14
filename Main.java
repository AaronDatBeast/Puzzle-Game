import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Main extends JPanel {
	private static final long serialVersionUID = 2720210890168986172L;
	private ImageProcessing[][] images;
	private ImageProcessing[][] masterArray;
	private int[][] pixelArray;
	private BufferedImage img;
	private int num;

	public Main(File file, int num, int inWidth, int inHeight) {
		this.num = num;
		try 
		{
			img = createResizedCopy(ImageIO.read(file), inWidth, inHeight);
			int width = img.getWidth();
			int height = img.getHeight();
			images = new ImageProcessing[num][num];
			masterArray = new ImageProcessing[num][num];
			pixelArray = new int[width][height];
			bufferedImageTo2DArray();
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * credit to Burkhard from https://stackoverflow.com/questions/244164/how-can-i-resize-an-image-using-java
	 */
	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight)
	{
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = scaledBI.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
		g.dispose();
		return scaledBI;
	}

	public void init() {
		for(int i = 0; i < num; i++) {
			for(int j = 0; j < num; j++) {
				ImageProcessing n = new ImageProcessing(pixelArray, img, j, i, num);
				images[j][i] = n;
				masterArray[j][i] = n;
			}
		}
		images[num - 1][num - 1].setNull(true);
 		randomizeArray();
		
	}

	private void bufferedImageTo2DArray() {//restricted

		for(int x=0;x< img.getWidth();x++){
			for(int y=0;y< img.getHeight();y++){
				pixelArray[x][y]= img.getRGB(x, y);
			}
		}
	}

	//copied from my semester 1 final lol (modified to work for this project of course)
		public void randomizeArray() {
			
			    Random random = new Random();
			    for (int i = images.length - 1; i > 0; i--) {
			        for (int j = images[i].length - 1; j > 0; j--) {
			            int m = random.nextInt(i + 1);
			            int n = random.nextInt(j + 1);

			            ImageProcessing temp = images[i][j];
			            
			            images[i][j] = images[m][n];
			            images[m][n] = temp;
			            
			        }
			    }
			    for(int i = 0; i < images.length; i++) {
			    	for(int j = 0; j < images.length; j++) {
			    		if(images[j][i].equals(masterArray[j][i])) {
			    			randomizeArray();
			    		}
			    	}
			    }
			    
		}

	
	public ImageProcessing[][] getBufferedArray() {
		return images;
	}
	
	public ImageProcessing[][] getMaser() {
		return masterArray;
	}
}