package game.graphics;

import game.Runner;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite {
	
	private URL resURL;
	public int
//			x = 0,
//			y = 0,
			n = 1,
			m = 1;
	
	public double animationSpeed = 0.2;
	private Image[] images;
	
	public Sprite(String resName) {
		this.resURL = Runner.class.getResource(resName);
	}
	
	public Sprite(URL resURL) {
		this.resURL = resURL;
	}
	
	public Sprite(String resName, int n, int m, double animationSpeed) {
		this(resName);
		this.n = n;
		this.m = m;
		this.animationSpeed = animationSpeed;
	}
	
	public Sprite(URL resURL, int n, int m, double animationSpeed) {
		this(resURL);
		this.n = n;
		this.m = m;
		this.animationSpeed = animationSpeed;
	}
	
	private void lazyLoad() {
		if(images == null) {
			BufferedImage src = null;
			try {
				src = ImageIO.read(resURL);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			int szx = src.getWidth() / n,
				szy = src.getHeight() / m;
			images = new BufferedImage[n*m];
			for (int j = 0; j < m; j++)
				for (int i = 0; i < n; i++)
				{
					BufferedImage bmp = new BufferedImage(szx, szy, BufferedImage.TYPE_INT_ARGB);
					images[j*n+i] = bmp;
					Graphics g = bmp.createGraphics();
					g.drawImage(src,
							0, 0, szx, szy,
							i*szx, j*szy, (i+1)*szx, (j+1)*szy,
							null);
				}
		}
	}
	
	private int getImgNum(double lifeTime) {
		return ((int)Math.round(animationSpeed * lifeTime)) % images.length;
	}
	
	public int getFramesCount() {
		lazyLoad();
		return images.length;
	}
	
	public int getWidth() {
		lazyLoad();
		return images[0].getWidth(null);
	}
	
	public int getHeight() {
		lazyLoad();
		return images[0].getHeight(null);
	}
	
	public Image getFrame() {
		lazyLoad();
		return images[0];
	}
	
	public Image getFrame(double lifeTime) {
		lazyLoad();
		return images[getImgNum(lifeTime)];
	}
	
}
