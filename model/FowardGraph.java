package model;

import java.util.*;

/**
 * Created by wyy on 5/17/17.
 */
public class FowardGraph {
    private int T;  //total number of functional blocks
    private int V;  // vertex of the graph including NICs
    private int E;  //edge number

    public List<List<Integer>> adj;  //

    public FowardGraph(int v, int t){
        V = v;
        T = t;
        E = 0;
        adj = new ArrayList<List<Integer>>(V);
        for(int i=0;i<v;i++){
            adj.add(i,new LinkedList<Integer>());
        }
    }

    public void addEdge(int v, int w){
       adj.get(v).add(w);
        E++;
    }
    public void printInformation(){
        System.out.println("functional blocks:  "+T);
        for(int i=0;i<V;i++){
           List<Integer> vlist = adj.get(i);
            System.out.print(i+"->");
            for(int n : vlist)
             System.out.print(n+" ");
            System.out.println();
        }
    }
    public void printInformation(int[] alias){
        System.out.println("functional blocks:  "+T);
        for(int i=0;i<V;i++){
            List<Integer> vlist = adj.get(i);
            System.out.print(alias[i]+"->");
            for(int n : vlist)
                System.out.print(alias[n]+" ");
            System.out.println();
        }
    }
}
