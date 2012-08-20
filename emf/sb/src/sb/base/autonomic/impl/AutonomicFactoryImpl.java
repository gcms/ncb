/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.autonomic.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import sb.base.autonomic.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AutonomicFactoryImpl extends EFactoryImpl implements AutonomicFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AutonomicFactory init() {
		try {
			AutonomicFactory theAutonomicFactory = (AutonomicFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.inf.ufg.br/mestrado/sb/base/autonomic"); 
			if (theAutonomicFactory != null) {
				return theAutonomicFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AutonomicFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutonomicFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AutonomicPackage.SYMPTOM: return createSymptom();
			case AutonomicPackage.CHANGE_REQUEST: return createChangeRequest();
			case AutonomicPackage.CHANGE_PLAN: return createChangePlan();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symptom createSymptom() {
		SymptomImpl symptom = new SymptomImpl();
		return symptom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeRequest createChangeRequest() {
		ChangeRequestImpl changeRequest = new ChangeRequestImpl();
		return changeRequest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangePlan createChangePlan() {
		ChangePlanImpl changePlan = new ChangePlanImpl();
		return changePlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutonomicPackage getAutonomicPackage() {
		return (AutonomicPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AutonomicPackage getPackage() {
		return AutonomicPackage.eINSTANCE;
	}

} //AutonomicFactoryImpl
