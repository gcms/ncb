/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import sb.base.policy.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see sb.base.policy.PolicyPackage
 * @generated
 */
public class PolicyAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PolicyPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PolicyPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicySwitch<Adapter> modelSwitch =
		new PolicySwitch<Adapter>() {
			@Override
			public Adapter casePolicyEvaluationPoint(PolicyEvaluationPoint object) {
				return createPolicyEvaluationPointAdapter();
			}
			@Override
			public Adapter casePolicyEvaluationRequest(PolicyEvaluationRequest object) {
				return createPolicyEvaluationRequestAdapter();
			}
			@Override
			public Adapter casePolicyEvaluationHandler(PolicyEvaluationHandler object) {
				return createPolicyEvaluationHandlerAdapter();
			}
			@Override
			public Adapter casePolicyEvaluationContext(PolicyEvaluationContext object) {
				return createPolicyEvaluationContextAdapter();
			}
			@Override
			public Adapter casePolicyEvaluation(PolicyEvaluation object) {
				return createPolicyEvaluationAdapter();
			}
			@Override
			public Adapter casePolicy(Policy object) {
				return createPolicyAdapter();
			}
			@Override
			public Adapter casePolicyDecision(PolicyDecision object) {
				return createPolicyDecisionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyEvaluationPoint <em>Evaluation Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyEvaluationPoint
	 * @generated
	 */
	public Adapter createPolicyEvaluationPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyEvaluationRequest <em>Evaluation Request</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyEvaluationRequest
	 * @generated
	 */
	public Adapter createPolicyEvaluationRequestAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyEvaluationHandler <em>Evaluation Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyEvaluationHandler
	 * @generated
	 */
	public Adapter createPolicyEvaluationHandlerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyEvaluationContext <em>Evaluation Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyEvaluationContext
	 * @generated
	 */
	public Adapter createPolicyEvaluationContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyEvaluation <em>Evaluation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyEvaluation
	 * @generated
	 */
	public Adapter createPolicyEvaluationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.Policy <em>Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.Policy
	 * @generated
	 */
	public Adapter createPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link sb.base.policy.PolicyDecision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see sb.base.policy.PolicyDecision
	 * @generated
	 */
	public Adapter createPolicyDecisionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PolicyAdapterFactory
