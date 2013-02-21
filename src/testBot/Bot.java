package testBot;

import game.engine.World;
import game.physics.objects.*;
import game.physics.objects.Vehicle;
import game.server.BotAction;
import game.utils.*;

public class Bot {
	public Bot() {
	}
	
	public void init() {
		
	}
	
	public final double TURN = 1;
	public final double POWER = 1; 
	public final int BORDER_REFLECTION = 30;
	public final int BORDER_SLOWING = 150;
	public final double RETORDATION_COEFFICIENT = 0.1;
	public final double EXTRA_PIXELS_IN_DODGE = 4;
	
	
	private World world;
	private Vehicle self;
	private BotAction action;
	
	public void move(World world, Vehicle self, BotAction action) {
		this.world = world;
		this.self = self;
		this.action = action;
		bot();
	}
	
	private void bot() {
		Bonus bonus = getNeigborBonus();
		if ( bonus != null ) {
			/* found the target */
			Vector2d goal = bonus.position;
			/* if obstacle, then find a waypoint */
			goal = getTemporaryGoal(goal);
			pivotedToTheGoal(goal);
			speedControl(goal);
		}
		
	}
	
	private Vector2d getTemporaryGoal(Vector2d goal) {
		Vector2d tempGoal;
		for (int i = 0; i < world.vehicles.size(); i++ ) {
			tempGoal = getGoalNotCollision(goal, world.vehicles.get(i).position, world.vehicles.get(i).radius);
			if ( tempGoal != null && world.vehicles.get(i).isMaterial 
					&& world.vehicles.get(i).id != self.id ) {
				goal = tempGoal;
			}
		}
		return goal;
	}
	
	private Vector2d getGoalNotCollision(Vector2d goal, Vector2d obstacle, double radius) {
		if ( equationOfLine(self.position, goal, obstacle.x) < obstacle.y + radius 
				&& equationOfLine(self.position, goal, obstacle.x) > obstacle.y - radius) {
			if ( equationOfLine(self.position, goal, obstacle.x) >= obstacle.y ) {
				Vector2d newGoal = new Vector2d();
				newGoal.x = obstacle.x;
				newGoal.y = obstacle.y + radius + self.radius + EXTRA_PIXELS_IN_DODGE;
				return newGoal;
			} else {
				Vector2d newGoal = new Vector2d();
				newGoal.x = obstacle.x;
				newGoal.y = obstacle.y - radius - self.radius - EXTRA_PIXELS_IN_DODGE;
				return newGoal;
			}
		}
		return null;
	}
	
	private void printBoom() {
		if ( (self.position.x - self.radius) <=0 ) {
			System.out.println("boom! X <= 1");
		}
		if ( (self.position.x + self.radius) >= world.width - 0 ) {
			System.out.println("boom! X >= world.width - 1");
		}
		if ( (self.position.y - self.radius) <=0 ) {
			System.out.println("boom! Y <= 1");
		}
		if ( (self.position.y + self.radius) >= world.height - 0 ) {
			System.out.println("boom! Y >= world.height - 1");
		}
	}
	
	private boolean isItTrueDirection(Vector2d goal) {
		if ( self.position.x == getPointLine().x ) {
			return false;
		}
		if ( getCourse() == 1 || getCourse() == 4 ) {
			if ( self.position.x > goal.x ) {
				return false;
			}
		} else {
			if ( self.position.x < goal.x ) {
				return false;
			}
		}
		return true;
	}
	
	private void speedControl(Vector2d goal) {
		printBoom();
		if ( isItTrueDirection(goal) ) {
			speedControlOnTheDistanceToTheBonus(goal);
		} else {
			action.power = POWER * 0.1;
		}
		reflectionBorder();
	}
	
