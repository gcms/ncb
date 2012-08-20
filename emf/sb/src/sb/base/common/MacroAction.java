/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.common;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Macro Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.common.MacroAction#getImpl <em>Impl</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.common.CommonPackage#getMacroAction()
 * @model
 * @generated
 */
public interface MacroAction extends Action {
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
	 * @see sb.base.common.CommonPackage#getMacroAction_Impl()
	 * @model required="true"
	 * @generated
	 */
	String getImpl();

	/**
	 * Sets the value of the '{@link sb.base.common.MacroAction#getImpl <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impl</em>' attribute.
	 * @see #getImpl()
	 * @generated
	 */
	void setImpl(String value);

} // MacroAction
