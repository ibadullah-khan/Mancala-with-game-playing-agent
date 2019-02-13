/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala2;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author user
 */
public class Mancala2 {

    public static Game game;
    
    public static void main(String[] args) 
    {
        
        System.out.println("============ WELCOME TO MANCALA =============\n" +"\n" +"IMPORTANT\n" +"-you are player and your cups are below ones,\n" +"They continue from right to left means that game\n" +
        "is running in clockwise rotation.\n" +"\n" +"- You should use array indexes increasing from \n" +
        "left to right and 0 to 5 like this\n" +"\n" +"\n" +"    x  x  x  x  x  x\n" +" M                    x\n" +"    0  1  2  3  4  5\n" +
        "\n" +"\n" +"-Your index is your turn");
        
        System.out.println("\n\n\n");
        int player = 1;
        int boardSize=6;
        int[] board2=new int[boardSize];
        int[] board1=new int[boardSize];
        for(int i=0;i<boardSize;i++)
        {
            board2[i]=4;
            board1[i]=4;
        }
        int mancala2=0;
        int mancala1=0;
        
        Game g=new Game(player, board2, board1, mancala2, mancala1);
        Functions util=new Functions();
        game = g;
        int flag = 0;
        
        //Printing initial board
        
        int [] b1;
        b1 = g.getBoardPlayer1();
        for(int i = 0; i < b1.length; i++)
        {
            System.out.print(" "+b1[i]+" ");
        }
        System.out.println();
        System.out.println(""+g.getMancalaPlayer2()+"                "+g.getMancalaPlayer1());
        int [] b2;
        b2 = g.getBoardPlayer2();
        for(int i = 0; i < b2.length; i++)
        {
            System.out.print(" "+b2[i]+" ");
        }
        System.out.println();
        

        
        Random randomNum = new Random();
        boolean baari = randomNum.nextBoolean();
        while(true)
        {
            g = game;
            if(baari == true)
            {
                while(true)
                {
                    AlphaBeta ab=new AlphaBeta(2);
                    try{
                        Game g1=ab.alphabeta(g);
                        System.out.println("\n\nCPU did : ");
                    
                        int [] b3;
                        b3 = g1.getBoardPlayer1();
                        System.out.println("");
                        System.out.println("");
                        for(int i = 0; i < b3.length; i++)
                        {
                            System.out.print(" "+b3[i]+" ");
                        }
                        System.out.println("");
                        System.out.println(""+g1.getMancalaPlayer2()+"                "+g1.getMancalaPlayer1());

                        int [] b4;
                        b4 = g1.getBoardPlayer2();
                        for(int i = 0; i < b4.length; i++)
                        {
                            System.out.print(" "+b4[i]+" ");
                        }
                        System.out.println();

                        if(util.GameOver(g1))
                        {
                            flag = 1;
                            CrownWinner(g1);
                            break;
                        }
                        if(g1.getGetAnotherTurn() == true)
                        {
                            break;

                        }
                        else
                        {
                            game = g1;
                            baari = false;
                            break;
                        }
                }
                catch (Exception ex)
                {
                    System.out.println("Exception in AlphaBeta main : "+ex.getMessage());
                }
                
            }
                if(flag == 1)
                {
                    break;
                }
            }
            
            else
            {
                while (true)
            {
                
                Game g2 = player2turn(game);
 
                if(util.GameOver(g2))
                {
                    CrownWinner(g2);
                    flag =1;
                    break;
                }
                if(g2.getGetAnotherTurn() == true)
                {
                    game=g2;
                    
                }
                else
                {
                    game = g2;
                    baari=true;
                    break;
                }
            }
            }
            
            
           if(flag == 1)
           {
               break;
           }
        }
        
    }
    
    public static void CrownWinner(Game g)
    {
        int m1 = g.getMancalaPlayer1();
        int m2 = g.getMancalaPlayer2();

        if(m1 > m2)
        {
            System.out.println("Agent won");
        }
        else if (m2 > m1)
        {
            System.out.println("Player won");
        }
        else
        {
            System.out.println("Draw");
        }
    }
    
    public static Game player2turn(Game g1)
    {
        Functions util=new Functions();
        Scanner s = new Scanner(System.in);
        System.out.println("\n\nEnter your turn player : ");
        int turn2 = s.nextInt();
        int player = 2;
        
        
        int [] check;
        check = g1.getBoardPlayer2();
        while(turn2 > 5 || turn2 < 0 || check[turn2]== 0)
        {
            System.out.println("\nInvalid move : Please Enter again");
            turn2 = s.nextInt();
        }
        
        
        Game g2 = util.moveStones(g1, player, turn2);
        int [] b1;
        b1 = g2.getBoardPlayer1();
        System.out.println("");
        System.out.println("");
        for(int i = 0; i < b1.length; i++)
        {
            System.out.print(" "+b1[i]+" ");
        }
        System.out.println("");
        System.out.println(""+g2.getMancalaPlayer2()+"                "+g2.getMancalaPlayer1());
        int [] b2;
        b2 = g2.getBoardPlayer2();
        for(int i = 0; i < b2.length; i++)
        {
            System.out.print(" "+b2[i]+" ");
        }
        System.out.println();
        
        return g2;
    }
    /*
    public static Game player1turn(Game g)
    {
        Functions util=new Functions();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter your turn player 1: ");
        int turn = s.nextInt();
        int player = 1;
        
        int [] check;
        check = g.getBoardPlayer1();
        while(check[turn]== 0 || turn > 5 || turn < 0)
        {
            System.out.println("Invalid move : Please Enter again");
            turn = s.nextInt();
        }
        Game g1 = util.moveStones(g, player, turn);
        int [] b3;
        b3 = g1.getBoardPlayer1();
        System.out.println("");
        System.out.println("");
        for(int i = 0; i < b3.length; i++)
        {
            System.out.print(" "+b3[i]+" ");
        }
        System.out.println(""+g1.getMancalaPlayer2()+"                "+g1.getMancalaPlayer1());
        int [] b4;
        b4 = g1.getBoardPlayer2();
        for(int i = 0; i < b4.length; i++)
        {
            System.out.print(" "+b4[i]+" ");
        }
        System.out.println();
        
        return g1;
    }
    */
}
