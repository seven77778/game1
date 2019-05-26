package com.game1;

import java.util.Random;

/**
 * ?§Ù??: ?????????????
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;  //???????
	
	/** ????????? */
	public Airplane(){
		this.image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/** ??????? */
	@Override
	public int getScore() {  
		return 5;
	}

	/** //??¼“?? */
	@Override
	public 	boolean outOfBounds() {   
		return y>ShootGame.HEIGHT;
	}

	/** ??? */
	@Override
	public void step() {   
		y += speed;
	}

}

