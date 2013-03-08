package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.imageio.ImageIO;

public class Obstacle extends Circle implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 4749768202608073202L;
	protected static BufferedImage image = null;
	
	public Obstacle(double radius) {
		super(radius);
	}

	public static void prepareImages() {
		if(image == null) {
			try {
				URL url = Runner.class.getResource("/res/tire1.png");
				image = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		if(Settings.Renderer.drawImages) {
			prepareImages();
			AffineTransform oldTransform = graphics.getTransform();
			graphics.translate(position.x, position.y);
			//graphics.rotate(angle);
			graphics.drawImage(image, (int)-radius, (int)-radius,
					(int)(2*radius), (int)(2*radius), null);
			graphics.setTransform(oldTransform);
		}
		else {
			AffineTransform oldTransform = graphics.getTransform();
			graphics.translate(position.x-radius, position.y-radius);
			graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
			graphics.setTransform(oldTransform);
		}
	}
	
	@Override
	public String getClassName() {
		return "Obstacle";
	}
	
	@Override
	public JSONObject toJSON() {
		return super.toJSON();
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
	}
	
}
