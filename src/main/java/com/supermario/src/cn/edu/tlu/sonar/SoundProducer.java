package com.supermario.src.cn.edu.tlu.sonar;

public interface SoundProducer {
	public float read(float[] buf, int readRate);

	public void skip(int samplesToSkip, int readRate);

	public boolean isLive();
}