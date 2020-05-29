import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;
import java.util.List;

public class Task3 {
    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] inputs = value.toString().split("\t");

            //input[0] = preprocessed word, input[1] = document name, input[2] = word frequency in doc, input[3] = N
            WordFrequencyMap.addWordFrequency(inputs[0],inputs[1],inputs[2],inputs[3]);
            context.write(new Text(inputs[0]), new IntWritable(Integer.parseInt(inputs[2])));
        }
    }

    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            List<WordCount> list = WordFrequencyMap.getWordFrequency(key.toString());

            for( WordCount word:list){
                context.write(new Text(key+"\t"+word.getDocName()+"\t"+word.getFrequency()+"\t"+word.getN()),new IntWritable(sum));
            }
        }
    }
}
