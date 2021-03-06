package com.supermario.src.cn.edu.tlu.mario;

import com.supermario.src.cn.edu.tlu.sonar.SonarSoundEngine;
import com.supermario.src.cn.edu.tlu.sonar.SoundListener;

import java.awt.Graphics;


/**
 * 场景抽象类
 * @author G
 *
 */
public abstract class Scene implements SoundListener {
	public SonarSoundEngine sound;
	public static boolean[] keys = new boolean[16];

	public void toggleKey(int key, boolean isPressed) {
		keys[key] = isPressed;
	}

	public final void setSound(SonarSoundEngine sound) {
		sound.setListener(this);
		this.sound = sound;
	}

	public abstract void init();

	public abstract void tick();

	public abstract void render(Graphics og, float alpha);
}