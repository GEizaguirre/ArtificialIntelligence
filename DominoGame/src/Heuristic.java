import java.util.HashSet;

enum HEURISTICS { NDIF, DSF, HCM };

public class Heuristic {
     public static float apply (HEURISTICS h, Node node){
         float value = 0f;
         switch(h){
             // Number of different numbers in our tokens.
             case NDIF:
                 value = nDifferentNumber(node); break;
             case DSF:
                 value = DoubleSumFirm(node); break;
             case HCM:
                 value = HighCloseMajority(node); break;
         }
         return value;
     }

     public static float nDifferentNumber(Node node){
         //Interface.printTokenSet(node.getMyTokens());
         HashSet<Short> numbers = new HashSet<>();
         for (Token t: node.getMyTokens()){
             if (!numbers.contains(t.getNleft())) numbers.add(t.getNleft());
             if (!numbers.contains(t.getNright())) numbers.add(t.getNright());
         }
         return numbers.size();
     }

    public static float DoubleSumFirm(Node node){
         float totalResult=0;
        //First value: if game "doubled"
        if (node.gettLeft()==node.gettRight()) totalResult+=0.25;
        //Second value: using high-value token
        totalResult += (node.getLastToken().getNright()+node.getLastToken().getNleft())*0.25;
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
        if ((iHaveLeft && !iHasLeft) || (iHaveRight && !iHasRight)) totalResult+=0.5;
        return totalResult;
    }

    public static float HighCloseMajority (Node node) {
         float totalResult=0;

         /** CODE **/

         return totalResult;
    }

     public static String printHeuristic (HEURISTICS h){
         switch(h) {
             case NDIF:
                 return "NDIF";
             case DSF:
                 return "DSF";
             case HCM:
                 return "HCM";
         }
         return "No one";
    }
}
