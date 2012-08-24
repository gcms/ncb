package au.com.bytecode.opencsv.bean

import au.com.bytecode.opencsv.CSVReader

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 24/08/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
class CsvToMap {
    public static List<Map> parse(Reader reader) {
        parse(new CSVReader(reader))
    }

    public static List<Map> parse(CSVReader reader) {
        new CsvToMap(reader).readAll()
    }

    List headers

    private CSVReader reader

    public CsvToMap(CSVReader reader) {
        headers = reader.readNext()
        this.reader = reader
    }

    public Map readNext() {
        parseLine(reader.readNext())
    }

    public List<Map> readAll() {
        reader.readAll().collect { parseLine(it) }
    }

    private Map parseLine(String[] line) {
        Map result = [:]
        line.eachWithIndex { value, col ->
            result[getHeader(col)] = value
        }
        result
    }

    private String getHeader(int col) {
        headers[col]
    }
}
