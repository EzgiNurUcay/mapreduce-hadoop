import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Task4 {
    // D is defined according to document count.
    public static final int D = 19;

    //In Task-4, the TF*IDF is calculated using Task-3 output.
    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, LongWritable> {

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] inputs = value.toString().split("\t");
            //input[0] = preprocessed word, input[1] = document name, input[2] = n, input[3] = N, input[4]=m
            long TF_IDF = (long) (Integer.parseInt(inputs[2]) * 1.0 / Integer.parseInt(inputs[3]) * Math.log10(D / Integer.parseInt(inputs[4])));
            context.write(new Text(inputs[0] + "\t" + inputs[1]), new LongWritable(TF_IDF));
        }
    }

    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, LongWritable, Text, LongWritable> {

        public void reduce(Text key, Iterable<LongWritable> values,
                           Context context) throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable val : values) {
                sum += val.get();
            }
            context.write(key, new LongWritable(sum));
        }
    }
}
