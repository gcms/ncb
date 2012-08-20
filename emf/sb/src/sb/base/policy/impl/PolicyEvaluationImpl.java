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

import sb.base.common.ParameterBinding;
import sb.base.common.Value;

import sb.base.policy.PolicyEvaluation;
import sb.base.policy.PolicyEvaluationContext;
import sb.base.policy.PolicyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Evaluation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationImpl#getContextBinding <em>Context Binding</em>}</li>
 *   <li>{@link sb.base.policy.impl.PolicyEvaluationImpl#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PolicyEvaluationImpl extends EObjectImpl implements PolicyEvaluation {
	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected Value feature;

	/**
	 * The cached value of the '{@link #getContextBinding() <em>Context Binding</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextBinding()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterBinding> contextBinding;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected PolicyEvaluationContext context;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicyEvaluationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PolicyPackage.Literals.POLICY_EVALUATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeature(Value newFeature, NotificationChain msgs) {
		Value oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION__FEATURE, oldFeature, newFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(Value newFeature) {
		if (newFeature != feature) {
			NotificationChain msgs = null;
			if (feature != null)
				msgs = ((InternalEObject)feature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION__FEATURE, null, msgs);
			if (newFeature != null)
				msgs = ((InternalEObject)newFeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION__FEATURE, null, msgs);
			msgs = basicSetFeature(newFeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION__FEATURE, newFeature, newFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterBinding> getContextBinding() {
		if (contextBinding == null) {
			contextBinding = new EObjectContainmentEList<ParameterBinding>(ParameterBinding.class, this, PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING);
		}
		return contextBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyEvaluationContext getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContext(PolicyEvaluationContext newContext, NotificationChain msgs) {
		PolicyEvaluationContext oldContext = context;
		context = newContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION__CONTEXT, oldContext, newContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(PolicyEvaluationContext newContext) {
		if (newContext != context) {
			NotificationChain msgs = null;
			if (context != null)
				msgs = ((InternalEObject)context).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION__CONTEXT, null, msgs);
			if (newContext != null)
				msgs = ((InternalEObject)newContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_EVALUATION__CONTEXT, null, msgs);
			msgs = basicSetContext(newContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_EVALUATION__CONTEXT, newContext, newContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PolicyPackage.POLICY_EVALUATION__FEATURE:
				return basicSetFeature(null, msgs);
			case PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING:
				return ((InternalEList<?>)getContextBinding()).basicRemove(otherEnd, msgs);
			case PolicyPackage.POLICY_EVALUATION__CONTEXT:
				return basicSetContext(null, msgs);
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
			case PolicyPackage.POLICY_EVALUATION__FEATURE:
				return getFeature();
			case PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING:
				return getContextBinding();
			case PolicyPackage.POLICY_EVALUATION__CONTEXT:
				return getContext();
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
			case PolicyPackage.POLICY_EVALUATION__FEATURE:
				setFeature((Value)newValue);
				return;
			case PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING:
				getContextBinding().clear();
				getContextBinding().addAll((Collection<? extends ParameterBinding>)newValue);
				return;
			case PolicyPackage.POLICY_EVALUATION__CONTEXT:
				setContext((PolicyEvaluationContext)newValue);
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
			case PolicyPackage.POLICY_EVALUATION__FEATURE:
				setFeature((Value)null);
				return;
			case PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING:
				getContextBinding().clear();
				return;
			case PolicyPackage.POLICY_EVALUATION__CONTEXT:
				setContext((PolicyEvaluationContext)null);
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
			case PolicyPackage.POLICY_EVALUATION__FEATURE:
				return feature != null;
			case PolicyPackage.POLICY_EVALUATION__CONTEXT_BINDING:
				return contextBinding != null && !contextBinding.isEmpty();
			case PolicyPackage.POLICY_EVALUATION__CONTEXT:
				return context != null;
		}
		return super.eIsSet(featureID);
	}

} //PolicyEvaluationImpl
