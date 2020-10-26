import java.util.ArrayList;

/* To run, just compile and run.  The test output shown illustrates neuron 3 learning
the carry operation in a single layer network, and the inability of neuron 3 to learn
sum.  The final weights match the configuration outlined in the project specs (1, 2, -3)
*/

class Main{


    public static void main (String[] args){

        singleLayerNetwork();
       // doubleLayerNetwork();

    }
    
    public static void trainNetwork(NeuralNetwork neuralNetwork, ArrayList<int[][]> data){

        for(int i = 0; i < data.size(); i++){

            int[][] row = data.get(i);
            ArrayList<Neuron> inputs = neuralNetwork.getVerticesAtDepth(0);
            inputs.get(0).setOutput(row[0][0]);
            inputs.get(1).setOutput(row[0][1]);

            System.out.println("\ninput: " + " n1: " + row[0][0] + " n2: " + row[0][1]);
            System.out.println("goal: " + " n3: " + row[1][0] + " n4: " + row[1][1] + "\n");
            neuralNetwork.train(row[1], false);

            System.out.println(neuralNetwork.toString());
        }

    }
    
    public static void singleLayerNetwork(){
        NeuralNetwork neuralNetwork = new NeuralNetwork();

        Neuron n1 = new Neuron("n1");
        Neuron n2 = new Neuron("n2");
        Neuron n3 = new Neuron("n3");
        Neuron n4 = new Neuron("n4");
        Neuron dummy3 = new Neuron("dummy3");
        Neuron dummy4 = new Neuron("dummy4");

        int[][] dataSet1 = {{0,0},{0,0}};
        int[][] dataSet2 = {{0,1},{0,1}};
        int[][] dataSet3 = {{1,0},{0,1}};
        int[][] dataSet4 = {{1,1},{1,0}};

        ArrayList<int[][]> data = new ArrayList<int[][]>();

        data.add(dataSet1);
        data.add(dataSet2);
        data.add(dataSet3);
        data.add(dataSet4);
        
        neuralNetwork.addDirectedEdge(n1, n3, 1, "");
        neuralNetwork.addDirectedEdge(n1, n4, 1, "");
        neuralNetwork.addDirectedEdge(n2, n3, 1, "");
        neuralNetwork.addDirectedEdge(n2, n4, 1, "");
        neuralNetwork.addDirectedEdge(dummy3, n3, 1, "");
        neuralNetwork.addDirectedEdge(dummy4, n4, 1, "");

        for(int i = 0; i < 7; i++)
            trainNetwork(neuralNetwork, data);
    }
    
    public static void doubleLayerNetwork(){
        NeuralNetwork neuralNetwork = new NeuralNetwork();

        Neuron n1 = new Neuron("n1");
        Neuron n2 = new Neuron("n2");
        Neuron n3 = new Neuron("n3");
        Neuron n4 = new Neuron("n4");
        Neuron n5 = new Neuron("n5");
        Neuron n6 = new Neuron("n6");
        Neuron dummy3 = new Neuron("dummy3");
        Neuron dummy4 = new Neuron("dummy4");
        Neuron dummy5 = new Neuron("dummy5");
        Neuron dummy6 = new Neuron("dummy6");

        int[][] dataSet1 = {{0,0},{0,0}};
        int[][] dataSet2 = {{0,1},{0,1}};
        int[][] dataSet3 = {{1,0},{0,1}};
        int[][] dataSet4 = {{1,1},{1,0}};

        ArrayList<int[][]> data = new ArrayList<int[][]>();

        data.add(dataSet1);
        data.add(dataSet2);
        data.add(dataSet3);
        data.add(dataSet4);
        
        neuralNetwork.addDirectedEdge(n1, n3, 1, "");
        neuralNetwork.addDirectedEdge(n1, n4, 1, "");
        neuralNetwork.addDirectedEdge(n2, n3, 1, "");
        neuralNetwork.addDirectedEdge(n2, n4, 1, "");
        neuralNetwork.addDirectedEdge(n3, n5, 1, "");
        neuralNetwork.addDirectedEdge(n3, n5, 1, "");
        neuralNetwork.addDirectedEdge(n4, n6, 1, "");
        neuralNetwork.addDirectedEdge(n4, n6, 1, "");
        neuralNetwork.addDirectedEdge(dummy3, n3, 1, "");
        neuralNetwork.addDirectedEdge(dummy4, n4, 1, "");
        neuralNetwork.addDirectedEdge(dummy5, n5, 1, "");
        neuralNetwork.addDirectedEdge(dummy6, n6, 1, "");

        for(int i = 0; i < 10; i++)
            trainNetwork(neuralNetwork, data);
    }

}
