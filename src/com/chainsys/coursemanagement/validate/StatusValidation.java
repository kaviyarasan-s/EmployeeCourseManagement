package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.EmployeeTopic;

public class StatusValidation {
	public static boolean addStatusValidation(EmployeeTopic employeeTopic) {
		boolean validationResult = true;
		if(employeeTopic.getTopic().getCourse().getId()>0)
		{
			if(employeeTopic.getTopic().getId()>0)
			{
				
				if(employeeTopic.getStatus().getId()>0)
				{
					if(employeeTopic.getEmployee().getId()<0)						
					{
						validationResult=false;
					}					
				}
				else
				{
					validationResult=false;
				}
			}
			else
			{
				validationResult=false;
			}
		}
		else
		{
			validationResult=false;
		}
		return validationResult;
	}
	
	public static boolean updateStatusValidation(EmployeeTopic employeeTopic) {
		boolean validationResult = true;
		if(employeeTopic.getTopic().getCourse().getId()>0)
		{
			if(employeeTopic.getTopic().getId()>0)
			{
				
				if(employeeTopic.getStatus().getId()>0)
				{
					if(employeeTopic.getEmployee().getId()<0)						
					{
						validationResult=false;
					}					
				}
				else
				{
					validationResult=false;
				}
			}
			else
			{
				validationResult=false;
			}
		}
		else
		{
			validationResult=false;
		}
		return validationResult;
	}
}
