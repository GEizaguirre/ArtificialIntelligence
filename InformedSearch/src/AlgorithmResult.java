import java.util.HashMap;
import java.util.List;

public class AlgorithmResult {

    private List<Movement> solution;
    private HashMap<Node, Boolean> treatList;
    private double time;
    private double cost;

    public AlgorithmResult (List <Movement> sol, HashMap <Node, Boolean> tl, double time, double cost) {
        this.solution = sol;
        this.treatList = tl;
        this.time = time;
        this.cost = cost;
    }

    public List<Movement> getSolution() {
        return solution;
    }

    public void setSolution(List<Movement> solution) {
        this.solution = solution;
    }

    public HashMap<Node, Boolean> getTreatList() {
        return treatList;
    }

    public void setTreatList(HashMap<Node, Boolean> treatList) {
        this.treatList = treatList;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }
}