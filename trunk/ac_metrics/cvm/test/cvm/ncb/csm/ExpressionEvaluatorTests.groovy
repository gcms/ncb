package cvm.ncb.csm

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 23/01/12
 * Time: 02:00
 * To change this template use File | Settings | File Templates.
 */
class ExpressionEvaluatorTests extends GroovyTestCase {
    public void testExpression() {
        def expressionEvaluator = new ExpressionEvaluator([a: 10, b: 20])
        assertEquals 30, expressionEvaluator.evaluate("a + b")
    }
}
