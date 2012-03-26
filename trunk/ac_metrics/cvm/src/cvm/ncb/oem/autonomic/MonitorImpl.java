package cvm.ncb.oem.autonomic;

import cvm.ncb.csm.Binder;
import cvm.ncb.csm.ConditionEvaluator;
import cvm.ncb.csm.EvaluationResult;
import cvm.ncb.csm.SignalLogger;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.ncb.oem.pe.actions.ManagerContext;
import sb.base.Binding;
import sb.base.Condition;
import sb.base.autonomic.Monitor;
import sb.base.autonomic.Symptom;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MonitorImpl implements SignalLogger {
    private Monitor monitor;
    private ManagerContext context;

    public MonitorImpl(Monitor monitor) {
        this.monitor = monitor;
    }

    private AnalyzerImpl analyzer;

    public void setAnalyzer(AnalyzerImpl analyzer) {
        this.analyzer = analyzer;
    }

    private Map<String, SignalInstance> state = new LinkedHashMap<String, SignalInstance>();

    public SignalInstance getSignalInstance(String name) {
        return state.get(name);
    }

    public void sense(SignalInstance signal) {
        state.put(signal.getName(), signal);

        for (Symptom symptom : monitor.getIdentifies()) {
            checkSymptom(symptom);
        }
    }

    public void checkSymptom(Symptom symptom) {
        Collection<EvaluationResult> results = evaluateConditions(symptom);
        for (EvaluationResult result : results) {
            enableSymptom(symptom, result);
        }
    }

    private Collection<EvaluationResult> evaluateConditions(Symptom symptom) {
        Collection<String> expressions = new LinkedHashSet<String>();
        Collection<Binding> bindings = symptom.getBindings();

        for (Condition condition : symptom.getConditions()) {
            expressions.add(condition.getExpression());
        }

        ConditionEvaluator evaluator = new ConditionEvaluator(new Binder(context, this));
        return evaluator.evaluate(expressions, bindings);
    }

    public synchronized void enableSymptom(Symptom symptom, EvaluationResult result) {
        analyzer.symptomDetected(new SymptomOccurrence(symptom, result.getParams()));
    }

    public void setContext(ManagerContext context) {
        this.context = context;
    }
}
