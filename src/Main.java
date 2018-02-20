import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {

    static int PROTEIN_LENGTH;
    static double MAX_H_FREQUENCY;
    static int NUM_OF_MIN;
    static Pair<Integer, Integer>[] locations;

    static PrintWriter writer = null;
    static {
        try {
            writer = new PrintWriter("Output.txt");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String args[]) {
        PROTEIN_LENGTH = Integer.parseInt(args[0]);
        NUM_OF_MIN = Integer.parseInt(args[1]);
        MAX_H_FREQUENCY = Double.parseDouble(args[2]);
        System.out.println("In a moment you'll discover a wonderful world of homologous proteins...");
        long startTime = System.nanoTime();

        initLocations();
        LinkedList<String> sequences = HelperFunctions.seqCreator(PROTEIN_LENGTH, MAX_H_FREQUENCY);  // Creates all possible sequences due to limitations
        HashMap<Integer, Pair<String, LinkedList<String>>> seqScore = new HashMap<>();  // Hash map of (index,<sequence, folding sequences list>)
        computeStructures(sequences, seqScore);

        HomologousByMinima hbm = new HomologousByMinima();
        LinkedList<Pair<Pair<String,String>, String>> homologous= hbm.findHomologous(seqScore);

        // Print section
        if (homologous.size() == 0)
            Main.writer.println("No homologous found for length " + PROTEIN_LENGTH);
        else {
            Main.writer.println("CONGRATULATIONS!!");
            Main.writer.println("The homologous pairs of size " + PROTEIN_LENGTH + " are:");
            HelperFunctions.printList(homologous);
        }

        // Time section
        long totalTime = System.nanoTime() - startTime;
        long fixedTotalTime = TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
        if (fixedTotalTime<60)
            System.out.println("\nThe program ran for " + fixedTotalTime + " seconds");
        else if (fixedTotalTime<3600)
            System.out.println("\nThe program ran for " + fixedTotalTime/60 + " minutes and " + fixedTotalTime%60 + " seconds");
        else
            System.out.println("\nThe program ran for " + fixedTotalTime/3600 +" hours and " + (fixedTotalTime%3600)/60 + " minutes");

        writer.close();
    }

    /**
     * For any sequence from "sequences" builds possibles structures and saves those with maximum bonds number in Hash map
     */
    public static void computeStructures(LinkedList<String> sequences, HashMap<Integer, Pair<String, LinkedList<String>>> seqScore){
        int[][] matrix = new int[PROTEIN_LENGTH * 2][PROTEIN_LENGTH * 2];
        int seqNum = 0;
        LinkedList<String> maxResults = new LinkedList<>();
        Pair<Integer, LinkedList<String>> bestResults = new Pair<>(0, maxResults);
        StringBuilder foldSeq = new StringBuilder("E");
        for (String seq : sequences){
            maxResults.clear();
            bestResults.set(0, maxResults);
            matrix[matrix[0].length / 2][matrix.length / 2] = 1;
            //Pair<Integer, Integer> first = new Pair<>(matrix[0].length / 2, matrix.length / 2);
            locations[0].set(matrix[0].length / 2, matrix.length / 2);
            int isFirst = 0;
            bestResults = Structures.folding(seq, foldSeq, matrix, matrix[0].length / 2, (matrix.length / 2) + 1, 2, isFirst, bestResults);
            matrix[matrix.length / 2][matrix.length / 2] = 0; // Set the matrix back to zeroes (1)
            matrix[matrix.length / 2][(matrix[0].length / 2) + 1] = 0; // Set the matrix back to zeroes (2)

            if (bestResults.getRight().size() == NUM_OF_MIN && bestResults.getLeft() > 0) {
                seqNum ++;
                LinkedList<String> tmpMaxResults = new LinkedList<>();
                tmpMaxResults.addAll(maxResults);
                seqScore.put(seqNum, new Pair<>(seq, tmpMaxResults));
            }
        }
    }

    // Initiation locations array with empty pairs
    public static void initLocations(){
        locations = new Pair[PROTEIN_LENGTH];
        for (int i = 0; i< locations.length; i++)
            locations[i] = new Pair<>(0,0);
    }
}