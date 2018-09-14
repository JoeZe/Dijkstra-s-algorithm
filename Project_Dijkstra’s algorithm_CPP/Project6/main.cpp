#include<iostream>
#include<fstream>
#include<string>
using namespace std;
class DijktraSSS {
public:
	int numNodes, sourceNode, minNode, currentNode, newCost;
	int** costMatrix;
	int* fatherAry;
	int* toDoAry;
	int* bestCostAry;

	DijktraSSS(int node, int source) {
		numNodes=node;
		sourceNode = source;
		costMatrix = new int*[numNodes+1];
		toDoAry=new int[numNodes];
		fatherAry = new int[numNodes+1];
		bestCostAry = new int[numNodes+1];
		for (int i = 1; i < numNodes+1; i++)
			costMatrix[i] = new int[numNodes+1];

		for (int i = 1; i<numNodes+1; i++) {
			costMatrix[i][i] = 0;
			bestCostAry[i] = 99999;
			toDoAry[i] = 1;
			for (int j = 1; j<numNodes+1; j++) {
				if (i != j)
					costMatrix[i][j] = 99999;
			}
		}
	}

	void loadCostMatrix(int i, int j, int cost) {
		costMatrix[i][j] = cost;
	}

	void setBestCostAry(int sourceNode) {
		for(int i=1; i<=numNodes; i++)
			bestCostAry[i] = costMatrix[sourceNode][i];
	}

	void setFatherAry(int sourceNode) {
		for(int i=1; i<=numNodes; i++)
			fatherAry[i] =sourceNode ;
	}

	void setToDoAry(int sourceNode) {
		for (int i = 1; i <= numNodes; i++) {
			if (sourceNode == i)
				toDoAry[sourceNode] = 0;
			else
				toDoAry[i] = 1;
		}
	}

	int findMinNode() {
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

	int computeCost(int minNode, int currentNode) {
		return bestCostAry[minNode] + costMatrix[minNode] [currentNode];		 
	}

	void markMinNode(int minNode) {
		toDoAry[minNode] = 0;
	}

	void changeFather(int node, int minNode) {
		fatherAry[node] = minNode;
	}

	void changeCost(int node, int newCost) {
		bestCostAry[node] = newCost;
	}

	void debugPrint(ofstream &outPut2) {
		outPut2 << "sourceNode " << sourceNode << endl;
		outPut2 << "toDoAry ";
		for (int i = 1; i <= numNodes; i++)
			outPut2 << toDoAry[i] << " ";
		outPut2 << endl;

		outPut2 << "bestCostAry ";
		for (int i = 1; i <= numNodes; i++)
			outPut2 << bestCostAry[i] << " ";
		outPut2 << endl;

		outPut2 << "fatherAry ";
		for (int i = 1; i <= numNodes; i++)
			outPut2 << fatherAry[i] << " ";
		outPut2 << endl<<endl;
	}

	void printShorestPaths(int sourceN, ofstream &outPut1) {
		currentNode = sourceN;
		int totalCost=bestCostAry[sourceN];
		outPut1 << "The path from " << sourceNode << " to " << currentNode << ": ";
		outPut1 << currentNode;
		while(fatherAry[currentNode]!=sourceNode){
			currentNode = fatherAry[currentNode];
			outPut1 << " <- " << currentNode;
		}
		if (fatherAry[currentNode] == sourceNode)
			outPut1 << " <- " << sourceNode << ": cost= " << totalCost << endl;
	}

	bool checkToDoAry() {
		for (int i = 1; i <= numNodes; i++) {
			if (toDoAry[i] == 1) {
				return true;
				break;
			}
		}
		return false;
	}
};

int main(int argc, char** argv) {
	ifstream inFile;
	ofstream outPut1, outPut2;
	inFile.open(argv[1]);
	outPut1.open(argv[2]);
	outPut2.open(argv[3]);
	int numNode, sourceNode, currentNode, i, j, cost;
	inFile >> numNode;
	inFile >> sourceNode;
	DijktraSSS path(numNode, sourceNode);
	while (inFile >>i ) {
		inFile >> j;
		inFile >> cost;
		path.loadCostMatrix(i, j, cost);
	}

	path.setBestCostAry(sourceNode);
	path.setFatherAry(sourceNode);
	path.setToDoAry(sourceNode);
	
	while (path.checkToDoAry()) {
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
	outPut1 << "Source node = " << sourceNode << endl;
	outPut1 << "The shortest paths from the source node " << sourceNode << " are:\n";
	while (currentNode <= numNode) {
		path.printShorestPaths(currentNode, outPut1);
		currentNode++;
	}
	inFile.close();
	outPut1.close();
	outPut2.close();
}

