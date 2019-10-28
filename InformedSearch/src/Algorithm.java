import java.util.*;

enum Movement {UP, DOWN, RIGHT, LEFT};

public class Algorithm {

    private static OrderedNodeList pendentList;
    private static HashMap <Node, Boolean>  treatList;
    private static List <Movement> solution;
    private static float operatorsCost;

    public static void clear() {
        pendentList = new OrderedNodeList();
        treatList = new HashMap<>();
        solution = new LinkedList<>();
        operatorsCost = 0;
    }

    public static AlgorithmResult BFSearch ( int x1, int y1, int x2, int y2, HEURISTIC h){

        // Initial checks.
        if ( x1<0 || y1<0 ||
                x1>Graph.getMapSize() || y1>Graph.getMapSize()
                || Graph.getValue(x1, y1)==0)
            return null;

       // Prepare the algorithm.
        clear();
        long startTime = System.nanoTime();

        Boolean end = false;
        Node current, next;

        pendentList.add(new Node(x1, y1, 0));
        while (!end && pendentList.size()!=0) {
            current = pendentList.getRemove(0);
            if (current.getX()==x2 && current.getY()==y2) {

                solution = current.clonePath();
                end = true;
            }
            else  {
                treatList.put(current, true);
                for ( Movement m : Movement.values()){


                    next = operate(m, current);

                    if (next!=null) {

                        if (!pendentList.contains(next) && !treatList.containsKey(next)) {
                            solution = current.clonePath();
                            solution.add(m);
                            next.setPath(solution);
                            next.setValue(next.applyHeuristic(h, next, x2, y2));

                            pendentList.add(next);
                        }
                    }
                }
            }

        }

        long endTime = System.nanoTime();
        if (end) {
            AlgorithmResult result = new AlgorithmResult(solution, treatList, endTime - startTime, operatorsCost);
            return result;
        }
        else {
            return null;
        }

    }

    public static AlgorithmResult ASSearch ( int x1, int y1, int x2, int y2, HEURISTIC h){

        // Initial checks.
        if ( x1<0 || y1<0 ||
                x1>Graph.getMapSize() || y1>Graph.getMapSize()
                || Graph.getValue(x1, y1)==0)
            return null;

        // Prepare the algorithm.
        clear();
        long startTime = System.nanoTime();

        Boolean end = false;
        Node current, next;

        pendentList.add(new Node(x1, y1, 0));
        while (!end && pendentList.size()!=0) {
            current = pendentList.getRemove(0);
            if (current.getX()==x2 && current.getY()==y2) {

                solution = current.clonePath();
                end = true;
            }
            else  {
                treatList.put(current, true);
                for ( Movement m : Movement.values()){


                    next = operate(m, current);

                    if (next!=null && !treatList.containsKey(next)) {
                            solution = current.clonePath();
                            solution.add(m);
                            next.setPath(solution);
                            next.setValue(next.applyHeuristic(h, next, x2, y2) + Graph.getSolutionCost(solution, x1, y1) );

                            pendentList.add(next);
                    }
                }
            }

        }

        long endTime = System.nanoTime();
        if (end) {
            AlgorithmResult result = new AlgorithmResult(solution, treatList, endTime - startTime, operatorsCost);
            return result;
        }
        else {
            return null;
        }

    }

    public static List<Movement> ASSearch ( int x1, int y1, int x2, int y2){

        clear();
        Boolean end = false;
        Node current, next;

        /**Algorithm**/

        return solution;

    }

    public static Node operate (Movement m, Node n) {
        int nx = n.getX();
        int ny = n.getY();
        switch (m) {
            case UP:
                nx--;
                break;
            case DOWN:
                nx++;
                break;
            case LEFT:
                ny--;
                break;
            case RIGHT:
                ny++;
                break;
        }
        Node nn = new Node (nx, ny);
        if (Graph.isInside(nn)) return nn;
        else return null;

    }
}
