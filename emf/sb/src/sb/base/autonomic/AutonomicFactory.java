/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.autonomic;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sb.base.autonomic.AutonomicPackage
 * @generated
 */
public interface AutonomicFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AutonomicFactory eINSTANCE = sb.base.autonomic.impl.AutonomicFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Symptom</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symptom</em>'.
	 * @generated
	 */
	Symptom createSymptom();

	/**
	 * Returns a new object of class '<em>Change Request</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Request</em>'.
	 * @generated
	 */
	ChangeRequest createChangeRequest();

	/**
	 * Returns a new object of class '<em>Change Plan</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Plan</em>'.
	 * @generated
	 */
	ChangePlan createChangePlan();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AutonomicPackage getAutonomicPackage();

} //AutonomicFactory
