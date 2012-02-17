package cvm.ncb.oem.pe;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 23/01/12
 * Time: 02:28
 * To change this template use File | Settings | File Templates.
 */
public class Monitor {
    private Analyzer analyzer;
    private Map<String, Map<String, Object>> state = new LinkedHashMap<String, Map<String, Object>>();

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void sense(SignalInstance signal) {

    }
}
