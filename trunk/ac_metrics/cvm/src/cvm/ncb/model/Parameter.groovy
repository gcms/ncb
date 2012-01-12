package cvm.ncb.model

/**
 * User: Gustavo Sousa
 * Date: 20/12/11
 * Time: 22:43
 */
class Parameter {
    String name
    Class type

    String toString() {
        "$type.simpleName $name"
    }
}
