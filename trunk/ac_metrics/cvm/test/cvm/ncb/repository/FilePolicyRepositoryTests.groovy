package cvm.ncb.repository

import cvm.ncb.repository.loader.FilePolicyLoader
import cvm.ncb.repository.loader.GlobalConstant
import cvm.ncb.repository.policy.PolicyRepository

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 20/12/11
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
class FilePolicyRepositoryTests extends GroovyTestCase {
    PolicyRepository policyRepository

    void setUp() {
        def path = getClass().getResource("/cvm/ncb/repository/examples").toURI()
        policyRepository = new FilePolicyRepository(FilePolicyLoader.createInstance(path))
    }

    void testeLookupRequest() {
        checkPolicy(GlobalConstant.RequestedType.Audio, GlobalConstant.OperationType.request, [
                'selectComm_Audio_01', 'selectComm_Audio_02'
        ])

        checkPolicy(GlobalConstant.RequestedType.Payment, GlobalConstant.OperationType.request, [
                'selectComm_Payment_01'
        ])

        checkPolicy(GlobalConstant.RequestedType.Video, GlobalConstant.OperationType.request, [
                'selectComm_Video_01', 'selectComm_Video_02', 'selectComm_Video_03'
        ])

        checkPolicy(GlobalConstant.RequestedType.Audio, GlobalConstant.OperationType.selection, [])
        checkPolicy(GlobalConstant.RequestedType.Payment, GlobalConstant.OperationType.selection, [])
        checkPolicy(GlobalConstant.RequestedType.Video, GlobalConstant.OperationType.selection, [])
    }

    void checkPolicy(GlobalConstant.RequestedType request, GlobalConstant.OperationType operation, List policiesNames) {
        def policies = policyRepository.load(request, operation)
        assertEquals policiesNames.size(), policies.size()

        policiesNames.each { String name ->
            assertNotNull policies.find { it.getName() == name }
        }
    }
}
