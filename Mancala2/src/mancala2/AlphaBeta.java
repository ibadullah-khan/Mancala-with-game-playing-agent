/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala2;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author user
 */
public class AlphaBeta {
    Functions util=new Functions();
    private final int depth;
    private static int count=0;
    
    public AlphaBeta(int depth){
        this.depth=depth;
    }
    
    
    public Game alphabeta(Game g) throws IOException{
        try{
            Node root=new Node("root", g, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, true, null);
            //System.out.println("hi1");
            getAllMoves(root);
            //System.out.println("hi2");
            Game g1 = util.nextState(root);
            //System.out.println("hi3");
            return g1;
        }
        catch(Exception ex){
            System.out.println("Exception in AlphaBeta : "+ex.getMessage());
            return null;
        }
        
    }
    
    
    public void getAllMoves(Node n) throws IOException{
        count=n.depth;
        //Leaf Node
        if(count>=depth && !n.game.getGetAnotherTurn()){
            n.eval=util.eval(n.game.getPlayer(), n.game.getMancalaPlayer1(), n.game.getMancalaPlayer2());
                        
            return;
        }
        
        boolean valid=false;
        if(count==depth && n.game.getGetAnotherTurn())
            valid=true;
        while((count<depth || valid) && !util.GameOver(n.game)){
            util.expansion(n);
            valid=false;
            ListIterator<Node> listIterator=n.children.listIterator();
            while(listIterator.hasNext()){
                Node temp=listIterator.next();
                temp.alpha=n.alpha;
                temp.beta=n.beta;
                getAllMoves(temp);
                if(n.max){
                    if(temp.eval>n.eval)
                        n.eval=temp.eval;
                    if(!prun(n)){
                        if(temp.eval>n.alpha)
                            n.alpha=temp.eval;
                    }
                }
                else{
                    if(temp.eval<n.eval)
                        n.eval=temp.eval; 
                    if(!prun(n)){
                        if(temp.eval<n.beta)
                            n.beta=temp.eval;
                    }
                }
                if(prun(n))
                    break;
            }
            count++;
        }   
    }
    
    public boolean prun(Node n){
        if(n.max){
            if(n.beta<=n.eval){ //Pruning
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(n.alpha>=n.eval){ //Pruning
                return true;
            }
            else{
                return false;
            }
        }
    }
}
