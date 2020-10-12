package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph{
    int nodeNum;
    int edgesNum;
    Queue<Edges> edges;
    int miniSpining = 0;
    Graph miniTree;
    public Graph(int nNum, int eNum, Queue<Edges> queue){
        nodeNum = nNum;
        edgesNum = eNum;
        edges = new PriorityQueue<>(queue);
    }

    public void addEdge(int start, int end, int weight){
        Edges edge = new Edges(start, end, weight);
        this.edges.offer(edge);
        edgesNum++;
        if(start > this.nodeNum || end > this.nodeNum){
            nodeNum++;
        }
    }

    public static Graph parseEdges(String fileName){
        int nNum = 0;
        int eNum = 0;
        Queue<Edges> queue = new LinkedList<>();
        try {
            int row = 0;
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null){
                String[] strs = str.split(" ");
                if(row == 0){
                    nNum = Integer.parseInt(strs[0]);
                    eNum = Integer.parseInt(strs[1]);
                    row++;
                }else{
                    int start = Integer.parseInt(strs[0]);
                    int end = Integer.parseInt(strs[1]);
                    int len = Integer.parseInt(strs[2]);
                    Edges edge = new Edges(start, end, len);
                    queue.offer(edge);
                }
            }
        }catch (IOException e){
            System.out.println("Read Error!");
        }
        return new Graph(nNum, eNum, queue);
    }

    public static int computeMST(Graph G){
        int nodeNum = G.nodeNum;
        Queue<Edges> edges = new PriorityQueue<>(G.edges);
        int result = 0;
        int addedEdges = 0;
        int[] nodes = new int[nodeNum];
        Queue<Edges> miniEdges = new PriorityQueue<>();
        for(int i = 0; i < nodeNum; i++){
            nodes[i] = i;
        }
//        find-union
        while(addedEdges < nodeNum - 1){
            Edges edge = edges.poll();
//            have circle or not
            int start = edge.start;
            int end = edge.end;
            if(find(start, nodes) != find(end, nodes)){
                miniEdges.offer(edge);
                union(start, end, nodes);
                result += edge.len;
                addedEdges++;
            }
        }
        G.miniSpining = result;
        G.miniTree = new Graph(nodeNum, nodeNum - 1, miniEdges);
        return result;
    }

    public static int recomputeMST(int u, int v, int weight, Graph G){
            G.miniTree.addEdge(u, v, weight);
            int res =  computeMST(G.miniTree);
            G.miniTree = G.miniTree.miniTree;
            return res;
    }

    public static int find(int x, int[] array){
        if(array[x] == x){
            return x;
        }
        return array[x] = find(array[x], array);
    }

    public static void union(int x, int y, int[] array){
        int rootX = find(x, array);
        int rootY = find(y, array);
        if(rootX != rootY){
            array[rootX] = rootY;
        }
    }
}
