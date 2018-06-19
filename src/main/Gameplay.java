package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score;
    private int totalBricks = 21;
    private Timer time;
    private int delay = 8;

    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator mapGen;

    public Gameplay() {
        mapGen = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        //drawing mapGen
        mapGen.draw((Graphics2D)g);
        //border
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        //ball
        g.setColor(Color.yellow);
        g.fillRect(ballPosX, ballPosY, 20, 20);
        g.dispose();
    }
        public void moveRight(){
        play=true;
        playerX+=15;
        }
        public void moveLeft(){
        play=true;
        playerX-=15;
        }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        time.start();
    repaint();
        if (new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX, 550, 100,8))){
            ballYdir =- ballYdir;
        }
        
        for (int i=0;i<mapGen.map.length;i++){
            for(int j=0; j<mapGen.map[0].length;j++){
                if(mapGen.map[i][j] >0){
                    int brickX = j * mapGen.brickWidth +80;
                    int brickY = i* mapGen.brickHight +50;
                    int brickWidth = mapGen.brickWidth;
                    int brickHight = mapGen.brickHight;

                    Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHight);
                    Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                    Rectangle brickRect = rect;

                    if (ballRect.intersects(brickRect)){
                        mapGen.setBrickValue(0,i,j);
                        totalBricks--;

                    }
                }
            }
        }
        if (play==true){
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if (ballPosX <0 ){
                ballXdir = - ballXdir;
            }
            if (ballPosY<0 ){
                ballYdir = - ballYdir;
            }
            if (ballPosX  >670 ){
                ballXdir = - ballXdir;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }


}
