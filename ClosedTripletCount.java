import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class ClosedTripletCount extends Configured implements Tool {
    public static class ParseNodesMapper extends Mapper<LongWritable, Text, LongWritable, LongWritable> {
        public void map(LongWritable k, Text text, Context context) throws IOException, InterruptedException {
            String[] pair = text.toString().split("\\s+");
            if (pair.length > 1) { // if edge is valid
                long u = Long.parseLong(pair[0]);
                long v = Long.parseLong(pair[1]);

                if (u < v) {
                    context.write(new LongWritable(u), new LongWritable(v));
                } else {
                    context.write(new LongWritable(v), new LongWritable(u));
                }
                System.out.println(u + " " + v);
            }
        }
    }
}