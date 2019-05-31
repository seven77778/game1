package com.mystar.stars;

import com.mystar.common.Utils;

import javax.swing.JFrame;

/**
 * 满天星
 * @author G
 *
 */
public class MyStars {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Utils.initFrame(frame, 800, 600);
		
		MyStarsPanel panel = new MyStarsPanel();
		frame.add(panel);
		
		Thread thread = new Thread(panel);
		thread.start();
	}
}
