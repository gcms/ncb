/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.autonomic;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Request</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.autonomic.ChangeRequest#getName <em>Name</em>}</li>
 *   <li>{@link sb.base.autonomic.ChangeRequest#getBasedOn <em>Based On</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.autonomic.AutonomicPackage#getChangeRequest()
 * @model
 * @generated
 */
public interface ChangeRequest extends EObject {
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
	 * @see sb.base.autonomic.AutonomicPackage#getChangeRequest_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sb.base.autonomic.ChangeRequest#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Based On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Based On</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Based On</em>' reference.
	 * @see #setBasedOn(Symptom)
	 * @see sb.base.autonomic.AutonomicPackage#getChangeRequest_BasedOn()
	 * @model required="true"
	 * @generated
	 */
	Symptom getBasedOn();

	/**
	 * Sets the value of the '{@link sb.base.autonomic.ChangeRequest#getBasedOn <em>Based On</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Based On</em>' reference.
	 * @see #getBasedOn()
	 * @generated
	 */
	void setBasedOn(Symptom value);

} // ChangeRequest
