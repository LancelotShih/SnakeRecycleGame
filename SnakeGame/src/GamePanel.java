import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.TexturePaint;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class GamePanel extends JPanel implements ActionListener{

	static final int screen_width = 600;
	static final int screen_height = 600;
	static final int unitsize = 50;
	static final int gameunits = (screen_width*screen_height)/unitsize;
	static final int delay = 75; // change this value to increase/decrease the speed of the snake (difficulty change)
	
	final int x[] = new int[gameunits]; // holds all x coordinates of snake
	final int y[] = new int[gameunits]; // holds all y coordinates of snake
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX; // this is randomized
	int appleY; // this is also randomized
	int trashX;
	int trashY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	private boolean start = false;
	private TexturePaint snaketp;
	private BufferedImage snake;
	private TexturePaint recycletp;
	private BufferedImage recycle;
	private TexturePaint trashtp;
	private BufferedImage trash;
    private static TrustManager[] getTrustingManager() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

               @Override
               public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                     return null;
               }

               @Override
               public void checkClientTrusted(X509Certificate[] certs, String authType) {
               }

               @Override
               public void checkServerTrusted(X509Certificate[] certs, String authType) {
               }
        } };
        return trustAllCerts;
 }
	
	
	GamePanel() {
		random = new Random();
		loadRecycle();
		loadSnake();
		loadTrash();
		this.setPreferredSize(new Dimension(screen_width, screen_height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		spawnApple();
		spawnTrash();
		running = true;
		timer = new Timer(delay, this); // dictates how fast the game runs (hard mode changes this delay value)
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void loadSnake() {
 
         try {
 			URL url = new URL("https://pixelartmaker-data-78746291193.nyc3.digitaloceanspaces.com/image/2ff5966906c4cb6.png");
 		    snake = ImageIO.read(url);
 		    	
 		}
 		catch(IOException e) {
 			throw new RuntimeException(e);
 		}
		
	}
	
	public void loadRecycle() {
        try {
			URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Recycle001.svg/1200px-Recycle001.svg.png");
		    recycle = ImageIO.read(url);
		    	
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void loadTrash() {
		try {
			URL url = new URL("https://images.onlinelabels.com/images/clip-art/Andy/Andy_Trash_Can.png");
		    trash = ImageIO.read(url);
		    	
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		if(running) {
				//startMenu(g);
				rules(g);
				g.setColor(new Color(200, 200,200));
				for (int x = 0; x < screen_height/unitsize;x++) {
					g.drawLine(x*unitsize, 0, x*unitsize, screen_height);
					g.drawLine(0, x*unitsize, screen_width, x*unitsize);
				}
				
				
				recycletp = new TexturePaint(recycle, new Rectangle(0,0,unitsize,unitsize));
				g2d.setPaint(recycletp); g2d.fillRect(appleX, appleY, unitsize, unitsize);
				 
				
				trashtp = new TexturePaint(trash, new Rectangle(0,0,unitsize,unitsize));
				g2d.setPaint(trashtp); g2d.fillRect(trashX, trashY, unitsize, unitsize);
				
				
				for(int i = 0; i < bodyParts; i++) {
					if (i == 0) { //dealing with the head of the snake 
						
						
						 snaketp = new TexturePaint(snake, new Rectangle(0, 0, unitsize, unitsize)); // this changes the size of the image itself 
						 g2d.setPaint(snaketp);
						 g2d.fillRect(x[i], y[i], unitsize, unitsize); // this changes the size of the rectangle the image is contained in 
						 g2d.dispose();
						 
					}
					else { // dealing with body of snake
						g.setColor(new Color(45, 180,0));
						g.fillRect(x[i], y[i], unitsize, unitsize);
					}
				}
				g.setColor(Color.white);
				g.setFont(new Font("Audiowide", Font.BOLD, 40));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Score " + applesEaten * 10,(screen_width - metrics.stringWidth("Score " + applesEaten * 10))/2, g.getFont().getSize());
			
		}
		else {
			gameOver(g);
		}
	}
	
	public void spawnApple() {
		appleX = random.nextInt((int)(screen_width/unitsize)) * unitsize;
		appleY = ((int)(Math.random() * screen_height/unitsize)) * unitsize;

		
	}
	
	public void spawnTrash() {
		trashX = random.nextInt((int)(screen_width/unitsize)) * unitsize;
		trashY = ((int)(Math.random() * screen_height/unitsize)) * unitsize;
	}
	
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1]; // moves body parts one by one from the current index to an index one smaller
			y[i] = y[i-1];
		}
		switch(direction) {
		case'U':
			y[0] = y[0] - unitsize;
			break;
		case'D':
			y[0] = y[0] + unitsize;
			break;
		case'L':
			x[0] = x[0] - unitsize;
			break;
		case'R':
			x[0] = x[0] + unitsize;
			break;
		}
		
	}
	
	public void CheckApple() {
		if(x[0] == appleX && y[0] == appleY) {
			bodyParts++;
			applesEaten++;
			spawnApple();
			spawnTrash();
		}
		
	}
	
	public void CheckTrash() {
		if(x[0] == trashX && y[0] == trashY) {
			bodyParts--;
			applesEaten--;
			spawnTrash();
		}
	}

	
	public void checkCollisions() {
		for (int i = bodyParts; i > 0; i--) {
			if (x[0]== x[i] && y[0] == y[i]) { // if the first index of the snake array (the head) collides with any given index (the body), stop running
				running = false;
			}
		}
		//check for head touching borders
		if(x[0] < 0) {
			running = false;
		}
		
		if(x[0] > screen_width) {
			running = false;
		}
		
		if(y[0] < 0) {
			running = false;
		}
		
		if(y[0] > screen_height) {
			running = false;
		}
		if(!running) { // if running is false, the game will stop and the frame will freeze
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Audiowide", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over",(screen_width - metrics.stringWidth("Game Over"))/2, screen_height/2); // gives game over in center of screen
		g.setColor(Color.white);
		g.setFont(new Font("Audiowide", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score " + applesEaten * 10,(screen_width - metrics1.stringWidth("Score " + applesEaten * 10))/2, g.getFont().getSize());
	}
	
	public void startMenu(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Audiowide", Font.BOLD, 20));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("How to play",(screen_width - metrics.stringWidth("How to play"))/2, screen_height/2); // gives game over in center of screen
		g.setColor(Color.white);
		g.drawString("WASD to move Up Left Down and Right.",(screen_width - metrics.stringWidth("WASD to move Up Left Down and Right."))/2, screen_height-80); // gives game over in center of screen
		g.drawString("Eat recycle signs to increase your snake's size and your score",(screen_width - metrics.stringWidth("Eat recycle signs to increase your snake's size and your score"))/2, screen_height-20); // gives game over in center of screen	
		g.drawString("Beware of trash cans!",(screen_width - metrics.stringWidth("Eat recycle signs to increase your snake's size and your score"))/2, screen_height-60); // gives game over in center of screen	
	}
	
	public void rules(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Audiowide", Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("WASD to move Up Left Down and Right.",(screen_width - metrics.stringWidth("WASD to move Up Left Down and Right."))/2, screen_height-40); // gives game over in center of screen
		g.drawString("Eat recycle signs to increase your snake's size and your score",(screen_width - metrics.stringWidth("Eat recycle signs to increase your snake's size and your score"))/2, screen_height-20); // gives game over in center of screen	
		g.drawString("Beware of trash cans!",(screen_width - metrics.stringWidth("Eat recycle signs to increase your snake's size and your score"))/2, screen_height-20); // gives game over in center of screen	
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			CheckApple();
			CheckTrash();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		//override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
				direction = 'R';
				}
				break;
			
			case KeyEvent.VK_UP:
				if(direction != 'D') {
				direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
				direction = 'D';
				}
				break;
			case KeyEvent.VK_ENTER:
				start = true;
			}
		}
	}
}
