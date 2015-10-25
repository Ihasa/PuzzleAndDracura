package puzzlefield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public class AnimationThread extends Thread{
	FieldState currentState, prevState;
	Graphics graphics;
	
	private int blockWidth;
	private int blockHeight;
	private Panel applet;
	private Image[] img;
	public AnimationThread(FieldState current, FieldState prev, int width, int height, Panel app, Image[] images){
		currentState = current;
		prevState = prev;
		
		applet = app;
		blockWidth = width;
		blockHeight = height;
		graphics = applet.getGraphics();
		img = images;
	}
	public void run(){
		int ms = 100;
		int dt = 20;
		int allFrames = ms / dt;
		int frames = allFrames + 1;
		DiffIndex diff = getDiff(currentState,prevState);
		
		if(diff == null){
			graphics.clearRect(0, 0, currentState.width * blockWidth, currentState.height * blockHeight);
			for(int y = 0; y < currentState.height; y++){
				for(int x = 0; x < currentState.width; x++){
					BlockColor color = currentState.get(x, y);
					Cursor cursor = currentState.getCursor();
					
					if(color != BlockColor.EMPTY){
						if(cursor.selected && cursor.x == x && cursor.y == y){
							graphics.setColor(Color.MAGENTA);
							graphics.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
						}
						int imgIndex = color.ordinal();
						graphics.drawImage(img[imgIndex], x * blockWidth, y * blockHeight, blockWidth, blockHeight, applet);
					}
				}
			}
		}else{
			while(frames-- > 0){
				graphics.clearRect(0, 0, currentState.width * blockWidth, currentState.height * blockHeight);
				for(int y = 0; y < currentState.height; y++){
					for(int x = 0; x < currentState.width; x++){
						BlockColor color = currentState.get(x, y);
						Cursor cursor = currentState.getCursor();
						
						if(color != BlockColor.EMPTY){
							if(cursor.selected && cursor.x == x && cursor.y == y){
								graphics.setColor(Color.MAGENTA);
								graphics.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
							}
							int imgIndex = color.ordinal();
							float rate = frames / (float)allFrames;
							if(x == diff.x1 && y == diff.y1){
								graphics.drawImage(img[imgIndex],
										(int)(blockWidth * (x + (diff.x2 - x) * rate)),
										(int)(blockHeight * (y + (diff.y2 - y) * rate)),
										blockWidth,
										blockHeight,
										applet);
							}else if(x == diff.x2 && y == diff.y2){
								graphics.drawImage(img[imgIndex], 
										(int)(blockWidth * (x + (diff.x1 - x) * rate)),
										(int)(blockHeight * (y + (diff.y1 - y) * rate)),
										blockWidth, 
										blockHeight, 
										applet);									
							}else{
								graphics.drawImage(img[imgIndex], x * blockWidth, y * blockHeight, blockWidth, blockHeight, applet);
							}
						}
					}
				}
				try{
					Thread.sleep(dt);
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
	private DiffIndex getDiff(FieldState f1, FieldState f2){
		if(f1.equals(f2))
			return null;
		DiffIndex index = new DiffIndex();
		int found = 0;
		Cursor c1 = f1.getCursor();
		Cursor c2 = f2.getCursor();
		for(int y = 0; y < f1.height; y++){
			for(int x = 0; x < f1.width; x++){
				if(f1.get(x, y) != f2.get(x, y)){
					if(found == 0){
						index.x1 = x;
						index.y1 = y;
						found++;
					}else{
						index.x2 = x;
						index.y2 = y;
						return index;
					}
				}
			}
		}
		found = 0;
		for(int y = 0; y < f1.height; y++){
			for(int x = 0; x < f1.width; x++){
				if((c1.x == x && c1.y == y) || (c2.x == x && c2.y == y)){
					if(found == 0){
						index.x1 = x;
						index.y1 = y;
						found++;
					}else{
						index.x2 = x;
						index.y2 = y;
						return index;
					}
				}
			}
		}
		return null;
	}
	private class DiffIndex{
		int x1, y1;
		int x2, y2;
	}
}
