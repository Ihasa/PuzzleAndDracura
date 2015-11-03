package puzzlefield;

class Animation extends Thread{
	private Job job;
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

