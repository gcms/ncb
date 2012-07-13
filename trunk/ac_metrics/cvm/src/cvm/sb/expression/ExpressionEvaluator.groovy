package cvm.sb.expression

class ExpressionEvaluator {
    GroovyShell shell

    public ExpressionEvaluator(Map context) {
        shell = new GroovyShell(new Binding(context))
    }

    public Object evaluate(String expression) {
        shell.evaluate(expression)
    }
}

