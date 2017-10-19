package com.hukuna.bd.demo1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by HUKUNA on 2017/10/19.
 * reduce过程
 */
public class SecondarySortReducer extends Reducer<DateTemperaturePair, Text, Text, Text> {

    @Override
	/**
	 * 由于在DateTemperatureGroupingComparator阶段对键相同的值进行了group，所以这里传入的values是group完之后值的list
	 */
    protected void reduce(DateTemperaturePair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    	StringBuilder builder = new StringBuilder();
    	for (Text value : values) {
    		builder.append(value.toString());
    		builder.append(",");
		}
        context.write(key.getYearMonth(), new Text(builder.toString()));
    }
}
