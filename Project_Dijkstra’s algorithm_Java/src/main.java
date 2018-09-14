import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
   public static void main(String[] args) throws IOException{    
      int numNode, sourceNode, currentNode, i, j, cost;
      try {
         Scanner inFile=new Scanner(new FileReader(args[0]));
         FileWriter outPut1=new FileWriter(args[1]);
         FileWriter outPut2=new FileWriter(args[2]);
         numNode=inFile.nextInt();
         sourceNode=inFile.nextInt();
         DijktraSSS path= new DijktraSSS(numNode,sourceNode);
         while(inFile.hasNext()){
            i=inFile.nextInt();
            j=inFile.nextInt();
            cost=inFile.nextInt();
            path.loadCostMatrix(i, j, cost);          
         }
         path.setBestCostAry(sourceNode);
         path.setFatherAry(sourceNode);
         path.setToDoAry(sourceNode);
         while(path.checkToDoAry()){
            path.minNode = path.findMinNode();
            path.markMinNode(path.minNode);
            path.debugPrint(outPut2);
            currentNode = 1;
            while (currentNode <= numNode) {
               if (path.toDoAry[currentNode] == 1) {
                  path.newCost=path.computeCost(path.minNode, currentNode);
                  if (path.newCost < path.bestCostAry[currentNode]) {
                     path.changeCost(currentNode, path.newCost);
                     path.changeFather(currentNode, path.minNode);
                     path.debugPrint(outPut2);
                 }
               }
               currentNode++;
            }
         }       
         currentNode = 1;
         outPut1.write("Source node = " + sourceNode+"\r\n");
         outPut1.write("The shortest paths from the source nodes " +sourceNode + " are:\r\n");
         while (currentNode <= numNode) {
             path.printShorestPaths(currentNode, outPut1);
             currentNode++;
         }
         inFile.close();
         outPut1.close();
         outPut2.close();        
         
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
