package client;

import client.model.*;
import client.utils.*;
import client.model.World;
import client.model.Vehicle;
import client.model.BotAction;

public class Bot {
	public final double TURN = -1;
	public final double POWER = 1; 
	public final int BORDER_REFLECTION = 30;
	public final int BORDER_SLOWING = 150;
	public final double RETORDATION_COEFFICIENT = 0.1;
	public final double EXTRA_PIXELS_IN_DODGE = 4;
	
	private World world;
	private Vehicle self;
	private BotAction action;
	
	public Bot() {
	}
	
	public String init() {
		return "TestBot";
	}
	
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
			Vector2d goal = bonus.getPosition();
			/* if obstacle, then find a waypoint */
			goal = getTemporaryGoal(goal);
			pivotedToTheGoal(goal);
			speedControl(goal);
		}
		
	}
	
	private Vector2d getTemporaryGoal(Vector2d goal) {
		Vector2d tempGoal;
		for (int i = 0; i < world.getVehicles().length; i++ ) {
			tempGoal = getGoalNotCollision(goal, world.getVehicles()[i].getPosition(), world.getVehicles()[i].getRadius());
			if ( tempGoal != null && world.getVehicles()[i].getId() != self.getId() ) {
				goal = tempGoal;
			}
		}
		return goal;
	}

	private Vector2d getGoalNotCollision(Vector2d goal, Vector2d obstacle, double radius) {
		if ( equationOfLine(self.getPosition(), goal, obstacle.x) < obstacle.y + radius 
				&& equationOfLine(self.getPosition(), goal, obstacle.x) > obstacle.y - radius) {
			if ( equationOfLine(self.getPosition(), goal, obstacle.x) >= obstacle.y ) {
				Vector2d newGoal = new Vector2d();
				newGoal.x = obstacle.x;
				newGoal.y = obstacle.y + radius + self.getRadius() + EXTRA_PIXELS_IN_DODGE;
				return newGoal;
			} else {
				Vector2d newGoal = new Vector2d();
				newGoal.x = obstacle.x;
				newGoal.y = obstacle.y - radius - self.getRadius() - EXTRA_PIXELS_IN_DODGE;
				return newGoal;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private void printBoom() {
		if ( (self.getPosition().x - self.getRadius()) <=0 ) {
			System.out.println("boom! X <= 1");
		}
		if ( (self.getPosition().x + self.getRadius()) >= world.getWidth() - 0 ) {
			System.out.println("boom! X >= world.width - 1");
		}
		if ( (self.getPosition().y - self.getRadius()) <=0 ) {
			System.out.println("boom! Y <= 1");
		}
		if ( (self.getPosition().y + self.getRadius()) >= world.getHeight() - 0 ) {
			System.out.println("boom! Y >= world.height - 1");
		}
	}
	
	private boolean isItTrueDirection(Vector2d goal) {
		if ( self.getPosition().x == getPointLine().x ) {
			return false;
		}
		if ( getCourse() == 1 || getCourse() == 4 ) {
			if ( self.getPosition().x > goal.x ) {
				return false;
			}
		} else {
			if ( self.getPosition().x < goal.x ) {
				return false;
			}
		}
		return true;
	}
	
	private void speedControl(Vector2d goal) {
		//printBoom();
		if ( isItTrueDirection(goal) ) {
			speedControlOnTheDistanceToTheBonus(goal);
		} else {
			action.power = POWER * 0.1;
		}
		reflectionBorder();
	}
	
	private void speedControlOnTheDistanceToTheBonus(Vector2d goal) {
		double distance = getDistance(self.getPosition(), goal);
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
		if ( self.getPosition().x < BORDER_SLOWING  && (getCourse() == 2 || getCourse() == 3) ) {
			if ( self.getPosition().x < BORDER_REFLECTION && (getCourse() == 2 || getCourse() == 3) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.getPosition().x > world.getWidth() - BORDER_SLOWING && (getCourse() == 1 || getCourse() == 4) ) {
			if ( self.getPosition().x > world.getWidth() - BORDER_REFLECTION && (getCourse() == 1 || getCourse() == 4) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.getPosition().y < BORDER_SLOWING && (getCourse() == 1 || getCourse() == 2) ) {
			if ( self.getPosition().y < BORDER_REFLECTION && (getCourse() == 1 || getCourse() == 2) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
		if ( self.getPosition().y > world.getHeight() - BORDER_SLOWING && (getCourse() == 3 || getCourse() == 4) ) {
			if ( self.getPosition().y > world.getHeight() - BORDER_REFLECTION && (getCourse() == 3 || getCourse() == 4) ) {
				action.power = -1;
				return;
			}
			action.power *= RETORDATION_COEFFICIENT;
			return;
		}
	}
	
	private int getCourse() {
		Vector2d pointLine = getPointLine();
		if ( (pointLine.x > self.getPosition().x) && (pointLine.y > self.getPosition().y) )
			return 4;
		if ( (pointLine.x > self.getPosition().x) && (pointLine.y < self.getPosition().y) )
			return 1;
		if ( (pointLine.x < self.getPosition().x) && (pointLine.y > self.getPosition().y) )
			return 3;
		if ( (pointLine.x < self.getPosition().x) && (pointLine.y < self.getPosition().y) )
			return 2;
		return 0;
	}
	
	private Bonus getNeigborBonus() {
		if ( world.getBonuses().length == 0 ) return null;
		Bonus bonus = world.getBonuses()[0];
		for (int i = 1; i < world.getBonuses().length; i++) {
			Bonus currentBonus = world.getBonuses()[i];
			if ( getDistance(bonus.getPosition(), self.getPosition()) > 
					getDistance(currentBonus.getPosition(), self.getPosition()) ) {
				bonus = currentBonus;
			}
		}
		return bonus;
	}
	
	private double getDistance(Vector2d a, Vector2d b) {
		return  Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public double equationOfLine(Vector2d a, Vector2d b, double x) {
		return (x - a.x)*(b.y - a.y)/(b.x - a.x) + a.y;
	}
	
	private void pivotedToTheGoal(Vector2d goal) {
		Vector2d pointLine = getPointLine();
		if ( pointLine.x == self.getPosition().x ) {
			action.turn = 0;
			if ( goal.x < self.getPosition().x ) {
				if ( self.getPosition().y < pointLine.y ) {
					action.turn = -TURN; 
				} else {
					action.turn = TURN;
				}
				return;
			}
			if ( goal.x > self.getPosition().x ) {
				if ( self.getPosition().y < pointLine.y ) {
					action.turn = TURN;
				} else {
					action.turn = -TURN;
				}
				return;
			}
			action.turn = 0;
			return;
		}
		if ( equationOfLine(self.getPosition(), pointLine, goal.x) == goal.y ) {
			action.turn = 0;
		} else {
			if ( equationOfLine(self.getPosition(), pointLine, goal.x) > goal.y ) {
				if ( self.getPosition().x < pointLine.x ) {
					action.turn = TURN;
				} else {
					action.turn = -TURN;
				}
			} else {
				if ( self.getPosition().x < pointLine.x ) {
					action.turn = -TURN;
				} else {
					action.turn = TURN;
				}
			}
		}
	}
	
	private Vector2d getPointLine() {
		Vector2d pointLine = new Vector2d();
		pointLine.x = self.getPosition().x + self.getRadius() * Math.cos(self.getAngle());
		pointLine.y = self.getPosition().y + self.getRadius() * Math.sin(self.getAngle());
		return pointLine;
	}
}
