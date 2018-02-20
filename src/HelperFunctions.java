import java.util.LinkedList;

/**
 * This class contain sub-function which used us for different calculations
 */
public class HelperFunctions {

    /**
     * @param len Protein length
     * @param frequency Maximum ratio of H's (between 0 to 1) in the sequence
     * @return List of all sequences due to limitation
     */
    public static LinkedList<String> seqCreator(int len, double frequency){
        LinkedList<String> permutations = new LinkedList<>();
        int maxH =(int)Math.floor(frequency * len)-1;
        for(int i = 0; i<Math.pow(2,len);i++){
            String protein = "";
            int temp = i;
            int countH = 0;
            for (int j = 0; j < len; j++) {
                if (temp % 2 == 1) {
                    if (countH > maxH) {
                        protein = "";
                        break;
                    }
                    else{
                        protein = 'H' + protein;
                        countH++;
                    }
                }
                else
                    protein = 'P' + protein;
                temp = temp / 2;
            }
            if (protein.length() == len)
                permutations.add(protein);
        }
        return permutations;
    }

    // Print the protein folding. Currently unused but can be called any time for testing or for visual display.
    public static void printMatrix(int[][] matrix){
        System.out.println("Protein structure:\n");
        boolean notJustZero;
        for (int k = 0; k < matrix.length; k++){
            notJustZero = false;
            for (int m = 0; m < matrix[0].length; m++)
                if (matrix[k][m] != 0)
                    notJustZero = true;

            if(notJustZero){
                for (int m = 0; m < matrix[0].length; m++) {
                    if (matrix[k][m] == 0)
                        System.out.printf("%2s", "");
                    else
                        System.out.printf("%2d", matrix[k][m]);
                }
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }

    /**
     *
     * @param oldSeq folding sequence with 'N', 'E', 'S', 'W' directions
     * @return fixed sequence with 'f', 'l', 'r' directions
     */
    public static String fixDirections(StringBuilder oldSeq){
        String newSeq = "F";  // always start with forward
        for (int i = 0; i < oldSeq.length()-1; i++){
            if (oldSeq.charAt(i) == oldSeq.charAt(i+1))
                newSeq += 'f';
            else{
                String two = "" + oldSeq.charAt(i) + oldSeq.charAt(i+1);
                if (two.equals("NE") || two.equals("WN") || two.equals("ES") || two.equals("SW"))
                    newSeq += 'r';
                if (two.equals("NW") || two.equals("WS") || two.equals("EN") || two.equals("SE"))
                    newSeq += 'l';
            }
        }
        return newSeq;
    }

    /**
     * print the homologous list
     */
    public static void printList(LinkedList<Pair<Pair<String,String>, String>> linkedL) {
        for (int i = 0; i < linkedL.size(); i++)
            Main.writer.println("{" + linkedL.get(i).getLeft().getLeft()+ ", " + linkedL.get(i).getLeft().getRight() +  "} - " + linkedL.get(i).getRight());
    }

}
