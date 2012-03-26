package cvm.ncb.oem.policy.repository.policy;

public class Policy implements Comparable {
    private Scope pScope;
    private Condition pCondition;
    private BusinessValue pbvalue;
    private Decision pDecision;
    private String pType;

    public boolean isEnabled;
    public String pName;

    public Policy(Scope scope, Condition condition, BusinessValue bvalue, Decision decision, String type, String name) {
        pScope = scope;
        pCondition = condition;
        pbvalue = bvalue;
        pDecision = decision;
        pType = type;
        pName = name;
    }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Scope getScope() {
        return pScope;
    }

    public Condition getCondition() {
        return pCondition;
    }

    public BusinessValue getBValue() {
        return pbvalue;
    }

    public Decision getDecision() {
        return pDecision;
    }

    public String getPolicyType() {
        return pType;
    }

    public void setName(String name) {
        pName = name;
    }

    public String getName() {
        return pName;
    }

//    public String toString() {
//        String str = "Outputing the object representation of the NCB Policy \n"
//                + "Scope of policy : " + pScope.getService() + " " + pScope.getOperation() + " " + pScope.getActive() + "\n"
//                + "Condition of policy : " + pCondition.getFeature() + " " + pCondition.getOperation() + " " + pCondition.getLiteral() + "\n"
//                + "Business Value of policy : " + pbvalue.getBusinessGroup() + " " + pbvalue.getValue() + "\n"
//                + "Decision of policy : " + pDecision.getParam() + " " + pDecision.getOperation() + " " + pDecision.getValue();
//
//        return str;
//    }

    public int compareTo(Object anotherPolicy) {
        int myIntVar = Integer.parseInt(this.getBValue().getValue());
        int theOtherIntVar = Integer.parseInt(((Policy) anotherPolicy).getBValue().getValue());

        if (myIntVar < theOtherIntVar)
            return 1;
        else if (myIntVar > theOtherIntVar)
            return -1;
        else
            return 0;
    }

}
