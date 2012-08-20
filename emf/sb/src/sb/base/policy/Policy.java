/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.policy.Policy#getName <em>Name</em>}</li>
 *   <li>{@link sb.base.policy.Policy#getBusinessValue <em>Business Value</em>}</li>
 *   <li>{@link sb.base.policy.Policy#getFeature <em>Feature</em>}</li>
 *   <li>{@link sb.base.policy.Policy#getDecision <em>Decision</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.policy.PolicyPackage#getPolicy()
 * @model
 * @generated
 */
public interface Policy extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see sb.base.policy.PolicyPackage#getPolicy_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sb.base.policy.Policy#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Business Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Business Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Business Value</em>' attribute.
	 * @see #setBusinessValue(Integer)
	 * @see sb.base.policy.PolicyPackage#getPolicy_BusinessValue()
	 * @model required="true"
	 * @generated
	 */
	Integer getBusinessValue();

	/**
	 * Sets the value of the '{@link sb.base.policy.Policy#getBusinessValue <em>Business Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Business Value</em>' attribute.
	 * @see #getBusinessValue()
	 * @generated
	 */
	void setBusinessValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' attribute.
	 * @see #setFeature(String)
	 * @see sb.base.policy.PolicyPackage#getPolicy_Feature()
	 * @model required="true"
	 * @generated
	 */
	String getFeature();

	/**
	 * Sets the value of the '{@link sb.base.policy.Policy#getFeature <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' attribute.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(String value);

	/**
	 * Returns the value of the '<em><b>Decision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision</em>' containment reference.
	 * @see #setDecision(PolicyDecision)
	 * @see sb.base.policy.PolicyPackage#getPolicy_Decision()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PolicyDecision getDecision();

	/**
	 * Sets the value of the '{@link sb.base.policy.Policy#getDecision <em>Decision</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decision</em>' containment reference.
	 * @see #getDecision()
	 * @generated
	 */
	void setDecision(PolicyDecision value);

} // Policy
