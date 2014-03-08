package ecrosogames.staterenderer;

import javax.swing.JFrame;

public class StateRendererTest {

	private static JFrame frame;
	private static GameManager manager;

	public StateRendererTest() {
		frame = new JFrame();
		manager = new GameManager(500, 500);

		initFrame();
		manager.start();
	}

	private void initFrame() {
		frame.setTitle("test");
		frame.setResizable(false);
		frame.add(manager);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new StateRendererTest();
	}
}
