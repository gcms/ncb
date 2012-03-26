package cvm.ncb.oem.policy

import sb.base.ParameterBinding
import sb.base.Signal

class PolicyEvalPoint {
    Signal signal
    PolicyEvalRequest request
    Collection<ParameterBinding> bindings
}
