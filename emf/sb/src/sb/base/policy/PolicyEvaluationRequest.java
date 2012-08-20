/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import sb.base.common.ParameterBinding;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Evaluation Request</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.policy.PolicyEvaluationRequest#getHandler <em>Handler</em>}</li>
 *   <li>{@link sb.base.policy.PolicyEvaluationRequest#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationRequest()
 * @model
 * @generated
 */
public interface PolicyEvaluationRequest extends EObject {
	/**
	 * Returns the value of the '<em><b>Handler</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handler</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Handler</em>' reference.
	 * @see #setHandler(PolicyEvaluationHandler)
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationRequest_Handler()
	 * @model required="true"
	 * @generated
	 */
	PolicyEvaluationHandler getHandler();

	/**
	 * Sets the value of the '{@link sb.base.policy.PolicyEvaluationRequest#getHandler <em>Handler</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Handler</em>' reference.
	 * @see #getHandler()
	 * @generated
	 */
	void setHandler(PolicyEvaluationHandler value);

	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.ParameterBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluationRequest_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterBinding> getBindings();

} // PolicyEvaluationRequest
