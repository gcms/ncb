package br.ufg.inf

import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.bean.MappingStrategy
import java.beans.PropertyDescriptor
import au.com.bytecode.opencsv.bean.CsvToBean
import au.com.bytecode.opencsv.bean.CsvToMap
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation
import org.apache.commons.math3.stat.descriptive.moment.Mean

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 24/08/12
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
class Parser {
    static boolean DEBUG = true

    public static void main(String[] args) {
        println getResult('standard', 'FrameworkChange') { it.method =~ /(cvm).*/ }
        println getResult('model', 'FrameworkChange') { it.method =~ /(cvm).*/ }
    }

    private static Map getResult(String impl, String test, Closure filter) {
        File dir = new File("D:/Documents and Settings/gustavosousa/Desktop/gustavo/ncb/dissertacao/res/profiler/$impl/")
        List files = dir.listFiles([accept: { it.name.startsWith("test$test") }] as FileFilter)
        getResult(files, filter) + [impl: impl, test: test]
    }

    private static Map getResult(List files, Closure filter) {
        List results = files.collect { File file -> getTotalTime(file, filter) }
        [runs: results.size(), media: getMedia(results as double[]), desvioPadrao: getDesvioPadrao(results as double[])]
    }

    private static double getTotalTime(File file, Closure filter) {
        getAll(file, filter).sum { it.time }
    }


    private static List<Map> getAll(File file, Closure filter) {
        getCsvToMap(file).readAll().collect {
            [method: it.method, time: it.time.replace(' ms', '').toBigDecimal()]
        }.findAll(filter).collect {
            if (DEBUG) println it
            it
        }
    }

    private static CsvToMap getCsvToMap(File file) {
        CsvToMap csv = new CsvToMap(new CSVReader(new FileReader(file), (char) ",", (char) '"'))
        csv.headers = ['method', 'percent', 'time', 'invocation']
        csv
    }

    private static double getMedia(double[] values) {
        new Mean().evaluate(values)
    }

    private static BigDecimal getDesvioPadrao(double[] values) {
        new StandardDeviation().evaluate(values)
    }
}