package game;

import java.awt.Insets;

import javax.swing.*;
import puzzlefield.PuzzleApplet;

public class PuzzleMain {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(640,480);
		PuzzleApplet puzzle = new PuzzleApplet();
		frame.add(puzzle);
		//frame.setDefaultCloseOperation(0);
	}
}
