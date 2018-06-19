package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frameObj = new JFrame();
        Gameplay gp = new Gameplay();

        frameObj.setBounds(10,10,700,600);
        frameObj.setTitle("Breakout Ball");
        frameObj.setVisible(true);
        frameObj.setResizable(false);
        frameObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameObj.add(gp);



    }
}
