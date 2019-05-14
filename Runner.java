import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Runner {
	public static void main(String[] args) {
		int num = 3;
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setContentPane(new PuzzlePanel(num, frame));
		frame.getContentPane().setBackground(Color.BLACK);
		double widthDimension = frame.getPreferredSize().getWidth() + (frame.getPreferredSize().getWidth() / 2);
		double heightDimension = frame.getPreferredSize().getHeight() + (frame.getPreferredSize().getHeight() / 4);
		frame.setPreferredSize(new Dimension((int)widthDimension, (int)heightDimension));
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}