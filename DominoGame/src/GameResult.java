public class GameResult {

    private String winnerName;
    private HEURISTICS winnerHeur;
    private double totalExecutionTime;
    private double avgDecisionTime;
    private String algorithm;
    private double worstTime;

    public GameResult(String winnerName, HEURISTICS winnerHeur, double totalExecutionTime, double avgDecisionTime, ALGORITHM alg, double worstTime ) {
        this.winnerName = winnerName;
        this.winnerHeur = winnerHeur;
        this.totalExecutionTime = totalExecutionTime;
        this.avgDecisionTime = avgDecisionTime;
        this.worstTime = worstTime;
        algorithm = DominoPlayer.printAlgorithm(alg);
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public HEURISTICS getWinnerHeur() {
        return winnerHeur;
    }

    public void setWinnerHeur(HEURISTICS winnerHeur) {
        this.winnerHeur = winnerHeur;
    }

    public double getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public void setTotalExecutionTime(double totalExecutionTime) {
        this.totalExecutionTime = totalExecutionTime;
    }

    public double getAvgDecisionTime() {
        return avgDecisionTime;
    }

    public void setAvgDecisionTime(double avgDecisionTime) {
        this.avgDecisionTime = avgDecisionTime;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public double getWorstTime() {
        return worstTime;
    }

    public void setWorstTime(double worstTime) {
        this.worstTime = worstTime;
    }

    public String toString() {
        return " Winner: "+winnerName+" with heuristic "+Heuristic.printHeuristic(winnerHeur)+".\n" +
                "   Total execution time: "+totalExecutionTime+"\n" +
                "   Average decision time: "+avgDecisionTime+"\n"+
                "   Using game algorithm: "+algorithm;
    }
}
