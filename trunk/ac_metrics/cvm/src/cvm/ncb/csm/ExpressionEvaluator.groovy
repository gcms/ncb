package cvm.ncb.csm

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 23/01/12
 * Time: 01:56
 * To change this template use File | Settings | File Templates.
 */
class ExpressionEvaluator {
    GroovyShell shell

    public ExpressionEvaluator(Map context) {
        shell = new GroovyShell(new Binding(context))
    }
    public Object evaluate(String expression) {
        shell.evaluate(expression)
    }
}
