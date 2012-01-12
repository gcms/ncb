package cvm.ncb.model

import util.StringUtils

/**
 * User: Gustavo Sousa
 * Date: 20/12/11
 * Time: 22:41
 */
class Method implements Message {
    String name
    Class returnType
    Collection<Parameter> parameters

    String toString() {
        "$returnType.simpleName ${StringUtils.methodToString(name, parameters)}"
    }
}
