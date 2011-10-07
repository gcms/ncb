package cvm.model;

import java.util.ArrayList;

/**
 * This class encapsulates all the data specific to any macro. 
 * This object simplifies the transfer of information across 
 * subsystems dealing with macros.
 * 
 * @author Frank Hernandez and Eduardo Monteiro
 *
 */
public class Macro 
{
	
	private ArrayList paramNameList;
	private ArrayList paramTypeList;
	private ArrayList thrownExceptions;
	private String script;
	private String returnType;
	private String name;
	
	/**
	 * This method returns a list containing the name
	 * of the parameters inside a macro.
	 * 
	 * @return List of Macro Parameter Names
	 */
	public ArrayList getParamNameList() 
	{
		return paramNameList;
	}
	
	/**
	 * This method sets the list of parameter names.
	 * 
	 * @param paramNameList
	 */
	public void setParamNameList(ArrayList paramNameList) 
	{
		this.paramNameList = paramNameList;
	}
	
	/**
	 * This method returns a list containing the types of 
	 * all the parameters in a macro.
	 * 
	 * @return List of type of parameters.
	 */
	public ArrayList getParamTypeList() 
	{
		return paramTypeList;
	}
	
	/**
	 * This method set the list of parameters types of a macro.
	 * @param paramTypeList
	 */
	public void setParamTypeList(ArrayList paramTypeList) 
	{
		this.paramTypeList = paramTypeList;
	}
	
	/**
	 * This function returns a string representation of a script.
	 * That is it retuns the execution section of a macro in string
	 * format.
	 * 
	 * @return String contining the execution section of a macro.
	 */
	public String getScript() 
	{
		return script;
	}
	
	/**
	 * Sets the execution section of a macro.
	 * @param script
	 */
	public void setScript(String script) 
	{
		this.script = script;
	}
	
	/**
	 * This method returns the return type of a macro.
	 * @return The return type of the macro.
	 */
	public String getReturnType() 
	{
		return returnType;
	}
	
	/**
	 * This method sets the return type of a macro.
	 * @param returnType
	 */
	public void setReturnType(String returnType) 
	{
		this.returnType = returnType;
	}
	
	/**
	 * This method returns the name of the macro
	 * @return Name of the macro.
	 */
	public String getName() 
	{
		return name;
	}
	
	/** 
	 * This sets the name of the macro.
	 * @param name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Assignes the list of thrown excpetions.
	 * @param thrownExceptions
	 */
	public void setThrownExceptions(ArrayList thrownExceptions)
	{
		this.thrownExceptions = thrownExceptions;
	}
	
	/**
	 * Returns the list of thrown exceptions.
	 * @return
	 */
	public ArrayList getThrownExceptions()
	{
		return this.thrownExceptions;
	}
	
	public String toString() {
		
		String temp = "";
		temp += "Name: " + this.name + "\n" +
				"Return Type: " + this.returnType + "\n";
		
		if (this.paramNameList != null)
			temp +=	"Parameter Names: " + this.paramNameList.toString() + "\n";
		if (this.paramTypeList != null)
			temp += "Parameter Types: " + this.paramTypeList.toString() + "\n";
		if (this.thrownExceptions != null)
			temp += "Exceptions: " + this.thrownExceptions + "\n";
			
		temp += "Script: " + this.script + "\n";
		
		return temp;
				
	}
}