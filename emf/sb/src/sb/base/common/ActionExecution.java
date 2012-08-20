/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.common;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Execution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.common.ActionExecution#getAction <em>Action</em>}</li>
 *   <li>{@link sb.base.common.ActionExecution#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.common.CommonPackage#getActionExecution()
 * @model
 * @generated
 */
public interface ActionExecution extends Value {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see sb.base.common.CommonPackage#getActionExecution_Action()
	 * @model required="true"
	 * @generated
	 */
	Action getAction();

	/**
	 * Sets the value of the '{@link sb.base.common.ActionExecution#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

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
	 * @see sb.base.common.CommonPackage#getActionExecution_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterBinding> getBindings();

} // ActionExecution
