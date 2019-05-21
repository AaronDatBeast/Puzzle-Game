import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Main extends JPanel {
	private static final long serialVersionUID = 2720210890168986172L;
	private ImageProcessing[][] images;
	private ImageProcessing[][] masterArray;
	private int[][] pixelArray;
	private BufferedImage img;
	private int num;
	private int invariantRow;
	private int invariantCol;

	public Main(File file, int num, int inWidth, int inHeight) {
		this.num = num;
		invariantRow = num - 1;
		invariantCol = num - 1;
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

	public void randomizeArray() {
		//need to check for solvability
		//currently solvability is about 50%
		Random random = new Random();
		

				boolean isValid = false;
			
				int number = random.nextInt(4);
				while(!isValid) {

			
				switch(number) {
				case 0: 
					if((invariantRow-1) >= 0) {
						isValid = true;
						ImageProcessing temp = images[invariantRow][invariantCol];

						images[invariantRow][invariantCol] = images[invariantRow - 1][invariantCol];
						images[invariantRow-1][invariantCol] = temp;
						invariantRow--;
					}
					break;
				case 1:
					if((invariantRow+1) < images.length) {
						isValid = true;
						ImageProcessing temp = images[invariantRow][invariantCol];

						images[invariantRow][invariantCol] = images[invariantRow + 1][invariantCol];
						images[invariantRow+1][invariantCol] = temp;
						invariantRow++;
					}
					break;
				case 2:
					if((invariantCol+1) < images.length) {
						isValid = true;
						ImageProcessing temp = images[invariantRow][invariantCol];

						images[invariantRow][invariantCol] = images[invariantRow][invariantCol + 1];
						images[invariantRow][invariantCol + 1] = temp;
						invariantCol++;
					}
					break;
				case 3:
					if((invariantCol - 1) >= 0) {
						isValid = true;
						ImageProcessing temp = images[invariantRow][invariantCol];

						images[invariantRow][invariantCol] = images[invariantRow][invariantCol - 1];
						images[invariantRow][invariantCol - 1] = temp;
						invariantCol--;
					}
					break;
				default:
					break;
				}
				number = random.nextInt(4);

				}
		
		for(int k = 0; k < images.length; k++) {
			for(int l = 0; l < images.length; l++) {
				if(images[l][k].equals(masterArray[l][k])) {
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