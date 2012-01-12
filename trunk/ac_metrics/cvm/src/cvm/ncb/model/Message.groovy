package cvm.ncb.model

/**
 * User: Gustavo Sousa
 * Date: 20/12/11
 * Time: 22:41
 */
interface Message {
    String getName()
    Collection<Parameter> getParameters()
    // getParameters().unique { it.name }  == getParameters()
}
