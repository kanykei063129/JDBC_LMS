package org.example;

import org.example.config.Config;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.service.EmployeeService;
import org.example.service.JobService;
import org.example.service.serviceImpl.EmployeeServiceImpl;
import org.example.service.serviceImpl.JobServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();

////        employeeService.createEmployee();
//        employeeService.addEmployee(new Employee("Kanykei","Akjoltoi kyzy",17,"akjoltoikyzykanykei@gmail.com", 1));
//        employeeService.addEmployee(new Employee("Madina","Halikova",16,"halikovamadina@gmail.com",4));
//        employeeService.addEmployee(new Employee("Adilet","Islambek uulu",22,"islambekuuluadilet@gmail.com",2));
//        employeeService.dropTable();
//        employeeService.cleanTable();
//        employeeService.updateEmployee(5L,new Employee("Sanzhar","Abdumomunov",20,"abdumomunovsanzhar@gmail.com",5));
//        System.out.println(employeeService.getAllEmployees());
//        System.out.println(employeeService.findByEmail("akjoltoikyzykanykei@gmail.com"));
//        System.out.println(employeeService.getEmployeeById(4L));
//        System.out.println(employeeService.getEmployeeByPosition("Mentor"));

//        jobService.createJobTable();
//        jobService.addJob(new Job("Mentor","Java","Backend developer",3));
//        jobService.addJob(new Job("Mentor","JS","Fronted developer",1));
//        jobService.addJob(new Job("Management","JS","Fronted developer",8));
//        jobService.addJob(new Job("Instructor","Java","Backend developer",5));
//        jobService.addJob(new Job("Instructor","JS","Fronted developer",2));
//        System.out.println(jobService.getJobById(1L));
//        System.out.println(jobService.sortByExperience("asc"));
//        System.out.println(jobService.getJobByEmployeeId(5L));
        jobService.deleteDescriptionColumn();
    }
}