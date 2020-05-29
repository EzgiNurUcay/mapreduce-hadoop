import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;
import java.util.List;

public class Task2 {

    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] inputs = value.toString().split("\t");

            //input[0] = preprocessed word, input[1] = document name, input[2] = word frequency in doc
            WordCountMap.addWordCount(inputs[1],inputs[0],inputs[2]);
            context.write(new Text(inputs[1]), new IntWritable(Integer.parseInt(inputs[2])));
        }
    }

    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            List<WordCount> list = WordCountMap.getWordCount(key.toString());

            for( WordCount word:list){

                context.write(new Text(word.getWord()+"\t"+key+"\t"+word.getFrequency()),new IntWritable(sum));
            }
        }
    }
}
