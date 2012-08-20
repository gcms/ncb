/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import sb.base.policy.Policy;
import sb.base.policy.PolicyEvaluationHandler;
import sb.base.policy.PolicyEvaluationPoint;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Policy Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.PolicyManager#getPoints <em>Points</em>}</li>
 *   <li>{@link sb.base.PolicyManager#getHandlers <em>Handlers</em>}</li>
 *   <li>{@link sb.base.PolicyManager#getPolicies <em>Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getPolicyManager()
 * @model
 * @generated
 */
public interface PolicyManager extends EObject {
	/**
	 * Returns the value of the '<em><b>Points</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.policy.PolicyEvaluationPoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Points</em>' containment reference list.
	 * @see sb.base.BasePackage#getPolicyManager_Points()
	 * @model containment="true"
	 * @generated
	 */
	EList<PolicyEvaluationPoint> getPoints();

	/**
	 * Returns the value of the '<em><b>Handlers</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.policy.PolicyEvaluationHandler}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handlers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Handlers</em>' containment reference list.
	 * @see sb.base.BasePackage#getPolicyManager_Handlers()
	 * @model containment="true"
	 * @generated
	 */
	EList<PolicyEvaluationHandler> getHandlers();

	/**
	 * Returns the value of the '<em><b>Policies</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.policy.Policy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policies</em>' containment reference list.
	 * @see sb.base.BasePackage#getPolicyManager_Policies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Policy> getPolicies();

} // PolicyManager
