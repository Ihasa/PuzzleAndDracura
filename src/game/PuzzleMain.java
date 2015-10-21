package game;

import java.awt.Insets;

import javax.swing.*;
import puzzlefield.PuzzleApplet;

public class PuzzleMain {
	public static void main(String[] args) {
		int w = 320;
		int h = 480;
		JFrame frame = new JFrame();
		frame.setVisible(true);
		Insets i = frame.getInsets();
		frame.setSize(w + i.left + i.right, h + i.top + i.bottom);
		PuzzleApplet puzzle = new PuzzleApplet(w,h);
		frame.add(puzzle);
		//frame.setDefaultCloseOperation(0);
	}
}
