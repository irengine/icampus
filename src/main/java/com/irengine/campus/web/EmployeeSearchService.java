package com.irengine.campus.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import com.irengine.campus.web.xml.Employee;
import com.irengine.campus.web.xml.EmployeeList;
import org.springframework.stereotype.Service;

@Service("employeeSearchService")
public class EmployeeSearchService {

	private static Logger logger = Logger.getLogger(EmployeeSearchService.class);

	public Message<EmployeeList> getEmployee(Message<?> inMessage){

		EmployeeList employeeList = new EmployeeList();
		Map<String, Object> responseHeaderMap = new HashMap<String, Object>();

		try{
			MessageHeaders headers = inMessage.getHeaders();
			String id = (String)headers.get("employeeId");
			boolean isFound;
			if (id.equals("1")){
				employeeList.getEmployee().add(new Employee(1, "John", "Doe"));
				isFound = true;
			}else if (id.equals("2")){
				employeeList.getEmployee().add(new Employee(2, "Jane", "Doe"));
				isFound = true;
			}else if (id.equals("0")){
				employeeList.getEmployee().add(new Employee(1, "John", "Doe"));
				employeeList.getEmployee().add(new Employee(2, "Jane", "Doe"));
				isFound = true;
			}else{
				isFound = false;
			}
			if (isFound){
				setReturnStatusAndMessage("0", "Success", employeeList, responseHeaderMap);
			}else{
				setReturnStatusAndMessage("2", "Employee Not Found", employeeList, responseHeaderMap);
			}

		}catch (Throwable e){
			setReturnStatusAndMessage("1", "System Error", employeeList, responseHeaderMap);
			logger.error("System error occured :"+e);
		}
		Message<EmployeeList> message = new GenericMessage<EmployeeList>(employeeList, responseHeaderMap);
		return message;
	}

	private void setReturnStatusAndMessage(String status,
						String message,
						EmployeeList employeeList,
						Map<String, Object> responseHeaderMap){

		employeeList.setReturnStatus(status);
		employeeList.setReturnStatusMsg(message);
		responseHeaderMap.put("Return-Status", status);
		responseHeaderMap.put("Return-Status-Msg", message);
	}
}


