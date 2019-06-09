package com.saolei;

/**
 * Created by lsh on 2019-05-28.
 *
 * from : blog.csdn.net/su20145104009/article/details/52152514
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;





import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class MineClient extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //??????
    private int screenWidth;
    //??????
    private int screenHeight;
    //?????
    private int imgWidth = 20;
    //?????
    private int imgHeight = 20;
    //?????????
    private int rowNum = 0;
    //?????????
    private int colNum = 0;
    //?????????
    private int mineNum=99;
    //?????
    private int timer=0;
    //??????
    private int time=0;
    //¦Ä???????
    private int restMine;
    //??????????
    private int notMine;
    private MyPanel myPanel;
    //????????
    private String gameState = "start";
    //????¦Å??
    private boolean firstClick = true;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem lowLevel;
    private JMenuItem midLevel;
    private JMenuItem heightLevel;
    private JMenuItem restart;
    private Toolkit tk=Toolkit.getDefaultToolkit();
    private Image icon=tk.getImage("Image/icon.jpg");
    //???????
    private ArrayList<Bomb> bombList = new ArrayList<Bomb>();


    public MineClient(int screenWidth,int screenHeight,int mineNum) {
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
        this.mineNum=mineNum;
        //??????????
        initMenu();
        setTitle("???");
        setIconImage(icon);
        setSize(screenWidth	, screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        initList();
        myPanel = new MyPanel();
        myPanel.setBackground(Color.BLACK);
        add(myPanel);
        //??????
        myPanel.addMouseListener(new MyMouseListener(this));
        new updateThread().start();
    }
    /*
     * ??????????
     */
    private void initMenu() {
        menu=new JMenu("????????");
        menuBar=new JMenuBar();
        lowLevel=new JMenuItem("??????10?????");
        midLevel=new JMenuItem("?§Þ???44?????");
        heightLevel=new JMenuItem("?????99?????");
        restart=new JMenuItem("??????");
        lowLevel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(225,305,10);
            }
        });
        midLevel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(380,460,44);
            }
        });
        heightLevel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(660,460,99);
            }
        });
        restart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
                new MineClient(screenWidth,screenHeight,mineNum);
            }
        });
        menu.add(restart);
        menu.add(new JSeparator());
        menu.add(lowLevel);
        menu.add(midLevel);
        menu.add(heightLevel);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public boolean isFirstClick() {
        return firstClick;
    }
    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }
    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public MyPanel getMyPanel() {
        return myPanel;
    }

    public void setMyPanel(MyPanel myPanel) {
        this.myPanel = myPanel;
    }
    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Bomb> getBombList() {
        return bombList;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getMineNum() {
        return mineNum;
    }

    //???????
    private void initList() {
        for (int i = imgWidth; i < this.getWidth() - 2 * imgWidth; i += imgWidth) {
            for (int j = imgWidth; j < this.getHeight() - 6 * imgWidth; j += imgHeight) {
                rowNum = rowNum > i / imgWidth ? rowNum : i / imgWidth;
                colNum = colNum > j / imgWidth ? colNum : j / imgWidth;
                Bomb bomb = new Bomb(i, j, 13, this);
                bombList.add(bomb);
            }
        }
    }

    public static void main(String[] args) {
        new MineClient(225,305,10);

    }


    //?????panel
    public class MyPanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void paint(Graphics g) {
            super.paintComponent(g);
            restMine=mineNum;
            notMine=0;
            //?????? ????
            for (Bomb bomb : bombList) {
                bomb.draw(g);
                if(bomb.getWhat()==11) {
                    restMine--;
                }
                if(bomb.getWhat()>=0&&bomb.getWhat()<=8) {
                    notMine++;
                }
            }
            //??????
            if (gameState.equals("lose")) {
                for (Bomb bomb : bombList) {
                    if (bomb.getHide() == 9) {
                        bomb.setWhat(bomb.getHide());
                    }
                }
                Font font = new Font("??????", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(new Color(255, 0, 255));
                g.drawString("GAME OVER!!", this.getWidth() / 2 - 80,
                        this.getHeight() / 2);
            }
            //???????????????  ??¦Ä?????????
            drawTimeAndMineNum(g);

            //?????????
            if(!gameState.equals("lose") & restMine+mineNum==colNum*rowNum)
            {
                gameState="win";
                Toolkit tk=Toolkit.getDefaultToolkit();
                Image img=tk.getImage("Image/win.jpg");
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
                Font font1 = new Font("?????§á?", Font.BOLD, 40);
                g.setFont(font1);
                g.setColor(new Color(248 ,29 ,56));
                g.drawString("YOU WIN!!!", this.getWidth()/2-100, 30);
            }
        }

        private void drawTimeAndMineNum(Graphics g) {
            Font font = new Font("??????", Font.BOLD, 15);
            g.setFont(font);
            g.setColor(Color.orange);
            g.drawString("???????"+time+" ??", 0, this.getHeight()-20);
            g.drawString("¦Ä????"+restMine+" ??", this.getWidth()-100, this.getHeight()-20);

        }
    }
    //??????100ms??????
    public class updateThread extends Thread {
        @Override
        public void run() {

            while (true) {
                repaint();
                if(!firstClick){
                    timer+=100;
                    if(timer==1000){
                        timer=0;
                        time++;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}