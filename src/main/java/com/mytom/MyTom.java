package com.mytom;

import com.mytom.common.Utils;

import javax.swing.*;

/**
 * 汤姆猫
 * @author G
 *
 */
public class MyTom {

	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("汤姆猫");
		Utils.initFrame(frame, 430, 700);
		
		MyTomPanel panel = new MyTomPanel();
		frame.add(panel);
		frame.addMouseListener(panel);
		
		Thread thread = new Thread(panel);
		thread.start();
		
		
	}
}
