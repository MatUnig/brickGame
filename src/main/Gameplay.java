package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score;
    private int totalBricks;
    private Timer time;
    private int level=1;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private double ballXdir = -1;
    private double ballYdir = -3;

    private MapGenerator mapGen;

    public Gameplay() {
        Random r = new Random();
        int a = r.nextInt(8)+1;
        int b = r.nextInt(6)+1;
        mapGen = new MapGenerator(a, b);
        totalBricks=a*b;
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
        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: "+score, 540, 30);
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        //ball
        g.setColor(Color.yellow);
        g.fillRect(ballPosX, ballPosY, 20, 20);
        if (ballPosY > 570){
            play=false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over! Score: "+ score, 190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press enter to restart", 200,350);
        }
        if (totalBricks <=0){
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You won level: "+ level, 190,300);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press enter to play next", 200,350);
        }

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
                        score += 10;
                        System.out.println(totalBricks);

                        if (ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width){
                            //Changes direction
                            ballXdir = -ballXdir;
                        } else{
                            ballYdir = - ballYdir;
                        }
                    }
                }
            }
        }
        if (play==true){
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            //Changes direction
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                Random r = new Random();
                int a = r.nextInt(8)+1;
                int b = r.nextInt(6)+1;
                play = true;
                ballPosX=120;
                ballPosY=350;
                ballXdir=-1;
                ballYdir=-2;
                score=0;
                playerX = 310;
                totalBricks=a*b;
                mapGen =  new MapGenerator(a,b);
                repaint();
            }
            if (totalBricks<=0){
                Random r = new Random();
                int a = r.nextInt(8)+1;
                int b = r.nextInt(6)+1;
                ballPosX=120;
                ballPosY=350;
                ballXdir=-1-(level*0.1);
                ballYdir=-2-(level*0.05);
                playerX = 310;
                totalBricks=a*b;
                level++;
                mapGen =  new MapGenerator(a,b);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }


}
