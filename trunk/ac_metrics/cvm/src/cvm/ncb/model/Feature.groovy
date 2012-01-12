package cvm.ncb.model

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 03/01/12
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
class Feature {
    String name
    List<Feature> children = []
    List<Attribute> attributes = []
}
