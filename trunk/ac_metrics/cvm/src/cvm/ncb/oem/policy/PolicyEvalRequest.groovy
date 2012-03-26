package cvm.ncb.oem.policy

import sb.base.ParameterBinding

class PolicyEvalRequest {
    String feature
    Collection<ParameterBinding> bindings
    String handlerImpl
}
