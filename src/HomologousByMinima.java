import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class finds homologous if the two proteins have exactly the same folding.
 */

public class HomologousByMinima implements Homologous {
    static LinkedList<Pair<Pair<String,String>, String>> homologous = new LinkedList<>();

    @Override
    public LinkedList<Pair<Pair<String,String>, String>> findHomologous(HashMap<Integer, Pair<String, LinkedList<String>>> seqScore) {
        for (int i = 1; i <= seqScore.size(); i++) {
            String seq1 = seqScore.get(i).getLeft();
            LinkedList<String> list1 = seqScore.get(i).getRight();
            for (int j = i + 1; j < seqScore.size(); j++) {
                String seq2 = seqScore.get(j).getLeft();
                LinkedList<String> list2 = seqScore.get(j).getRight();
                compareLists(seq1, seq2, list1, list2);
            }
        }
        return homologous;
    }

    public void compareLists(String seq1, String seq2, LinkedList<String> list1, LinkedList<String> list2) {
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    Pair<Pair<String,String>, String> h = new Pair<>((new Pair<>(seq1, seq2)),(list1.get(i)));
                    homologous.add(h);
                    return;
                }
            }
        }
    }
}