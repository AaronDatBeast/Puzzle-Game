import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {
	JFrame f;
	private JPanel panel;
	private JLabel[][] labelArray;
	PuzzlePanel(int num, JFrame f) {
		this.f = f;
		panel = new JPanel();
		panel.setLayout(new GridLayout(num, num,3,3));
		this.setLayout(new GridBagLayout());
		panel.setBackground(Color.BLACK);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File  
				(System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));
		fileChooser.showOpenDialog(this);
		File selFile =  fileChooser.getSelectedFile();
		int input = 500;
		Main m = new Main(selFile, num, input, input);
		f.addMouseListener(new PuzzleMouseListener(m, this));
		
		updatePositions(m.getBufferedArray(), m.getMaser());
		


		panel.setPreferredSize(panel.getPreferredSize());
		this.add(panel, new GridBagConstraints());
	}

	public void updatePositions(ImageProcessing[][] bufferedImages, ImageProcessing[][] master) {

		panel.removeAll();
		
		
		labelArray = new JLabel[bufferedImages.length][bufferedImages.length];
		for(int i = 0; i < bufferedImages.length; i++) {
			for(int j = 0; j < bufferedImages.length; j++) {
				if(bufferedImages[j][i].getNull() == true) {
					System.out.println(j + "" + i + "");
					BufferedImage bf = bufferedImages[j][i].getSubImage();
					JLabel l = new JLabel(new ImageIcon(bf));
					labelArray[j][i] = null;
					l.setVisible(false);
					panel.add(l);
				} else {
				BufferedImage bf = bufferedImages[j][i].getSubImage();



				if(bufferedImages[j][i].getPos() == 0) 
					bufferedImages[j][i].setPos((j+1)*(i+1));
				JLabel l = new JLabel(new ImageIcon(bf));
				labelArray[j][i] = l;
				l.addMouseListener( new MouseAdapter() {

					public void mouseEntered( MouseEvent e ) {
						// add a highlight around when entered
					}

					public void mouseClicked( MouseEvent e ) {
						int currentRow = 0;
						int currentCol = 0;
						for(int i = 0; i < labelArray.length; i++) {
							for(int j = 0; j < labelArray.length; j++) {
								if(labelArray[j][i] == null)
									continue;
								if(labelArray[j][i].equals(e.getSource())) {
									try {
										if(labelArray[j+1][i] == null) {
											ImageProcessing temp = bufferedImages[j + 1][i];
											bufferedImages[j + 1][i] = bufferedImages[j][i];
											bufferedImages[j][i] = temp;										
											updatePositions(bufferedImages, master);
										} 
									} catch (IndexOutOfBoundsException ee) {


									}
									try {
										if(labelArray[j-1][i] == null) {
											ImageProcessing temp = bufferedImages[j - 1][i];
											bufferedImages[j - 1][i] = bufferedImages[j][i];
											bufferedImages[j][i] = temp;
											updatePositions(bufferedImages, master);										} 
									} catch (IndexOutOfBoundsException ee) {


									}
									try {
										if(labelArray[j][i+1] == null) {
											ImageProcessing temp = bufferedImages[j][i+1];
											bufferedImages[j][i+1] = bufferedImages[j][i];
											bufferedImages[j][i] = temp;
											updatePositions(bufferedImages, master);										} 
									} catch (IndexOutOfBoundsException ee) {


									}
									try {
										if(labelArray[j][i-1] == null) {
											ImageProcessing temp = bufferedImages[j][i-1];
											bufferedImages[j][i-1] = bufferedImages[j][i];
											bufferedImages[j][i] = temp;
											updatePositions(bufferedImages, master);										}
									} catch (IndexOutOfBoundsException ee) {


									}

								}
							}
						}



					}
				} );
				
				panel.add(l);
				}
			}
		}
		panel.revalidate();
		panel.repaint();
	}
}