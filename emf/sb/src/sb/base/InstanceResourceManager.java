/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sb.base;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance Resource Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sb.base.InstanceResourceManager#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see sb.base.BasePackage#getInstanceResourceManager()
 * @model
 * @generated
 */
public interface InstanceResourceManager extends ResourceManager {
	/**
	 * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
	 * The list contents are of type {@link sb.base.Instance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see sb.base.BasePackage#getInstanceResourceManager_Instances()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instance> getInstances();

} // InstanceResourceManager
