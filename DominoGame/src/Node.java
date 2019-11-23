import gnu.trove.THashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private int level = 0;
    private Integer tRight, tLeft;
    private Token lastToken;
    private THashSet<Token> myTokens;
    private THashSet<Token> boardTokens;
    private List<Node> successors;
    Iterator<Node> successorIterator;

    public Node(Integer tRight, Integer tLeft, THashSet<Token> myTokens, THashSet<Token> boardTokens, Token lt, int level) {
        this.tRight = tRight;
        this.tLeft = tLeft;
        this.myTokens = myTokens;
        this.successors = new LinkedList<>();
        this.lastToken = lt;
        this.boardTokens = boardTokens;
        this.level = level;
    }

    public Integer gettRight() {
        return tRight;
    }

    public void settRight(Integer tRight) {
        this.tRight = tRight;
    }

    public Integer gettLeft() {
        return tLeft;
    }

    public void settLeft(Integer tLeft) {
        this.tLeft = tLeft;
    }

    public THashSet<Token> getMyTokens() {
        return myTokens;
    }

    public void setMyTokens(THashSet<Token> myTokens) {
        this.myTokens = myTokens;
    }

    public THashSet<Token> getBoardTokens() {
        return boardTokens;
    }

    public void setBoardTokens(THashSet<Token> boardTokens) {
        this.boardTokens = boardTokens;
    }

    public boolean hasOpponentSuccesors() {
        for (Token t: DominoGame.totalTokens) {
            if (!boardTokens.contains(t) && !myTokens.contains(t)) {
                if ((t.getNleft() == tLeft) || (t.getNright() == tLeft) || (t.getNleft() == tRight) ||(t.getNright() == tRight))
                    return true;
            }
        }
        return false;
    }

    public boolean hasSuccesors() {
        for (Token t: myTokens) {
            if ((t.getNleft() == tLeft) || (t.getNright() == tLeft) || (t.getNleft() == tRight) ||(t.getNright() == tRight))
                    return true;
        }
        return false;
    }

    public Node nextSuccessor (){
        if (successorIterator==null) {
            // No tokens on board, any token is successor.
            if (tLeft==null){
                for (Token t: myTokens) {
                    THashSet<Token> updatedMyTokens = new THashSet<>();
                    for (Token t1: myTokens) updatedMyTokens.add(t1);
                    updatedMyTokens.remove(t);
                    THashSet<Token> updatedBoardTokens = new THashSet<>();
                    for (Token t1: boardTokens) updatedBoardTokens.add(t1);
                    updatedBoardTokens.add(t);
                    successors.add(new Node(t.getNleft(), t.getNright(), updatedMyTokens, updatedBoardTokens, t, level+1));
                }
            }
            else {
                if (level%2==0) {
                    for (Token t : myTokens) {
                        THashSet<Token> updatedMyTokens = new THashSet<>(myTokens.size());
                        for (Token t1 : myTokens) updatedMyTokens.add(t1);

                        updatedMyTokens.remove(t);
                        THashSet<Token> updatedBoardTokens = new THashSet<>(boardTokens.size()+1);
                        for (Token t1 : boardTokens) updatedBoardTokens.add(t1);
                        updatedBoardTokens.add(t);

                        // Equality manager for succesor number simplification.
                        boolean eq = ((t.getNleft() == t.getNright()) || (tLeft == tRight));
                        if (t.getNleft() == tLeft) {
                            successors.add(new Node(tRight, t.getNright(), updatedMyTokens, updatedBoardTokens, t, level+1));
                            if (eq) continue;
                        }
                        if (t.getNright() == tLeft) {
                            successors.add(new Node(tRight, t.getNleft(), updatedMyTokens, updatedBoardTokens, t, level+1));
                            if (eq) continue;
                        }
                        if (t.getNleft() == tRight) {
                            successors.add(new Node(t.getNright(), tLeft, updatedMyTokens, updatedBoardTokens, t, level+1));
                            if (eq) continue;
                        }
                        if (t.getNright() == tRight) {
                            successors.add(new Node(t.getNleft(), tLeft, updatedMyTokens, updatedBoardTokens, t, level+1));
                            if (eq) continue;
                        }
                    }
                } else{
                    for (Token t : DominoGame.totalTokens) {
                        if (!myTokens.contains(t) && !boardTokens.contains(t)){
                            THashSet<Token> updatedMyTokens = new THashSet<>(myTokens.size());
                            for (Token t1 : myTokens) updatedMyTokens.add(t1);
                            THashSet<Token> updatedBoardTokens = new THashSet<>(myTokens.size());
                            for (Token t1 : boardTokens) updatedBoardTokens.add(t1);
                            updatedBoardTokens.add(t);

                            boolean eq = ((t.getNleft() == t.getNright()) || (tLeft == tRight));
                            if (t.getNleft() == tLeft) {
                                successors.add(new Node(tRight, t.getNright(), updatedMyTokens, updatedBoardTokens, t, level+1));
                                if (eq) continue;
                            }
                            if (t.getNright() == tLeft) {
                                successors.add(new Node(tRight, t.getNleft(), updatedMyTokens, updatedBoardTokens, t, level+1));
                                if (eq) continue;
                            }
                            if (t.getNleft() == tRight) {
                                successors.add(new Node(t.getNright(), tLeft, updatedMyTokens, updatedBoardTokens, t, level+1));
                                if (eq) continue;
                            }
                            if (t.getNright() == tRight) {
                                successors.add(new Node(t.getNleft(), tLeft, updatedMyTokens, updatedBoardTokens, t, level+1));
                                if (eq) continue;
                            }
                        }
                    }
                }
            }
            successorIterator = successors.iterator();
        }
        if (!successorIterator.hasNext()) return null;
        else return successorIterator.next();
    }

    // Test if neither this player or the other have successors.
    public boolean isFinal() {
        if ((myTokens.isEmpty()) || ((boardTokens.size()+myTokens.size())==DominoGame.NUM_TOKENS) || (!boardTokens.isEmpty() && !hasSuccesors() && !hasOpponentSuccesors())) return true;
        return false;
    }

    // Get winner based on points.
    public boolean amIWinner () {
        if (myTokens.isEmpty()) return true;
        if ((boardTokens.size()+myTokens.size())==DominoGame.NUM_TOKENS) return false;
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

    // Changes tokens of the current player
    public Node turnTokens () {
        THashSet <Token> newTokens = new THashSet<>();
        for (Token t : DominoGame.totalTokens){
            if (!myTokens.contains(t) && !boardTokens.contains(t)) newTokens.add(t);
        }
        myTokens = newTokens;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
