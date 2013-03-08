package game.engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;

import game.Runner;
import game.physics.objects.InfoTip;
import game.utils.Utils;
import game.utils.Vector2d;

public class TipPlacer {
	
	protected static Dimension tmpSize;

	public static void placeTip(InfoTip tip, Vector2d position) {
		tmpSize = tip.getSize((Graphics2D)Runner.inst().graphics());
		tip.position.assign(Utils.trimToInterval(position.x, 0, Runner.inst().world.width - tmpSize.width),
				Utils.trimToInterval(position.y, 0, Runner.inst().world.height-tmpSize.height));
	}

	public static void placeTip(InfoTip tip, double x, double y) {
		tmpSize = tip.getSize((Graphics2D)Runner.inst().graphics());
		tip.position.assign(Utils.trimToInterval(x, 0, Runner.inst().world.width - tmpSize.width),
				Utils.trimToInterval(y, 0, Runner.inst().world.height-tmpSize.height));
	}
	
	public static void placeTips(List<? extends InfoTip> tips, Vector2d position) {
		placeTips(tips, position.x, position.y);
	}
	
	public static void placeTips(List<? extends InfoTip> tips, double x, double y) {
		if(tips.size() > 0)
			placeTip(tips.get(0), x, y);
		for(int i = 1; i < tips.size(); i++)
			joinBottom(tips.get(i-1), tips.get(i), Settings.AnimatedTip.stackIndent);
	}
	
	/**
	 * Joins tip under the anchor tip
	 * @param anchor	Anchor tip, relative to that position of tip will be calculated
	 * @param tip		Tip, which position is need to be calculated
	 * @param indent	Indent between tips
	 * @return	true if best position of new tip is out of screen
	 * 	and it moved to other position; false if tip is placed to its best position
	 */
	public static boolean joinBottom(InfoTip anchor, InfoTip tip, double indent) {
		return join(anchor, tip, 0, 1, indent);
	}
	
	public static boolean join(InfoTip anchor, InfoTip tip, double xDirection, double yDirection, double indent) {
		Dimension sz = tip.getSize(Runner.inst().graphics());
		double x = anchor.position.x + xDirection*(sz.width+indent);
		double y = anchor.position.y + yDirection*(sz.height+indent);
		double realX = Utils.trimToInterval(x, 0, Runner.inst().world.width-sz.width);
		double realY = Utils.trimToInterval(y, 0, Runner.inst().world.height-sz.height);
		tip.position.assign(realX, realY);
		return x == realX && y == realY;
	}
	
}
