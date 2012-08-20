/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see sb.base.policy.PolicyFactory
 * @model kind="package"
 * @generated
 */
public interface PolicyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "policy";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.inf.ufg.br/mestrado/sb/base/policy";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sb.base.policy";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PolicyPackage eINSTANCE = sb.base.policy.impl.PolicyPackageImpl.init();

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyEvaluationPointImpl <em>Evaluation Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyEvaluationPointImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationPoint()
	 * @generated
	 */
	int POLICY_EVALUATION_POINT = 0;

	/**
	 * The feature id for the '<em><b>Signal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_POINT__SIGNAL = 0;

	/**
	 * The feature id for the '<em><b>Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_POINT__REQUEST = 1;

	/**
	 * The number of structural features of the '<em>Evaluation Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_POINT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyEvaluationRequestImpl <em>Evaluation Request</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyEvaluationRequestImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationRequest()
	 * @generated
	 */
	int POLICY_EVALUATION_REQUEST = 1;

	/**
	 * The feature id for the '<em><b>Handler</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_REQUEST__HANDLER = 0;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_REQUEST__BINDINGS = 1;

	/**
	 * The number of structural features of the '<em>Evaluation Request</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_REQUEST_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyEvaluationHandlerImpl <em>Evaluation Handler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyEvaluationHandlerImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationHandler()
	 * @generated
	 */
	int POLICY_EVALUATION_HANDLER = 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_HANDLER__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Handler Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_HANDLER__HANDLER_IMPL = 1;

	/**
	 * The feature id for the '<em><b>Evaluation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_HANDLER__EVALUATION = 2;

	/**
	 * The number of structural features of the '<em>Evaluation Handler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_HANDLER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyEvaluationContextImpl <em>Evaluation Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyEvaluationContextImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationContext()
	 * @generated
	 */
	int POLICY_EVALUATION_CONTEXT = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_CONTEXT__PARAMETERS = 0;

	/**
	 * The number of structural features of the '<em>Evaluation Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_CONTEXT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyEvaluationImpl <em>Evaluation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyEvaluationImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluation()
	 * @generated
	 */
	int POLICY_EVALUATION = 4;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Context Binding</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION__CONTEXT_BINDING = 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION__CONTEXT = 2;

	/**
	 * The number of structural features of the '<em>Evaluation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_EVALUATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyImpl <em>Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicy()
	 * @generated
	 */
	int POLICY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Business Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY__BUSINESS_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY__FEATURE = 2;

	/**
	 * The feature id for the '<em><b>Decision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY__DECISION = 3;

	/**
	 * The number of structural features of the '<em>Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link sb.base.policy.impl.PolicyDecisionImpl <em>Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.policy.impl.PolicyDecisionImpl
	 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyDecision()
	 * @generated
	 */
	int POLICY_DECISION = 6;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_DECISION__PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_DECISION__OPERATION = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_DECISION__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_DECISION_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyEvaluationPoint <em>Evaluation Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evaluation Point</em>'.
	 * @see sb.base.policy.PolicyEvaluationPoint
	 * @generated
	 */
	EClass getPolicyEvaluationPoint();

	/**
	 * Returns the meta object for the reference '{@link sb.base.policy.PolicyEvaluationPoint#getSignal <em>Signal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signal</em>'.
	 * @see sb.base.policy.PolicyEvaluationPoint#getSignal()
	 * @see #getPolicyEvaluationPoint()
	 * @generated
	 */
	EReference getPolicyEvaluationPoint_Signal();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.policy.PolicyEvaluationPoint#getRequest <em>Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Request</em>'.
	 * @see sb.base.policy.PolicyEvaluationPoint#getRequest()
	 * @see #getPolicyEvaluationPoint()
	 * @generated
	 */
	EReference getPolicyEvaluationPoint_Request();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyEvaluationRequest <em>Evaluation Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evaluation Request</em>'.
	 * @see sb.base.policy.PolicyEvaluationRequest
	 * @generated
	 */
	EClass getPolicyEvaluationRequest();

	/**
	 * Returns the meta object for the reference '{@link sb.base.policy.PolicyEvaluationRequest#getHandler <em>Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Handler</em>'.
	 * @see sb.base.policy.PolicyEvaluationRequest#getHandler()
	 * @see #getPolicyEvaluationRequest()
	 * @generated
	 */
	EReference getPolicyEvaluationRequest_Handler();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.policy.PolicyEvaluationRequest#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see sb.base.policy.PolicyEvaluationRequest#getBindings()
	 * @see #getPolicyEvaluationRequest()
	 * @generated
	 */
	EReference getPolicyEvaluationRequest_Bindings();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyEvaluationHandler <em>Evaluation Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evaluation Handler</em>'.
	 * @see sb.base.policy.PolicyEvaluationHandler
	 * @generated
	 */
	EClass getPolicyEvaluationHandler();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.policy.PolicyEvaluationHandler#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see sb.base.policy.PolicyEvaluationHandler#getParameters()
	 * @see #getPolicyEvaluationHandler()
	 * @generated
	 */
	EReference getPolicyEvaluationHandler_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.PolicyEvaluationHandler#getHandlerImpl <em>Handler Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Handler Impl</em>'.
	 * @see sb.base.policy.PolicyEvaluationHandler#getHandlerImpl()
	 * @see #getPolicyEvaluationHandler()
	 * @generated
	 */
	EAttribute getPolicyEvaluationHandler_HandlerImpl();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.policy.PolicyEvaluationHandler#getEvaluation <em>Evaluation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evaluation</em>'.
	 * @see sb.base.policy.PolicyEvaluationHandler#getEvaluation()
	 * @see #getPolicyEvaluationHandler()
	 * @generated
	 */
	EReference getPolicyEvaluationHandler_Evaluation();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyEvaluationContext <em>Evaluation Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evaluation Context</em>'.
	 * @see sb.base.policy.PolicyEvaluationContext
	 * @generated
	 */
	EClass getPolicyEvaluationContext();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.policy.PolicyEvaluationContext#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see sb.base.policy.PolicyEvaluationContext#getParameters()
	 * @see #getPolicyEvaluationContext()
	 * @generated
	 */
	EReference getPolicyEvaluationContext_Parameters();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyEvaluation <em>Evaluation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evaluation</em>'.
	 * @see sb.base.policy.PolicyEvaluation
	 * @generated
	 */
	EClass getPolicyEvaluation();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.policy.PolicyEvaluation#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature</em>'.
	 * @see sb.base.policy.PolicyEvaluation#getFeature()
	 * @see #getPolicyEvaluation()
	 * @generated
	 */
	EReference getPolicyEvaluation_Feature();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.policy.PolicyEvaluation#getContextBinding <em>Context Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context Binding</em>'.
	 * @see sb.base.policy.PolicyEvaluation#getContextBinding()
	 * @see #getPolicyEvaluation()
	 * @generated
	 */
	EReference getPolicyEvaluation_ContextBinding();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.policy.PolicyEvaluation#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context</em>'.
	 * @see sb.base.policy.PolicyEvaluation#getContext()
	 * @see #getPolicyEvaluation()
	 * @generated
	 */
	EReference getPolicyEvaluation_Context();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.Policy <em>Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Policy</em>'.
	 * @see sb.base.policy.Policy
	 * @generated
	 */
	EClass getPolicy();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.Policy#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sb.base.policy.Policy#getName()
	 * @see #getPolicy()
	 * @generated
	 */
	EAttribute getPolicy_Name();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.Policy#getBusinessValue <em>Business Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Business Value</em>'.
	 * @see sb.base.policy.Policy#getBusinessValue()
	 * @see #getPolicy()
	 * @generated
	 */
	EAttribute getPolicy_BusinessValue();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.Policy#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see sb.base.policy.Policy#getFeature()
	 * @see #getPolicy()
	 * @generated
	 */
	EAttribute getPolicy_Feature();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.policy.Policy#getDecision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Decision</em>'.
	 * @see sb.base.policy.Policy#getDecision()
	 * @see #getPolicy()
	 * @generated
	 */
	EReference getPolicy_Decision();

	/**
	 * Returns the meta object for class '{@link sb.base.policy.PolicyDecision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision</em>'.
	 * @see sb.base.policy.PolicyDecision
	 * @generated
	 */
	EClass getPolicyDecision();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.PolicyDecision#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter</em>'.
	 * @see sb.base.policy.PolicyDecision#getParameter()
	 * @see #getPolicyDecision()
	 * @generated
	 */
	EAttribute getPolicyDecision_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.PolicyDecision#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation</em>'.
	 * @see sb.base.policy.PolicyDecision#getOperation()
	 * @see #getPolicyDecision()
	 * @generated
	 */
	EAttribute getPolicyDecision_Operation();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.policy.PolicyDecision#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see sb.base.policy.PolicyDecision#getValue()
	 * @see #getPolicyDecision()
	 * @generated
	 */
	EAttribute getPolicyDecision_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PolicyFactory getPolicyFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyEvaluationPointImpl <em>Evaluation Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyEvaluationPointImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationPoint()
		 * @generated
		 */
		EClass POLICY_EVALUATION_POINT = eINSTANCE.getPolicyEvaluationPoint();

		/**
		 * The meta object literal for the '<em><b>Signal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_POINT__SIGNAL = eINSTANCE.getPolicyEvaluationPoint_Signal();

		/**
		 * The meta object literal for the '<em><b>Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_POINT__REQUEST = eINSTANCE.getPolicyEvaluationPoint_Request();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyEvaluationRequestImpl <em>Evaluation Request</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyEvaluationRequestImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationRequest()
		 * @generated
		 */
		EClass POLICY_EVALUATION_REQUEST = eINSTANCE.getPolicyEvaluationRequest();

		/**
		 * The meta object literal for the '<em><b>Handler</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_REQUEST__HANDLER = eINSTANCE.getPolicyEvaluationRequest_Handler();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_REQUEST__BINDINGS = eINSTANCE.getPolicyEvaluationRequest_Bindings();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyEvaluationHandlerImpl <em>Evaluation Handler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyEvaluationHandlerImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationHandler()
		 * @generated
		 */
		EClass POLICY_EVALUATION_HANDLER = eINSTANCE.getPolicyEvaluationHandler();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_HANDLER__PARAMETERS = eINSTANCE.getPolicyEvaluationHandler_Parameters();

		/**
		 * The meta object literal for the '<em><b>Handler Impl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_EVALUATION_HANDLER__HANDLER_IMPL = eINSTANCE.getPolicyEvaluationHandler_HandlerImpl();

		/**
		 * The meta object literal for the '<em><b>Evaluation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_HANDLER__EVALUATION = eINSTANCE.getPolicyEvaluationHandler_Evaluation();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyEvaluationContextImpl <em>Evaluation Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyEvaluationContextImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluationContext()
		 * @generated
		 */
		EClass POLICY_EVALUATION_CONTEXT = eINSTANCE.getPolicyEvaluationContext();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION_CONTEXT__PARAMETERS = eINSTANCE.getPolicyEvaluationContext_Parameters();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyEvaluationImpl <em>Evaluation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyEvaluationImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyEvaluation()
		 * @generated
		 */
		EClass POLICY_EVALUATION = eINSTANCE.getPolicyEvaluation();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION__FEATURE = eINSTANCE.getPolicyEvaluation_Feature();

		/**
		 * The meta object literal for the '<em><b>Context Binding</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION__CONTEXT_BINDING = eINSTANCE.getPolicyEvaluation_ContextBinding();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_EVALUATION__CONTEXT = eINSTANCE.getPolicyEvaluation_Context();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyImpl <em>Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicy()
		 * @generated
		 */
		EClass POLICY = eINSTANCE.getPolicy();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY__NAME = eINSTANCE.getPolicy_Name();

		/**
		 * The meta object literal for the '<em><b>Business Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY__BUSINESS_VALUE = eINSTANCE.getPolicy_BusinessValue();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY__FEATURE = eINSTANCE.getPolicy_Feature();

		/**
		 * The meta object literal for the '<em><b>Decision</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY__DECISION = eINSTANCE.getPolicy_Decision();

		/**
		 * The meta object literal for the '{@link sb.base.policy.impl.PolicyDecisionImpl <em>Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.policy.impl.PolicyDecisionImpl
		 * @see sb.base.policy.impl.PolicyPackageImpl#getPolicyDecision()
		 * @generated
		 */
		EClass POLICY_DECISION = eINSTANCE.getPolicyDecision();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_DECISION__PARAMETER = eINSTANCE.getPolicyDecision_Parameter();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_DECISION__OPERATION = eINSTANCE.getPolicyDecision_Operation();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_DECISION__VALUE = eINSTANCE.getPolicyDecision_Value();

	}

} //PolicyPackage
