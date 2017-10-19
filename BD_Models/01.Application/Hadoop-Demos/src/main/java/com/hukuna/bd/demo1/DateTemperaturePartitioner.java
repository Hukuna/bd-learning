package com.hukuna.bd.demo1;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by HUKUNA on 2017/10/19.
 * 自定义分区类：通过自然键对数据进行分区，分区算法基于hashcode
 * 在hadoop处理流程中，分区过程在mapper之后，reduce之前
 *
 */
public class DateTemperaturePartitioner extends Partitioner<DateTemperaturePair, Text> {

    @Override
    public int getPartition(DateTemperaturePair pair, Text text, int numberOfPartitions) {
    	// 保证分区数不为负
        return Math.abs(pair.getYearMonth().hashCode() % numberOfPartitions);
    }
}
