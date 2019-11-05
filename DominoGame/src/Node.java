import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private int tRight, tLeft;
    private Token lastToken;
    private HashSet<Token> myTokens;
    private HashSet<Token> boardTokens;
    private List<Node> successors;
    Iterator<Node> successorIterator;

    public Node(int tRight, int tLeft, HashSet<Token> myTokens, HashSet<Token> boardTokens, Token lt) {
        this.tRight = tRight;
        this.tLeft = tLeft;
        this.myTokens = myTokens;
        this.successors = new LinkedList<>();
        this.lastToken = lt;
        this.boardTokens = boardTokens;
        // Successor calculation.
        // Possible new structures.
        //System.out.println("Original:");
        //Interface.printTokenSet(myTokens);
    }

    public int gettRight() {
        return tRight;
    }

    public void settRight(int tRight) {
        this.tRight = tRight;
    }

    public int gettLeft() {
        return tLeft;
    }

    public void settLeft(int tLeft) {
        this.tLeft = tLeft;
    }

    public HashSet<Token> getMyTokens() {
        return myTokens;
    }

    public void setMyTokens(HashSet<Token> myTokens) {
        this.myTokens = myTokens;
    }

    public HashSet<Token> getBoardTokens() {
        return boardTokens;
    }

    public void setBoardTokens(HashSet<Token> boardTokens) {
        this.boardTokens = boardTokens;
    }

    public Node nextSuccessor (){
        if (successorIterator==null) {
            for (Token t: myTokens) {
                HashSet<Token> updatedMyTokens = new HashSet<>();
                for (Token t1: myTokens) updatedMyTokens.add(t1);
                //System.out.println("updated");
                //Interface.printTokenSet(updatedMyTokens);
                updatedMyTokens.remove(t);
                HashSet<Token> updatedBoardTokens = new HashSet<>();
                for (Token t1: boardTokens) updatedBoardTokens.add(t1);
                updatedBoardTokens.add(t);
                if (t.getNleft()==tLeft) successors.add(new Node(tRight, t.getNright(), updatedMyTokens, updatedBoardTokens, t));
                if (t.getNright()==tLeft) successors.add(new Node(tRight, t.getNleft(), updatedMyTokens, updatedBoardTokens, t));
                if (t.getNleft()==tRight) successors.add(new Node(t.getNright(), tLeft, updatedMyTokens, updatedBoardTokens, t));
                if (t.getNright()==tRight) successors.add(new Node(t.getNleft(), tLeft, updatedMyTokens, updatedBoardTokens, t));
            }
            successorIterator = successors.iterator();
        }
        if (!successorIterator.hasNext()) return null;
        else return successorIterator.next();
    }

    // Test if neither this player or the other have successors.
    public boolean isFinal() {
        for (Token t: myTokens) {
            HashSet<Token> updatedMyTokens = new HashSet<>();
            for (Token t1: myTokens) updatedMyTokens.add(t1);
            //System.out.println("updated");
            //Interface.printTokenSet(updatedMyTokens);
            updatedMyTokens.remove(t);
            HashSet<Token> updatedBoardTokens = new HashSet<>();
            for (Token t1: boardTokens) updatedBoardTokens.add(t1);
            updatedBoardTokens.add(t);
            if (t.getNleft()==tLeft) successors.add(new Node(tRight, t.getNright(), updatedMyTokens, updatedBoardTokens, t));
            if (t.getNright()==tLeft) successors.add(new Node(tRight, t.getNleft(), updatedMyTokens, updatedBoardTokens, t));
            if (t.getNleft()==tRight) successors.add(new Node(t.getNright(), tLeft, updatedMyTokens, updatedBoardTokens, t));
            if (t.getNright()==tRight) successors.add(new Node(t.getNleft(), tLeft, updatedMyTokens, updatedBoardTokens, t));
        }
        successorIterator = successors.iterator();
        if (successors.isEmpty()){
            // Test opponent's movements (it has successors?).
            for (Token t: DominoGame.totalTokens){
                if (!myTokens.contains(t) && (!boardTokens.contains(t)) && (((t.getNleft()==tLeft))
                            || ((t.getNleft()==tRight)
                            || (t.getNright()==tRight)
                            || (t.getNright()==tLeft)))) return false;
            }
            return true;
        }
        else return false;
    }

    // Get winner based on points.
    public boolean amIWinner () {
        if (myTokens.isEmpty()) return true;
        float opponentPoints=0;
        for (Token t: DominoGame.totalTokens){
            if (!myTokens.contains(t) && !boardTokens.contains(t)) opponentPoints = opponentPoints + t.getNleft() + t.getNright();
        }
        return (myTokens.stream().map((x) -> x.getNright() + x.getNleft()).reduce(0, Integer::sum))<opponentPoints;
    }

    public Token getLastToken() {
        return lastToken;
    }

    public void setLastToken(Token lastToken) {
        this.lastToken = lastToken;
    }
}
