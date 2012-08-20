/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.autonomic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import sb.base.common.Binding;
import sb.base.common.Condition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symptom</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.autonomic.Symptom#getName <em>Name</em>}</li>
 *   <li>{@link sb.base.autonomic.Symptom#getBindings <em>Bindings</em>}</li>
 *   <li>{@link sb.base.autonomic.Symptom#getConditions <em>Conditions</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.autonomic.AutonomicPackage#getSymptom()
 * @model
 * @generated
 */
public interface Symptom extends EObject {
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
	 * @see sb.base.autonomic.AutonomicPackage#getSymptom_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sb.base.autonomic.Symptom#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.Binding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see sb.base.autonomic.AutonomicPackage#getSymptom_Bindings()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Binding> getBindings();

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.Condition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see sb.base.autonomic.AutonomicPackage#getSymptom_Conditions()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Condition> getConditions();

} // Symptom
