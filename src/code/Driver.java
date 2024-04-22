package code;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings("unused")
public class Driver extends JFrame{
	
	private static final boolean SCALE_ASPECT_RATIO = true;
	private static final long serialVersionUID = 1L;
	
	private Main programMain;
    private int scaledWidth, scaledHeight;
	
	static GraphicsDevice device = GraphicsEnvironment
		.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public Driver()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height;
	
		Dimension gameSize = new Dimension(640, 480);
		Dimension scaledDimension = new Dimension(getScaledDimension(gameSize, screenSize));
	
		if(SCALE_ASPECT_RATIO)
			programMain = new Main(
				scaledDimension.width, scaledDimension.height, screenWidth, screenHeight);
		else
			programMain = new Main(
				screenWidth, screenHeight, screenWidth, screenHeight);	
	
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		
		programMain.setSize(screenWidth, screenHeight);
		add(programMain);
		
		setTitle("Game"); 
		setSize(screenWidth, screenHeight);
		setUndecorated(false);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//device.setFullScreenWindow(this);
	}	

	Dimension getScaledDimension(Dimension imageSize, Dimension boundary) 
	{
	    double widthRatio = boundary.getWidth() / imageSize.getWidth();
	    double heightRatio = boundary.getHeight() / imageSize.getHeight();
	    double ratio = Math.min(widthRatio, heightRatio);

	    return new Dimension((int) (imageSize.width  * ratio),
	                         (int) (imageSize.height * ratio));
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() ->
		{
			Driver driver = new Driver();
			driver.setVisible(true);
		});
	}
}

