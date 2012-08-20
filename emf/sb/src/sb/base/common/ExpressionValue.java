/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.common;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.common.ExpressionValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.common.CommonPackage#getExpressionValue()
 * @model
 * @generated
 */
public interface ExpressionValue extends Value {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see sb.base.common.CommonPackage#getExpressionValue_Value()
	 * @model required="true"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link sb.base.common.ExpressionValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // ExpressionValue
