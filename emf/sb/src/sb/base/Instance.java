/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import sb.base.common.Interface;

import sb.base.metadata.Annotable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.Instance#getImpl <em>Impl</em>}</li>
 *   <li>{@link sb.base.Instance#getIface <em>Iface</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getInstance()
 * @model
 * @generated
 */
public interface Instance extends Annotable {
	/**
	 * Returns the value of the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Impl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Impl</em>' attribute.
	 * @see #setImpl(String)
	 * @see sb.base.BasePackage#getInstance_Impl()
	 * @model required="true"
	 * @generated
	 */
	String getImpl();

	/**
	 * Sets the value of the '{@link sb.base.Instance#getImpl <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impl</em>' attribute.
	 * @see #getImpl()
	 * @generated
	 */
	void setImpl(String value);

	/**
	 * Returns the value of the '<em><b>Iface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iface</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iface</em>' reference.
	 * @see #setIface(Interface)
	 * @see sb.base.BasePackage#getInstance_Iface()
	 * @model required="true" derived="true"
	 * @generated
	 */
	Interface getIface();

	/**
	 * Sets the value of the '{@link sb.base.Instance#getIface <em>Iface</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iface</em>' reference.
	 * @see #getIface()
	 * @generated
	 */
	void setIface(Interface value);

} // Instance
