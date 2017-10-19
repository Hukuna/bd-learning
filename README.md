1、二次排序：首先按照第一字段排序,然后再对第一字段相同的行按照第二字段排序,注意不能破坏第一次排序的结果
  MapReduce实现要点：a.待排序的实体类需实现Writable, WritableComparable接口
                    b.map阶段实现Mapper接口
                    c.reduce阶段实现Reducer接口
                    d.在map和reduce中间可以插入group阶段对相同的键进行归约（GroupingComparator extends WritableComparator）
                    e.还可以自定义partition
