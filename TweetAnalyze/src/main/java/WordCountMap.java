import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCountMap {
    // Map key is document name.
    private static Map<String, List<WordCount>> wordCountMap;

    static {
        wordCountMap = new HashMap<>();
    }

    public static void addWordCount(String docName, String word, String frequency) {
        List<WordCount> list;
        if (wordCountMap.get(docName) == null) {
            list = new ArrayList<>();
        } else {
            list = wordCountMap.get(docName);
        }
        list.add(new WordCount(word, frequency));
        wordCountMap.put(docName, list);

    }

    public static List<WordCount> getWordCount(String docName) {
        return wordCountMap.get(docName);

    }
}
