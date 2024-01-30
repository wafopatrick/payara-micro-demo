package com.demo.demorestservice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DatabaseConnector {

    @PersistenceContext(unitName = "demo")
    private EntityManager entityManager;

    @Transactional
    public List<Employee> getEmployees() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    @Transactional
    public Employee getEmployee(int id) {
        return entityManager.find(Employee.class, id);
    }

    @Transactional
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        entityManager.merge(employee);
    }

    @Transactional
    public void deleteEmployee(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }

}