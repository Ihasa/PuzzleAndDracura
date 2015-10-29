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
	protected void drawUpdatedBlocks(Graphics graphics, FieldState currentState, FieldState prev, FieldIndex[] diff) {
		th = new Animation((frames, allFrames)->{
			if(diff.length != 2)
				return;
			for(FieldIndex idx:diff){
				graphics.clearRect(idx.x * blockWidth, idx.y * blockHeight, blockWidth, blockHeight);
			}
			FieldIndex idx1 = diff[0];
			FieldIndex idx2 = diff[1];
			float rate = frames / (float)allFrames;
//			Cursor cursor = currentState.getCursor();
			
//			if(cursor.selected){
//				graphics.setColor(Color.MAGENTA);
//				graphics.fillRect(cursor.x * blockWidth, cursor.y * blockHeight, blockWidth, blockHeight);
//			}

			this.drawBlockImage(graphics, currentState.get(idx1.x,idx1.y), 
					(int)(blockWidth * (idx1.x + (idx2.x - idx1.x) * rate)),
					(int)(blockHeight * (idx1.y + (idx2.y - idx1.y) * rate)),
					blockWidth,
					blockHeight
					);
			this.drawBlockImage(graphics, currentState.get(idx2.x,idx2.y), 
					(int)(blockWidth * (idx2.x + (idx1.x - idx2.x) * rate)),
					(int)(blockHeight * (idx2.y + (idx1.y - idx2.y) * rate)),
					blockWidth, 
					blockHeight
					);
		}, 120);
		th.start();
		
	}	
}
interface Job{
	public void doJob(int currentFrame, int allFrame);
}
class Animation extends Thread{
	Job job;
	private int ms;
	private static final int dt = 20;
	private int frames;
	private int currentFrame;
	public Animation(Job j, int timeInMillis){
		job = j;
		ms = timeInMillis;
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

