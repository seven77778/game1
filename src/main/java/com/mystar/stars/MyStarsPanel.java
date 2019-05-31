package com.mystar.stars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * ������-�����
 * @author G
 *
 */
public class MyStarsPanel extends JPanel implements Runnable{
	
	// �������꼯��
	int[] xx = new int[302];
	int[] yy = new int[302];
	
	public MyStarsPanel() {
		// ��������
		for (int i = 0; i < 300; i++) {
			xx[i] = (int)(Math.random()*800);
			yy[i] = (int)(Math.random()*600);
		}
		// ��������
		xx[300]=600;
		yy[300]=70;
		// ��������
		xx[301]=540;
		yy[301]=70;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.BLACK);
		// ������
		g.setFont(new Font("΢���ź�", Font.BOLD,120));
		g.setColor(Color.yellow);
		g.drawString("����", xx[200], yy[200]);
		// ������
		g.setColor(Color.black);
		g.drawString("~~", xx[301], yy[301]);
		
		// ������
		for (int i = 0; i < 300; i++) {
			g.setFont(new Font("΢���ź�", Font.BOLD, (int)(Math.random()*10)+10));
			g.setColor(new Color((int)(Math.random()*200)+55, (int)(Math.random()*200)+55, (int)(Math.random()*200)+55));
			g.drawString("*", xx[i], yy[i]);
		}
	}
	
	@Override
	public void run(){
		
		while(true){
			for (int i = 0; i < 300; i++) {
				yy[i]++;
				xx[i]++;
				if (yy[i]>600) {
					yy[i]=1;
				}
				if (xx[i]>800) {
					xx[i]=1;
				}
			}
			
			xx[301]++;
			if (xx[301]==600) {
				xx[301]=540;
			}

			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			repaint();
		}
	}
}
