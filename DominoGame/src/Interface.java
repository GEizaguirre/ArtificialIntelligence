import java.util.*;

public class Interface {

    private static Scanner in;

    public static void main(String args[]) {


        in = new Scanner(System.in);
        int option = -1;
        Aesthetics.printDominoImage();
        System.out.println("**Author: German Telmo Eizaguirre Suarez**");
        System.out.println(" (1) Play against the machine.");
        System.out.println(" (2) Execute performance evaluation.");
        System.out.print(">");
        option = Integer.parseInt(in.nextLine());
        while (option != 1 && option != 2) {
            System.out.println("Wrong option.");
            option = Integer.parseInt(in.nextLine());
        }
        if (option==1) playerGame();
        else testExecution();
    }

    public static void testExecution() {

        System.out.println("Executing tests...");


        System.out.println(" AVERAGE RESOLUTION TIME");
        System.out.println("*************Minimax tests****************");

        HashMap<Integer, List<Double>> avgResults;
        HashMap<Integer, List<Double>> worsResults;
        HashMap<Integer, List<Double>> totResults;

        /*for (HEURISTICS heu : HEURISTICS.values()) {
            System.out.println("·················" + Heuristic.printHeuristic(heu) + "····················");
            avgResults  = new HashMap<>();
            worsResults  = new HashMap<>();
            totResults  = new HashMap<>();

            for (int level = 1; level <= 8; level++) {
                Heuristic.setMaxLevel(heu, "minimax", level);
                List<Double> levResults1 = new LinkedList<>();
                List<Double> levResults2 = new LinkedList<>();
                List<Double> levResults3 = new LinkedList<>();
                for (int i = 0; i < 10; i++) {
                    try {
                        GameResult ar = combatCPUsSilent(heu, heu, ALGORITHM.MINIMAX, ALGORITHM.MINIMAX);
                        //System.out.println(ar.getAvgDecisionTime());
                        levResults1.add(ar.getAvgDecisionTime());
                        levResults2.add(ar.getWorstTime());
                        levResults3.add(ar.getTotalExecutionTime());
                        System.out.print(i+" ");
                    } catch (java.lang.OutOfMemoryError ome) {
                        System.out.println("Out of memory error");
                        levResults1.add(-1d);levResults2.add(-1d);levResults3.add(-1d);

                    }
                }
                System.out.println();
                avgResults.put(level, levResults1);
                worsResults.put(level, levResults2);
                totResults.put(level, levResults3);
            }
            System.out.println("Average time");
            for (int i=0; i<10; i++){
                for (int j=1; j<9; j++){
                    System.out.print(avgResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
            System.out.println("Worst time");
            for (int i=0; i<10; i++){
                for (int j=1; j<9; j++){
                    System.out.print(worsResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
            System.out.println("Total time");
            for (int i=0; i<10; i++){
                for (int j=1; j<9; j++){
                    System.out.print(totResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
        }
        */

        /*System.out.println("*************AlBeta tests****************");
        for (HEURISTICS heu : HEURISTICS.values()) {
            System.out.println("·················" + Heuristic.printHeuristic(heu) + "····················");
            avgResults  = new HashMap<>();
            worsResults  = new HashMap<>();
            totResults  = new HashMap<>();

            for (int level = 1; level <= 13; level++) {
                Heuristic.setMaxLevel(heu, "alphabeta", level);
                List<Double> levResults1 = new LinkedList<>();
                List<Double> levResults2 = new LinkedList<>();
                List<Double> levResults3 = new LinkedList<>();
                for (int i = 0; i < 10; i++) {
                    try {
                        GameResult ar = combatCPUsSilent(heu, heu, ALGORITHM.ALPHABETA, ALGORITHM.ALPHABETA);
                        //System.out.println(ar.getAvgDecisionTime());
                        levResults1.add(ar.getAvgDecisionTime());
                        levResults2.add(ar.getWorstTime());
                        levResults3.add(ar.getTotalExecutionTime());
                        System.out.print(i+" ");
                    } catch (java.lang.OutOfMemoryError ome) {
                        System.out.println("Out of memory error");
                        levResults1.add(-1d);levResults2.add(-1d);levResults3.add(-1d);

                    }
                }
                System.out.println();
                avgResults.put(level, levResults1);
                worsResults.put(level, levResults2);
                totResults.put(level, levResults3);
            }
            System.out.println("Average time");
            for (int i=0; i<10; i++){
                for (int j=1; j<14; j++){
                    System.out.print(avgResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
            System.out.println("Worst time");
            for (int i=0; i<10; i++){
                for (int j=1; j<14; j++){
                    System.out.print(worsResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
            System.out.println("Total time");
            for (int i=0; i<10; i++){
                for (int j=1; j<14; j++){
                    System.out.print(totResults.get(j).get(i)+"\t");
                }
                System.out.println();
            }
        }*/


        System.out.println(" GAME QUALITY IN MINIMAX");
        List <String> winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++) {
            GameResult ar = combatCPUsSilent(HEURISTICS.TVAL, HEURISTICS.DDST, ALGORITHM.MINIMAX, ALGORITHM.MINIMAX);
            System.out.print(i+" ");
            winnerList.add(ar.getWinnerName());
        }
        System.out.println("\nTVAL vs DDST - 40 games played");
        System.out.print("TVAL wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu1")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("DDST wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu2")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("tie")==0 ).count()*100/winnerList.size()+"%");

        winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++){
            GameResult ar = combatCPUsSilent(HEURISTICS.TVAL, HEURISTICS.HCMC, ALGORITHM.MINIMAX, ALGORITHM.MINIMAX);
            System.out.print(i+" ");
            winnerList.add(ar.getWinnerName());
        }
        System.out.println("TVAL vs HCMC - 40 games played");
        System.out.print("TVAL wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu1")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("HCMC wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu2")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("tie")==0 ).count()*100/winnerList.size()+"%");

        winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++){
            GameResult ar = combatCPUsSilent(HEURISTICS.DDST, HEURISTICS.HCMC, ALGORITHM.MINIMAX, ALGORITHM.MINIMAX);
            System.out.print(i+" ");
            winnerList.add(ar.getWinnerName());
        }
        System.out.println("DDST vs HCMC - 40 games played");
        System.out.print("DDST wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu1")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("HCMC wins: "+(float)winnerList.stream().filter( x -> x.compareTo("cpu2")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("tie")==0 ).count()*100/winnerList.size()+"%");




        System.out.println(" GAME QUALITY MINIMAX VS ALPHABETA");
        winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++){
            GameResult ar = combatCPUsSilent(HEURISTICS.TVAL, HEURISTICS.TVAL, ALGORITHM.MINIMAX, ALGORITHM.ALPHABETA);
            System.out.print(i+" ");
            winnerList.add(ar.getAlgorithm());
        }
        System.out.println("Minimax vs alphabeta - 40 games played");
        System.out.print("Minimax wins: "+(float)winnerList.stream().filter( x -> x.compareTo("minimax")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Alphabeta wins: "+(float)winnerList.stream().filter( x -> x.compareTo("alphabeta")==0 ).count()/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("NONE")==0 ).count()*100/winnerList.size()+"%");

        winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++){
            GameResult ar = combatCPUsSilent(HEURISTICS.DDST, HEURISTICS.DDST, ALGORITHM.MINIMAX, ALGORITHM.ALPHABETA);
            System.out.print(i+" ");
            winnerList.add(ar.getAlgorithm());
        }
        System.out.println("Minimax vs alphabeta - 40 games played");
        System.out.print("Minimax wins: "+(float)winnerList.stream().filter( x -> x.compareTo("minimax")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Alphabeta wins: "+(float)winnerList.stream().filter( x -> x.compareTo("alphabeta")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("NONE")==0 ).count()*100/winnerList.size()+"%");

        winnerList = new LinkedList<String>();
        for (int i=0; i<40; i++){
            GameResult ar = combatCPUsSilent(HEURISTICS.HCMC, HEURISTICS.HCMC, ALGORITHM.MINIMAX, ALGORITHM.ALPHABETA);
            System.out.print(i+" ");
            winnerList.add(ar.getAlgorithm());
        }
        System.out.println("Minimax vs alphabeta - 40 games played");
        System.out.print("Minimax wins: "+(float)winnerList.stream().filter( x -> x.compareTo("minimax")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Alphabeta wins: "+(float)winnerList.stream().filter( x -> x.compareTo("alphabeta")==0 ).count()*100/winnerList.size()+"%");
        System.out.print("Ties: "+(float)winnerList.stream().filter( x -> x.compareTo("NONE")==0 ).count()*100/winnerList.size()+"%");




    }

    public static GameResult combatCPUsVerbose(HEURISTICS h1, HEURISTICS h2, ALGORITHM alg1, ALGORITHM alg2) {

        double gsTime = System.nanoTime();

        // Set up.
        DominoGame.clear();
        DominoGame.start();
        DominoPlayer cpu1 = new DominoPlayer("cpu1", new HashSet<>(), h1);
        DominoPlayer cpu2 = new DominoPlayer("cpu2", new HashSet<>(), h2);
        DominoGame.distributeCards(cpu1, cpu2);

        // Game
        AlgorithmResult currentAlgorithmResult;
        Node currentNode;
        String winner = "none";
        DominoPlayer currentPlayer;
        Double worstTime = 0d;
        List<Double> decisionTimes = new LinkedList<>();
        Token lastToken = null;
        boolean cpuTurn = cpu1.hasToken(DominoGame.FIRST_LEFT, DominoGame.FIRST_RIGHT) ? true : false;
        if (cpuTurn) {
            currentNode = new Node(null, null, cpu1.getMyTokens(), DominoGame.boardTokens, null, 0);
        } else {
            currentNode = new Node(null, null, cpu2.getMyTokens(), DominoGame.boardTokens, null, 0);
        }

        System.out.println("Starts " + (cpuTurn ? "cpu" : "user"));
        System.out.println("Tokens of " + cpu1.getName());
        printTokenSet(cpu1.getMyTokens());
        System.out.println("Tokens of " + cpu2.getName());
        printTokenSet(cpu2.getMyTokens());
        currentPlayer = cpuTurn ? cpu1 : cpu2;
        while (!DominoGame.end) {

            System.out.println("Stats");
            System.out.println("tokens at board:" + DominoGame.boardTokens.size());
            System.out.println("tokens of " + cpu1.getName() + ":" + cpu1.getMyTokens().size());
            System.out.println("tokens of " + cpu2.getName() + ":" + cpu2.getMyTokens().size());
            System.out.println("Turn: " + (cpuTurn ? cpu1.getName() : cpu2.getName()));
            System.out.println("Board: " + DominoGame.graphicBoardEdges());
            printTokenSet(DominoGame.boardTokens);
            System.out.println("Player tokens: ");
            printTokenSet(currentNode.getMyTokens());

            double sTime = System.nanoTime();
            if (cpuTurn)
                currentAlgorithmResult = cpu1.playTurn(currentNode, alg1);
            else
                currentAlgorithmResult = cpu2.playTurn(currentNode, alg2);
            currentNode = currentAlgorithmResult.getNode();

            double eTime = System.nanoTime();
            if ((eTime - sTime) > worstTime) worstTime = (eTime - sTime);
            System.out.println("Decision time: " + (eTime - sTime));
            decisionTimes.add(eTime - sTime);

            // Test result.
            if (currentNode != null) {
                if (currentNode.getBoardTokens().size() != DominoGame.boardTokens.size()) {
                    System.out.println("Set: <" + currentNode.getLastToken().getNleft() + ">--<" + currentNode.getLastToken().getNright() + ">");
                    Boolean res = currentPlayer.removeToken(currentNode.getLastToken().getNleft(), currentNode.getLastToken().getNright());
                    if (lastToken == null)
                        lastToken = new Token(currentNode.getLastToken().getNright(), currentNode.getLastToken().getNleft());
                    else {
                        lastToken.setNright(currentNode.getLastToken().getNright());
                        lastToken.setNleft(currentNode.getLastToken().getNleft());
                    }

                    DominoGame.update(currentNode);
                    if (!res) {
                        System.out.println("--------");
                        System.out.println("ERROR");
                        printTokenSet(currentNode.getMyTokens());
                        System.out.println("--------");
                    }
                }
                System.out.println("Board state: <" + DominoGame.nleft + ">--<" + DominoGame.nright + ">");
                printTokenSet(DominoGame.boardTokens);

                if (currentNode.isFinal()) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastToken, 0);
                }
            } else {

                if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastToken, 0);
                }
            }

        }

        // Final result
        if (winner.compareTo("Tie") != 0) System.out.println("The winner is " + winner);
        else System.out.println("The game resulted in tie");

        System.out.println(cpu1.getName() + " left tokens (sum " + (cpu1.getMyTokens().isEmpty() ? 0 : (cpu1.getMyTokens().stream().map((x) -> x.getNright() + x.getNleft()).reduce(0, Integer::sum))) + "):");
        printTokenSet(cpu1.getMyTokens());
        System.out.println(cpu2.getName() + " left tokens (sum " + (cpu2.getMyTokens().isEmpty() ? 0 : (cpu2.getMyTokens().stream().map((x) -> x.getNright() + x.getNleft()).reduce(0, Integer::sum))) + "):");
        printTokenSet(cpu2.getMyTokens());

        double geTime = System.nanoTime();
        ALGORITHM algr = (winner.compareTo("cpu1") == 0) ? alg1 : alg2;

        return new GameResult(winner, (winner == "cpu1") ? h1 : h2, (geTime - gsTime), decisionTimes.stream().mapToDouble((x) -> x).average().getAsDouble(), algr, worstTime);
    }

    public static GameResult combatCPUsSilent(HEURISTICS h1, HEURISTICS h2, ALGORITHM alg1, ALGORITHM alg2) {

        double gsTime = System.nanoTime();

        // Set up.
        DominoGame.clear();
        DominoGame.start();
        DominoPlayer cpu1 = new DominoPlayer("cpu1", new HashSet<>(), h1);
        DominoPlayer cpu2 = new DominoPlayer("cpu2", new HashSet<>(), h2);
        DominoGame.distributeCards(cpu1, cpu2);

        // Game
        AlgorithmResult currentAlgorithmResult;
        Node currentNode;
        String winner = "none";
        DominoPlayer currentPlayer;
        Double worstTime = 0d;
        List<Double> decisionTimes = new LinkedList<>();
        Token lastToken = null;
        boolean cpuTurn = cpu1.hasToken(DominoGame.FIRST_LEFT, DominoGame.FIRST_RIGHT) ? true : false;
        if (cpuTurn) {
            currentNode = new Node(null, null, cpu1.getMyTokens(), DominoGame.boardTokens, null, 0);
        } else {
            currentNode = new Node(null, null, cpu2.getMyTokens(), DominoGame.boardTokens, null, 0);
        }

        currentPlayer = cpuTurn ? cpu1 : cpu2;
        while (!DominoGame.end) {


            double sTime = System.nanoTime();
            if (cpuTurn)
                currentAlgorithmResult = cpu1.playTurn(currentNode, alg1);
            else
                currentAlgorithmResult = cpu2.playTurn(currentNode, alg2);
            currentNode = currentAlgorithmResult.getNode();

            double eTime = System.nanoTime();
            if ((eTime - sTime) > worstTime) worstTime = (eTime - sTime);
            decisionTimes.add(eTime - sTime);

            // Test result.
            if (currentNode != null) {
                if (currentNode.getBoardTokens().size() != DominoGame.boardTokens.size()) {
                    Boolean res = currentPlayer.removeToken(currentNode.getLastToken().getNleft(), currentNode.getLastToken().getNright());
                    if (lastToken == null)
                        lastToken = new Token(currentNode.getLastToken().getNright(), currentNode.getLastToken().getNleft());
                    else {
                        lastToken.setNright(currentNode.getLastToken().getNright());
                        lastToken.setNleft(currentNode.getLastToken().getNleft());
                    }
                    DominoGame.update(currentNode);
                    if (!res) {
                        System.out.println("--------");
                        System.out.println("ERROR");
                        printTokenSet(currentNode.getMyTokens());
                        System.out.println("--------");
                    }
                }
                if (currentNode.isFinal()) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastToken, 0);
                }
            } else {

                if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(cpu1.getMyTokens(), cpu2.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu1.getName() : cpu2.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu2.getName() : cpu1.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu1 : cpu2;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), DominoGame.boardTokens, lastToken, 0);
                }
            }
        }

        // Final result
        double geTime = System.nanoTime();

        ALGORITHM algr = (winner.compareTo("cpu1") == 0) ? alg1 : alg2;
        return new GameResult(winner, (winner == "cpu1") ? h1 : h2, (geTime - gsTime), decisionTimes.stream().mapToDouble((x) -> x).average().getAsDouble(), algr, worstTime);
    }

    public static void playerGame() {

        Integer option;
        ALGORITHM alg;
        HEURISTICS h = HEURISTICS.TVAL;

        System.out.println("Welcome to our Domino Simulator.");
        System.out.println("Define cpu's parameters:");
        System.out.println("Choose algorithm: (1) Minimax (2) Alpha Beta Pruning");
        System.out.print(">");
        option = Integer.parseInt(in.nextLine());
        while (option != 1 && option != 2) {
            System.out.println("Wrong answer, must be 1 or 2.");
            System.out.print(">");
            option = Integer.parseInt(in.nextLine());
        }
        if (option == 1) alg = ALGORITHM.MINIMAX;
        else alg = ALGORITHM.ALPHABETA;
        System.out.println("Choose heuristic: (1) TVAL (2) DDST (3) HCMC");
        System.out.print(">");
        option = Integer.parseInt(in.nextLine());
        while (option != 1 && option != 2 && option != 3) {
            System.out.println("Wrong answer, must be 1 or 2 or 3.");
            System.out.print(">");
            option = Integer.parseInt(in.nextLine());
        }
        if (option == 1) h = HEURISTICS.TVAL;
        if (option == 2) h = HEURISTICS.DDST;
        if (option == 3) h = HEURISTICS.HCMC;
        System.out.println("Insert maximum exploration level:");
        System.out.print(">");
        option = Integer.parseInt(in.nextLine());
        if (alg == ALGORITHM.ALPHABETA) {
            while (option <= 0 || option > 12) {
                System.out.println("Wrong answer, maximum exploration level should be [1,12] for alpha beta pruning.");
                System.out.print(">");
                option = Integer.parseInt(in.nextLine());
            }
        } else {
            while (option <= 0 || option > 8) {
                System.out.println("Wrong answer, maximum exploration level should be [1,8] for minimax.");
                System.out.print(">");
                option = Integer.parseInt(in.nextLine());
            }
        }
        Heuristic.setMaxLevel(h, DominoPlayer.printAlgorithm(alg), option);

        // Set up.
        DominoGame.clear();
        DominoGame.start();
        DominoPlayer cpu = new DominoPlayer("cpu", new HashSet<>(), h);
        DominoPlayer user = new DominoPlayer("user", new HashSet<>());
        DominoGame.distributeCards(cpu, user);

        // Game
        AlgorithmResult currentAlgorithmResult;
        String winner = "none";
        Token lastToken = null;
        boolean cpuTurn = cpu.hasToken(DominoGame.FIRST_LEFT, DominoGame.FIRST_RIGHT) ? true : false;
        DominoPlayer currentPlayer = (cpuTurn) ? cpu : user;
        Node currentNode = new Node(null, null, currentPlayer.getMyTokens(), copyTokenSet(DominoGame.boardTokens), null, 0);
        while (!DominoGame.end) {
            System.out.println("-·-·-·-·-·-·-·-·-·-·-·--·-·-·-·-·-·-·-·-·-·-·-");
            if (cpuTurn) {
                System.out.println("CPU moves...");
                System.out.println("Tokens before cpu turn");
                printTokenSet(currentNode.getMyTokens());
                if (!currentNode.getBoardTokens().isEmpty()) {
                    System.out.println("Board tokens before cpu turn");
                    printTokenSet(currentNode.getBoardTokens());
                    System.out.println("Last token:" + currentNode.getLastToken().graphicFormat());
                }
                currentAlgorithmResult = cpu.playTurn(currentNode, alg);
                System.out.println("Tokens after cpu turn");
                printTokenSet(currentAlgorithmResult.getNode().getMyTokens());
                System.out.println("Board tokens after cpu turn");
                printTokenSet(currentAlgorithmResult.getNode().getBoardTokens());
                System.out.println("Set " + currentAlgorithmResult.getNode().getLastToken().graphicFormat());
            } else {
                System.out.println("You move...");
                System.out.println("Board size before user turn." + DominoGame.boardTokens.size());
                currentAlgorithmResult = userMove(currentNode, user);
            }
            currentNode = currentAlgorithmResult.getNode();
            System.out.println("Tokens after user turn");
            printTokenSet(currentNode.getMyTokens());
            System.out.println("Board tokens after user turn");
            printTokenSet(currentNode.getBoardTokens());
            System.out.println("Last token:" + currentNode.getLastToken().graphicFormat());

            // Test result.
            if (currentNode != null) {
                System.out.println("Current node is no null");
                System.out.println("cuurent node board size:" + currentNode.getBoardTokens().size());
                System.out.println("game node board size:" + DominoGame.boardTokens.size());
                // If not passed.
                if (currentNode.getBoardTokens().size() != DominoGame.boardTokens.size()) {
                    System.out.println("Did not pass game.");
                    Boolean res = currentPlayer.removeToken(currentNode.getLastToken().getNleft(), currentNode.getLastToken().getNright());
                    if (lastToken == null) {
                        System.out.println("Creating new last token");
                        lastToken = new Token(currentNode.getLastToken().getNright(), currentNode.getLastToken().getNleft());
                    } else {
                        System.out.println("Last token is not null");
                        lastToken.setNright(currentNode.getLastToken().getNright());
                        lastToken.setNleft(currentNode.getLastToken().getNleft());
                    }
                    DominoGame.update(currentNode);
                    if (!res) {
                        System.out.println("--------");
                        System.out.println("ERROR");
                        printTokenSet(currentNode.getMyTokens());
                        System.out.println("--------");
                    }
                }
                if (currentNode.isFinal()) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(cpu.getMyTokens(), user.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu.getName() : user.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? user.getName() : cpu.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu : user;
                    System.out.println("New current node");
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), copyTokenSet(DominoGame.boardTokens), lastToken, 0);
                    System.out.println("User tokens of new current node");
                    printTokenSet(currentNode.getMyTokens());
                    System.out.println("Board tokensof new current node");
                    printTokenSet(currentNode.getBoardTokens());
                    System.out.println("Last token of new current node:" + currentNode.getLastToken().graphicFormat());
                }
            } else {

                if (DominoGame.isEnd(currentPlayer.getMyTokens())) {
                    DominoGame.end = true;
                    if (DominoGame.testTie(user.getMyTokens(), cpu.getMyTokens())) winner = "Tie";
                    else {
                        if (cpuTurn) {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? cpu.getName() : user.getName();
                        } else {
                            winner = (currentAlgorithmResult.getResult() == Integer.MAX_VALUE) ? user.getName() : cpu.getName();
                        }
                    }
                } else {
                    cpuTurn = !cpuTurn;
                    currentPlayer = cpuTurn ? cpu : user;
                    currentNode = new Node(DominoGame.nleft, DominoGame.nright, currentPlayer.getMyTokens(), copyTokenSet(DominoGame.boardTokens), lastToken, 0);
                }
            }
        }

        // Final result
        if (winner.compareTo("Tie") != 0) System.out.println("The winner is " + winner);
        else System.out.println("The game resulted in tie");

        System.out.println(cpu.getName() + " left tokens (sum " + (cpu.getMyTokens().isEmpty() ? 0 : (cpu.getMyTokens().stream().map((x) -> x.getNright() + x.getNleft()).reduce(0, Integer::sum))) + "):");
        printTokenSet(cpu.getMyTokens());
        System.out.println(user.getName() + " left tokens (sum " + (user.getMyTokens().isEmpty() ? 0 : (user.getMyTokens().stream().map((x) -> x.getNright() + x.getNleft()).reduce(0, Integer::sum))) + "):");
        printTokenSet(user.getMyTokens());

    }

    public static AlgorithmResult userMove(Node currentNode, DominoPlayer user) {
        System.out.println("Tokens at board:" + currentNode.getBoardTokens().size());
        System.out.println("Board edges: " + ((DominoGame.boardTokens.isEmpty()) ? "empty" : DominoGame.graphicBoardEdges()));
        printTokenSet(DominoGame.boardTokens);
        System.out.println("Your tokens:");
        printTokenSet(user.getMyTokens());
        System.out.println("Choose a possible movement: ");
        List<TokenMove> ltm = new LinkedList<>();
        int counter = 0;
        System.out.println(" (" + counter++ + ")\tPass.");
        if (currentNode.getBoardTokens().isEmpty()) {
            for (Token t : user.getMyTokens()) {
                System.out.println(" (" + (counter++) + ")\t[" + t.graphicFormat() + "]");
                ltm.add(new TokenMove(t, "x", "x"));
            }
        } else {
            for (Token t : user.getMyTokens()) {
                if (t.getNleft() == currentNode.gettLeft()) {
                    System.out.println(" (" + (counter++) + ")\t[<" + t.getNright() + ">-<" + t.getNleft() + ">] <" + currentNode.gettLeft() + ">-<" + currentNode.gettRight() + ">");
                    ltm.add(new TokenMove(t, "l", "l"));
                }
                if (t.getNright() == currentNode.gettLeft()) {
                    System.out.println(" (" + (counter++) + ")\t[<" + t.getNleft() + ">-<" + t.getNright() + ">] <" + currentNode.gettLeft() + ">-<" + currentNode.gettRight() + ">");
                    ltm.add(new TokenMove(t, "r", "l"));
                }
                if (t.getNright() == currentNode.gettRight()) {
                    System.out.println(" (" + (counter++) + ")\t          <" + currentNode.gettLeft() + ">-<" + currentNode.gettRight() + "> [<" + t.getNright() + ">-<" + t.getNleft() + ">]");
                    ltm.add(new TokenMove(t, "r", "r"));
                }
                if (t.getNleft() == currentNode.gettRight()) {
                    System.out.println(" (" + (counter++) + ")\t          <" + currentNode.gettLeft() + ">-<" + currentNode.gettRight() + "> [<" + t.getNleft() + ">-<" + t.getNright() + ">]");
                    ltm.add(new TokenMove(t, "l", "r"));
                }
            }
        }
        int userChoice = -1;
        System.out.print(">");
        try {
            userChoice = Integer.parseInt(in.nextLine());
        } catch (java.lang.NumberFormatException e){
            System.out.println("Void selection.");
            userChoice=-1;
        }
        while (userChoice < 0 || userChoice > counter-1) {
            System.out.println("The token number does not exist. Choose an existing token, please.");
            System.out.print(">");
            try {
                userChoice = Integer.parseInt(in.nextLine());
            } catch (java.lang.NumberFormatException e){
                System.out.println("Void selection.");
                userChoice=-1;
            }
        }
        if (userChoice != 0) {
            Token t = ltm.get(userChoice - 1).t;
            currentNode.getMyTokens().remove(t);
            currentNode.getBoardTokens().add(t);
            currentNode.setLastToken(t);
            if (ltm.get(userChoice - 1).boardTouch.compareTo("l") == 0) {
                if (ltm.get(userChoice - 1).tokenTouch.compareTo("l") == 0) {
                    currentNode.settLeft(t.getNright());
                } else {
                    currentNode.settLeft(t.getNleft());
                }
            } else if (ltm.get(userChoice - 1).boardTouch.compareTo("r") == 0){
                if (ltm.get(userChoice - 1).tokenTouch.compareTo("r") == 0) {
                    currentNode.settRight(t.getNleft());
                } else {
                    currentNode.settRight(t.getNright());
                }
            }
            else {
                currentNode.settRight(t.getNright());
                currentNode.settLeft(t.getNleft());
            }
        }
        return new AlgorithmResult(currentNode, 0);
    }

    private static class TokenMove {
        private Token t;
        private String tokenTouch, boardTouch;

        public TokenMove(Token tk, String tt, String bt) {
            t = tk;
            tokenTouch = tt;
            boardTouch = bt;
        }
    }

    public static void printTokenSet(HashSet<Token> set) {
        int counter = 0;
        for (Token f : set) {
            System.out.print("  " + f.graphicFormat());
            counter++;
            if (counter == 5) {
                System.out.println();
                counter = 0;
            }
        }
        System.out.println();
    }

    public static void printTokenListNumbered(List<Token> ls) {
        int counter = 0;
        int absCounter = 0;
        for (Token f : ls) {
            System.out.print("  (" + absCounter + ") " + f.graphicFormat());
            counter++;
            absCounter++;
            if (counter == 5) {
                System.out.println();
                counter = 0;
            }
        }

    }

    public static HashSet<Token> copyTokenSet(HashSet<Token> oldSet) {
        HashSet<Token> newSet = new HashSet<>();
        newSet.addAll(oldSet);
        return newSet;
    }

}
