import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//credit from this class goes to Sumit from https://stackoverflow.com/questions/11897297/constructing-image-from-2d-array-in-java
//Heavy modifications were made
//Organization of class was modified heavily
//at this point most of the code is original, given that I have a comprehensive understanding of the workings of the class

public class ImageProcessing {
	private BufferedImage subImage;
	private int num;
	private int pos;
	private boolean isNull;
	public ImageProcessing(int[][] px, BufferedImage mainImage, int imageRow, int imageCol, int num) {
		this.num = num;
		initImage(imageRow, imageCol, px, mainImage);
	}

	private BufferedImage pixelArrayToBufferedImage(int[][] pixelArray, int width, int height, int startX, int startY, int num) {//restructed
		BufferedImage bufferImage2=new BufferedImage(width / num, height / num,BufferedImage.TYPE_INT_RGB);
		for(int y=0;y< height/num; y++){
			for(int x=0;x<width/num; x++){
				bufferImage2.setRGB(x, y, pixelArray[x+startX][y+startY]);
			}
		}  
		return bufferImage2;
	}

	private void initImage(int row, int col, int[][] pixelArray, BufferedImage imgMain) {
		subImage = pixelArrayToBufferedImage(pixelArray, imgMain.getHeight(), imgMain.getWidth(), (row*imgMain.getWidth()) / num, (col*imgMain.getHeight())/ num, num);
	}

	public BufferedImage getSubImage() {
		return subImage;
	}

	public void setSubImage(BufferedImage subImage) {
		this.subImage = subImage;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public boolean getNull() {
		return isNull;
	}

	public void setNull(boolean b) {
		isNull = b;
	}
}   