import java.util.*;

public class DominoPlayer {

    final static int NUM_TOKENS = 28;
    private HashSet<Token> myTokens;
    private String name;
    private int maxLevel;
    private HEURISTICS h;

    public DominoPlayer (String name, HashSet<Token> tokens, HEURISTICS hs){
        myTokens = tokens;
        this.name=name;
        this.maxLevel = DominoGame.MAX_LEVELS;
        h = hs;
    }

    public Result minimaxStrategy (Node node, int level){
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) {
                return new Result(null, Integer.MAX_VALUE); }
            // Loose game.
            else {
                return new Result(null, Integer.MIN_VALUE);
            }
        }
        else if (level == maxLevel) {
            return new Result(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            float returnValue;
            // Max-type level.
            if (level%2==0) returnValue= Integer.MIN_VALUE;
            // Min-type level.
            else returnValue = Integer.MAX_VALUE;
            Node next=null;
            Node returnNode=null;
            Result res;
            while ((next=node.nextSuccessor())!=null) {
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
            return new Result(returnNode, returnValue);
        }
    }

    public HashSet<Token> getMyTokens() {
        return myTokens;
    }

    public void setMyTokens(HashSet<Token> myTokens) {
        this.myTokens = myTokens;
    }

    public boolean hasToken (short n1, short n2){
        short nleft, nright;
        if (n1<n2) return myTokens.contains(new Token (n1, n2));
        else return myTokens.contains(new Token(n2, n1));
    }

    public boolean removeToken (short n1, short n2) {
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

    /*public Result userDisplay(){
        System.out.println("User turn:");
        System.out.println("Current board:");
        System.out.println("<"+DominoGame.nleft+">-...-<"+DominoGame.nright+">");
        Interface.printTokenSet(DominoGame.boardTokens);
        System.out.println("Your tokens:");
        List<Token> guiList = new LinkedList();
        for (Token t: myTokens) guiList.add(t);
        Interface.printTokenListNumbered(guiList);
        System.out.println("Select token number... (-1 to ignore your turn)");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int option = reader.nextInt();
        if (option != -1) {
            removeToken(guiList.get(option).getNleft(), guiList.get(option).getNright());
        }
    }*/
}
