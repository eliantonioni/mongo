package com.luxoft.dao;

import com.luxoft.model.MRResult;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.sun.beans.decoder.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Repository;

/**
 * Created by aeliseev on 14.04.2014
 */
@Repository
public class MRDao {

    @Autowired
    private MongoTemplate mongoOperations;

    public MapReduceResults<MRResult> doMR() {
        MapReduceResults<MRResult> results = mongoOperations.mapReduce(
                "mr_test", "classpath:js/map.js", "classpath:js/reduce.js", MRResult.class
        );

        return results;
    }

    public MapReduceOutput doMR2() {

        DBCollection coll = mongoOperations.getCollection("mr_test");

        String map = "function () { " +
                "    for (var i = 0; i < this.x.length; i++) { " +
                "        emit(this.x[i], 1); " +
                "    } " +
                "}";
        String reduce = "function (key, values) { " +
                "    var sum = 0; " +
                "    for (var i = 0; i < values.length; i++) " +
                "        sum += values[i]; " +
                "    return sum;" +
                "}";

        MapReduceCommand cmd = new MapReduceCommand(
                coll, map, reduce, null, MapReduceCommand.OutputType.INLINE, null
        );
        return coll.mapReduce(cmd);
    }
}
