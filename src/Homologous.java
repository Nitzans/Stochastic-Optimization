import java.util.HashMap;
import java.util.LinkedList;

public interface Homologous {
    /**
     * Interface with only one method "findHomologous", which can be implemented in any way you chose.
     * @param structures Hash map when key = protein index, value = pair of sequence and folding sequences.
     * @return LinkedList of pairs when in each pair: left = pair of homologous, right = folding sequence.
     */
    LinkedList<Pair<Pair<String,String>, String>> findHomologous(HashMap<Integer, Pair<String, LinkedList<String>>> structures);

}
