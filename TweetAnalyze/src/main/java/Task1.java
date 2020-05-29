import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Task1 {

    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {
        private Text word = new Text();
        private Set<String> stopWordList = new HashSet<String>();
        private static final Locale TURKISH = Locale.forLanguageTag("tr");

        @SuppressWarnings("deprecation")
        protected void setup(Context context) throws java.io.IOException,
                InterruptedException {

            try {
                List<String> allLines = Files.readAllLines(Paths.get("stopwords/stopword"));
                for (String line : allLines) {
                    stopWordList.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // The tweet_text converted to lowercase according to Turkish language and extracted to URLs,
        // and extracted of words smaller than 2 characters.
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            String filename = fileSplit.getPath().getName();
            String[] tweet = value.toString().split("\t");

            String tweet_text = tweet[tweet.length-1].toLowerCase(TURKISH).replaceAll("https://[-a-zA-Z0-9çıöşüğ+&@#/%?=~_|!:,.;]*", " ").replaceAll("[^a-zA-Z0-9çıöşüğ\\t]", " ").replaceAll("\\b[\\wçıöşüğ]{1,2}\\b", " ");
            StringTokenizer tokenizer = new StringTokenizer(tweet_text);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (!stopWordList.contains(token)) {
                    word.set(token);
                    context.write(new Text(word + "\t" + filename), new IntWritable(1));
                }
            }
        }
    }

    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }


}
