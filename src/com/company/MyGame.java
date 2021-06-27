package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {
    // declaring variable
    JLabel heading , clockLabel;

    // declaring JPanel , it is like container in html
    JPanel mainPanel ;

    // declaring a font class
    Font font = new Font("", Font.BOLD,25);

    // declaring button class
    JButton []btns = new JButton[9];  // creating buttons array the size of these array is 9
    Boolean gameOVer = false ;

    int gameChances[] = {2,2,2,2,2,2,2,2,2}; // we set all index position value is 2 , 2 is represent the neither press 1 and nor 2
    int activePlayer = 0 ;  // we are set the default value of current player is 0 , 0 it means when user press on any button it show 0

    // getting of array of array , this array are the chances to win user row by row  or row by colomn
    int wps[][] = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
    int winner = 2 ; // set the winner value 2 by default , because at starting of game no anyone win
    int failure = 2 ;
    MyGame(){
        System.out.println("Hello from MyGame constructor");
        super.setTitle("Tic Tac Toe");
        super.setSize(550,550);
        super.setLocation(400,60);
        ImageIcon game_icon = new ImageIcon("src/images/game_icon.png");
        super.setIconImage(game_icon.getImage());

        createGUI();

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }
    public void createGUI() {
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#2196f3"));

        heading = new JLabel("Tic Tac Toe");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(font);
        heading.setForeground(Color.white);
        heading.setBackground(Color.blue);

        this.add(heading, BorderLayout.NORTH);

        clockLabel = new JLabel("Clock");
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setFont(font);
        clockLabel.setForeground(Color.white);
        clockLabel.setBackground(Color.blue);
        this.add(clockLabel, BorderLayout.SOUTH);   // this code set the clock in bottom of main table

        // creating a updated clock by using Thread
        Thread thread = new Thread(){
          public void run(){
              try {
                  while(true){
                      String dataTime = new Date().toLocaleString();
                      clockLabel.setText(dataTime);
                      Thread.sleep(1000);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
        }; thread.start();

        // creating a button
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
        mainPanel.setBackground(Color.blue);

        for(int i=1 ; i<=9 ; i++){
            JButton btn = new JButton();
//            btn.setIcon(new ImageIcon("src/images/zero.png"));
            btn.setFont(font);
            mainPanel.add(btn);   // add all btn in mainPanel
            btns[i-1] = btn ;   // this code push index value of btn into 0 index, by default btn starting at 1 index position
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {  // e are the event listener
        JButton currrentBtn =(JButton)e.getSource() ;
        String btnName = currrentBtn.getName();
        int name = Integer.parseInt(btnName);
//        System.out.println("in the form of string :"+btnName);
//        System.out.println("in the form of int :"+name);

        if (gameOVer){
            JOptionPane.showMessageDialog(this, "Game already OVer");
            return ;
        }

        // checking what button user press and set the button depends upon the user what they press
        if (gameChances[name] == 2){  // equal 2 it represent nothing value to press of any button
            if(activePlayer == 0){
                currrentBtn.setIcon(new ImageIcon("src/images/zero.png"));
                gameChances[name] = activePlayer ;
                activePlayer = 1 ;
            }
            else{
                currrentBtn.setIcon(new ImageIcon("src/images/one.png"));
                gameChances[name] = activePlayer ;
                activePlayer = 0 ;
            }

            // find the winner
            for(int []temp:wps) {
                if ((gameChances[temp[0]] == gameChances[temp[1]] && gameChances[temp[1]] == gameChances[temp[2]]) && gameChances[temp[2]] != 2) {
                    winner = gameChances[temp[0]];
                    gameOVer = true ;
                    JOptionPane.showMessageDialog(this ,"Player "+ winner +" has won the Game");
                    int i = JOptionPane.showConfirmDialog(this,"Do want to Play more ");
//                    System.out.println(i);
                    if (i == 0){
                        this.setVisible(false);
                        new MyGame();  // creating new instance of MyGame constructor and lounch new window screen
                    }
                    else if(i == 1){
                        System.exit(404);
                    }
                    break ;  //because after winner of player close or stop the loop for checking match the item
                }
            }

            // find the failure
            int counter = 0;
            for (int x : gameChances){
                if(x==2){
                    counter++;
                    break;
                }
            }
            if (counter == 0 && gameOVer == false){
                JOptionPane.showMessageDialog(null, "Its, draw");
                int i = JOptionPane.showConfirmDialog(this, "play more");
                if (i == 0){
                    this.setVisible(false);
                    new MyGame();
                }
                else if(i==1){
                    System.exit(404);
                }
            }

        }
        else{
            JOptionPane.showMessageDialog(this,"Position already occupied");
        }

    }
}
