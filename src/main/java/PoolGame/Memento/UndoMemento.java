package PoolGame.Memento;

import java.util.ArrayList;
import java.util.List;
import PoolGame.Config.TableConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.Score;
import PoolGame.Items.Clock;
import PoolGame.Release.UpdateScoreObservable;

public class UndoMemento {
	protected List<BallParameter> prev_ball;
	protected List<Ball> ball_ref;
	protected long current_time;
	protected static int score;
	protected Clock clk;
	protected Score scr;
	protected UpdateScoreObservable uscr;
	private static boolean update_done = false;
	private static boolean registerobs = false;
	
	public UndoMemento (TableConfig config, Clock clock, List<Ball> balls) {
        prev_ball = new ArrayList<BallParameter>();
        clk = clock;
        scr = scr.getInstance();
        ball_ref = balls;
        uscr = new UpdateScoreObservable();
	}
	
	public void update_record(List<Ball> balls, int scr)
	{
		score= scr;
		current_time = clk.getTime();
		prev_ball.removeAll(prev_ball);
		ball_ref = balls;
		if (registerobs == false) {
			uscr.addObserver();
			registerobs = true;
		}
		for (Ball ball : balls) {
            this.prev_ball.add(new BallParameter(ball));
        }
	}
	
	public void update_record()
	{
		if (registerobs == false) {
			uscr.addObserver();
			registerobs = true;
		}
		score= this.scr.getScore();
		current_time = clk.getTime();
		prev_ball.removeAll(prev_ball);
		for (Ball ball : ball_ref) {
            this.prev_ball.add(new BallParameter(ball));
        }
		update_done = true;
	}
	
	public void update_ball_ref(List<Ball> balls)
	{
		prev_ball.removeAll(prev_ball);
		ball_ref = balls;
	}
	
	public void resort_record()
	{	
		if(update_done) {
			update_done = false;
			uscr.resetScore(score);
			clk.resetTime(current_time);
			int y = 0;
			for (Ball ball : ball_ref) {
				ball.setXPos(prev_ball.get(y).getX());
				ball.setYPos(prev_ball.get(y).getY());
				ball.enableBall(prev_ball.get(y).getmfd());
				y = y + 1;
			}
		}
	}
}
