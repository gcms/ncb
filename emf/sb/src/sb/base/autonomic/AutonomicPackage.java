/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.autonomic;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see sb.base.autonomic.AutonomicFactory
 * @model kind="package"
 * @generated
 */
public interface AutonomicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "autonomic";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.inf.ufg.br/mestrado/sb/base/autonomic";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sb.base.autonomic";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AutonomicPackage eINSTANCE = sb.base.autonomic.impl.AutonomicPackageImpl.init();

	/**
	 * The meta object id for the '{@link sb.base.autonomic.impl.SymptomImpl <em>Symptom</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.autonomic.impl.SymptomImpl
	 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getSymptom()
	 * @generated
	 */
	int SYMPTOM = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOM__BINDINGS = 1;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOM__CONDITIONS = 2;

	/**
	 * The number of structural features of the '<em>Symptom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOM_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.autonomic.impl.ChangeRequestImpl <em>Change Request</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.autonomic.impl.ChangeRequestImpl
	 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getChangeRequest()
	 * @generated
	 */
	int CHANGE_REQUEST = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_REQUEST__NAME = 0;

	/**
	 * The feature id for the '<em><b>Based On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_REQUEST__BASED_ON = 1;

	/**
	 * The number of structural features of the '<em>Change Request</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_REQUEST_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link sb.base.autonomic.impl.ChangePlanImpl <em>Change Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.autonomic.impl.ChangePlanImpl
	 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getChangePlan()
	 * @generated
	 */
	int CHANGE_PLAN = 2;

	/**
	 * The feature id for the '<em><b>Based On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_PLAN__BASED_ON = 0;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_PLAN__ACTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_PLAN__NAME = 2;

	/**
	 * The number of structural features of the '<em>Change Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_PLAN_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link sb.base.autonomic.Symptom <em>Symptom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symptom</em>'.
	 * @see sb.base.autonomic.Symptom
	 * @generated
	 */
	EClass getSymptom();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.autonomic.Symptom#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sb.base.autonomic.Symptom#getName()
	 * @see #getSymptom()
	 * @generated
	 */
	EAttribute getSymptom_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.autonomic.Symptom#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see sb.base.autonomic.Symptom#getBindings()
	 * @see #getSymptom()
	 * @generated
	 */
	EReference getSymptom_Bindings();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.autonomic.Symptom#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see sb.base.autonomic.Symptom#getConditions()
	 * @see #getSymptom()
	 * @generated
	 */
	EReference getSymptom_Conditions();

	/**
	 * Returns the meta object for class '{@link sb.base.autonomic.ChangeRequest <em>Change Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Request</em>'.
	 * @see sb.base.autonomic.ChangeRequest
	 * @generated
	 */
	EClass getChangeRequest();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.autonomic.ChangeRequest#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sb.base.autonomic.ChangeRequest#getName()
	 * @see #getChangeRequest()
	 * @generated
	 */
	EAttribute getChangeRequest_Name();

	/**
	 * Returns the meta object for the reference '{@link sb.base.autonomic.ChangeRequest#getBasedOn <em>Based On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Based On</em>'.
	 * @see sb.base.autonomic.ChangeRequest#getBasedOn()
	 * @see #getChangeRequest()
	 * @generated
	 */
	EReference getChangeRequest_BasedOn();

	/**
	 * Returns the meta object for class '{@link sb.base.autonomic.ChangePlan <em>Change Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Plan</em>'.
	 * @see sb.base.autonomic.ChangePlan
	 * @generated
	 */
	EClass getChangePlan();

	/**
	 * Returns the meta object for the reference '{@link sb.base.autonomic.ChangePlan#getBasedOn <em>Based On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Based On</em>'.
	 * @see sb.base.autonomic.ChangePlan#getBasedOn()
	 * @see #getChangePlan()
	 * @generated
	 */
	EReference getChangePlan_BasedOn();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.autonomic.ChangePlan#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see sb.base.autonomic.ChangePlan#getAction()
	 * @see #getChangePlan()
	 * @generated
	 */
	EReference getChangePlan_Action();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.autonomic.ChangePlan#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sb.base.autonomic.ChangePlan#getName()
	 * @see #getChangePlan()
	 * @generated
	 */
	EAttribute getChangePlan_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AutonomicFactory getAutonomicFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link sb.base.autonomic.impl.SymptomImpl <em>Symptom</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.autonomic.impl.SymptomImpl
		 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getSymptom()
		 * @generated
		 */
		EClass SYMPTOM = eINSTANCE.getSymptom();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMPTOM__NAME = eINSTANCE.getSymptom_Name();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMPTOM__BINDINGS = eINSTANCE.getSymptom_Bindings();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMPTOM__CONDITIONS = eINSTANCE.getSymptom_Conditions();

		/**
		 * The meta object literal for the '{@link sb.base.autonomic.impl.ChangeRequestImpl <em>Change Request</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.autonomic.impl.ChangeRequestImpl
		 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getChangeRequest()
		 * @generated
		 */
		EClass CHANGE_REQUEST = eINSTANCE.getChangeRequest();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_REQUEST__NAME = eINSTANCE.getChangeRequest_Name();

		/**
		 * The meta object literal for the '<em><b>Based On</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_REQUEST__BASED_ON = eINSTANCE.getChangeRequest_BasedOn();

		/**
		 * The meta object literal for the '{@link sb.base.autonomic.impl.ChangePlanImpl <em>Change Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.autonomic.impl.ChangePlanImpl
		 * @see sb.base.autonomic.impl.AutonomicPackageImpl#getChangePlan()
		 * @generated
		 */
		EClass CHANGE_PLAN = eINSTANCE.getChangePlan();

		/**
		 * The meta object literal for the '<em><b>Based On</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_PLAN__BASED_ON = eINSTANCE.getChangePlan_BasedOn();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_PLAN__ACTION = eINSTANCE.getChangePlan_Action();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_PLAN__NAME = eINSTANCE.getChangePlan_Name();

	}

} //AutonomicPackage
