import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;

public class Graph {

    private static String [][] graph;
    private static int mapSize = -1;

    public static int readGraph (String filename) {

        BufferedReader csvReader;
        String line;
        int nLines=0;

        try {
            csvReader = new BufferedReader(new FileReader(filename));
            while (csvReader.readLine() != null) nLines++;
            csvReader.close(); }
        catch (java.io.FileNotFoundException fnfe) {
            System.out.println("Map file could not be found");
            return -1; }
        catch (java.io.IOException ioe) {
            System.out.println("Map file could not be opened");
            return -1; }

        mapSize = nLines;
        graph = new String [nLines][];

        nLines = 0;
        try {
            csvReader = new BufferedReader(new FileReader(filename));
            while (nLines<mapSize)  graph[nLines++] = csvReader.readLine().split(",");
            csvReader.close(); }
        catch (java.io.FileNotFoundException fnfe) { return -1; }
        catch (java.io.IOException ioe) { return -1; }
        return nLines;

    }

    public static void printMap(){
        for (int i = 0; i<mapSize; i++){
            for (int j = 0; j< mapSize; j++){
                System.out.print(" "+graph[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static int getMapSize() {
        return mapSize;
    }

    public static String [][] getMap() {
        return graph;
    }

    public static void clear() {
        mapSize=-1;
    }

    public static Boolean empty() {
        return mapSize==-1;
    }

    public static int getValue (int x, int y) {
        return Integer.parseInt(graph[x][y]);
    }

    public static float getSolutionCost (List<Movement> solution, int x1, int y1){
        float value = 0;
        value += Double.parseDouble(graph[x1][y1]);
        for (Movement m : solution){
            switch (m){
                case UP: x1--; break;
                case DOWN: x1++; break;
                case LEFT: y1--; break;
                case RIGHT: y1++; break;
            }
            value += Double.parseDouble(graph[x1][y1]);
        }
        return value;
    }

    public static boolean isInside (Node n){
        return (n.getX()<mapSize
                && n.getY()<mapSize
                && n.getX()>=0
                && n.getY()>=0
                && Integer.parseInt(graph[n.getX()][n.getY()])!=0);
    }

    public static void printResult(List<Movement> solution, int x1, int y1){
        String [][] new_graph = new String[mapSize][mapSize];
        for (int i = 0; i<mapSize; i++){
            for (int j = 0; j< mapSize; j++){
                new_graph[i][j] = graph[i][j];
            }
        }
        new_graph[x1][y1] = "*";
        for (Movement m : solution){
            switch (m){
                case UP: x1--; break;
                case DOWN: x1++; break;
                case RIGHT: y1++; break;
                case LEFT: y1--; break;
            }
            new_graph[x1][y1] = "*";
        }
        for (int i = 0; i<mapSize; i++){
            for (int j = 0; j< mapSize; j++){
                System.out.print(" "+graph[i][j]);
            }
            System.out.print("\t");
            for (int j = 0; j< mapSize; j++){
                System.out.print(" "+new_graph[i][j]);
            }
            System.out.print("\n");
        }
    }
}
