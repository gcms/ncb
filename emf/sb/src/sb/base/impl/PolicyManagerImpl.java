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
import sb.base.PolicyManager;

import sb.base.policy.Policy;
import sb.base.policy.PolicyEvaluationHandler;
import sb.base.policy.PolicyEvaluationPoint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Policy Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.impl.PolicyManagerImpl#getPoints <em>Points</em>}</li>
 *   <li>{@link sb.base.impl.PolicyManagerImpl#getHandlers <em>Handlers</em>}</li>
 *   <li>{@link sb.base.impl.PolicyManagerImpl#getPolicies <em>Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PolicyManagerImpl extends EObjectImpl implements PolicyManager {
	/**
	 * The cached value of the '{@link #getPoints() <em>Points</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<PolicyEvaluationPoint> points;

	/**
	 * The cached value of the '{@link #getHandlers() <em>Handlers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlers()
	 * @generated
	 * @ordered
	 */
	protected EList<PolicyEvaluationHandler> handlers;

	/**
	 * The cached value of the '{@link #getPolicies() <em>Policies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPolicies()
	 * @generated
	 * @ordered
	 */
	protected EList<Policy> policies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicyManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.POLICY_MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PolicyEvaluationPoint> getPoints() {
		if (points == null) {
			points = new EObjectContainmentEList<PolicyEvaluationPoint>(PolicyEvaluationPoint.class, this, BasePackage.POLICY_MANAGER__POINTS);
		}
		return points;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PolicyEvaluationHandler> getHandlers() {
		if (handlers == null) {
			handlers = new EObjectContainmentEList<PolicyEvaluationHandler>(PolicyEvaluationHandler.class, this, BasePackage.POLICY_MANAGER__HANDLERS);
		}
		return handlers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Policy> getPolicies() {
		if (policies == null) {
			policies = new EObjectContainmentEList<Policy>(Policy.class, this, BasePackage.POLICY_MANAGER__POLICIES);
		}
		return policies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.POLICY_MANAGER__POINTS:
				return ((InternalEList<?>)getPoints()).basicRemove(otherEnd, msgs);
			case BasePackage.POLICY_MANAGER__HANDLERS:
				return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
			case BasePackage.POLICY_MANAGER__POLICIES:
				return ((InternalEList<?>)getPolicies()).basicRemove(otherEnd, msgs);
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
			case BasePackage.POLICY_MANAGER__POINTS:
				return getPoints();
			case BasePackage.POLICY_MANAGER__HANDLERS:
				return getHandlers();
			case BasePackage.POLICY_MANAGER__POLICIES:
				return getPolicies();
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
			case BasePackage.POLICY_MANAGER__POINTS:
				getPoints().clear();
				getPoints().addAll((Collection<? extends PolicyEvaluationPoint>)newValue);
				return;
			case BasePackage.POLICY_MANAGER__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends PolicyEvaluationHandler>)newValue);
				return;
			case BasePackage.POLICY_MANAGER__POLICIES:
				getPolicies().clear();
				getPolicies().addAll((Collection<? extends Policy>)newValue);
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
			case BasePackage.POLICY_MANAGER__POINTS:
				getPoints().clear();
				return;
			case BasePackage.POLICY_MANAGER__HANDLERS:
				getHandlers().clear();
				return;
			case BasePackage.POLICY_MANAGER__POLICIES:
				getPolicies().clear();
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
			case BasePackage.POLICY_MANAGER__POINTS:
				return points != null && !points.isEmpty();
			case BasePackage.POLICY_MANAGER__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasePackage.POLICY_MANAGER__POLICIES:
				return policies != null && !policies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PolicyManagerImpl
