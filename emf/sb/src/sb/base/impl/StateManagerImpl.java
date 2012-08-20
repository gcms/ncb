/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import sb.base.BasePackage;
import sb.base.StateManager;

import sb.base.context.State;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.impl.StateManagerImpl#getStateTypes <em>State Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateManagerImpl extends EObjectImpl implements StateManager {
	/**
	 * The cached value of the '{@link #getStateTypes() <em>State Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<State> stateTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.STATE_MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getStateTypes() {
		if (stateTypes == null) {
			stateTypes = new EObjectContainmentEList<State>(State.class, this, BasePackage.STATE_MANAGER__STATE_TYPES);
		}
		return stateTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.STATE_MANAGER__STATE_TYPES:
				return ((InternalEList<?>)getStateTypes()).basicRemove(otherEnd, msgs);
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
			case BasePackage.STATE_MANAGER__STATE_TYPES:
				return getStateTypes();
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
			case BasePackage.STATE_MANAGER__STATE_TYPES:
				getStateTypes().clear();
				getStateTypes().addAll((Collection<? extends State>)newValue);
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
			case BasePackage.STATE_MANAGER__STATE_TYPES:
				getStateTypes().clear();
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
			case BasePackage.STATE_MANAGER__STATE_TYPES:
				return stateTypes != null && !stateTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateManagerImpl
