/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala2;

import java.io.IOException;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class Functions {
    
    int[] localBoard1, localBoard2;
    int localMancala1, localMancala2;
    
    public void setLocals(Game x){
        this.localMancala1=x.getMancalaPlayer1();
        this.localMancala2=x.getMancalaPlayer2();
        localBoard1=new int[x.getBoardSize()];
        localBoard2=new int[x.getBoardSize()];
        System.arraycopy(x.getBoardPlayer1(), 0, localBoard1, 0, x.getBoardSize());
        System.arraycopy(x.getBoardPlayer2(), 0, localBoard2, 0, x.getBoardSize());
    }
    
    
    
    
    public Game moveStones(Game game, int player, int index){
        Game state=new Game(game);
        state.setGetAnotherTurn(false);
        setLocals(state);
        if(player==1){
            if(localBoard1[index]>0){
                int stones=localBoard1[index];
                localBoard1[index]=0;
                int j=index+1;
                boolean reverse=false;
                while(stones>0){
                    if(j==state.getBoardSize()){    //Check if player has played last cup
                        if(stones==1)
                            state.setGetAnotherTurn(true);
                        localMancala1++;
                        stones--;
                        reverse=true;
                        j=state.getBoardSize()-1;
                    }
                    else if(j<0){ 
                        j=0;
                        reverse=false;
                    }
                    else if(reverse){       // condition where we have to add in opponent's cup
                        localBoard2[j]++;
                        stones--;
                        j--;
                    }
                    else{
                        if(stones==1 && localBoard1[j]==0){ // Game condition where robbery happens
                            localMancala1+=localBoard2[j]+1;
                            localBoard2[j]=0;
                            stones--;
                        }
                        else{                       //Simple condition
                            localBoard1[j]++;
                            stones--;
                            j++;
                        }
                    }
                }
            }
        }
        else{
            if(localBoard2[index]>0){
                int stones=localBoard2[index];
                localBoard2[index]=0;
                int j=index-1;
                boolean reverse=true;
                while(stones>0){
                    if(j<0){
                        if(stones==1)
                            state.setGetAnotherTurn(true);
                        localMancala2++;
                        stones--;
                        reverse=false;
                        j=0;
                    }
                    else if(j==state.getBoardSize()){
                        j=state.getBoardSize()-1;
                        reverse=true;
                    }
                    else if(!reverse){
                        localBoard1[j]++;
                        stones--;
                        j++;
                    }
                    else{
                        if(stones==1 && localBoard2[j]==0){
                            localMancala2+=localBoard1[j]+1;
                            localBoard1[j]=0;
                            stones--;
                        }
                        else{
                            localBoard2[j]++;
                            stones--;
                            j--;
                        }
                    }
                }
            }
        }
        if(starvationPlayer1(localBoard1)){         //If no turn left for player 1 
            for(int i=0;i<localBoard2.length;i++){
                localMancala2+=localBoard2[i];      //Add the stones of player 2 to its mancala
                localBoard2[i]=0;
            }
            state.setGetAnotherTurn(false);
        }
        if(starvationPlayer2(localBoard2)){         //If no turn left for player 2
            for(int i=0;i<localBoard1.length;i++){
                localMancala1+=localBoard1[i];      //Add the stones of player 2 to its mancala
                localBoard1[i]=0;
            }
            state.setGetAnotherTurn(false);
        }
                
        state.setBoardPlayer1(localBoard1);
        state.setBoardPlayer2(localBoard2);
        state.setMancalaPlayer1(localMancala1);
        state.setMancalaPlayer2(localMancala2);
        return  state;
    }
    
    
    
    public boolean GameOver(Game x){
        int[] temp1=new int[x.getBoardSize()];
        int[] temp2=new int[x.getBoardSize()];
        System.arraycopy(x.getBoardPlayer1(), 0, temp1, 0, x.getBoardSize());
        System.arraycopy(x.getBoardPlayer2(), 0, temp2, 0, x.getBoardSize());
        
        boolean side1Empty=true;
        boolean side2Empty=true;
        for(int i=0;i<x.getBoardSize();i++){
            if(temp1[i]>0){
                side1Empty=false;
                break;
            }
        }
        for(int i=0;i<x.getBoardSize();i++){
            if(temp2[i]>0){
                side2Empty=false;
                break;
            }
        }
        return (side1Empty || side2Empty);
    }
    
    
    public int eval(int player, int mancala1, int mancala2){
        if(player==1){
            return mancala1-mancala2;
        }
        else{
            return mancala2-mancala1;
        }
    }
    
    
    //Get next best move from current game state which is represented in root Node.
    
    public Game nextState(Node root) throws IOException{
        ListIterator<Node> listIterator=root.children.listIterator();
        while(listIterator.hasNext()){
            Node temp=listIterator.next();
            if(temp.eval==root.eval){
                Game move=null;
                if(temp.game.getGetAnotherTurn()){
                    Node x=getNextState(temp);
                    move=new Game(x.game);
                }
                else{
                    move=new Game(temp.game);
                }
                //writeNextState(move);
                return move;
                   
            }
        }
        return null;
    }
    
    
    
    
    public Node getNextState(Node n){
        Node nextMove=null;
        ListIterator<Node> listIterator=n.children.listIterator();
        while(listIterator.hasNext()){
            Node temp=listIterator.next();
            if(temp.eval==n.eval){
                if(temp.game.getGetAnotherTurn()){
                    nextMove=getNextState(temp);
                }
                else{
                    nextMove=new Node(temp);
                    break;
                }
            }
        }
        return nextMove;
    }
    
    //Check if all the holes on player 1's side are empty 
    //which is one of the condition for end of game.
    
    
    public boolean starvationPlayer1(int[] boardPlayer1){
        for(int i=0;i<boardPlayer1.length;i++){
            if(boardPlayer1[i]>0)
                return false;
        }
        return true;
    }
    
    
    //Check if all the holes on player 2's side are empty 
    //which is one of the condition for end of game. 
    
    public boolean starvationPlayer2(int[] boardPlayer2){
        for(int i=0;i<boardPlayer2.length;i++){
            if(boardPlayer2[i]>0)
                return false;
        }
        return true;
    }
    
    
    //Expands the node to its children
    public void expansion(Node n){
        if(!(GameOver(n.game))){
            int currentPlayer;
            if(n.max)
                currentPlayer=n.game.getPlayer();
            else{
                if(n.game.getPlayer()==1)
                    currentPlayer=2;
                else
                    currentPlayer=1;
            }

            int[] checkValidMove=new int[n.game.getBoardSize()];
            if(currentPlayer==1){
                System.arraycopy(n.game.getBoardPlayer1(), 0, checkValidMove, 0, n.game.getBoardSize());
            }
            else{
                System.arraycopy(n.game.getBoardPlayer2(), 0, checkValidMove, 0, n.game.getBoardSize());
            }
            for(int i=0;i<n.game.getBoardSize();i++){
                if(checkValidMove[i]>0){
                    Game temp=new Game(moveStones(n.game, currentPlayer, i));
                    Node xn=new Node();
                    xn.parent=n;
                    xn.game=new Game(temp);
                    xn.name=getName(currentPlayer, i);
                    char startChar=n.name.charAt(0);
                    if (xn.name.charAt(0)==startChar){
                        xn.depth=n.depth;
                    }
                    else{
                        xn.depth=n.depth+1;
                    }
                    if(temp.getGetAnotherTurn()){
                        xn.max=n.max;
                    }
                    else{
                        xn.max=!n.max;
                    }
                    if(xn.max){
                        xn.eval=Double.NEGATIVE_INFINITY;
                    }
                    else{
                        xn.eval=Double.POSITIVE_INFINITY;
                    }
                    xn.alpha=n.alpha;
                    xn.beta=n.beta;
                    n.children.add(xn);
                }
            }
        }
    }
    
    public String getName(int player, int index){
        String name="";
        if(player==1){
            name="B".concat(Integer.toString(index+2));
        }
        else{
            name="A".concat(Integer.toString(index+2));
        }
        return name;
    }
    
}
