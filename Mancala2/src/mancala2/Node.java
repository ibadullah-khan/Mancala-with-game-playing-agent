/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala2;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class Node {
    String name;        //To identify root
    Game game=null;     //To save game state
    double eval;        //Evaluation value
    double alpha;
    double beta;
    
    int depth;
    boolean max;
    Node parent;
    List<Node> children;
    
    //Default Constructor
    public Node(){
        this.alpha=Double.NEGATIVE_INFINITY;
        this.beta=Double.POSITIVE_INFINITY;
        this.children=new LinkedList<Node>();
    }
    
    public Node(String name, Game game, double eval, double alpha, double beta, int depth, boolean max, Node parent){
        this.name=name;
        this.game=new Game(game);
        this.eval=eval;
        this.alpha=alpha;
        this.beta=beta;
        this.depth=depth;
        this.max=max;
        this.parent=parent;
        this.children=new LinkedList<Node>();
    }
    
    public Node(Node x){
        name=x.name;
        game=new Game(x.game);
        eval=x.eval;
        alpha=x.alpha;
        beta=x.beta;
        depth=x.depth;
        max=x.max;
        parent=x.parent;
        children=new LinkedList<Node>();
        ListIterator<Node> listIterator=x.children.listIterator();
        while(listIterator.hasNext()){
            children.add(listIterator.next());
        }
    }
}
