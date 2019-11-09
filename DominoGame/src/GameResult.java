public class GameResult {

    private String winnerName;
    private HEURISTICS winnerHeur;
    private double totalExecutionTime;
    private double avgDecisionTime;
    private String algorithm;

    public GameResult(String winnerName, HEURISTICS winnerHeur, double totalExecutionTime, double avgDecisionTime, ALGORITHM alg) {
        this.winnerName = winnerName;
        this.winnerHeur = winnerHeur;
        this.totalExecutionTime = totalExecutionTime;
        this.avgDecisionTime = avgDecisionTime;
        switch(alg){
            case MINIMAX:
                algorithm="Minimax"; break;
            case ALPHABETA:
                algorithm="Alpha Beta Pruning"; break;
        }
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

    public String toString() {
        return " Winner: "+winnerName+" with heuristic "+Heuristic.printHeuristic(winnerHeur)+".\n" +
                "   Total execution time: "+totalExecutionTime+"\n" +
                "   Average decision time: "+avgDecisionTime+"\n"+
                "   Using game algorithm: "+algorithm;
    }
}
