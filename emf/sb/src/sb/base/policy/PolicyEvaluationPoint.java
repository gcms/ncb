/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy;

import org.eclipse.emf.ecore.EObject;

import sb.base.common.Signal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Evaluation Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.policy.PolicyEvaluationPoint#getSignal <em>Signal</em>}</li>
 *   <li>{@link sb.base.policy.PolicyEvaluationPoint#getRequest <em>Request</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationPoint()
 * @model
 * @generated
 */
public interface PolicyEvaluationPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Signal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signal</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signal</em>' reference.
	 * @see #setSignal(Signal)
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationPoint_Signal()
	 * @model required="true"
	 * @generated
	 */
	Signal getSignal();

	/**
	 * Sets the value of the '{@link sb.base.policy.PolicyEvaluationPoint#getSignal <em>Signal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signal</em>' reference.
	 * @see #getSignal()
	 * @generated
	 */
	void setSignal(Signal value);

	/**
	 * Returns the value of the '<em><b>Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Request</em>' containment reference.
	 * @see #setRequest(PolicyEvaluationRequest)
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationPoint_Request()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PolicyEvaluationRequest getRequest();

	/**
	 * Sets the value of the '{@link sb.base.policy.PolicyEvaluationPoint#getRequest <em>Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Request</em>' containment reference.
	 * @see #getRequest()
	 * @generated
	 */
	void setRequest(PolicyEvaluationRequest value);

} // PolicyEvaluationPoint
