import java.util.*;

public class DominoGame {

    // Minimum max level must be 1.
    public final static int MAX_LEVELS = 2;
    public final static int MAX_NUM=6;
    public final static int MIN_NUM=0;
    public static HashSet<Token> totalTokens;
    public static HashSet<Token> boardTokens;
    public static boolean end = false;
    public static int nleft, nright;

    public static void start () {
        generateDominoTokens();
        boardTokens = new HashSet<>();
    }

    public static void clear() {
        totalTokens = new HashSet<>();
        boardTokens = new HashSet<>();
        end=true;

    }

    public static boolean isEnd(HashSet<Token> userTokens){

        if (userTokens.size()==0) return true;
        // Test user's movements.
        for (Token t : userTokens)
            if (((t.getNleft()==nleft))
                    || ((t.getNleft()==nright)
                    || (t.getNright()==nright)
                    || (t.getNright()==nleft))) return false;

        // Test opponent's movements (it has successors?).
        for (Token t: DominoGame.totalTokens)
            if (!userTokens.contains(t) && (!boardTokens.contains(t)) && (((t.getNleft()==nleft))
                    || ((t.getNleft()==nright)
                    || (t.getNright()==nright)
                    || (t.getNright()==nleft)))) return false;

        return true;
    }

    public static void generateDominoTokens(){
        totalTokens = new HashSet<>();
        for (short i=MIN_NUM; i<=MAX_NUM; i++){
            for (short j=i; j<=MAX_NUM; j++){
                totalTokens.add(new Token(i,j));
            }
        }
    }

    public static void setTotalTokens(HashSet<Token> tt) {
        totalTokens = tt;
    }

    public static void update(Node node) {
        boardTokens.addAll(node.getBoardTokens());
        end = node.isFinal();
        if (node.isFinal()) System.out.println("FINAL");
        /*System.out.println("-------");
        Interface.printTokenSet(node.getBoardTokens());
        Interface.printTokenSet(node.getMyTokens());
        System.out.println("-------");*/
        nleft = node.gettLeft();
        nright = node.gettRight();

    }

    public static void distributeCards(DominoPlayer p1, DominoPlayer p2) {
        Random rand = new Random();
        List<Token> auxTotalTokens = new LinkedList<>();
        for (Token t : totalTokens) auxTotalTokens.add(t);


        for (int i = 0; i < totalTokens.size() / 2; i++) {

            // take a random index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(auxTotalTokens.size());

            // add element in temporary list

            p1.getMyTokens().add(auxTotalTokens.get(randomIndex));

            // Remove selected element from orginal list
            auxTotalTokens.remove(randomIndex);
        }
        for (Token t : auxTotalTokens) {
            p2.getMyTokens().add(t);
        }
    }

    public static boolean testTie (HashSet<Token> u1, HashSet<Token> u2){
        if (!u1.isEmpty() && !u2.isEmpty())
            return ((u1.stream().map( (x) ->  x.getNright() + x.getNleft() ).reduce(Integer::sum) )
                    ==
                    (u2.stream().map( (x) ->  x.getNright() + x.getNleft() ).reduce(Integer::sum) ));
        else return false;
    }

    public static String graphicBoardEdges(){
        return "<"+DominoGame.nleft+">-...-<"+DominoGame.nright+">";
    }

}
