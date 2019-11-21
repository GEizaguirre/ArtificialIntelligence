import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

enum HEURISTICS { TVAL, DDST, HCMC };

public class Heuristic {


     private static final Map<String, Integer> maxLevels = new HashMap<>();
     // Initiate tunned up maximum epxloration levels.
     static  {
         for (HEURISTICS h : HEURISTICS.values()){
             maxLevels.put(Heuristic.printHeuristic(h)+"minimax", 7);
         }
         for (HEURISTICS h : HEURISTICS.values()){
             maxLevels.put(Heuristic.printHeuristic(h)+"alphabeta", 13);
         }
     }


     public static float apply (HEURISTICS h, Node node){
         float value = 0f;
         switch(h){

             case TVAL:
                 value = nTokenValue(node); break;
             case DDST:
                 value = DoubleDifStrong(node); break;
             case HCMC:
                 value = HighCloseMajorityContinue(node); break;
         }
         return value;
     }

     public static float nTokenValue(Node node){
         if (node.getLevel()%2==0) return (node.getLastToken().getNright()+node.getLastToken().getNleft())/(DominoGame.MAX_NUM*2);
         else return 1-(node.getLastToken().getNright()+node.getLastToken().getNleft())/(DominoGame.MAX_NUM*2);
     }

    public static float DoubleDifStrong(Node node){
         float totalResult=0;
        // First value: if game "doubled"
        if (node.gettLeft()==node.gettRight()) {
            if (node.getLevel()%2!=0) totalResult+=0.5;
            else totalResult-=0.5;
        }
        // Second value: Number of different numbers in our tokens.
        HashSet<Integer> numbers = new HashSet<>();
        for (Token t: node.getMyTokens()){
            if (!numbers.contains(t.getNleft())) numbers.add(t.getNleft());
            if (!numbers.contains(t.getNright())) numbers.add(t.getNright());
        }
        totalResult += numbers.size()*0.25;
        //Third value: check if you have a "strong" play (dominate at least one side).
        boolean iHaveLeft = false;
        boolean iHaveRight = false;
        boolean iHasLeft = false;
        boolean iHasRight = false;
        // I can access any side?
        for (Token t: node.getMyTokens()){
            if ( t.getNleft()==node.gettLeft() || t.getNright()==node.gettLeft()) iHaveLeft=true;
            if ( t.getNleft()==node.gettRight() || t.getNright()==node.gettRight()) iHaveRight=true;
        }
        // He can access any side?
        for (Token t: DominoGame.totalTokens){
            if (!node.getBoardTokens().contains(t) && !node.getMyTokens().contains(t)) {
                if ( t.getNleft()==node.gettLeft() || t.getNright()==node.gettLeft()) iHasLeft=true;
                if ( t.getNleft()==node.gettRight() || t.getNright()==node.gettRight()) iHasRight=true;
            }
        }
        if (iHaveLeft && !iHasLeft) totalResult+=1;
        if (iHaveRight && !iHasRight) totalResult+=1;
        return totalResult;
    }

    public static float HighCloseMajorityContinue (Node node) {
         float totalResult=0;
         int counter;
        // First value: playing with highest numbers in our token set (get relative position according to total value).
        // Machine used last token.
        if (node.getLevel()%2!=1) {
            List<Integer> orderedValues = node.getMyTokens().stream().map((x) -> x.getNleft() + x.getNright()).sorted(Integer::compareTo).collect(Collectors.toList());
            counter = 1;
            int lTokenSum = node.getLastToken().getNright() + node.getLastToken().getNleft();
            for (Integer v : orderedValues) {
                if (lTokenSum > v) counter++;
                else break;
            }
            totalResult += 0.33f * counter / (orderedValues.size() + 1);
        }
        // Contrary used last token.
        else {
            HashSet<Token> contraryTokens = new HashSet<>();
            for (Token t : DominoGame.totalTokens){
                if (!node.getMyTokens().contains(t) && !node.getBoardTokens().contains(t)) contraryTokens.add(t);
            }
            List<Integer> orderedValues = contraryTokens.stream().map((x) -> x.getNleft() + x.getNright()).sorted(Integer::compareTo).collect(Collectors.toList());
            counter = 1;
            int lTokenSum = node.getLastToken().getNright() + node.getLastToken().getNleft();
            for (Integer v : orderedValues) {
                if (lTokenSum > v) counter++;
                else break;
            }
            totalResult += 0.33f * (1-(counter / (orderedValues.size() + 1)));
        }
        // Second value: Close opponent.
        // Calculate board edges' numbers' frequency in opponent tokens.
        int n1 = node.gettLeft();
        int n2 = node.gettRight();
        float frequency1 = 0;
        float frequency2 = 0;
        int tokenCounter = 0;
        for (Token t: DominoGame.totalTokens){
            if (!node.getMyTokens().contains(t) && !node.getBoardTokens().contains(t)){
                if (t.getNleft()==n1) frequency1++;
                if (t.getNright()==n1) frequency1++;
                if (t.getNleft()==n2) frequency2++;
                if (t.getNright()==n2) frequency2++;
                tokenCounter = tokenCounter + 2;
            }
        }
        if (tokenCounter==0) {
            frequency1=0;
            frequency2=0;
        } else {
            frequency1 = frequency1 / tokenCounter;
            frequency2 = frequency2 / tokenCounter;
        }
        // If less frequency of last token's numbers in opponent's tokens, better.
        totalResult += ((1-frequency1)+(1-frequency2))*0.33f;
        // Third value: frequency of edges' numbers in my tokens.
        frequency1 = 0;
        frequency2 = 0;
        if (node.getMyTokens().isEmpty()) {
            frequency1=0;
            frequency2=0;
        } else {
            for (Token t: node.getMyTokens()){
                if (t.getNleft()==n1) frequency1++;
                if (t.getNright()==n1) frequency1++;
                if (t.getNleft()==n2) frequency2++;
                if (t.getNright()==n2) frequency2++;
            }
            frequency1 = frequency1 / node.getMyTokens().size();
            frequency2 = frequency2 / node.getMyTokens().size();
        }
        totalResult += (frequency1+frequency2)*0.33f;
        // Fourth value: Check number of non continuable tokens in player's hand.
        counter = 0;
        for (Token t1: node.getMyTokens()){
            n1 = t1.getNleft();
            n2 = t1.getNright();
            if (n1 != node.gettLeft() && n2 != node.gettRight() && n1 != node.gettLeft() && n2 != node.gettRight()) {
                boolean found = false;
                for (Token t2: DominoGame.totalTokens) {
                    if (!node.getMyTokens().contains(t2) && !node.getBoardTokens().contains(t2)) {
                        if (t2.getNright() == n1 || t2.getNright() == n2 || t2.getNleft() == n1 || t2.getNleft() == n2 ){
                            found = true; break; }
                    }
                }
                if (found) counter++;
            }
        }
        totalResult = totalResult - counter*0.25f;
        return totalResult;
    }

     public static String printHeuristic (HEURISTICS h){
         switch(h) {
             case TVAL:
                 return "TVAL";
             case DDST:
                 return "DDST";
             case HCMC:
                 return "HCMC";
         }
         return "No one";
    }

    public static void setMaxLevel (HEURISTICS h, String algorithm, int level){
        maxLevels.put(printHeuristic(h)+algorithm, level);
    }

    public static int getMaxLevel(HEURISTICS h, String algorithm){
        return maxLevels.get(printHeuristic(h)+algorithm);
    }
}
