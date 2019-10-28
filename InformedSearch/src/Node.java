import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

public class Node {

    private int x,y;
    private float value;
    private List<Movement> solution;

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        solution = new LinkedList<>();

    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        solution = new LinkedList<>();
    }

    public float getValue ( ) { return value; }

    public int getX() { return x; }

    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node n = (Node) o;
        return getX() == n.getX() &&
                getY() == n.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void setValue (float value) {
        this.value = value;
    }

    public void setPath(List<Movement> l){
        for (Movement m : l) solution.add(m);
    }

    public List<Movement> clonePath() {
        List <Movement> nl = new LinkedList<>();
        for (Movement m : solution) nl.add(m);
        return nl;

    }

    public boolean addMov (Movement m){
        return solution.add(m);
    }

    public float applyHeuristic (HEURISTIC h, Node current, int xf, int yf){
        float value = -1;
        switch (h){
            /* Straight line distance from current point to the end */
            case DIS:
                value = Heuristics.distance(current.getX(), current.getY(), xf, yf);  break;
            /* Point number of rasterized line */
            case RAS:
                value = Heuristics.defineDrawLine(current.getX(), current.getY(), xf, yf); break;
            /* Multiply the value of the node per the rasterized point number to the end */
            case DVAL:
                value = Heuristics.valDis(current.getX(), current.getY(), xf, yf); break;
            /* Multiply the value of the node per the rasterized point number to the end */
            case RVAL:
                value = Heuristics.valRas(current.getX(), current.getY(), xf, yf); break;
            case YDIS:
                value = Heuristics.yDis(current.getY(), yf); break;
            case RVALD:
                value = Heuristics.valRasDif(current.getX(), current.getY(), xf, yf); break;
            case DVALD:
                value = Heuristics.valDisDif(current.getX(), current.getY(), xf, yf); break;
            case VDIF:
                value = Heuristics.vDif(current.getX(), current.getY(), xf, yf); break;
            case VAL:
                value = Heuristics.val(current.getX(), current.getY()); break;
            case YDDIF:
                value = Heuristics.yDisDif(current.getX(), current.getY(), xf, yf); break;
            case LDIS:
                value = Heuristics.lDis(current.getX(), current.getY(), xf, yf); break;
        }
        return value;
    }

    public int yDistance (Node current, int yf ){
        return (-abs(yf-current.getY()));
    }

    public String toString(){
        return "("+getX()+", "+getY()+"): "+getValue();
    }
}
