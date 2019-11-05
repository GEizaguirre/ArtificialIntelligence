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
        //System.out.println(level);
        // End.
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) return new Result(null, Integer.MAX_VALUE);
            // Loose game.
            else return new Result(null, Integer.MIN_VALUE);
        }
        else if (level == maxLevel) {
            //System.out.println("Heuristic: "+ Heuristic.apply(h, node));
            return new Result(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            //System.out.println("Analyze successors");
            float returnValue;
            // Max-type level.
            if (level%2==0) returnValue= Integer.MIN_VALUE;
            // Min-type level.
            else returnValue = Integer.MAX_VALUE;
            Node next=null;
            Node returnNode=null;
            Result res;
            while ((next=node.nextSuccessor())!=null) {
                //Interface.printTokenSet(next.getMyTokens());
                //System.out.println("Calling minimax");
                res = minimaxStrategy(next, level+1);
                if ((level%2==0) && (res.getResult()>returnValue)){
                        returnValue = res.getResult();
                        returnNode = next;
                }
                else if (res.getResult()<returnValue){
                    returnValue = res.getResult();
                    returnNode = next;
                }
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
        if (n1<n2) {
            nleft=n1;
            nright=n2;
        } else {
            nleft = n2;
            nright = n1;
        }
        for (Token t: myTokens){
            if (t.getNleft()==nleft && t.getNright()==nright) return true;
        }
        return false;
    }

    public boolean removeToken (short n1, short n2) {
        short nleft, nright;
        if (n1<n2) {
            nright=n1;
            nleft=n2;
        } else {
            nleft = n1;
            nright = n2;
        }
        for (Token t: myTokens){
            if (t.getNleft()==nleft && t.getNright()==nright) {
                myTokens.remove(t);
                return true;
            }
        }
        return false;
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
