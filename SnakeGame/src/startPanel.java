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

public class startPanel extends JPanel implements ActionListener{
	
	static final int screen_width = 600;
	static final int screen_height = 600;
	Random random;
	
	startPanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(screen_width, screen_height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
	}
	
	public void startMenu(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("Audiowide", Font.BOLD, 20));
		g.drawString("How to play",100,100); // gives game over in center of screen
		g.setColor(Color.white);
		g.drawString("WASD to move Up Left Down and Right.",100,100); // gives game over in center of screen
		g.drawString("Eat recycle signs to increase your snake's size and your score",100,100); // gives game over in center of screen	
		g.drawString("Beware of trash cans!",100,100); // gives game over in center of screen	
		
	}
		

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}