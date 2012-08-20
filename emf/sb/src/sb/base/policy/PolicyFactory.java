/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sb.base.policy.PolicyPackage
 * @generated
 */
public interface PolicyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PolicyFactory eINSTANCE = sb.base.policy.impl.PolicyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Evaluation Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evaluation Point</em>'.
	 * @generated
	 */
	PolicyEvaluationPoint createPolicyEvaluationPoint();

	/**
	 * Returns a new object of class '<em>Evaluation Request</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evaluation Request</em>'.
	 * @generated
	 */
	PolicyEvaluationRequest createPolicyEvaluationRequest();

	/**
	 * Returns a new object of class '<em>Evaluation Handler</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evaluation Handler</em>'.
	 * @generated
	 */
	PolicyEvaluationHandler createPolicyEvaluationHandler();

	/**
	 * Returns a new object of class '<em>Evaluation Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evaluation Context</em>'.
	 * @generated
	 */
	PolicyEvaluationContext createPolicyEvaluationContext();

	/**
	 * Returns a new object of class '<em>Evaluation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evaluation</em>'.
	 * @generated
	 */
	PolicyEvaluation createPolicyEvaluation();

	/**
	 * Returns a new object of class '<em>Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Policy</em>'.
	 * @generated
	 */
	Policy createPolicy();

	/**
	 * Returns a new object of class '<em>Decision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision</em>'.
	 * @generated
	 */
	PolicyDecision createPolicyDecision();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PolicyPackage getPolicyPackage();

} //PolicyFactory
