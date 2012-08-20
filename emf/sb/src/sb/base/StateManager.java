/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import sb.base.context.State;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.StateManager#getStateTypes <em>State Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getStateManager()
 * @model
 * @generated
 */
public interface StateManager extends EObject {
	/**
	 * Returns the value of the '<em><b>State Types</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.context.State}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Types</em>' containment reference list.
	 * @see sb.base.BasePackage#getStateManager_StateTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getStateTypes();

} // StateManager
