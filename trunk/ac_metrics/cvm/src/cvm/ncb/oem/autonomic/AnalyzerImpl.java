package cvm.ncb.oem.autonomic;

import sb.base.autonomic.ChangeRequest;

import java.util.Collection;

public class AnalyzerImpl {
    private Collection<ChangeRequest> requests;

    public AnalyzerImpl(Collection<ChangeRequest> requests) {
        this.requests = requests;
    }

    private PlannerImpl planner;
    public void setPlanner(PlannerImpl planner) {
        this.planner = planner;
    }

    public void symptomDetected(SymptomOccurrence symptom) {
        for (ChangeRequest request : requests) {
            if (request.getBasedOn().equals(symptom.getSymptom())) {
                requestChange(symptom, request);
            }
        }
    }

    private void requestChange(SymptomOccurrence symptom, ChangeRequest request) {
        planner.change(new ChangeRequestInstance(request, symptom));
    }

}
