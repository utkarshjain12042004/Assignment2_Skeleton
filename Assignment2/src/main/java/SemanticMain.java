import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;
    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }
    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }
    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();
        //TODO Task 6.1
        for (int i = 0; i <= listVocabulary.size()-1; i++){
            if (!STOPWORDS.contains(listVocabulary.get(i))){ listResult.add(new Glove(listVocabulary.get(i), new Vector(listVectors.get(i)))); }
        }
        return listResult;
    }
    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.2
        listGlove = CreateGloveList();
        boolean contains = false;
        int index = -1;
        for (int i = 0; i <= listGlove.size()-1; i++){
            if (listGlove.get(i).getVocabulary().equals(_word)) {
                contains = true;
                index = i;
                break;
            }
            else if (listGlove.get(i).getVocabulary().equals("error")){ index = i; }
        }
        if (!contains){ _word = "error"; }
        for (Glove glove : listGlove){
            if (!_word.equals(glove.getVocabulary())) {
                listCosineSimilarity.add(new CosSimilarityPair(_word, glove.getVocabulary(),
                        listGlove.get(index).getVector().cosineSimilarity(glove.getVector())));
            }
        }
        return HeapSort.doHeapSort(listCosineSimilarity);
    }
    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.3
        for (int i = 0; i <= listGlove.size()-1; i++){
            if (!_vector.equals(listGlove.get(i).getVector())){
                listCosineSimilarity.add(new CosSimilarityPair(_vector, listGlove.get(i).getVocabulary(),
                        _vector.cosineSimilarity(listGlove.get(i).getVector())));
            }
        }
        return HeapSort.doHeapSort(listCosineSimilarity);
    }
    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        //TODO Task 6.4
        listGlove = CreateGloveList();
        List<CosSimilarityPair> nearest = new ArrayList<>();
        ArrayList<String> vocabulary = new ArrayList<>();
        for (Glove glove : listGlove){
            vocabulary.add(glove.getVocabulary());
        }
        if (vocabulary.contains(_firTORef) && vocabulary.contains(_firISRef) && vocabulary.contains(_secISRef)){
            nearest = (WordsNearest(listGlove.get(vocabulary.indexOf(_secISRef)).getVector())).subList(0, _top);
        }
        for (CosSimilarityPair csp : nearest){
            if (!csp.getWord2().equals(_firISRef) || !csp.getWord2().equals(_firTORef)){
                listResult.add(csp);
            }
        }
        return listResult;
    }
}