/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.ecore.EObject;

import sb.base.common.Interface;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.ResourceManager#getIface <em>Iface</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getResourceManager()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ResourceManager extends EObject {
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
	 * @see sb.base.BasePackage#getResourceManager_Iface()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Interface getIface();

	/**
	 * Sets the value of the '{@link sb.base.ResourceManager#getIface <em>Iface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iface</em>' containment reference.
	 * @see #getIface()
	 * @generated
	 */
	void setIface(Interface value);

} // ResourceManager
