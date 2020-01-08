package com.alten.springboot.taskmanager.controller;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.alten.springboot.taskmanager.business_service.TaskBusinessService;
import com.alten.springboot.taskmanager.dto.TaskDto;



@Component
public class TaskRestController implements ITaskRestController{

	@Autowired
	private TaskBusinessService taskService;

	@Override
	public List<TaskDto> getTasks() {
		return taskService.findAll();
	}

	@Override
	public TaskDto getTask(
			 @PathParam("taskId") String taskId) {

		TaskDto theTask = taskService.findById(Integer.parseInt(taskId));

		return theTask;
	}

	@Override
	public TaskDto addTask(
			TaskDto theTask) {
		theTask.setId(0); // cioè inserisco, perche provo ad aggiornare ma l'id 0 non esiste

		// se vuoi portare fuori le logiche strettamente di business
		// 1) employeeService.insertTask(taskEntity, employId);
		// 2) employeeService.findById(employId);
		// 3) emplooyee.size() > 5
		// 4) update attribute
		// 5) employeeService.updateEmplyee(employee)

		// se NON vuoi portare fuori le logiche strettamente di business
		// 1) employeeService.insertTask(taskEntity, employId);
		theTask = taskService.save(theTask);
		return theTask;
	}

	@Override
	public TaskDto updateTaskAdmin(
			@RequestBody TaskDto theTask) {
		
		taskService.update(theTask);
		return theTask;
	}

	@Override
	public TaskDto updateTask(
			@RequestBody TaskDto theTask) {
		TaskDto oldTask = taskService.findById(theTask.getId());
		

		if (theTask.getRealStartTime() != null) {
			oldTask.setRealStartTime(theTask.getRealStartTime());
		}
		if (theTask.getRealEndTime() != null) {
			oldTask.setRealEndTime(theTask.getRealEndTime());
		}

		taskService.update(oldTask);

		return oldTask;
	}

	@Override
	public String deleteTask(
			 @PathParam("taskId") String taskId ) {

		taskService.delete(Integer.parseInt(taskId));

		return "Deleted task with id: " + taskId;

	}

	@Override
	public List<TaskDto> getTasksByEmployeeId(
			@PathParam("employeeId") String employeeId) {

		List<TaskDto> tasks = taskService.findByEmployeeId(Integer.parseInt(employeeId));
		return tasks;
	}

}
