package cvm.ncb.oem.autonomic;

import cvm.sb.emf.ValueEvaluator;
import sb.base.autonomic.Analyzer;
import sb.base.autonomic.ChangeRequest;

import java.util.Map;

public class AnalyzerImpl {
    private Analyzer analyzer;

    public AnalyzerImpl(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    private PlannerImpl planner;
    public void setPlanner(PlannerImpl planner) {
        this.planner = planner;
    }

    public void symptomDetected(SymptomOccurrence symptom) {
        for (ChangeRequest request : analyzer.getRequests()) {
            if (request.getBasedOn().equals(symptom.getSymptom())) {
                requestChange(symptom, request);
            }
        }
    }

    private void requestChange(SymptomOccurrence symptom, ChangeRequest request) {
        planner.change(new ChangeRequestInstance(request, symptom));
    }

}
