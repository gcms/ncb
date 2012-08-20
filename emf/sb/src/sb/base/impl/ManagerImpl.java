/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import sb.base.AutonomicManager;
import sb.base.BasePackage;
import sb.base.Handler;
import sb.base.Manager;
import sb.base.PolicyManager;
import sb.base.ResourceManager;
import sb.base.StateManager;

import sb.base.common.Action;
import sb.base.common.Interface;

import sb.base.metadata.Feature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.impl.ManagerImpl#getName <em>Name</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getIface <em>Iface</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getHandlers <em>Handlers</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getStateManager <em>State Manager</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getResourceManager <em>Resource Manager</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getAutonomicManager <em>Autonomic Manager</em>}</li>
 *   <li>{@link sb.base.impl.ManagerImpl#getPolicyManager <em>Policy Manager</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ManagerImpl extends EObjectImpl implements Manager {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	/**
	 * The cached value of the '{@link #getIface() <em>Iface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIface()
	 * @generated
	 * @ordered
	 */
	protected Interface iface;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	/**
	 * The cached value of the '{@link #getHandlers() <em>Handlers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlers()
	 * @generated
	 * @ordered
	 */
	protected EList<Handler> handlers;

	/**
	 * The cached value of the '{@link #getStateManager() <em>State Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateManager()
	 * @generated
	 * @ordered
	 */
	protected StateManager stateManager;

	/**
	 * The cached value of the '{@link #getResourceManager() <em>Resource Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceManager()
	 * @generated
	 * @ordered
	 */
	protected ResourceManager resourceManager;

	/**
	 * The cached value of the '{@link #getAutonomicManager() <em>Autonomic Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutonomicManager()
	 * @generated
	 * @ordered
	 */
	protected AutonomicManager autonomicManager;

	/**
	 * The cached value of the '{@link #getPolicyManager() <em>Policy Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPolicyManager()
	 * @generated
	 * @ordered
	 */
	protected PolicyManager policyManager;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Feature> getFeatures() {
		if (features == null) {
			features = new EObjectContainmentEList<Feature>(Feature.class, this, BasePackage.MANAGER__FEATURES);
		}
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getIface() {
		return iface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIface(Interface newIface, NotificationChain msgs) {
		Interface oldIface = iface;
		iface = newIface;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__IFACE, oldIface, newIface);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIface(Interface newIface) {
		if (newIface != iface) {
			NotificationChain msgs = null;
			if (iface != null)
				msgs = ((InternalEObject)iface).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__IFACE, null, msgs);
			if (newIface != null)
				msgs = ((InternalEObject)newIface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__IFACE, null, msgs);
			msgs = basicSetIface(newIface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__IFACE, newIface, newIface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<Action>(Action.class, this, BasePackage.MANAGER__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Handler> getHandlers() {
		if (handlers == null) {
			handlers = new EObjectContainmentEList<Handler>(Handler.class, this, BasePackage.MANAGER__HANDLERS);
		}
		return handlers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateManager getStateManager() {
		return stateManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateManager(StateManager newStateManager, NotificationChain msgs) {
		StateManager oldStateManager = stateManager;
		stateManager = newStateManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__STATE_MANAGER, oldStateManager, newStateManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateManager(StateManager newStateManager) {
		if (newStateManager != stateManager) {
			NotificationChain msgs = null;
			if (stateManager != null)
				msgs = ((InternalEObject)stateManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__STATE_MANAGER, null, msgs);
			if (newStateManager != null)
				msgs = ((InternalEObject)newStateManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__STATE_MANAGER, null, msgs);
			msgs = basicSetStateManager(newStateManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__STATE_MANAGER, newStateManager, newStateManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceManager(ResourceManager newResourceManager, NotificationChain msgs) {
		ResourceManager oldResourceManager = resourceManager;
		resourceManager = newResourceManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__RESOURCE_MANAGER, oldResourceManager, newResourceManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceManager(ResourceManager newResourceManager) {
		if (newResourceManager != resourceManager) {
			NotificationChain msgs = null;
			if (resourceManager != null)
				msgs = ((InternalEObject)resourceManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__RESOURCE_MANAGER, null, msgs);
			if (newResourceManager != null)
				msgs = ((InternalEObject)newResourceManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__RESOURCE_MANAGER, null, msgs);
			msgs = basicSetResourceManager(newResourceManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__RESOURCE_MANAGER, newResourceManager, newResourceManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutonomicManager getAutonomicManager() {
		return autonomicManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAutonomicManager(AutonomicManager newAutonomicManager, NotificationChain msgs) {
		AutonomicManager oldAutonomicManager = autonomicManager;
		autonomicManager = newAutonomicManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__AUTONOMIC_MANAGER, oldAutonomicManager, newAutonomicManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutonomicManager(AutonomicManager newAutonomicManager) {
		if (newAutonomicManager != autonomicManager) {
			NotificationChain msgs = null;
			if (autonomicManager != null)
				msgs = ((InternalEObject)autonomicManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__AUTONOMIC_MANAGER, null, msgs);
			if (newAutonomicManager != null)
				msgs = ((InternalEObject)newAutonomicManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__AUTONOMIC_MANAGER, null, msgs);
			msgs = basicSetAutonomicManager(newAutonomicManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__AUTONOMIC_MANAGER, newAutonomicManager, newAutonomicManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyManager getPolicyManager() {
		return policyManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolicyManager(PolicyManager newPolicyManager, NotificationChain msgs) {
		PolicyManager oldPolicyManager = policyManager;
		policyManager = newPolicyManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__POLICY_MANAGER, oldPolicyManager, newPolicyManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolicyManager(PolicyManager newPolicyManager) {
		if (newPolicyManager != policyManager) {
			NotificationChain msgs = null;
			if (policyManager != null)
				msgs = ((InternalEObject)policyManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__POLICY_MANAGER, null, msgs);
			if (newPolicyManager != null)
				msgs = ((InternalEObject)newPolicyManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.MANAGER__POLICY_MANAGER, null, msgs);
			msgs = basicSetPolicyManager(newPolicyManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.MANAGER__POLICY_MANAGER, newPolicyManager, newPolicyManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.MANAGER__FEATURES:
				return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
			case BasePackage.MANAGER__IFACE:
				return basicSetIface(null, msgs);
			case BasePackage.MANAGER__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
			case BasePackage.MANAGER__HANDLERS:
				return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
			case BasePackage.MANAGER__STATE_MANAGER:
				return basicSetStateManager(null, msgs);
			case BasePackage.MANAGER__RESOURCE_MANAGER:
				return basicSetResourceManager(null, msgs);
			case BasePackage.MANAGER__AUTONOMIC_MANAGER:
				return basicSetAutonomicManager(null, msgs);
			case BasePackage.MANAGER__POLICY_MANAGER:
				return basicSetPolicyManager(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasePackage.MANAGER__NAME:
				return getName();
			case BasePackage.MANAGER__FEATURES:
				return getFeatures();
			case BasePackage.MANAGER__IFACE:
				return getIface();
			case BasePackage.MANAGER__ACTIONS:
				return getActions();
			case BasePackage.MANAGER__HANDLERS:
				return getHandlers();
			case BasePackage.MANAGER__STATE_MANAGER:
				return getStateManager();
			case BasePackage.MANAGER__RESOURCE_MANAGER:
				return getResourceManager();
			case BasePackage.MANAGER__AUTONOMIC_MANAGER:
				return getAutonomicManager();
			case BasePackage.MANAGER__POLICY_MANAGER:
				return getPolicyManager();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasePackage.MANAGER__NAME:
				setName((String)newValue);
				return;
			case BasePackage.MANAGER__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends Feature>)newValue);
				return;
			case BasePackage.MANAGER__IFACE:
				setIface((Interface)newValue);
				return;
			case BasePackage.MANAGER__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
				return;
			case BasePackage.MANAGER__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends Handler>)newValue);
				return;
			case BasePackage.MANAGER__STATE_MANAGER:
				setStateManager((StateManager)newValue);
				return;
			case BasePackage.MANAGER__RESOURCE_MANAGER:
				setResourceManager((ResourceManager)newValue);
				return;
			case BasePackage.MANAGER__AUTONOMIC_MANAGER:
				setAutonomicManager((AutonomicManager)newValue);
				return;
			case BasePackage.MANAGER__POLICY_MANAGER:
				setPolicyManager((PolicyManager)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BasePackage.MANAGER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BasePackage.MANAGER__FEATURES:
				getFeatures().clear();
				return;
			case BasePackage.MANAGER__IFACE:
				setIface((Interface)null);
				return;
			case BasePackage.MANAGER__ACTIONS:
				getActions().clear();
				return;
			case BasePackage.MANAGER__HANDLERS:
				getHandlers().clear();
				return;
			case BasePackage.MANAGER__STATE_MANAGER:
				setStateManager((StateManager)null);
				return;
			case BasePackage.MANAGER__RESOURCE_MANAGER:
				setResourceManager((ResourceManager)null);
				return;
			case BasePackage.MANAGER__AUTONOMIC_MANAGER:
				setAutonomicManager((AutonomicManager)null);
				return;
			case BasePackage.MANAGER__POLICY_MANAGER:
				setPolicyManager((PolicyManager)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BasePackage.MANAGER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BasePackage.MANAGER__FEATURES:
				return features != null && !features.isEmpty();
			case BasePackage.MANAGER__IFACE:
				return iface != null;
			case BasePackage.MANAGER__ACTIONS:
				return actions != null && !actions.isEmpty();
			case BasePackage.MANAGER__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasePackage.MANAGER__STATE_MANAGER:
				return stateManager != null;
			case BasePackage.MANAGER__RESOURCE_MANAGER:
				return resourceManager != null;
			case BasePackage.MANAGER__AUTONOMIC_MANAGER:
				return autonomicManager != null;
			case BasePackage.MANAGER__POLICY_MANAGER:
				return policyManager != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ManagerImpl
