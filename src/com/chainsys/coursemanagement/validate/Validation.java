package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.Employee;

public class Validation {

	public static boolean loginValidation(Employee employee)
	{		
		boolean validationResult=false;
		
		if(!employee.getEmail().equals(null)&&!employee.getEmail().isEmpty())	
		{			
			if(!employee.getPassword().equals(null)&&!employee.getPassword().isEmpty())
			{
				validationResult=true;
			}
		}				
		return validationResult;		
	}	
}
