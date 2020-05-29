public class WordCount {
    private String docName;
    private String word;
    private String frequency;
    private String N;
    public WordCount(String word,String frequency){
        this.word =word;
        this.frequency=frequency;
    }
    public WordCount(String docName,String frequency,String N){
        this.docName=docName;
        this.frequency=frequency;
        this.N =N;
    }

    public String getWord() {
        return word;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDocName() {
        return docName;
    }

    public String getN() {
        return N;
    }
}
