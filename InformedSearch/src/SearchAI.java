import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchAI {

    private static int x1 = 0;
    private static int y1 = 0;
    private static int x2 = 9;
    private static int y2 = 9;
    private static HEURISTIC[] selectecHeuristics = new HEURISTIC[] {HEURISTIC.VDIF, HEURISTIC.DVAL, HEURISTIC.RAS};

    public static String heuristicToString (HEURISTIC h){
        String value="";
        switch (h){
            case RAS:
                value = "rasterize"; break;
            case DIS:
                value = "distance"; break;
            case DVAL:
                value = "distance x value"; break;
            case DVALD:
                value = "distance x value difference"; break;
            case RVAL:
                value = "rasterized x value"; break;
            case RVALD:
                value = "rasterized x value difference"; break;
            case YDIS:
                value = "y coordinate distance"; break;
            case VDIF:
                value = "state value difference"; break;
            case VAL:
                value = "state value"; break;
            case YDDIF:
                value = "state value difference * y coordinate distance"; break;
            case LDIS:
                value = "l-shaped distance"; break;
        }
        return value;
    }

    public static void printResults (AlgorithmResult res, HEURISTIC h){
        if (res!=null) {
            System.out.println(" > " + heuristicToString(h));
            System.out.println("Execution time: " + res.getTime() + " ns");
            System.out.println("Treated list size: " + res.getTreatList().size() + " nodes.");
            System.out.println("Path length: " + res.getSolution().size() + " moves.");
            System.out.println("Path cost: " + Graph.getSolutionCost(res.getSolution(), x1, y1) + " time units.\n");
            Graph.printResult(res.getSolution(), x1, y1);
            System.out.println("\n--------------------------------------------");
        }
        else {
            System.out.println("I'm sorry, I could not find a solution :(");
        }
    }

    public static void main(String[] args) {
        String filename1 = "map_original.csv";
        String filename2 = "map_custom.csv";

        Graph.readGraph(filename1);
        System.out.println("-----MAP: "+filename1);
        System.out.println("-----BEST FIRST-----");
        for (HEURISTIC h : selectecHeuristics) {
            printResults(Algorithm.BFSearch(x1,y1,x2,y2,h), h);

        }
        System.out.println("-----A*------");
        for (HEURISTIC h : selectecHeuristics) {
            printResults(Algorithm.ASSearch(x1,y1,x2,y2,h), h);

        }
        Graph.readGraph(filename2);
        System.out.println("-----MAP: "+filename2);
        System.out.println("-----BEST FIRST-----");
        for (HEURISTIC h : selectecHeuristics) {
            printResults(Algorithm.BFSearch(x1,y1,x2,y2,h), h);

        }
        System.out.println("-----A*------");
        for (HEURISTIC h : selectecHeuristics) {
            printResults(Algorithm.ASSearch(x1,y1,x2,y2,h), h);

        }


    }

}
