import java.awt.Container;

import javax.swing.JFrame;

public class HelloWorld extends JFrame {
	public HelloWorld() {
		setTitle("ブロック崩し");
		MainPanel panel = new MainPanel();
		Container contentP = getContentPane();
		contentP.add(panel);

		pack();
	}
	public static void main(String[] args) {
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}