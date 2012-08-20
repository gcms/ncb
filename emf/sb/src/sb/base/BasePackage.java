/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import sb.base.metadata.MetadataPackage;

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
 * @see sb.base.BaseFactory
 * @model kind="package"
 * @generated
 */
public interface BasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "base";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.inf.ufg.br/mestrado/sb/base";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sb.base";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasePackage eINSTANCE = sb.base.impl.BasePackageImpl.init();

	/**
	 * The meta object id for the '{@link sb.base.impl.ManagerImpl <em>Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.ManagerImpl
	 * @see sb.base.impl.BasePackageImpl#getManager()
	 * @generated
	 */
	int MANAGER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__NAME = MetadataPackage.ANNOTABLE__NAME;

	/**
	 * The feature id for the '<em><b>Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__FEATURES = MetadataPackage.ANNOTABLE__FEATURES;

	/**
	 * The feature id for the '<em><b>Iface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__IFACE = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__ACTIONS = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Handlers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__HANDLERS = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__STATE_MANAGER = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Resource Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__RESOURCE_MANAGER = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Autonomic Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__AUTONOMIC_MANAGER = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Policy Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER__POLICY_MANAGER = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_FEATURE_COUNT = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link sb.base.ResourceManager <em>Resource Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.ResourceManager
	 * @see sb.base.impl.BasePackageImpl#getResourceManager()
	 * @generated
	 */
	int RESOURCE_MANAGER = 1;

	/**
	 * The feature id for the '<em><b>Iface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MANAGER__IFACE = 0;

	/**
	 * The number of structural features of the '<em>Resource Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MANAGER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link sb.base.impl.StateManagerImpl <em>State Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.StateManagerImpl
	 * @see sb.base.impl.BasePackageImpl#getStateManager()
	 * @generated
	 */
	int STATE_MANAGER = 2;

	/**
	 * The feature id for the '<em><b>State Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MANAGER__STATE_TYPES = 0;

	/**
	 * The number of structural features of the '<em>State Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MANAGER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link sb.base.impl.AutonomicManagerImpl <em>Autonomic Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.AutonomicManagerImpl
	 * @see sb.base.impl.BasePackageImpl#getAutonomicManager()
	 * @generated
	 */
	int AUTONOMIC_MANAGER = 3;

	/**
	 * The feature id for the '<em><b>Identifies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTONOMIC_MANAGER__IDENTIFIES = 0;

	/**
	 * The feature id for the '<em><b>Requests</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTONOMIC_MANAGER__REQUESTS = 1;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTONOMIC_MANAGER__PLANS = 2;

	/**
	 * The number of structural features of the '<em>Autonomic Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTONOMIC_MANAGER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.impl.PolicyManagerImpl <em>Policy Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.PolicyManagerImpl
	 * @see sb.base.impl.BasePackageImpl#getPolicyManager()
	 * @generated
	 */
	int POLICY_MANAGER = 4;

	/**
	 * The feature id for the '<em><b>Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_MANAGER__POINTS = 0;

	/**
	 * The feature id for the '<em><b>Handlers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_MANAGER__HANDLERS = 1;

	/**
	 * The feature id for the '<em><b>Policies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_MANAGER__POLICIES = 2;

	/**
	 * The number of structural features of the '<em>Policy Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_MANAGER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.impl.HandlerImpl <em>Handler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.HandlerImpl
	 * @see sb.base.impl.BasePackageImpl#getHandler()
	 * @generated
	 */
	int HANDLER = 5;

	/**
	 * The feature id for the '<em><b>Signal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__SIGNAL = 0;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__ACTION = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__ENABLED = 2;

	/**
	 * The number of structural features of the '<em>Handler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sb.base.impl.InstanceResourceManagerImpl <em>Instance Resource Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.InstanceResourceManagerImpl
	 * @see sb.base.impl.BasePackageImpl#getInstanceResourceManager()
	 * @generated
	 */
	int INSTANCE_RESOURCE_MANAGER = 6;

	/**
	 * The feature id for the '<em><b>Iface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_RESOURCE_MANAGER__IFACE = RESOURCE_MANAGER__IFACE;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_RESOURCE_MANAGER__INSTANCES = RESOURCE_MANAGER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Instance Resource Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_RESOURCE_MANAGER_FEATURE_COUNT = RESOURCE_MANAGER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link sb.base.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sb.base.impl.InstanceImpl
	 * @see sb.base.impl.BasePackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NAME = MetadataPackage.ANNOTABLE__NAME;

	/**
	 * The feature id for the '<em><b>Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__FEATURES = MetadataPackage.ANNOTABLE__FEATURES;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__IMPL = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Iface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__IFACE = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = MetadataPackage.ANNOTABLE_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link sb.base.Manager <em>Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manager</em>'.
	 * @see sb.base.Manager
	 * @generated
	 */
	EClass getManager();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Manager#getIface <em>Iface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Iface</em>'.
	 * @see sb.base.Manager#getIface()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_Iface();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.Manager#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see sb.base.Manager#getActions()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_Actions();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.Manager#getHandlers <em>Handlers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Handlers</em>'.
	 * @see sb.base.Manager#getHandlers()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_Handlers();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Manager#getStateManager <em>State Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>State Manager</em>'.
	 * @see sb.base.Manager#getStateManager()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_StateManager();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Manager#getResourceManager <em>Resource Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resource Manager</em>'.
	 * @see sb.base.Manager#getResourceManager()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_ResourceManager();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Manager#getAutonomicManager <em>Autonomic Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Autonomic Manager</em>'.
	 * @see sb.base.Manager#getAutonomicManager()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_AutonomicManager();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Manager#getPolicyManager <em>Policy Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Policy Manager</em>'.
	 * @see sb.base.Manager#getPolicyManager()
	 * @see #getManager()
	 * @generated
	 */
	EReference getManager_PolicyManager();

	/**
	 * Returns the meta object for class '{@link sb.base.ResourceManager <em>Resource Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Manager</em>'.
	 * @see sb.base.ResourceManager
	 * @generated
	 */
	EClass getResourceManager();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.ResourceManager#getIface <em>Iface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Iface</em>'.
	 * @see sb.base.ResourceManager#getIface()
	 * @see #getResourceManager()
	 * @generated
	 */
	EReference getResourceManager_Iface();

	/**
	 * Returns the meta object for class '{@link sb.base.StateManager <em>State Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Manager</em>'.
	 * @see sb.base.StateManager
	 * @generated
	 */
	EClass getStateManager();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.StateManager#getStateTypes <em>State Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Types</em>'.
	 * @see sb.base.StateManager#getStateTypes()
	 * @see #getStateManager()
	 * @generated
	 */
	EReference getStateManager_StateTypes();

	/**
	 * Returns the meta object for class '{@link sb.base.AutonomicManager <em>Autonomic Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Autonomic Manager</em>'.
	 * @see sb.base.AutonomicManager
	 * @generated
	 */
	EClass getAutonomicManager();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.AutonomicManager#getIdentifies <em>Identifies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Identifies</em>'.
	 * @see sb.base.AutonomicManager#getIdentifies()
	 * @see #getAutonomicManager()
	 * @generated
	 */
	EReference getAutonomicManager_Identifies();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.AutonomicManager#getRequests <em>Requests</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Requests</em>'.
	 * @see sb.base.AutonomicManager#getRequests()
	 * @see #getAutonomicManager()
	 * @generated
	 */
	EReference getAutonomicManager_Requests();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.AutonomicManager#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plans</em>'.
	 * @see sb.base.AutonomicManager#getPlans()
	 * @see #getAutonomicManager()
	 * @generated
	 */
	EReference getAutonomicManager_Plans();

	/**
	 * Returns the meta object for class '{@link sb.base.PolicyManager <em>Policy Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Policy Manager</em>'.
	 * @see sb.base.PolicyManager
	 * @generated
	 */
	EClass getPolicyManager();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.PolicyManager#getPoints <em>Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Points</em>'.
	 * @see sb.base.PolicyManager#getPoints()
	 * @see #getPolicyManager()
	 * @generated
	 */
	EReference getPolicyManager_Points();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.PolicyManager#getHandlers <em>Handlers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Handlers</em>'.
	 * @see sb.base.PolicyManager#getHandlers()
	 * @see #getPolicyManager()
	 * @generated
	 */
	EReference getPolicyManager_Handlers();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.PolicyManager#getPolicies <em>Policies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Policies</em>'.
	 * @see sb.base.PolicyManager#getPolicies()
	 * @see #getPolicyManager()
	 * @generated
	 */
	EReference getPolicyManager_Policies();

	/**
	 * Returns the meta object for class '{@link sb.base.Handler <em>Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Handler</em>'.
	 * @see sb.base.Handler
	 * @generated
	 */
	EClass getHandler();

	/**
	 * Returns the meta object for the reference '{@link sb.base.Handler#getSignal <em>Signal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signal</em>'.
	 * @see sb.base.Handler#getSignal()
	 * @see #getHandler()
	 * @generated
	 */
	EReference getHandler_Signal();

	/**
	 * Returns the meta object for the containment reference '{@link sb.base.Handler#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see sb.base.Handler#getAction()
	 * @see #getHandler()
	 * @generated
	 */
	EReference getHandler_Action();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.Handler#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see sb.base.Handler#isEnabled()
	 * @see #getHandler()
	 * @generated
	 */
	EAttribute getHandler_Enabled();

	/**
	 * Returns the meta object for class '{@link sb.base.InstanceResourceManager <em>Instance Resource Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance Resource Manager</em>'.
	 * @see sb.base.InstanceResourceManager
	 * @generated
	 */
	EClass getInstanceResourceManager();

	/**
	 * Returns the meta object for the containment reference list '{@link sb.base.InstanceResourceManager#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instances</em>'.
	 * @see sb.base.InstanceResourceManager#getInstances()
	 * @see #getInstanceResourceManager()
	 * @generated
	 */
	EReference getInstanceResourceManager_Instances();

	/**
	 * Returns the meta object for class '{@link sb.base.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see sb.base.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the attribute '{@link sb.base.Instance#getImpl <em>Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Impl</em>'.
	 * @see sb.base.Instance#getImpl()
	 * @see #getInstance()
	 * @generated
	 */
	EAttribute getInstance_Impl();

	/**
	 * Returns the meta object for the reference '{@link sb.base.Instance#getIface <em>Iface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Iface</em>'.
	 * @see sb.base.Instance#getIface()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Iface();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BaseFactory getBaseFactory();

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
		 * The meta object literal for the '{@link sb.base.impl.ManagerImpl <em>Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.ManagerImpl
		 * @see sb.base.impl.BasePackageImpl#getManager()
		 * @generated
		 */
		EClass MANAGER = eINSTANCE.getManager();

		/**
		 * The meta object literal for the '<em><b>Iface</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__IFACE = eINSTANCE.getManager_Iface();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__ACTIONS = eINSTANCE.getManager_Actions();

		/**
		 * The meta object literal for the '<em><b>Handlers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__HANDLERS = eINSTANCE.getManager_Handlers();

		/**
		 * The meta object literal for the '<em><b>State Manager</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__STATE_MANAGER = eINSTANCE.getManager_StateManager();

		/**
		 * The meta object literal for the '<em><b>Resource Manager</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__RESOURCE_MANAGER = eINSTANCE.getManager_ResourceManager();

		/**
		 * The meta object literal for the '<em><b>Autonomic Manager</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__AUTONOMIC_MANAGER = eINSTANCE.getManager_AutonomicManager();

		/**
		 * The meta object literal for the '<em><b>Policy Manager</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGER__POLICY_MANAGER = eINSTANCE.getManager_PolicyManager();

		/**
		 * The meta object literal for the '{@link sb.base.ResourceManager <em>Resource Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.ResourceManager
		 * @see sb.base.impl.BasePackageImpl#getResourceManager()
		 * @generated
		 */
		EClass RESOURCE_MANAGER = eINSTANCE.getResourceManager();

		/**
		 * The meta object literal for the '<em><b>Iface</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_MANAGER__IFACE = eINSTANCE.getResourceManager_Iface();

		/**
		 * The meta object literal for the '{@link sb.base.impl.StateManagerImpl <em>State Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.StateManagerImpl
		 * @see sb.base.impl.BasePackageImpl#getStateManager()
		 * @generated
		 */
		EClass STATE_MANAGER = eINSTANCE.getStateManager();

		/**
		 * The meta object literal for the '<em><b>State Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MANAGER__STATE_TYPES = eINSTANCE.getStateManager_StateTypes();

		/**
		 * The meta object literal for the '{@link sb.base.impl.AutonomicManagerImpl <em>Autonomic Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.AutonomicManagerImpl
		 * @see sb.base.impl.BasePackageImpl#getAutonomicManager()
		 * @generated
		 */
		EClass AUTONOMIC_MANAGER = eINSTANCE.getAutonomicManager();

		/**
		 * The meta object literal for the '<em><b>Identifies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTONOMIC_MANAGER__IDENTIFIES = eINSTANCE.getAutonomicManager_Identifies();

		/**
		 * The meta object literal for the '<em><b>Requests</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTONOMIC_MANAGER__REQUESTS = eINSTANCE.getAutonomicManager_Requests();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTONOMIC_MANAGER__PLANS = eINSTANCE.getAutonomicManager_Plans();

		/**
		 * The meta object literal for the '{@link sb.base.impl.PolicyManagerImpl <em>Policy Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.PolicyManagerImpl
		 * @see sb.base.impl.BasePackageImpl#getPolicyManager()
		 * @generated
		 */
		EClass POLICY_MANAGER = eINSTANCE.getPolicyManager();

		/**
		 * The meta object literal for the '<em><b>Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_MANAGER__POINTS = eINSTANCE.getPolicyManager_Points();

		/**
		 * The meta object literal for the '<em><b>Handlers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_MANAGER__HANDLERS = eINSTANCE.getPolicyManager_Handlers();

		/**
		 * The meta object literal for the '<em><b>Policies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_MANAGER__POLICIES = eINSTANCE.getPolicyManager_Policies();

		/**
		 * The meta object literal for the '{@link sb.base.impl.HandlerImpl <em>Handler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.HandlerImpl
		 * @see sb.base.impl.BasePackageImpl#getHandler()
		 * @generated
		 */
		EClass HANDLER = eINSTANCE.getHandler();

		/**
		 * The meta object literal for the '<em><b>Signal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDLER__SIGNAL = eINSTANCE.getHandler_Signal();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDLER__ACTION = eINSTANCE.getHandler_Action();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HANDLER__ENABLED = eINSTANCE.getHandler_Enabled();

		/**
		 * The meta object literal for the '{@link sb.base.impl.InstanceResourceManagerImpl <em>Instance Resource Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.InstanceResourceManagerImpl
		 * @see sb.base.impl.BasePackageImpl#getInstanceResourceManager()
		 * @generated
		 */
		EClass INSTANCE_RESOURCE_MANAGER = eINSTANCE.getInstanceResourceManager();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE_RESOURCE_MANAGER__INSTANCES = eINSTANCE.getInstanceResourceManager_Instances();

		/**
		 * The meta object literal for the '{@link sb.base.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sb.base.impl.InstanceImpl
		 * @see sb.base.impl.BasePackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Impl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTANCE__IMPL = eINSTANCE.getInstance_Impl();

		/**
		 * The meta object literal for the '<em><b>Iface</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__IFACE = eINSTANCE.getInstance_Iface();

	}

} //BasePackage
