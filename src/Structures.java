import java.util.LinkedList;
/**
 * This class create all structures.
 * It contains methods which folds the protein to any possible shape and compute number of bonds.
 */

public class Structures {

    /**
     * Recursive function which receives sequence and return pair of the best score and a list of structures that have this score
     */
    public static Pair<Integer,LinkedList<String>> folding(String seq, StringBuilder result, int[][] matrix, int y, int x, int i, int isFirst, Pair<Integer, LinkedList<String>> bestResults) {
        matrix[y][x] = i;
        Main.locations[i-1].set(x,y);
        String fixResult = "";
        if (i == Main.PROTEIN_LENGTH) {
            fixResult = HelperFunctions.fixDirections(result);
            int score = bondsCounter(matrix, seq, Main.locations);

            if (score == bestResults.getLeft())
                bestResults.getRight().add(fixResult);
            else if (score > bestResults.getLeft()) {
                bestResults.getRight().clear();
                bestResults.set(score, bestResults.getRight());
                bestResults.getRight().add(fixResult);
            }
        }
        else {
            if (inBounds(x+1, y, matrix) && matrix[y][x+1] == 0) {              // East
                folding(seq, result.append("E"), matrix, y, x + 1, i + 1, isFirst, bestResults);
                matrix[y][x + 1] = 0;
                result.deleteCharAt(result.length()-1);
            }

            if (inBounds(x, y-1, matrix) && matrix[y-1][x] == 0) {// North
                isFirst += 1;
                folding(seq, result.append("N"), matrix, y - 1, x, i + 1, isFirst, bestResults);
                matrix[y - 1][x] = 0;
                result.deleteCharAt(result.length()-1);
            }

            if (inBounds(x, y+1, matrix) && matrix[y+1][x] == 0 && isFirst >= 2) {  // South
                folding(seq, result.append("S"), matrix, y + 1, x, i + 1, isFirst, bestResults);
                matrix[y + 1][x] = 0;
                result.deleteCharAt(result.length()-1);
            }

            if (inBounds(x-1, y, matrix) && matrix[y][x-1] == 0) {              // West
                folding(seq, result.append("W"), matrix, y, x-1, i + 1, isFirst, bestResults);
                matrix[y][x-1] = 0;
                result.deleteCharAt(result.length()-1);
            }
        }
        return bestResults;
    }


    public static boolean inBounds(int x, int y, int[][] matrix){
        if (x >= 0 && y >= 0 && x < matrix[0].length && y < matrix.length)
            return true;
        return false;
    }

    public static int bondsCounter(int[][] matrix, String seq, Pair<Integer, Integer>[] locations){
        int[] justH = new int[Main.PROTEIN_LENGTH];
        int count = 0;
        for(int i = 0; i<Main.PROTEIN_LENGTH; i++){
            if (seq.charAt(i) == 'H')
                justH[i] = 1;
            else
                justH[i] = 0;
        }

        for (int i=0; i<locations.length; i++){
            int x = locations[i].getLeft();
            int y = locations[i].getRight();
            if (justH[i] == 1){
                if(inBounds(x, y+1, matrix) &&                                           // In matrix bounds
                matrix[y+1][x] != 0 && justH[matrix[y+1][x]-1] == 1  &&                     // There is H neighbor
                matrix[y+1][x] != matrix[y][x] + 1 && matrix[y+1][x] != matrix[y][x] - 1)   // Not consecutive
                    count++;
                if(inBounds(x+1, y, matrix) &&                                           // In matrix bounds
                matrix[y][x+1] != 0 && justH[(matrix[y][x+1])-1] == 1  &&                   // There is H neighbor
                matrix[y][x+1] != matrix[y][x] + 1 && matrix[y][x+1] != matrix[y][x] - 1)   // Not consecutive
                    count++;
                if(inBounds(x, y-1, matrix) &&                                            // In matrix bounds
                matrix[y-1][x] != 0 && justH[matrix[y-1][x]-1] == 1  &&                      // There is H neighbor
                matrix[y-1][x] != matrix[y][x] + 1 && matrix[y-1][x] != matrix[y][x] - 1)    // Not consecutive
                    count++;
                if(inBounds(x-1, y, matrix) &&                                           // In matrix bounds
                matrix[y][x-1] != 0 && justH[matrix[y][x-1]-1] == 1  &&                     // There is H neighbor
                matrix[y][x-1] != matrix[y][x] + 1 && matrix[y][x-1] != matrix[y][x] - 1)   // Not consecutive
                    count++;
            }
        }
        return count/2;  // Because every bond counts twice
    }

}
