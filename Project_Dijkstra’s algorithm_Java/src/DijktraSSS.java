import java.io.FileWriter;
import java.io.IOException;

public class DijktraSSS {
   int numNodes, sourceNode, minNode, currentNode, newCost;
   int[][] costMatrix;
   int[] fatherAry; 
   int[] toDoAry;
   int[] bestCostAry;
  
   public DijktraSSS(int numN, int sourceNode2){    
      numNodes=numN;
      sourceNode=sourceNode2;
      costMatrix=new int[numNodes+1][numNodes+1];
      fatherAry=new int[numNodes+1]; 
      toDoAry=new int[numNodes+1];
      bestCostAry=new int[numNodes+1];      
      for(int i=0; i<=numNodes; i++){
         toDoAry[i]=1;
         bestCostAry[i]=99999;
         for(int j=0; j<=numNodes; j++){
               costMatrix[i][j]=99999;
         }
         costMatrix[i][i]=0;
      }     
   }
   
   public void loadCostMatrix(int i, int j, int cost){
      costMatrix[i][j]=cost;   
   }
   
   public void setBestCostAry(int sourceNode){
      for(int i=1; i<=numNodes; i++)
         bestCostAry[i]=costMatrix[sourceNode][i];    
   }
   
   public void setFatherAry(int sourceNode){
      for(int i=1; i<=numNodes; i++)
         fatherAry[i]=sourceNode;     
   }
   
   public void setToDoAry(int sourceNode){
      for (int i = 1; i <= numNodes; i++) {
         if (sourceNode == i)
             toDoAry[sourceNode] = 0;
         else
             toDoAry[i] = 1;
     }     
   }
   
   public int findMinNode(){
      minNode = 1;
      int minCost = 99999;
      for (int i = 1; i <=numNodes; i++) {
          if (toDoAry[i] == 1) {
              if (bestCostAry[i] < minCost && bestCostAry[i] != 0) {
                  minNode =i;
                  minCost = bestCostAry[i];
              }   
          }
      }
      return minNode;
   }
   
   public int computeCost(int minNode, int currentNode){
      return bestCostAry[minNode]+ costMatrix[minNode][currentNode];
   }
   
   public void markMinNode(int minNode){
      toDoAry[minNode]=0;
   }
   
   public void changeFather(int node, int minNode){
      fatherAry[node]=minNode;
   }
   
   public void changeCost(int node, int newCost){
      bestCostAry[node]=newCost;
   }
   
   public void debugPrint(FileWriter outPut2) throws IOException{
      outPut2.write("sourceNode "+sourceNode+"\r\n");
      outPut2.write("toDoAry ");
      for (int i = 1; i <= numNodes; i++)
         outPut2.write(toDoAry[i]+ " ");
      outPut2.write("\r\n");

      outPut2.write("bestCostAry ");
      for (int i = 1; i <= numNodes; i++)
         outPut2.write(bestCostAry[i]+ " ");
      outPut2.write("\r\n");

      outPut2.write("fatherAry ");
      for (int i = 1; i <= numNodes; i++)
         outPut2.write(fatherAry[i]+ " ");
      outPut2.write("\r\n");

   }
   
   public void printShorestPaths(int sourceN, FileWriter outPut1) throws IOException{
      currentNode=sourceN;
      int totalCost=bestCostAry[sourceN];
      outPut1.write("The path form " + sourceNode + " to "+ currentNode+ ": ");
      String s="";
      s+=sourceN;
      while(fatherAry[currentNode]!=sourceNode){
         currentNode= fatherAry[currentNode];
         s+=" <- "+ currentNode;
      }
      if(fatherAry[currentNode]==sourceNode)
         s+=" <- "+ sourceNode + ": costs= "+ totalCost+"\r\n";
      outPut1.write(s);
   }
      
   public boolean checkToDoAry(){
      for (int i = 1; i <= numNodes; i++) 
         if (toDoAry[i] == 1) 
             return true;
     return false;
   
   }  
}
