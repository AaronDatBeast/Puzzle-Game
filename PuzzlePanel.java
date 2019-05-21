import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

public class PuzzlePanel extends JPanel {
	private JPanel panel;
	private JLabel[][] labelArray;
	private Main m;
	private JFrame frame;
	private boolean b = false;
	private boolean waiting = false;
	PuzzlePanel(int num, JFrame f) {
		int input = 500;
		frame = f;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File (System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));
		fileChooser.showOpenDialog(this);
		File selFile =  fileChooser.getSelectedFile();

		this.setLayout(new GridBagLayout());

		panel = new JPanel();
		panel.setLayout(new GridLayout(num, num,3,3));
		panel.setBackground(Color.BLACK);

		Main m = new Main(selFile, num, input, input);
this.m = m;
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
							
							l.setBorder(new CompoundBorder(BorderFactory.createDashedBorder(Color.WHITE, 3, 3), BorderFactory.createEmptyBorder(3, 3, 3, 3)));

							
							
							for (int i = 0; i < bufferedImages.length; i++) {
								if(!Arrays.equals(bufferedImages[i], master[i])) {
									b = false;
									break;
								} else {
									b = true;
								}
							}
							if(b) {
								System.out.println("you win");
									JDialog d = new JDialog(frame);
									d.setSize(100,100);
									d.setLocation(100, 100);
									d.setUndecorated(true);
									d.setVisible(true);
									frame.revalidate();
									try {
										if(waiting == false) {
										waiting = true;
										wait(5000);
										}
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									d.dispose();
									System.out.println("finished waiting");
								m.randomizeArray();
								updatePositions(bufferedImages, master);
							}
						}

						public void mousePressed( MouseEvent e ) {
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
												updatePositions(bufferedImages, master);									
											} 
										} catch (IndexOutOfBoundsException ee) {
										}
										
										try {
											if(labelArray[j][i+1] == null) {
												ImageProcessing temp = bufferedImages[j][i+1];
												bufferedImages[j][i+1] = bufferedImages[j][i];
												bufferedImages[j][i] = temp;
												updatePositions(bufferedImages, master);										
											} 
										} catch (IndexOutOfBoundsException ee) {
										}
										
										try {
											if(labelArray[j][i-1] == null) {
												ImageProcessing temp = bufferedImages[j][i-1];
												bufferedImages[j][i-1] = bufferedImages[j][i];
												bufferedImages[j][i] = temp;
												updatePositions(bufferedImages, master);										
											}
										} catch (IndexOutOfBoundsException ee) {										}
									}
								}
							}
							
						}
						
						public void mouseExited(MouseEvent e) {
							l.setBorder(null);
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