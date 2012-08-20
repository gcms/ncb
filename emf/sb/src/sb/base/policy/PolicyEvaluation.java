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
import sb.base.common.Value;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Evaluation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.policy.PolicyEvaluation#getFeature <em>Feature</em>}</li>
 *   <li>{@link sb.base.policy.PolicyEvaluation#getContextBinding <em>Context Binding</em>}</li>
 *   <li>{@link sb.base.policy.PolicyEvaluation#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.policy.PolicyPackage#getPolicyEvaluation()
 * @model
 * @generated
 */
public interface PolicyEvaluation extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' containment reference.
	 * @see #setFeature(Value)
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluation_Feature()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Value getFeature();

	/**
	 * Sets the value of the '{@link sb.base.policy.PolicyEvaluation#getFeature <em>Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' containment reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(Value value);

	/**
	 * Returns the value of the '<em><b>Context Binding</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.ParameterBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Binding</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Binding</em>' containment reference list.
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluation_ContextBinding()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterBinding> getContextBinding();

	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference.
	 * @see #setContext(PolicyEvaluationContext)
	 * @see sb.base.policy.PolicyPackage#getPolicyEvaluation_Context()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PolicyEvaluationContext getContext();

	/**
	 * Sets the value of the '{@link sb.base.policy.PolicyEvaluation#getContext <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' containment reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(PolicyEvaluationContext value);

} // PolicyEvaluation
