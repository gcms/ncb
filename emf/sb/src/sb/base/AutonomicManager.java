/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import sb.base.autonomic.ChangePlan;
import sb.base.autonomic.ChangeRequest;
import sb.base.autonomic.Symptom;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Autonomic Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.AutonomicManager#getIdentifies <em>Identifies</em>}</li>
 *   <li>{@link sb.base.AutonomicManager#getRequests <em>Requests</em>}</li>
 *   <li>{@link sb.base.AutonomicManager#getPlans <em>Plans</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getAutonomicManager()
 * @model
 * @generated
 */
public interface AutonomicManager extends EObject {
	/**
	 * Returns the value of the '<em><b>Identifies</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.autonomic.Symptom}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifies</em>' containment reference list.
	 * @see sb.base.BasePackage#getAutonomicManager_Identifies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Symptom> getIdentifies();

	/**
	 * Returns the value of the '<em><b>Requests</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.autonomic.ChangeRequest}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requests</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requests</em>' containment reference list.
	 * @see sb.base.BasePackage#getAutonomicManager_Requests()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChangeRequest> getRequests();

	/**
	 * Returns the value of the '<em><b>Plans</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.autonomic.ChangePlan}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plans</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plans</em>' containment reference list.
	 * @see sb.base.BasePackage#getAutonomicManager_Plans()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChangePlan> getPlans();

} // AutonomicManager
