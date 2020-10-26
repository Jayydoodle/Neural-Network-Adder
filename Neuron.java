
public class Neuron{

    private double input;
    private double output;
    private int depth;
    private String name;
    private double error;
    public double goal;

    public Neuron(String name){
        this.depth = 0;
        this.input = 0.0;
        this.output = 1.0;
        this.error = 0;
        this.name = name;
    }

    public void addToSum(double value, double weight){

        //System.out.println(name + " total: " + input + " input: " + value + " weight: " + weight);
        input += value * weight;
        //System.out.println(name + " total: " + input);
    }

    public void thresholdActivation(){

        if(input >= 0){
            output = 1.0;
        }
        else{
            output = 0.0;
        }
        System.out.println(name + " result:  " + output);
    }

    public void logit(){

        output = 1 / (1 + Math.exp(-input));

        System.out.println(name + " result:  " + output);
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString(){
        return name;
    }

    public double getError() {
        return error;
    }

    public void addError(double error) {
        this.error += error;
    }

    public void setError(double error) {
        this.error = error;
    }
}