package org.example.dao.daoImpl;

import org.example.config.Config;
import org.example.dao.EmployeeDao;
import org.example.model.Employee;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public void createEmployee() {
        String sql = "create table if not exists employeess(" +
                "id serial primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "age int," +
                "email varchar," +
                "job_id int references jobs(id));";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void addEmployee(Employee employee) {
        String sql = "insert into employeess (first_name,last_name,age,email,job_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void dropTable() {
        String sql = "drop table if exists employeess;";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("employeess is table deleted!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void cleanTable() {
        String sql = "truncate employeess;";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Cleaned!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = "UPDATE employeess SET first_name = ?, last_name = ?, age = ?,email = ?, job_id = ? WHERE id = ?;";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5,employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Employeess with id: " + id + " successfully updated!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employeess;";
        List<Employee> getAll = new ArrayList<>();
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                getAll.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getAll;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = null;
        String sql = "select * from employeess where email = ?;";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee,Job> employeeJobMap = new HashMap<>();
        String sql = "select * from employeess  join jobs j on employeess.job_id = j.id where employeess.id = ?;";
        try(Connection connection = Config.getConnection();
            PreparedStatement  preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setLong(1,employeeId);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                employeeJobMap.put(new Employee( result.getLong("id"),
                                result.getString ("first_name"),
                                result.getString("last_name"),
                                result.getInt("age"),
                                result.getString("email"),
                                result.getInt("job_id")),
                        new Job(result.getLong("id"),
                                result.getString("position"),
                                result.getString("profession"),
                                result.getString("description"),
                                result.getInt("experience"))
                );
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employeeJobMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee>employees = new ArrayList<>();
        String sql = "select * from employeess join jobs j on employeess.job_id = j.id where j.position in (?);";
        try(Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString( 1,position);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                employees.add(new Employee(
                        result.getLong("id"),
                        result.getString ("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getString("email"),
                        result.getInt("job_id")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
