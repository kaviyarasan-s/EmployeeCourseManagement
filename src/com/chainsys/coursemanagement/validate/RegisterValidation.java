package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.Employee;

public class RegisterValidation {
	
	
	public static boolean registerValidation(Employee employee)
	{
		boolean validationResult=false;
		if(!employee.getFirstName().equals(null)&&!employee.getFirstName().isEmpty()&&employee.getFirstName().matches("[a-zA-Z]+"))
		{
			if(!employee.getLastName().equals(null)&&!employee.getLastName().isEmpty() &&employee.getLastName().matches("[a-zA-Z]+"))
			{
				if(!employee.getPhonenumber().equals(null)&&!employee.getPhonenumber().isEmpty()&& employee.getPhonenumber().length()==10 &&employee.getPhonenumber().matches("[0-9]+"))
				{
					if(!employee.getEmail().equals(null)&&!employee.getEmail().isEmpty())
					{
						if(!employee.getPassword().equals(null)&&!employee.getPassword().isEmpty())
						{
							if(employee.getDepartment().getId()>0)
							{
								if(employee.getJob().getId()>0)
								{
									validationResult=true;
								}
							}
						}
					}
				}
			}
			
		}
		
		return validationResult;
	}

}
