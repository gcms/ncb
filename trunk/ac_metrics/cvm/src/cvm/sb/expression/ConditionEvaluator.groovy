package cvm.sb.expression

import sb.base.common.Binding

class ConditionEvaluator {
    ContextBinder binder

    public ConditionEvaluator(ContextBinder binder) {
        this.binder = binder;
    }

    public Collection<EvaluationResult> evaluate(String expression, Collection<Binding> bindings) {
        def context = binder.getBindings(bindings)
        CombinationEvaluator.evaluate(expression, context).findAll { it.isTrue() }
    }

    public Collection<EvaluationResult> evaluate(Collection<String> expressions, Collection<Binding> bindings) {
        Collection<EvaluationResult> result

        for (String expression : expressions) {
            Collection<EvaluationResult> evalResult = evaluate(expression, bindings)
            if (result == null)
                result = new LinkedHashSet<EvaluationResult>(evalResult)

            result.retainAll(evalResult)
        }

        result
    }

//    static void main(String[] args) {
//        Map ctx = [
//                a: [5, 4],
//                b: [7, 11],
//                c: [1, 2]
//        ]
//
//        List result = evaluate("a * b == 56 - c", ctx)
//        result.each { println it.dump() }
//
//    }
}