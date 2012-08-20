/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.policy.impl;

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

import sb.base.common.Parameter;

import sb.base.policy.PolicyEvaluation;
import sb.base.policy.PolicyEvaluationHandler;
import sb.base.policy.PolicyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Evaluation Handler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationHandlerImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationHandlerImpl#getHandlerImpl <em>Handler Impl</em>}</li>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationHandlerImpl#getEvaluation <em>Evaluation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PolicyEvaluationHandlerImpl extends EObjectImpl implements PolicyEvaluationHandler {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The default value of the '{@link #getHandlerImpl() <em>Handler Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlerImpl()
	 * @generated
	 * @ordered
	 */
	protected static final String HANDLER_IMPL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHandlerImpl() <em>Handler Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlerImpl()
	 * @generated
	 * @ordered
	 */
	protected String handlerImpl = HANDLER_IMPL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEvaluation() <em>Evaluation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvaluation()
	 * @generated
	 * @ordered
	 */
	protected PolicyEvaluation evaluation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicyEvaluationHandlerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PolicyPackage.Literals.POLICY_EVALUATION_HANDLER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHandlerImpl() {
		return handlerImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHandlerImpl(String newHandlerImpl) {
		String oldHandlerImpl = handlerImpl;
		handlerImpl = newHandlerImpl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION_HANDLER__HANDLER_IMPL, oldHandlerImpl, handlerImpl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyEvaluation getEvaluation() {
		return evaluation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvaluation(PolicyEvaluation newEvaluation, NotificationChain msgs) {
		PolicyEvaluation oldEvaluation = evaluation;
		evaluation = newEvaluation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION, oldEvaluation, newEvaluation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvaluation(PolicyEvaluation newEvaluation) {
		if (newEvaluation != evaluation) {
			NotificationChain msgs = null;
			if (evaluation != null)
				msgs = ((InternalEObject)evaluation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION, null, msgs);
			if (newEvaluation != null)
				msgs = ((InternalEObject)newEvaluation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION, null, msgs);
			msgs = basicSetEvaluation(newEvaluation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION, newEvaluation, newEvaluation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION:
				return basicSetEvaluation(null, msgs);
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
			case PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS:
				return getParameters();
			case PolicyPackage.POLICY_EVALUATION_HANDLER__HANDLER_IMPL:
				return getHandlerImpl();
			case PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION:
				return getEvaluation();
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
			case PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case PolicyPackage.POLICY_EVALUATION_HANDLER__HANDLER_IMPL:
				setHandlerImpl((String)newValue);
				return;
			case PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION:
				setEvaluation((PolicyEvaluation)newValue);
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
			case PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS:
				getParameters().clear();
				return;
			case PolicyPackage.POLICY_EVALUATION_HANDLER__HANDLER_IMPL:
				setHandlerImpl(HANDLER_IMPL_EDEFAULT);
				return;
			case PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION:
				setEvaluation((PolicyEvaluation)null);
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
			case PolicyPackage.POLICY_EVALUATION_HANDLER__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case PolicyPackage.POLICY_EVALUATION_HANDLER__HANDLER_IMPL:
				return HANDLER_IMPL_EDEFAULT == null ? handlerImpl != null : !HANDLER_IMPL_EDEFAULT.equals(handlerImpl);
			case PolicyPackage.POLICY_EVALUATION_HANDLER__EVALUATION:
				return evaluation != null;
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
		result.append(" (handlerImpl: ");
		result.append(handlerImpl);
		result.append(')');
		return result.toString();
	}

} //PolicyEvaluationHandlerImpl
