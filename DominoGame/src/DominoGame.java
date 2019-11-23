import gnu.trove.THashSet;

import java.util.*;

public class DominoGame {

    public static int SEED = 123;
    public final static int MAX_NUM= 6;
    public final static int MIN_NUM=0;
    public static int NUM_TOKENS = 28;
    public final static Integer FIRST_LEFT = 6;
    public static final Integer FIRST_RIGHT = 6;
    public static THashSet<Token> totalTokens;
    public static THashSet<Token> boardTokens;
    public static boolean end = false;
    public static Integer nleft, nright;

    public static void start () {
        generateDominoTokens();
        boardTokens = new THashSet<>();
        end = false;
    }

    public static void clear() {
        totalTokens = new THashSet<>();
        boardTokens = new THashSet<>();
        end=true;

    }

    public static boolean isEnd(THashSet<Token> userTokens){

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
        totalTokens = new THashSet<>();
        for (int i=MIN_NUM; i<=MAX_NUM; i++){
            for (int j=i; j<=MAX_NUM; j++){
                totalTokens.add(new Token(i,j));
            }
        }
        NUM_TOKENS = totalTokens.size();
    }

    public static void setTotalTokens(THashSet<Token> tt) {
        totalTokens = tt;
    }

    public static void update(Node node) {

        boardTokens.addAll(node.getBoardTokens());
        end = node.isFinal();
        nleft = node.gettLeft();
        nright = node.gettRight();

    }

    public static void distributeCards(DominoPlayer p1, DominoPlayer p2) {
        // Ensure randomness.
        Random rand = new Random(System.currentTimeMillis()%Integer.MAX_VALUE);
        SEED = (SEED+1)%Integer.MAX_VALUE+1;
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

    public static boolean testTie (THashSet<Token> u1, THashSet<Token> u2){
        if (!u1.isEmpty() && !u2.isEmpty())
            return ((u1.stream().map( (x) ->  x.getNright() + x.getNleft() ).reduce(0, Integer::sum) )
                    ==
                    (u2.stream().map( (x) ->  x.getNright() + x.getNleft() ).reduce(0, Integer::sum) ));
        else return false;
    }

    public static String graphicBoardEdges(){
        return "<"+DominoGame.nleft+">-...-<"+DominoGame.nright+">";
    }

}
