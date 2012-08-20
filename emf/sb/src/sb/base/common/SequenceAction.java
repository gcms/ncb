/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base.common;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sequence Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.common.SequenceAction#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.common.CommonPackage#getSequenceAction()
 * @model
 * @generated
 */
public interface SequenceAction extends Action {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.common.ActionExecution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see sb.base.common.CommonPackage#getSequenceAction_Children()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ActionExecution> getChildren();

} // SequenceAction
