package cvm.sb.expression

class CombinationEvaluator {
    public static Collection<EvaluationResult> evaluate(String expression, Map<String, Collection<ContextProvider>> context) {
        evaluate(expression, [:], context.entrySet() as List)
    }

    public static Collection<EvaluationResult> evaluate(String expression, Map context, List<Map.Entry> others) {
        if (others.empty)
            return [evaluateExpression(expression, context)]

        Map.Entry entry = others.first()
        Collection values = entry.value

        values.collect { ContextProvider value ->
            Map ctx = new LinkedHashMap(context)
            ctx.put(entry.key, value)
            evaluate(expression, ctx, others - entry)
        }.flatten()
    }

    public static EvaluationResult evaluateExpression(String expression, Map context) {
        Object result = new GroovyShell(new Binding(ContextProviderWrapper.wrap(context))).evaluate(expression)
        new EvaluationResult(result: result, params: context)
    }


}
