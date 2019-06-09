package com.supermario.src.cn.edu.tlu.mario.level;

import com.supermario.src.cn.edu.tlu.mario.LevelScene;
import com.supermario.src.cn.edu.tlu.mario.sprites.Enemy;
import com.supermario.src.cn.edu.tlu.mario.sprites.FlowerEnemy;
import com.supermario.src.cn.edu.tlu.mario.sprites.Sprite;

/**
 * 小东西模板 | 花朵、敌人
 * @author G
 *
 */
public class SpriteTemplate {
	public int lastVisibleTick = -1;
	public Sprite sprite;
	public boolean isDead = false;
	private boolean winged;

	private int type;

	public SpriteTemplate(int type, boolean winged) {
		this.type = type;
		this.winged = winged;
	}

	public void spawn(LevelScene world, int x, int y, int dir) {
		if (isDead)
			return;

		if (type == Enemy.ENEMY_FLOWER) {
			sprite = new FlowerEnemy(world, x * 16 + 15, y * 16 + 24);
		} else {
			sprite = new Enemy(world, x * 16 + 8, y * 16 + 15, dir, type, winged);
		}
		sprite.spriteTemplate = this;
		world.addSprite(sprite);
	}
}