	private void speedControlOnTheDistanceToTheBonus(Vector2d goal) {
		double distance = getDistance(self.position, goal);
		if ( distance < 10 ) {
			action.power = POWER * 0.5; 
		} else {	
			if ( distance < 30 ) { 
				action.power = POWER * 0.6; 
			} else {
				if ( distance < 50 ) {
					action.power = POWER * 0.7;
				} else {
					if ( distance < 70 )  {
						action.power = POWER * 0.8;
					} else {
						if ( distance < 90 ) {
							action.power = POWER * 0.9;
						} else {
							action.power = POWER * 1.0;
						}
					}
				}
			}
		}
	}
	
	private void reflectionBorder() {
		if ( self.position.x < BORDER_SLOWING  && (getCourse() == 2 || getCourse() == 3) ) {
			if ( self.position.x < BORDER_REFLECTION && (getCourse() == 2 || getCourse() == 3) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.position.x > world.width - BORDER_SLOWING && (getCourse() == 1 || getCourse() == 4) ) {
			if ( self.position.x > world.width - BORDER_REFLECTION && (getCourse() == 1 || getCourse() == 4) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.position.y < BORDER_SLOWING && (getCourse() == 1 || getCourse() == 2) ) {
			if ( self.position.y < BORDER_REFLECTION && (getCourse() == 1 || getCourse() == 2) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.position.y > world.height - BORDER_SLOWING && (getCourse() == 3 || getCourse() == 4) ) {
			if ( self.position.y > world.height - BORDER_REFLECTION && (getCourse() == 3 || getCourse() == 4) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
	}
	
	private int getCourse() {
		Vector2d pointLine = getPointLine();
		if ( (pointLine.x > self.position.x) && (pointLine.y > self.position.y) )
			return 4;
		if ( (pointLine.x > self.position.x) && (pointLine.y < self.position.y) )
			return 1;
		if ( (pointLine.x < self.position.x) && (pointLine.y > self.position.y) )
			return 3;
		if ( (pointLine.x < self.position.x) && (pointLine.y < self.position.y) )
			return 2;
		return 0;
	}
	
	private Bonus getNeigborBonus() {
		if ( world.bonuses.isEmpty() ) return null;
		Bonus bonus = world.bonuses.get(0);
		for (int i = 1; i < world.bonuses.size(); i++) {
			Bonus currentBonus = world.bonuses.get(i);
			if ( getDistance(bonus.position, self.position) > 
					getDistance(currentBonus.position, self.position) ) {
				bonus = currentBonus;
			}
		}
		return bonus;
	}
	
	private double getDistance(Vector2d a, Vector2d b) {
		return  Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	private void pivotedToTheGoal(Vector2d goal) {
		Vector2d pointLine = getPointLine();
		if ( pointLine.x == self.position.x ) {
			action.turn = 0;
			if ( goal.x < self.position.x ) {
				if ( self.position.y < pointLine.y ) {
					action.turn = -TURN; 
				} else {
					action.turn = TURN;
				}
				return;
			}
			if ( goal.x > self.position.x ) {
				if ( self.position.y < pointLine.y ) {
					action.turn = TURN;
				} else {
					action.turn = -TURN;
				}
				return;
			}
			action.turn = 0;
			return;
		}
		if ( equationOfLine(self.position, pointLine, goal.x) == goal.y ) {
			action.turn = 0;
		} else {
			if ( equationOfLine(self.position, pointLine, goal.x) > goal.y ) {
				if ( self.position.x < pointLine.x ) {
					action.turn = TURN;
				} else {
					action.turn = -TURN;
				}
			} else {
				if ( self.position.x < pointLine.x ) {
					action.turn = -TURN;
				} else {
					action.turn = TURN;
				}
			}
		}
	}
	
	private double equationOfLine(Vector2d a, Vector2d b, double x) {
		return (x - a.x)*(b.y - a.y)/(b.x - a.x) + a.y;
	}
	
	private Vector2d getPointLine() {
		Vector2d pointLine = new Vector2d();
		pointLine.x = self.position.x + self.radius * Math.sin(self.angle);
		pointLine.y = self.position.y + self.radius * Math.cos(self.angle);
		return pointLine;
	}
	
}

