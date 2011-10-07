package edu.fiu.strg.ACSTF.manager.knowledge;

import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author A.C.I.D
 *
 */
public class Mapping
{
	
 // XML Operation Constant Declaration Strings
	private final static String equalOp = "eq";
	private final static String greaterThanOp = "gt";
	private final static String lessThanOp = "lt";
	private final static String notEqualsOp = "ne";
    
 // Attributes
	private String accessor;
	private String operator;
	private String value;
	
	
	public Mapping(String accessor, String operator,String value)
	{
		super();
		this.accessor = accessor;
		this.operator = operator;
		this.value = value;
	}

	public boolean isTypeBoolean() {
		return this.value.equalsIgnoreCase("true") || 
		       this.value.equalsIgnoreCase("false");
	}
	
	public boolean isTypeInteger() {
	  try {
	     Integer.parseInt (this.value);
	     return true;
	  }
      catch (NumberFormatException e) {
		 return false;
	  } 
	}
	
	public boolean isTypeDouble() {
	  try {
	    Double.parseDouble (this.value);
	    return true;
	  }
	  catch (NumberFormatException e) {
	    return false;
	  } 
	}

	public boolean isTypeString() {
		return !(isTypeBoolean() || isTypeInteger() || isTypeDouble());
	}
	
	public boolean isSatisfied(Object object) {

		// Integer Comparisons
		if (isTypeBoolean()) {
			if (this.operator.equals(equalOp)) {
				return booleanEqualsMapping(object);
			}	
			else if (this.operator.equals(notEqualsOp)) {
				return !(booleanEqualsMapping(object));
			}
		}
		else if (isTypeInteger()) {
			if (this.operator.equals(equalOp)) {
				return intEqualsMapping(object);
			}
			else if (this.operator.equals(greaterThanOp)) {
				return intGreaterThanMapping(object);
			}
			else if (this.operator.equals(lessThanOp)) {
				return intLessThanMapping(object);
			}
		}
		// Double Comparisons
		else if(isTypeDouble()) {
			if (this.operator.equals(equalOp)) {
				return doubleEqualsMapping(object);
			}
			else if (this.operator.equals(greaterThanOp)) {
				return doubleGreaterThanMapping(object);
			}
			else if (this.operator.equals(lessThanOp)) {
				return doubleLessThanMapping(object);
			}
		}
		// String Comparisons
		else {
			if (this.operator.equals(equalOp)) {
				return stringEqualsMapping(object);
			}
			else if (this.operator.equals(notEqualsOp)) {
				return !stringEqualsMapping(object);
			}	
		}
		// Default, type or operator not found
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean stringEqualsMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return ((Boolean) retObj.equals(this.getValue()));
		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }

	@SuppressWarnings("unchecked")
	public boolean intEqualsMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Integer)(retObj)).intValue() == Integer.parseInt(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean intGreaterThanMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Integer)(retObj)).intValue() > Integer.parseInt(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean intLessThanMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Integer)(retObj)).intValue() < Integer.parseInt(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean doubleEqualsMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Double)(retObj)).doubleValue() == Double.parseDouble(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean doubleGreaterThanMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Double)(retObj)).doubleValue() > Double.parseDouble(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean doubleLessThanMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Double)(retObj)).doubleValue() < Double.parseDouble(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }
	
	@SuppressWarnings("unchecked")
	public boolean booleanEqualsMapping (Object object) {
		try {
			Class objectClass = object.getClass();
			Method method1 = objectClass.getMethod(this.accessor);
			Object retObj = method1.invoke(object);
			return (((Boolean)(retObj)).booleanValue() == Boolean.parseBoolean(this.getValue()));		}
		catch (IllegalAccessException e) {
     		e.printStackTrace();
	    }	 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}	 
        catch (NoSuchMethodException e) {
			e.printStackTrace();
        }	 	  
     return false;  	  
	 }

	
	public String getVariable()
	{
		return accessor;
	}

	public void setVariable(String variable)
	{
		this.accessor = variable;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
	public static void main (String args[]) {
        Mapping m = new Mapping("getState", "==","true");
		System.out.println(m.isTypeDouble());
	}
}
