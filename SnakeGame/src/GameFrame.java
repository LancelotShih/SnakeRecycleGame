import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
		//this.add(new startPanel());
		
		
		this.add(new GamePanel());
		
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); //the panel cannot be resized else the play area gets larger upon resizing the window
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
