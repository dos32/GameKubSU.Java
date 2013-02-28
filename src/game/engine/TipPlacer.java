package game.engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;

import game.Runner;
import game.physics.objects.AnimatedTip;
import game.utils.Utils;
import game.utils.Vector2d;

public class TipPlacer {
	
	protected static Dimension tmpSize;

	public static void placeTip(AnimatedTip tip, Vector2d position) {
		tmpSize = tip.getSize((Graphics2D)Runner.inst().graphics());
		tip.position.assign(Utils.trimToInterval(position.x, 0, Runner.inst().world.width - tmpSize.width),
				Utils.trimToInterval(position.y, tmpSize.height, Runner.inst().world.height));
	}

	public static void placeTip(AnimatedTip tip, double x, double y) {
		tmpSize = tip.getSize((Graphics2D)Runner.inst().graphics());
		tip.position.assign(Utils.trimToInterval(x, 0, Runner.inst().world.width - tmpSize.width),
				Utils.trimToInterval(y, tmpSize.height, Runner.inst().world.height));
	}
	
	public static void placeTips(List<AnimatedTip> tips, Vector2d position) {
		double indent = 0;
		for(AnimatedTip tip : tips) {
			placeTip(tip, position.x, position.y + indent);
			indent += tmpSize.height + Settings.AnimatedTip.stackIndent;
		}
	}
}
