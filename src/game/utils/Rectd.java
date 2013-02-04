package game.utils;

public class Rectd {
	public Vector2d LT = new Vector2d(),
			RB = new Vector2d();
	
	public static Rectd createSquare(Vector2d center, double halfSide) {
		Rectd res = new Rectd();
		res.LT.assign(center);
		res.LT.sub(halfSide, halfSide);
		res.RB.assign(center);
		res.RB.add(halfSide, halfSide);
		return res;
	}
	
	public boolean isIntersect(Rectd r) {
		if(RB.x<r.LT.x || LT.x>r.RB.x)
			return false;
		if(RB.y<r.LT.y || LT.y>r.RB.y)
			return false;
		return true;
	}
}
