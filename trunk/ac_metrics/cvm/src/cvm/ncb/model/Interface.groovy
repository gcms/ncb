package cvm.ncb.model

 /**
 * User: Gustavo Sousa
 * Date: 20/12/11
 * Time: 22:40
 */
class Interface {
    Collection<Method> methods

    static Interface createFromClass(Class iface) {
        new Interface(methods: getMethods(iface))
    }

    private static List<Method> getMethods(Class iface) {
        iface.methods.findAll { java.lang.reflect.Method method ->
            method.isAnnotationPresent(cvm.ncb.adapters.Method)
        }.collect { java.lang.reflect.Method method ->
            def annotation = method.getAnnotation(cvm.ncb.adapters.Method)
            new Method(name: annotation.name(), returnType: method.getReturnType(), parameters: getParameters(method, annotation))
        }
    }

    static List<Parameter> getParameters(java.lang.reflect.Method method, cvm.ncb.adapters.Method annotation) {
        int idx = 0
        annotation.parameters().collect { String name ->
            new Parameter(name: name, type: method.getParameterTypes()[idx++])
        }
    }
}
