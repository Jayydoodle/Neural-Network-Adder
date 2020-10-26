import java.util.*;

public class NeuralNetwork {
    private Set<Neuron> vertices;
    private Map<Edge<Neuron>, Double> weights;
    private Map<Edge<Neuron>, String> labels;
    private Map<Neuron, Set<Neuron>> neighbors;
    private Map<Neuron, Set<Neuron>> parents;
    private Map<Integer, ArrayList<Neuron>> verticesAtDepth;
    private int networkDepth;
    public List<Neuron> vertexArray;
    private double trainingRate = 0.2;

    /**
     * Create a new, empty NeuralNetwork.
     */
    public NeuralNetwork(){
        vertices = new HashSet<>();
        weights = new HashMap<>();
        labels = new HashMap<>();
        neighbors = new HashMap<>();
        parents = new HashMap<>();
        vertexArray = new ArrayList<>();
        verticesAtDepth = new HashMap<>();
        verticesAtDepth.put(0, new ArrayList<>());
        networkDepth = 0;
    }
    
    public void train(int[] data, boolean multilayer){

        // all neurons can be referenced by their tree depth
        for(int i = 0; i <= networkDepth; i++){

            // iterate through all neurons at the current depth
            for(Neuron n : verticesAtDepth.get(i)){

                // if we've reached the output nodes, do threshold activation
                if(i != 0){ 

                    if(multilayer == true) { n.logit(); }
                    else { n.thresholdActivation(); }
                }
                else{

                    Set<Neuron> childNodes = neighbors.get(n);

                    for(Neuron child : childNodes){ child.addToSum(n.getOutput(), getWeight(n, child)); }
                }
            }
        }
        if(multilayer){
            updateWeightsDerivative(data);
        }
        else
            updateWeights(data);
    }

    private void updateWeights(int[] goals){
            
        int goalIndex = 0;

        // working in reverse now
        for(Neuron child : verticesAtDepth.get(networkDepth)){

            // get the error between the goal and the neuron (post activation)
            double error = goals[goalIndex] - child.getOutput();

            Set<Neuron> parentNodes = parents.get(child);

            for(Neuron parent : parentNodes){

                // set the new weights based on the original input?  or the summed input?
                double newWeight = getWeight(parent, child) + (error * parent.getOutput());
                Edge<Neuron> edge = new Edge<Neuron>(parent, child);
                weights.replace(edge, newWeight);
            }
            goalIndex++; 
            child.setInput(0); // reset input to 0
        }
    }

    private void updateWeightsDerivative(int[] goals){
            
        int goalIndex = 0;

        // working in reverse now
        for(Neuron child : verticesAtDepth.get(networkDepth)){

            // get the error between the goal and the neuron (post activation)
            double error = child.getOutput() - goals[goalIndex];
           // System.out.println("error: "+error);

            /*if(error < 0.1 && error > -0.1){
                goalIndex++;
                continue;
            }*/

            Set<Neuron> parentNodes = parents.get(child);

            for(Neuron parent : parentNodes){

                double derivative = child.getOutput() * (1 - child.getOutput());
                double adjustment = error * derivative;
                
               // System.out.println("this is adjustment: "+adjustment);
                // set the new weights based on the original input?  or the summed input?
                double newWeight = getWeight(parent, child) + (adjustment * (parent.getOutput()));
                Edge<Neuron> edge = new Edge<Neuron>(parent, child);
                weights.replace(edge, newWeight);
            }
            goalIndex++; 
            child.setInput(0); // reset input to 0
        }
    }

     /**
     * Adds the vertex if it is new (otherwise does nothing). Note that this
     * vertex will not be connected to anything.
     * 
     * @param vertex The vertex to be added.
     */
    public void addVertex(Neuron vertex) {
        if(!neighbors.containsKey(vertex)) {
            neighbors.put(vertex, new HashSet<>());
            vertexArray.add(vertex);
        }

        if(!verticesAtDepth.containsKey(vertex.getDepth())){
            verticesAtDepth.put(vertex.getDepth(), new ArrayList<>());
            networkDepth++;
        }

        if(!vertices.contains(vertex)){
            
            vertices.add(vertex);
            verticesAtDepth.get(vertex.getDepth()).add(vertex);
        }
            
    }

    /**
     * Adds a new directed edge to the NeuralNetwork between the two vertices with
     * the given weight and label.
     * 
     * @param origin The origin vertex.
     * @param destination The destination vertex.
     * @param weight The weight for this edge.
     * @param label The desired label for this edge.
     */
    public void addDirectedEdge(Neuron origin, Neuron destination,
                                double weight, String label) {

        destination.setDepth(origin.getDepth() + 1);
       // destination                     
        // Keep track of both vertices, in case they're new
        addVertex(origin);
        addVertex(destination);
        // Actually build the neighbor connection
        neighbors.get(origin).add(destination);

        if(!parents.containsKey(destination)){
            parents.put(destination, new HashSet<>());
        }
        parents.get(destination).add(origin);
        // Keep track of edge attributes
        Edge<Neuron> edge = new Edge<Neuron>(origin, destination);
        weights.put(edge, weight);
        labels.put(edge, label);
    }

    /**
     * Provides the entire set of vertices for the NeuralNetwork.
     * 
     * @return All the vertices in the NeuralNetwork.
     */
    public Set<Neuron> getVertices() {
        return vertices;
    }

    /**
     * Provides all vertices that are connected to the given vertex.
     * 
     * @param origin The vertex to find the neighbors of.
     * @return Any vertices that are connected to the given vertex.
     */
    public Set<Neuron> getNeighbors(Neuron origin) {
        return neighbors.get(origin);
    }

    /**
     * Retrieve the weight of the given Edge in the NeuralNetwork.
     * 
     * @param edge The edge to get the weight of.
     * @return The weight of the edge.
     */
    public double getWeight(Edge<Neuron> edge) {
        return weights.get(edge);
    }
    
    /**
     * Retrieve the weight of the edge associated with the origin and destination
     * vertex. An error will occur if the edge does not exist.
     * 
     * @param origin The origin vertex
     * @param destination The destination vertex
     * @return The weight of the vertices' edge.
     */
    public double getWeight(Neuron origin, Neuron destination) {
        Edge<Neuron> e = new Edge<Neuron>(origin, destination);
        return getWeight(e);
    }

    public ArrayList<Neuron> getVerticesAtDepth(int depth){

        return verticesAtDepth.get(depth);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Neuron v: vertices) {
            sb.append("["+v.toString()+"]");
            List<String> neighborStrings = new ArrayList<String>();
            List<Double> neighborWeights = new ArrayList<Double>();
            for (Neuron n: neighbors.get(v)) {
                neighborStrings.add(n.toString());
                neighborWeights.add(getWeight(v,n));
            }
            sb.append(" -> (");
            for(int i = 0; i < neighborStrings.size(); i++) {
            	
            	sb.append(neighborStrings.get(i).toString() + " weight: " + neighborWeights.get(i));
            	if((i+1) != neighborStrings.size())
            	sb.append(", ");
            	
            }
            sb.append(")");
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String printVertices() {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < vertexArray.size(); i++) {
    		
    		sb.append(vertexArray.get(i).toString());
    		sb.append("\n");
    		
    	}
    	return sb.toString();
    }

}
