/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.common;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.common.Interface#getProvides <em>Provides</em>}</li>
 *   <li>{@link sb.base.common.Interface#getSignals <em>Signals</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.common.CommonPackage#getInterface()
 * @model
 * @generated
 */
public interface Interface extends EObject {
	/**
	 * Returns the value of the '<em><b>Provides</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.Call}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provides</em>' containment reference list.
	 * @see sb.base.common.CommonPackage#getInterface_Provides()
	 * @model containment="true"
	 * @generated
	 */
	EList<Call> getProvides();

	/**
	 * Returns the value of the '<em><b>Signals</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signals</em>' containment reference list.
	 * @see sb.base.common.CommonPackage#getInterface_Signals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Event> getSignals();

} // Interface
