package puzzlefield;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class BlockMoveDrawer extends AppletFieldDrawer{

	public BlockMoveDrawer(Applet app, int bWidth, int bHeight) {
		super(app, bWidth, bHeight);

	}

	@Override
	protected void drawAllBlocks(Graphics g, FieldState fState) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				drawBlock(g, x, y, fState);
			}
		}
	}
	private void drawBlock(Graphics g, int x, int y, FieldState fState){
		BlockColor color = fState.get(x, y);
		Cursor cursor = fState.getCursor();
		if(cursor.selected && cursor.x == x && cursor.y == y){
			g.setColor(Color.MAGENTA);
			g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
		}
		drawBlockImage(g,color,x * blockWidth,y * blockHeight,blockWidth,blockHeight);
	}
	
	Animation th;
	@Override
	protected void drawUpdatedBlocks(Graphics g, FieldState current, FieldState prev, FieldIndex[] diff) {
		th = new Animation((frame, allFrame)->{
			g.clearRect(0, 150, 200, 20);
			g.drawString("x = " + ((float)frame / allFrame), 0, 170);

			System.out.println("x = " + ((float)frame / allFrame));
		}, 1000, 20);
		th.start();
		
	}	
}
interface Job{
	public void doJob(int currentFrame, int allFrame);
}
class Animation extends Thread{
	Job job;
	int ms;
	int dt;
	int frames;
	int currentFrame;
	public Animation(Job j, int timeInMillis, int sleepTime){
		job = j;
		ms = timeInMillis;
		dt = sleepTime;
		frames = ms / dt;
		currentFrame = frames;
	}
	public void run(){
		while(currentFrame >= 0){
			job.doJob(currentFrame, frames);
			currentFrame--;
			
			try {
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

