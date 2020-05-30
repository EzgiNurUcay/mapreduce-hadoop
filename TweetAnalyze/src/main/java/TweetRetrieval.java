import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

//The main goal of the TweetRetrieval is calculate the Term Frequency (TF) and Inverse Document Frequency (IDF).
public class TweetRetrieval extends Configured implements Tool {
    // To store job result.
    static int jobResult = -1;

    public static void main(String[] args) throws Exception {

        int exitFlag = ToolRunner.run(new TweetRetrieval(), args);
        System.exit(exitFlag);
    }

    @Override
    public int run(String[] args) throws Exception {

        try {
            // Task-1 provides to calculate the frequency of a word in a document which is n parameter.
            // The inputs are files with the txt extension under the input folder.
            // The output of the Task-1 is like word+\t+docName+\t+n in output/task1/part-r-00000
            Job job1 = Job.getInstance(getConf(), "Tweet Analyze Task-1:");
            job1.setJarByClass(getClass());
            job1.setOutputKeyClass(Text.class);
            job1.setOutputValueClass(IntWritable.class);
            job1.setMapperClass(Task1.Mapper.class);
            job1.setReducerClass(Task1.Reducer.class);
            job1.setInputFormatClass(TextInputFormat.class);
            job1.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job1, new Path(args[0]));
            FileOutputFormat.setOutputPath(job1, new Path(args[1] + "/task1"));
            jobResult = job1.waitForCompletion(true) ? 0 : 1;
            if (jobResult != 0)
                throw new Error("Task-1 Failed");

            // Task-2 provides to calculate the total word count of a document which is N parameter.
            // The input is under the output/task1.
            // The output of the Task-2 is like word+\t+docName+\t+n+\t+N in output/task2/part-r-00000
            Job job2 = Job.getInstance(getConf(), "Tweet Analyze Task-2:");
            job2.setJarByClass(getClass());
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(IntWritable.class);
            job2.setMapperClass(Task2.Mapper.class);
            job2.setReducerClass(Task2.Reducer.class);
            job2.setInputFormatClass(TextInputFormat.class);
            job2.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job2, new Path(args[1] + "/task1"));
            FileOutputFormat.setOutputPath(job2, new Path(args[1] + "/task2"));

            jobResult = job2.waitForCompletion(true) ? 0 : 1;
            if (jobResult != 0)
                throw new Error("Task-2 Failed");

            // Task-3 provides to calculate the total count of a word in all documents which is m parameter.
            // The input is under the output/task2.
            // The output of the Task-2 is like word+\t+docName+\t+n+\t+N+\t+m in output/task3/part-r-00000
            Job job3 = Job.getInstance(getConf(), "Tweet Analyze Task-3:");
            job3.setJarByClass(getClass());
            job3.setOutputKeyClass(Text.class);
            job3.setOutputValueClass(IntWritable.class);
            job3.setMapperClass(Task3.Mapper.class);
            job3.setReducerClass(Task3.Reducer.class);
            job3.setInputFormatClass(TextInputFormat.class);
            job3.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job3, new Path(args[1] + "/task2"));
            FileOutputFormat.setOutputPath(job3, new Path(args[1] + "/task3"));

            jobResult = job3.waitForCompletion(true) ? 0 : 1;
            if (jobResult != 0)
                throw new Error("Task-3 Failed");

            // Task-4 provides to calculate TF*IDF result.
            // The input is under the output/task3.
            // The output of the Task-2 is like word+\t+docName+\t+TFIDF in output/task4/part-r-00000
            Job job4 = Job.getInstance(getConf(), "Tweet Analyze Task-4:");
            job4.setJarByClass(getClass());
            job4.setOutputKeyClass(Text.class);
            job4.setOutputValueClass(LongWritable.class);
            job4.setMapperClass(Task4.Mapper.class);
            job4.setReducerClass(Task4.Reducer.class);
            job4.setInputFormatClass(TextInputFormat.class);
            job4.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job4, new Path(args[1] + "/task3"));
            FileOutputFormat.setOutputPath(job4, new Path(args[1] + "/task4"));

            jobResult = job4.waitForCompletion(true) ? 0 : 1;

            if (jobResult != 0)
                throw new Error("Task-4 Failed");

            System.out.println("All tasks completed.");
            return 1;

        } catch (Exception e) {
            System.out.println(e);
            return 0;

        }
    }

}