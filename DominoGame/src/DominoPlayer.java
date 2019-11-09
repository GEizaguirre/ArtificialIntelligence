import java.util.*;

enum ALGORITHM { MINIMAX, ALPHABETA};

public class DominoPlayer {
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

    public MinimaxResult minimaxStrategy (Node node, int level){
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) {
                //System.out.println("GOT WIN RESULT");
                return new MinimaxResult(null, Integer.MAX_VALUE); }
            // Loose game.
            else {
                //System.out.println("GOT LOOSE RESULT");
                return new MinimaxResult(null, Integer.MIN_VALUE);
            }
        }
        else if (level == maxLevel) {
            //System.out.println("Board <"+node.gettLeft()+">--<"+node.gettRight()+"> heuristic:"+Heuristic.apply(h, node));
            return new MinimaxResult(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            float returnValue;

            if (level!=0) node = node.turnTokens();

            Node next=null;
            Node returnNode=null;
            MinimaxResult res;
            // Max-type level.
            if (level%2==0) returnValue= Integer.MIN_VALUE;
                // Min-type level.
            else returnValue = Integer.MAX_VALUE;
            while ((next=node.nextSuccessor())!=null) {
                // Evaluate other player's turn.
                //System.out.println("Level "+level+" successor, set <"+next.getLastToken().getNleft()+">--<"+next.getLastToken().getNright()+">");
                //System.out.println("This player has tokens: ");
                //Interface.printTokenSet(next.getMyTokens());
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
            return new MinimaxResult(returnNode, returnValue);
        }
    }

    public MinimaxResult abStrategy (Node node, int level, float alphav, float betav){
        float alpha = alphav;
        float beta = betav;
        if (node.isFinal()) {
            // Win game.
            if (node.amIWinner()) {
                //System.out.println("GOT WIN RESULT");
                return new MinimaxResult(null, Integer.MAX_VALUE); }
            // Loose game.
            else {
                //System.out.println("GOT LOOSE RESULT");
                return new MinimaxResult(null, Integer.MIN_VALUE);
            }
        }
        else if (level == maxLevel) {
            //System.out.println("Board <"+node.gettLeft()+">--<"+node.gettRight()+"> heuristic:"+Heuristic.apply(h, node));
            return new MinimaxResult(null, Heuristic.apply(h, node));
        }
        // Continue playing.
        else {
            float returnValue;

            if (level!=0) node = node.turnTokens();

            Node next=null;
            Node returnNode=null;
            MinimaxResult res;
            while (((next=node.nextSuccessor())!=null) && (alpha<beta)) {
                // Evaluate other player's turn.
                //System.out.println("Level "+level+" successor, set <"+next.getLastToken().getNleft()+">--<"+next.getLastToken().getNright()+">");
                //System.out.println("This player has tokens: ");
                //Interface.printTokenSet(next.getMyTokens());
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
            return new MinimaxResult(returnNode, returnValue);
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
