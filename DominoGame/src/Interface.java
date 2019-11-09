import java.util.HashSet;
import java.util.List;

public class Interface {

    public static void main(String args[]){
        System.out.println("**************DOMINO GAME*****************");
        System.out.println("**Author: German Telmo Eizaguirre Suarez**");
        DominoGame.start();
        if (DominoGame.MAX_LEVELS==0) {
            System.out.println("ERROR :: Maximum level must be at least 1.");
            System.exit(-1);
        }
        //printTokenSet(DominoGame.totalTokens);
        //System.out.println("Total: "+ DominoGame.totalTokens.size());


        DominoPlayer cpu = new DominoPlayer("cpu", new HashSet<>(), HEURISTICS.NDIF);
        DominoPlayer user = new DominoPlayer("user", new HashSet<>(), HEURISTICS.NDIF);
        DominoGame.distributeCards(cpu, user);
        // TESTS
        /*
        cpu.getMyTokens().add(new Token((short)0,(short)0));
        cpu.getMyTokens().add(new Token((short)1,(short)2));
        cpu.getMyTokens().add(new Token((short)2,(short)2));
        user.getMyTokens().add(new Token((short)0,(short)1));
        user.getMyTokens().add(new Token((short)0,(short)2));
        user.getMyTokens().add(new Token((short)1,(short)1));
        */
        
        // Game
        Result currentResult;
        Node currentNode;
        String winner = "none";
        DominoPlayer currentPlayer;
        Token lastNode = new Token((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT);
        DominoGame.boardTokens.add(new Token((short)DominoGame.FIRST_LEFT,(short)DominoGame.FIRST_RIGHT));
        DominoGame.nleft=DominoGame.FIRST_LEFT;
        DominoGame.nright=DominoGame.FIRST_RIGHT;
        boolean cpuTurn = cpu.hasToken((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT) ? true : false;
        if (cpuTurn) {
            if (!cpu.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }

            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, user.getMyTokens(), DominoGame.boardTokens, lastNode);
        }
        else {
            if (!user.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }
            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, cpu.getMyTokens(), DominoGame.boardTokens,lastNode);
        }

        System.out.println("Starts "+(cpuTurn?"cpu":"user"));
        System.out.println("CPUs tokens:");
        printTokenSet(cpu.getMyTokens());
        System.out.println("User's tokens:");
        printTokenSet(user.getMyTokens());
        cpuTurn = !cpuTurn;
        currentPlayer = cpuTurn ? cpu : user;
        int nullCounter=0;
        while (!DominoGame.end) {

            double sTime = System.nanoTime();
            System.out.println(sTime);
            System.out.println("Stats");
            System.out.println("tokens at board:"+DominoGame.boardTokens.size());
            System.out.println("tokens of cpu:"+cpu.getMyTokens().size());
            System.out.println("tokens of user:"+user.getMyTokens().size());
            System.out.println("Turn: "+(cpuTurn?"cpu":"user"));
            System.out.println("Board: "+DominoGame.graphicBoardEdges());
            printTokenSet(DominoGame.boardTokens);
            System.out.println("Player tokens: ");
            printTokenSet(currentPlayer.getMyTokens());

            if (cpuTurn)
                currentResult = cpu.minimaxStrategy(currentNode, 0);
            else
                currentResult = user.minimaxStrategy(currentNode, 0);
            currentNode = currentResult.getNode();
            double eTime = System.nanoTime();
            System.out.println(eTime);
            System.out.println(" Computation time: "+(eTime-sTime));

            if (currentNode!=null) {


               if (currentNode.getBoardTokens().size()!=DominoGame.boardTokens.size()) {
                   System.out.println("Set: <" + currentNode.getLastToken().getNleft() + ">--<" + currentNode.getLastToken().getNright() + ">");
                   Boolean res = currentPlayer.removeToken(currentNode.getLastToken().getNleft(), currentNode.getLastToken().getNright());
                   lastNode.setNright(currentNode.getLastToken().getNright());
                   lastNode.setNleft(currentNode.getLastToken().getNleft());
                   DominoGame.update(currentNode);
                   if (!res) {
                       System.out.println("--------");
                       System.out.println("ERROR");
                       printTokenSet(currentNode.getMyTokens());
                       System.out.println("--------");
                   }
               }
               System.out.println("Board state: <"+DominoGame.nleft+">--<"+DominoGame.nright+">");

               if (currentNode.isFinal()) {
                   DominoGame.end=true;
                   if (DominoGame.testTie(cpu.getMyTokens(), user.getMyTokens())) winner="Tie";
                   else {
                       if (cpuTurn) {
                           winner = (currentResult.getResult() == Integer.MAX_VALUE) ? cpu.getName() : user.getName();
                       } else {
                           winner = (currentResult.getResult() == Integer.MAX_VALUE) ? user.getName() : cpu.getName();
                       }
                   }
               }
               else {
                   cpuTurn = !cpuTurn;
                   currentPlayer = cpuTurn ? cpu : user;
                   currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
               }
           }
           else {

               if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                   DominoGame.end=true;
                   if (DominoGame.testTie(cpu.getMyTokens(), user.getMyTokens())) winner="Tie";
                   else {
                       if (cpuTurn) {
                           winner = (currentResult.getResult() == Integer.MAX_VALUE) ? cpu.getName() : user.getName();
                       } else {
                           winner = (currentResult.getResult() == Integer.MAX_VALUE) ? user.getName() : cpu.getName();
                       }
                   }
               }
               else {
                   cpuTurn = !cpuTurn;
                   currentPlayer = cpuTurn ? cpu : user;
                   currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
               }
           }
        }

        if (winner.compareTo("Tie")!=0) System.out.println("The winner is "+winner);
        else System.out.println("The game resulted in tie");
        System.out.println("CPU left tokens.");
        printTokenSet(cpu.getMyTokens());
        System.out.println("User left tokens.");
        printTokenSet(user.getMyTokens());
    }

    public static void printTokenSet (HashSet<Token> set){
        int counter = 0;
        for (Token f: set){
            System.out.print("  "+f.graphicFormat());
            counter++;
            if (counter==5) {
                System.out.println();
                counter=0;
            }
        }
        System.out.println();
    }

    public static void printTokenListNumbered (List<Token> ls){
        int counter = 0;
        int absCounter = 0;
        for (Token f: ls){
            System.out.print("  ("+absCounter+") "+f.graphicFormat());
            counter++;
            absCounter++;
            if (counter==5) {
                System.out.println();
                counter=0;
            }
        }

    }
}
