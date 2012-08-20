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

import sb.base.BasePackage;
import sb.base.Instance;
import sb.base.InstanceResourceManager;

import sb.base.common.Interface;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Resource Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.impl.InstanceResourceManagerImpl#getIface <em>Iface</em>}</li>
 *   <li>{@link sb.base.impl.InstanceResourceManagerImpl#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstanceResourceManagerImpl extends EObjectImpl implements InstanceResourceManager {
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
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> instances;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceResourceManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.INSTANCE_RESOURCE_MANAGER;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE, oldIface, newIface);
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
				msgs = ((InternalEObject)iface).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE, null, msgs);
			if (newIface != null)
				msgs = ((InternalEObject)newIface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE, null, msgs);
			msgs = basicSetIface(newIface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE, newIface, newIface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Instance>(Instance.class, this, BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE:
				return basicSetIface(null, msgs);
			case BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
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
			case BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE:
				return getIface();
			case BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES:
				return getInstances();
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
			case BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE:
				setIface((Interface)newValue);
				return;
			case BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Instance>)newValue);
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
			case BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE:
				setIface((Interface)null);
				return;
			case BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES:
				getInstances().clear();
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
			case BasePackage.INSTANCE_RESOURCE_MANAGER__IFACE:
				return iface != null;
			case BasePackage.INSTANCE_RESOURCE_MANAGER__INSTANCES:
				return instances != null && !instances.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InstanceResourceManagerImpl
