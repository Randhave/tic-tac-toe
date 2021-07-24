package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {
     
    JLabel heading , clockLabel;

    
    JPanel mainPanel ;

    
    Font font = new Font("", Font.BOLD,25);

    
    JButton []btns = new JButton[9];   
    Boolean gameOVer = false ;

    int gameChances[] = {2,2,2,2,2,2,2,2,2};  
    int activePlayer = 0 ;   

    
    int wps[][] = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
    int winner = 2 ; 
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
        this.add(clockLabel, BorderLayout.SOUTH);   

        //  updated clock by using Thread
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
        
            btn.setFont(font);
            mainPanel.add(btn);    
            btns[i-1] = btn ;    
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {  
        JButton currrentBtn =(JButton)e.getSource() ;
        String btnName = currrentBtn.getName();
        int name = Integer.parseInt(btnName);
 

        if (gameOVer){
            JOptionPane.showMessageDialog(this, "Game already OVer");
            return ;
        }

       
        if (gameChances[name] == 2){  
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
 
                    if (i == 0){
                        this.setVisible(false);
                        new MyGame();  
                    }
                    else if(i == 1){
                        System.exit(404);
                    }
                    break ;  
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
