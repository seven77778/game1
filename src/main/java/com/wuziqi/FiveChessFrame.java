package com.example.demo;

/**
 * Created by lsh on 2019-05-28.
 *
 * from : java.662p.com/thread-1915-1-2.html
 *
 */
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FiveChessFrame extends JFrame implements MouseListener, Runnable {
    private static final long serialVersionUID = 1L;
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int hight = Toolkit.getDefaultToolkit().getScreenSize().height;
    // ??????
    BufferedImage bjImage = null;
    // ????????????
    int x = 0;
    int y = 0;
    // ???????????????????
    // ?????????0:??????????????? 1????????? 2?????????
    int[][] allChess = new int[19][19];
    // ??????????èg????????????
    boolean isBlack = true;
    // ??????????????????
    boolean canPlay = true;
    // ???????????????
    String message = "???????";
    // ?????????ßÿ??????(??)
    int maxTime = 0;
    // ??????????????
    Thread t = new Thread(this);
    // ??????????????????
    int blackTime = 0;
    int whiteTime = 0;
    // ????????????????????
    String blackMessage = "??????";
    String whiteMessage = "??????";

    @SuppressWarnings("deprecation")
    public FiveChessFrame() {
        this.setTitle("??????");
        this.setSize(500, 500);
        this.setLocation((width - 500) / 2, (hight - 500) / 2);
        this.addMouseListener(this);
        // this.setResizable(false);
        this.setVisible(true);

        t.start();
        t.suspend();// ??????

        // ?????????????,??????????????????????
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            Resource resource = new ClassPathResource("background.jpg");
            File sourceFile =  resource.getFile();
            bjImage = ImageIO.read(sourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        // ????ùy?? ????????? ???????????????????ùy?????ßπ??????????????????????????????? g ??? g2 ???????
        // BufferedImage bi=new
        // BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
        // Graphics g2=bi.createGraphics();

        g.drawImage(bjImage, 0, 20, this);
        g.setFont(new Font("????", Font.BOLD, 20));
        g.drawString("????????" + message, 120, 60);
        g.setFont(new Font("????", 0, 14));
        g.drawString("??????" + blackMessage, 30, 470);
        g.drawString("??????" + whiteMessage, 260, 470);

        // ????????
        for (int i = 0; i < 19; i++) {
            g.drawLine(10, 70 + 20 * i, 370, 70 + 20 * i);
            g.drawLine(10 + 20 * i, 70, 10 + 20 * i, 430);
        }
        // ???ß≥???¶À
        g.fillOval(68, 128, 4, 4);
        g.fillOval(308, 128, 4, 4);
        g.fillOval(308, 368, 4, 4);
        g.fillOval(68, 368, 4, 4);
        g.fillOval(188, 128, 4, 4);
        g.fillOval(68, 248, 4, 4);
        g.fillOval(188, 368, 4, 4);
        g.fillOval(188, 248, 4, 4);
        g.fillOval(308, 248, 4, 4);

        // //????????
        // x=(x-10)/20*20+10; //?????????????????
        // y=(y-70)/20*20+70;
        // //????
        // g.fillOval(x-7, y-7, 14, 14);
        // //????
        // g.setColor(Color.BLACK);
        // g.fillOval(x-7, y-7, 14, 14);
        // g.setColor(Color.BLACK);
        // g.drawOval(x-7, y-7, 14, 14);

        // ????????????????
        // ???????????
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (allChess[i][j] == 1) {
                    // ????
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g.fillOval(tempX - 7, tempY - 7, 14, 14);
                }
                if (allChess[i][j] == 2) {
                    // ????
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - 7, tempY - 7, 14, 14);
                    g.setColor(Color.BLACK);
                    g.drawOval(tempX - 7, tempY - 7, 14, 14);
                }
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    private boolean checkWin() {
        boolean flag = false;
        // ???úÆ?ßÿ?????????????????
        int count = 1;
        // ?ßÿ???? ???allChess[x][y]??y????
        int color = allChess[x][y];
        // ?ßÿ????
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
            // ?ßÿ?????
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
                // ?ßÿ?????????
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                    // ?ßÿ?????????
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    // ?ßÿ?????????????
    private int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >= 0 && x + xChange <= 18 && y + yChange >= 0
                && y + yChange <= 18
                && color == allChess[x + xChange][y + yChange]) {
            count++;
            if (xChange != 0) {
                xChange++;
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (x - xChange >= 0 && x - xChange <= 18 && y - yChange >= 0
                && y - yChange <= 18
                && color == allChess[x - xChange][y - yChange]) {
            count++;
            if (xChange != 0) {
                xChange++;
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        return count;
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void mousePressed(MouseEvent e) {
        if (canPlay) {
            x = e.getX();
            y = e.getY();
            if (x >= 10 && x <= 370 && y >= 70 && y <= 430) {
                // System.out.println("???????¶∂???"+x+"--"+y);
                x = (x - 10) / 20; // ?????????????????
                y = (y - 70) / 20;
                if (allChess[x][y] == 0) {
                    // ?ßÿ???????????????
                    if (isBlack) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "??????";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "??????";
                    }
                    // ?ßÿ????????????????????????5??
                    boolean winFlag = this.checkWin();
                    if (winFlag) {
                        JOptionPane.showMessageDialog(this, "???????,"
                                + (allChess[x][y] == 1 ? "???" : "???") + "???!");
                        canPlay = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "???¶À???????????????????????????");
                }

                this.repaint();
            }
        }
        // ??? ?????? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 70
                && e.getY() <= 100) {
            int result = JOptionPane.showConfirmDialog(this, "??????????????");
            if (result == 0) {
                // ?????????????
                // ??????????????????1)?????????,allChess??????????????0??
                // 2)?????????????????
                // 3)????????????????
                for (int i = 0; i < 19; i++) {
                    for (int j = 0; j < 19; j++) {
                        allChess[i][j] = 0;
                    }
                }
                // ??????? allChess=new int[19][19]
                message = "???????";

                isBlack = true;
                blackTime = maxTime;
                whiteTime = maxTime;
                if (maxTime > 0) {
                    blackMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                    whiteMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                    t.resume();
                } else {
                    blackMessage = "??????";
                    whiteMessage = "??????";
                }
                this.repaint();// ?????????????????÷Œ?????

            }

        }// ??? ??????? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 120
                && e.getY() <= 150) {
            String input = JOptionPane
                    .showInputDialog("?????????????????(??¶À??????)?????????0 ???????????????");
            try {
                maxTime = Integer.parseInt(input) * 60;
                if (maxTime < 0) {
                    JOptionPane.showMessageDialog(this, "?????????????????????????");
                }
                if (maxTime == 0) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "??????????????????????");
                    if (result == 0) {
                        // ?????????????
                        // ??????????????????1)?????????,allChess??????????????0??
                        // 2)?????????????????
                        // 3)????????????????
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j < 19; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        // ??????? allChess=new int[19][19]
                        message = "???????";

                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = "??????";
                        whiteMessage = "??????";
                        this.repaint();// ?????????????????÷Œ?????

                    }
                }
                if (maxTime > 0) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "??????????????????????");
                    if (result == 0) {
                        // ?????????????
                        // ??????????????????1)?????????,allChess??????????????0??
                        // 2)?????????????????
                        // 3)????????????????
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j < 19; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        // ??????? allChess=new int[19][19]
                        message = "???????";

                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = maxTime / 3600 + ":"
                                + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                                + (maxTime - maxTime / 60 * 60);
                        whiteMessage = maxTime / 3600 + ":"
                                + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                                + (maxTime - maxTime / 60 * 60);
                        t.resume();
                        this.repaint();// ?????????????????÷Œ?????

                    }
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "???????????");
            }
        }// ??? ?????? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 170
                && e.getY() <= 200) {
            JOptionPane.showMessageDialog(this,
                    "????????????????????????????????»…??????????????????????????");
        }// ??? ???? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 270
                && e.getY() <= 300) {
            int result = JOptionPane.showConfirmDialog(this, "??????????");
            if (result == 0) {
                if (isBlack) {
                    JOptionPane.showMessageDialog(this, "???????????????????!!!");
                } else {
                    JOptionPane.showMessageDialog(this, "???????????????????!!!");
                }
                canPlay = false;
            }
        }// ??? ???? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 320
                && e.getY() <= 350) {
            JOptionPane.showMessageDialog(this,
                    "?????ß≥???");
        }// ??? ??? ???
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 370
                && e.getY() <= 400) {
            JOptionPane.showMessageDialog(this, "??????");
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void run() {
        // ?ßÿ?????????????
        if (maxTime > 0) {
            while (true) {
                if (isBlack) {
                    blackTime--;
                    if (blackTime == 0) {
                        JOptionPane.showMessageDialog(this, "???????????????!");
                    }
                } else {
                    whiteTime--;
                    if (whiteTime == 0) {
                        JOptionPane.showMessageDialog(this, "???????????????!");
                    }
                }
                blackMessage = blackTime / 3600 + ":"
                        + (blackTime / 60 - blackTime / 3600 * 60) + ":"
                        + (blackTime - blackTime / 60 * 60);
                whiteMessage = whiteTime / 3600 + ":"
                        + (whiteTime / 60 - whiteTime / 3600 * 60) + ":"
                        + (whiteTime - whiteTime / 60 * 60);
                this.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        FiveChessFrame mf = new FiveChessFrame();
    }

}