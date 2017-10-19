package com.hukuna.bd.demo1;

import org.apache.log4j.Logger;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by HUKUNA on 2017/10/19.
 * 提交任务
 */
public class SecondarySortDriver  extends Configured implements Tool {

	private static Logger theLogger = Logger.getLogger(SecondarySortDriver.class);

        @Override
	public int run(String[] args) throws Exception {

    	Configuration conf = getConf();
		Job job = new Job(conf);
		job.setJarByClass(SecondarySortDriver.class);
		job.setJobName("SecondarySortDriver");
		
    		// args[0] = input directory
    		// args[1] = output directory
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setOutputKeyClass(DateTemperaturePair.class);
		job.setOutputValueClass(Text.class);
		
        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReducer.class);
        //Hadoop采用插件式配置分区
        job.setPartitionerClass(DateTemperaturePartitioner.class);
        job.setGroupingComparatorClass(DateTemperatureGroupingComparator.class);

		boolean status = job.waitForCompletion(true);
		theLogger.info("run(): status="+status);
		return status ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			theLogger.warn("SecondarySortDriver <input-dir> <output-dir>");
			throw new IllegalArgumentException("SecondarySortDriver <input-dir> <output-dir>");
		}

		//String inputDir = args[0];
		//String outputDir = args[1];
		int returnStatus = submitJob(args);
		theLogger.info("returnStatus="+returnStatus);
		
		System.exit(returnStatus);
	}

	public static int submitJob(String[] args) throws Exception {
		int returnStatus = ToolRunner.run(new SecondarySortDriver(), args);
		return returnStatus;
	}
}



