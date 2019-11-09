import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Interface {

    public static void main(String args[]){
        System.out.println("**************DOMINO GAME*****************");
        System.out.println("**Author: German Telmo Eizaguirre Suarez**");
        if (DominoGame.MAX_LEVELS==0) {
            System.out.println("ERROR :: Maximum level must be at least 1.");
            System.exit(-1);
        }
        //printTokenSet(DominoGame.totalTokens);
        //System.out.println("Total: "+ DominoGame.totalTokens.size());

        DominoGame.MAX_LEVELS=3;
        System.out.println(combatCPUsSilent(HEURISTICS.NDIF, HEURISTICS.NDIF, ALGORITHM.MINIMAX));
        System.out.println(combatCPUsVerbose(HEURISTICS.NDIF, HEURISTICS.NDIF, ALGORITHM.ALPHABETA));


    }

    public static GameResult combatCPUsVerbose (HEURISTICS h1, HEURISTICS h2, ALGORITHM alg){

        double gsTime = System.nanoTime();

        // Set up.
        DominoGame.clear();
        DominoGame.start();
        DominoPlayer cpu1 = new DominoPlayer("cpu1", new HashSet<>(), h1);
        DominoPlayer cpu2 = new DominoPlayer("cpu2", new HashSet<>(), h2);
        DominoGame.distributeCards(cpu1, cpu2);

        // Game
        MinimaxResult currentMinimaxResult;
        Node currentNode;
        String winner = "none";
        DominoPlayer currentPlayer;
        List<Double> decisionTimes = new LinkedList<>();
        Token lastNode = new Token((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT);
        DominoGame.boardTokens.add(new Token((short)DominoGame.FIRST_LEFT,(short)DominoGame.FIRST_RIGHT));
        DominoGame.nleft=DominoGame.FIRST_LEFT;
        DominoGame.nright=DominoGame.FIRST_RIGHT;
        boolean cpuTurn = cpu1.hasToken((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT) ? true : false;
        if (cpuTurn) {
            if (!cpu1.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }
            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, cpu2.getMyTokens(), DominoGame.boardTokens, lastNode);
        }
        else {
            if (!cpu2.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }
            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, cpu1.getMyTokens(), DominoGame.boardTokens,lastNode);
        }

        System.out.println("Starts "+(cpuTurn?"cpu":"user"));
        System.out.println("CPUs tokens:");
        printTokenSet(cpu1.getMyTokens());
        System.out.println("User's tokens:");
        printTokenSet(cpu2.getMyTokens());
        cpuTurn = !cpuTurn;
        currentPlayer = cpuTurn ? cpu1 : cpu2;
        while (!DominoGame.end) {

            System.out.println("Stats");
            System.out.println("tokens at board:"+DominoGame.boardTokens.size());
            System.out.println("tokens of "+cpu1.getName()+":"+cpu1.getMyTokens().size());
            System.out.println("tokens of "+cpu2.getName()+":"+cpu2.getMyTokens().size());
            System.out.println("Turn: "+(cpuTurn?cpu1.getName():cpu2.getName()));
            System.out.println("Board: "+DominoGame.graphicBoardEdges());
            printTokenSet(DominoGame.boardTokens);
            System.out.println("Player tokens: ");
            printTokenSet(currentPlayer.getMyTokens());

            double sTime = System.nanoTime();
            if (cpuTurn)
                currentMinimaxResult = (alg==ALGORITHM.MINIMAX) ?  cpu1.minimaxStrategy(currentNode, 0) : cpu1.abStrategy(currentNode, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            else
                currentMinimaxResult = (alg==ALGORITHM.MINIMAX) ? cpu2.minimaxStrategy(currentNode, 0) : cpu2.abStrategy(currentNode, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            currentNode = currentMinimaxResult.getNode();
            double eTime = System.nanoTime();
            System.out.println("Decision time: "+(eTime-sTime));
            decisionTimes.add(eTime-sTime);

            // Test result.
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
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner="Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                }
                else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
                }
            }
            else {

                if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                    DominoGame.end=true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner="Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                }
                else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
                }
            }
        }



        // Final result
        if (winner.compareTo("Tie")!=0) System.out.println("The winner is "+winner);
        else System.out.println("The game resulted in tie");

        System.out.println(cpu1.getName()+" left tokens (sum "+(cpu1.getMyTokens().isEmpty()?0:(cpu1.getMyTokens().stream().map((x) -> x.getNright()+x.getNleft()).reduce(0,Integer::sum)))+"):");
        printTokenSet(cpu1.getMyTokens());
        System.out.println(cpu2.getName()+" left tokens (sum "+(cpu2.getMyTokens().isEmpty()?0:(cpu2.getMyTokens().stream().map((x) -> x.getNright()+x.getNleft()).reduce(0,Integer::sum)))+"):");
        printTokenSet(cpu2.getMyTokens());

        double geTime = System.nanoTime();

        return new GameResult(winner, (winner=="cpu1")?h1:h2, (geTime-gsTime), decisionTimes.stream().mapToDouble((x)->x).average().getAsDouble(), alg);
    }

    public static GameResult combatCPUsSilent (HEURISTICS h1, HEURISTICS h2, ALGORITHM alg){

        double gsTime = System.nanoTime();

        // Set up.
        DominoGame.clear();
        DominoGame.start();
        DominoPlayer cpu1 = new DominoPlayer("cpu1", new HashSet<>(), h1);
        DominoPlayer cpu2 = new DominoPlayer("cpu2", new HashSet<>(), h2);
        DominoGame.distributeCards(cpu1, cpu2);

        // Game
        MinimaxResult currentMinimaxResult;
        Node currentNode;
        String winner = "none";
        DominoPlayer currentPlayer;
        List<Double> decisionTimes = new LinkedList<>();
        Token lastNode = new Token((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT);
        DominoGame.boardTokens.add(new Token((short)DominoGame.FIRST_LEFT,(short)DominoGame.FIRST_RIGHT));
        DominoGame.nleft=DominoGame.FIRST_LEFT;
        DominoGame.nright=DominoGame.FIRST_RIGHT;
        boolean cpuTurn = cpu1.hasToken((short)DominoGame.FIRST_LEFT, (short)DominoGame.FIRST_RIGHT) ? true : false;
        if (cpuTurn) {
            if (!cpu1.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }

            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, cpu2.getMyTokens(), DominoGame.boardTokens, lastNode);
        }
        else {
            if (!cpu2.removeToken((short) DominoGame.FIRST_LEFT, (short) DominoGame.FIRST_RIGHT)) {
                System.out.println("ERROR :: when removing first token (6,6)");
                System.exit(-1);
            }
            currentNode = new Node(DominoGame.FIRST_LEFT,DominoGame.FIRST_RIGHT, cpu1.getMyTokens(), DominoGame.boardTokens,lastNode);
        }

        cpuTurn = !cpuTurn;
        currentPlayer = cpuTurn ? cpu1 : cpu2;
        while (!DominoGame.end) {

            double sTime = System.nanoTime();
            if (cpuTurn)
                currentMinimaxResult = (alg==ALGORITHM.MINIMAX) ?  cpu1.minimaxStrategy(currentNode, 0) : cpu1.abStrategy(currentNode, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            else
                currentMinimaxResult = (alg==ALGORITHM.MINIMAX) ? cpu2.minimaxStrategy(currentNode, 0) : cpu2.abStrategy(currentNode, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            currentNode = currentMinimaxResult.getNode();
            double eTime = System.nanoTime();
            decisionTimes.add(eTime-sTime);

            // Test result.
            if (currentNode!=null) {
                if (currentNode.getBoardTokens().size()!=DominoGame.boardTokens.size()) {

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

                if (currentNode.isFinal()) {
                    DominoGame.end=true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner="Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                }
                else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
                }
            }
            else {

                if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                    DominoGame.end=true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner="Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentMinimaxResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                }
                else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastNode);
                }
            }
        }

        double geTime = System.nanoTime();

        return new GameResult(winner, (winner=="cpu1")?h1:h2, (geTime-gsTime), decisionTimes.stream().mapToDouble((x)->x).average().getAsDouble(), alg);
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
