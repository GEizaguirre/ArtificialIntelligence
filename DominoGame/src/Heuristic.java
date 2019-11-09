import java.util.HashSet;

enum HEURISTICS { NDIF };

public class Heuristic {
     public static float apply (HEURISTICS h, Node node){
         float value = 0f;
         switch(h){
             // Number of different numbers in our tokens.
             case NDIF:
                 value = nDifferentNumber(node); break;
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
}
