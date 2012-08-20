/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.common.util.EList;

import sb.base.common.Action;
import sb.base.common.Interface;

import sb.base.metadata.Annotable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.Manager#getIface <em>Iface</em>}</li>
 *   <li>{@link sb.base.Manager#getActions <em>Actions</em>}</li>
 *   <li>{@link sb.base.Manager#getHandlers <em>Handlers</em>}</li>
 *   <li>{@link sb.base.Manager#getStateManager <em>State Manager</em>}</li>
 *   <li>{@link sb.base.Manager#getResourceManager <em>Resource Manager</em>}</li>
 *   <li>{@link sb.base.Manager#getAutonomicManager <em>Autonomic Manager</em>}</li>
 *   <li>{@link sb.base.Manager#getPolicyManager <em>Policy Manager</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getManager()
 * @model
 * @generated
 */
public interface Manager extends Annotable {
	/**
	 * Returns the value of the '<em><b>Iface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iface</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iface</em>' containment reference.
	 * @see #setIface(Interface)
	 * @see sb.base.BasePackage#getManager_Iface()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Interface getIface();

	/**
	 * Sets the value of the '{@link sb.base.Manager#getIface <em>Iface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iface</em>' containment reference.
	 * @see #getIface()
	 * @generated
	 */
	void setIface(Interface value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see sb.base.BasePackage#getManager_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Action> getActions();

	/**
	 * Returns the value of the '<em><b>Handlers</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.Handler}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handlers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Handlers</em>' containment reference list.
	 * @see sb.base.BasePackage#getManager_Handlers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Handler> getHandlers();

	/**
	 * Returns the value of the '<em><b>State Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Manager</em>' containment reference.
	 * @see #setStateManager(StateManager)
	 * @see sb.base.BasePackage#getManager_StateManager()
	 * @model containment="true"
	 * @generated
	 */
	StateManager getStateManager();

	/**
	 * Sets the value of the '{@link sb.base.Manager#getStateManager <em>State Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Manager</em>' containment reference.
	 * @see #getStateManager()
	 * @generated
	 */
	void setStateManager(StateManager value);

	/**
	 * Returns the value of the '<em><b>Resource Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Manager</em>' containment reference.
	 * @see #setResourceManager(ResourceManager)
	 * @see sb.base.BasePackage#getManager_ResourceManager()
	 * @model containment="true"
	 * @generated
	 */
	ResourceManager getResourceManager();

	/**
	 * Sets the value of the '{@link sb.base.Manager#getResourceManager <em>Resource Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Manager</em>' containment reference.
	 * @see #getResourceManager()
	 * @generated
	 */
	void setResourceManager(ResourceManager value);

	/**
	 * Returns the value of the '<em><b>Autonomic Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Autonomic Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Autonomic Manager</em>' containment reference.
	 * @see #setAutonomicManager(AutonomicManager)
	 * @see sb.base.BasePackage#getManager_AutonomicManager()
	 * @model containment="true"
	 * @generated
	 */
	AutonomicManager getAutonomicManager();

	/**
	 * Sets the value of the '{@link sb.base.Manager#getAutonomicManager <em>Autonomic Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Autonomic Manager</em>' containment reference.
	 * @see #getAutonomicManager()
	 * @generated
	 */
	void setAutonomicManager(AutonomicManager value);

	/**
	 * Returns the value of the '<em><b>Policy Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policy Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policy Manager</em>' containment reference.
	 * @see #setPolicyManager(PolicyManager)
	 * @see sb.base.BasePackage#getManager_PolicyManager()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PolicyManager getPolicyManager();

	/**
	 * Sets the value of the '{@link sb.base.Manager#getPolicyManager <em>Policy Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Policy Manager</em>' containment reference.
	 * @see #getPolicyManager()
	 * @generated
	 */
	void setPolicyManager(PolicyManager value);

} // Manager
