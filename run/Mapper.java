package run;
import model.*;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.util.*;
/**
 * Created by wyy on 5/18/17.
 */
public class Mapper {


    public int V;  //v = c+t
    public int C;  // number of NIC
    public int T;  // number of Pipelines
    public int N = 4;  //number of NUMA sockets;
    public int[] pcpu = null; // phycical cores of each socket
    public int[] numaplace = null; // socket id of all elements
    public int[][] numaDistance; // N x N matrix to record the distance among NUMA nodes

    public  int[] alias;  // if < 0 -> nic if > 0 pipeline
    public FowardGraph graph;

    public  void readPipeline(){
        String filename = "/Users/wyy/code/PipelineMapper/config.txt";  // read the HW information and service
        // requirement
        File file = new File(filename);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));

            T = Integer.parseInt(reader.readLine());
            C = Integer.parseInt(reader.readLine());
            V = T+C;
            numaplace = new int[V];
            alias = new int[V]; // 0~V representing nic or pipeline
            for(int i=0;i<C;i++)
                alias[i] = (-1)*(i+1);
            for(int j=C;j<V;j++)
                alias[j] = (j-C+1);
            graph = new FowardGraph(V,T);
            String temp;
            while((temp=reader.readLine())!=null){
                String[] edge = temp.split(" ");
               // System.out.println(edge[0]+" "+edge[1]);
                graph.addEdge(Integer.parseInt(edge[0]),Integer.parseInt(edge[1]));
            }

            reader.close();


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readHardware(){
        String filename = "/Users/wyy/code/PipelineMapper/hardware.txt";  // numa core and numadistance
        File file = new File(filename);

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            N = Integer.parseInt(reader.readLine());
            pcpu = new int[N];
            numaDistance = new int[N][N];
            for(int i=0;i<N;i++)
                pcpu[i] = Integer.parseInt(reader.readLine());
            int nic = Integer.parseInt(reader.readLine());
            for(int j=0;j<nic;j++)
                numaplace[j] = Integer.parseInt(reader.readLine());
            for(int k=0;k<N;k++){
                String[] dis = reader.readLine().split(" ");
                for(int i=0;i<N;i++)
                    numaDistance[k][i] = Integer.parseInt(dis[i]);
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void printHW(){
        System.out.println("total "+N+" sockets");
        for(int i=0;i<N;i++)
            System.out.println("socket"+i+": "+pcpu[i]+" cores");
        System.out.println("total "+C+" NICs");
        for(int j=0;j<C;j++)
            System.out.println("NIC"+(j+1)+" on socket "+numaplace[j]);
        System.out.println("Node Distances:");
        System.out.print("node  ");
        for(int i=0;i<N;i++) System.out.print(i+"   ");
        System.out.println();
        for(int j=0;j<N;j++){
            System.out.print("  "+j+":  ");
            for(int k=0;k<N;k++)
                System.out.print(numaDistance[j][k]+"  ");
            System.out.println();
        }
    }

    public static void main(String[] args){
        Mapper mapper = new Mapper();
        mapper.readPipeline();
        mapper.readHardware();
        //mapper.graph.printInformation(mapper.alias);
        //mapper.graph.printInformation();
        mapper.printHW();
        FowardGraph g = new FowardGraph(5,4); //v = 5 = nic + pipelines
        g.addEdge(0,1);
        g.addEdge(1,2);
        //g.printInformation();
    }
}
