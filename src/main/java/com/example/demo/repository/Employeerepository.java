package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;

public interface Employeerepository extends JpaRepository<Employee, Short> {

	List<Employee> findByName(String name);

	Optional<Employee> findByMobile(String mobile);
}
