import javax.swing.JFrame;

public class Mission970112App {
	public static void main(String[] args){
	

		JFrame frame = new JFrame("Mission970112");
		Game component = new Game();
		frame.add(component);
		frame.setVisible(true);
		frame.setSize(1000,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
}
