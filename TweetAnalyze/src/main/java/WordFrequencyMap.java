import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencyMap {
    // Map key is preprocessed word.
    private static Map<String, List<WordCount>> wordFrequencyMap;
    static{
        wordFrequencyMap= new HashMap<>();
    }
    public static void addWordFrequency(String word,String docName,String frequency,String N){
        List<WordCount> list;
        if (wordFrequencyMap.get(word) == null) {
            list = new ArrayList<>();
        }else{
            list = wordFrequencyMap.get(word);
        }
        list.add(new WordCount(docName, frequency,N));
        wordFrequencyMap.put(word, list);


    }
    public static List<WordCount> getWordFrequency(String word) {
        return wordFrequencyMap.get(word);

    }
}
