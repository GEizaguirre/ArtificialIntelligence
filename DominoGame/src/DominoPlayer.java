import java.util.*;

enum ALGORITHM { MINIMAX, ALPHABETA, NONE};

public class DominoPlayer {
    private HashSet<Token> myTokens;
    private String name;
    private HEURISTICS h;

    // Constructor for cpu.
    public DominoPlayer (String name, HashSet<Token> tokens, HEURISTICS hs){
        myTokens = tokens;
        this.name=name;
        h = hs;
    }

    // Constructor for human player.
    public DominoPlayer (String name, HashSet<Token> tokens) {
        myTokens = tokens;
        this.name = name;
    }

    public AlgorithmResult minimaxStrategy (Node node, int level){
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) {
                return new AlgorithmResult(null, Integer.MAX_VALUE); }
            // Loose game.
            else {
                return new AlgorithmResult(null, Integer.MIN_VALUE);
            }
        }
        else if (level == Heuristic.getMaxLevel(h, "minimax")) {
            return new AlgorithmResult(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            float returnValue;

            Node next=null;
            Node returnNode=null;
            AlgorithmResult res;
            // Max-type level.
            if (level%2==0) returnValue= Integer.MIN_VALUE;
                // Min-type level.
            else returnValue = Integer.MAX_VALUE;
            while ((next=node.nextSuccessor())!=null) {
                // Evaluate other player's turn.
                res = minimaxStrategy(next, level+1);
                if (((level%2==0) && (res.getResult()>=returnValue))) {
                        returnValue = res.getResult();
                        returnNode = next;
                }
                else if ((res.getResult()<=returnValue)) {
                    returnValue = res.getResult();
                    returnNode = next;
                }
                // If could not get next movement, jump turn.
            }
            if (returnNode == null){
                returnNode = node;
            }
            return new AlgorithmResult(returnNode, returnValue);
        }
    }

    public AlgorithmResult abStrategy (Node node, int level, float alphav, float betav){
        float alpha = alphav;
        float beta = betav;
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) {
                return new AlgorithmResult(null, Integer.MAX_VALUE); }
            // Loose game.
            else {
                return new AlgorithmResult(null, Integer.MIN_VALUE);
            }
        }
        else if (level == Heuristic.getMaxLevel(h, "alphabeta")) {
            return new AlgorithmResult(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            float returnValue;

            Node next=null;
            Node returnNode=null;
            AlgorithmResult res;
            while (((next=node.nextSuccessor())!=null) && (alpha<beta)) {
                // Evaluate other player's turn.
                res = abStrategy(next, level+1, alpha, beta);
                if (((level%2==0) && (res.getResult()>=alpha))) {
                    alpha = res.getResult();
                    returnNode = next;
                }
                else if ((res.getResult()<=beta)) {
                    beta = res.getResult();
                    returnNode = next;
                }
                // If could not get next movement, jump turn.
            }
            if (returnNode == null){
                returnNode = node;
            }
            // Max-type level.
            if (level%2==0) returnValue= alpha;
            // Min-type level.
            else returnValue = beta;
            return new AlgorithmResult(returnNode, returnValue);
        }
    }

    public HashSet<Token> getMyTokens() {
        return myTokens;
    }

    public void setMyTokens(HashSet<Token> myTokens) {
        this.myTokens = myTokens;
    }

    public boolean hasToken (Integer n1, Integer n2){
        short nleft, nright;
        if (n1<n2) return myTokens.contains(new Token (n1, n2));
        else return myTokens.contains(new Token(n2, n1));
    }

    public boolean removeToken (Integer n1, Integer n2) {
        short nleft, nright;
        if (n1<n2)
            return myTokens.remove(new Token(n1, n2));
        else
            return myTokens.remove(new Token(n2, n1));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public AlgorithmResult playTurn(Node node, ALGORITHM alg){
        switch (alg){
            case MINIMAX:
                return minimaxStrategy(node, 0);
            case ALPHABETA:
                return abStrategy(node, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            default:
                return minimaxStrategy(node, 0);
        }
    }

    public static String printAlgorithm (ALGORITHM alg){
        switch(alg){
            case MINIMAX:
                return "minimax";
            case ALPHABETA:
                return "alphabeta";
            default:
                return "NONE";
        }
    }
}
