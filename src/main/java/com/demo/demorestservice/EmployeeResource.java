package com.demo.demorestservice;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employees")
public class EmployeeResource {

    @Inject
    private DatabaseConnector databaseConnector;

    @GET
    @Produces("application/json")
    public List<Employee> getEmployees() {
        return databaseConnector.getEmployees();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Employee getEmployee(@PathParam("id") int id) {
        return databaseConnector.getEmployee(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createEmployee(Employee employee) {
        databaseConnector.addEmployee(employee);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateEmployee(@PathParam("id") int id, Employee employee) {
        Employee existingEmployee = databaseConnector.getEmployee(id);
        if (existingEmployee == null) {
            return Response.status(404).build();
        }
        existingEmployee.setName(employee.getName());
        existingEmployee.setPosition(employee.getPosition());
        databaseConnector.updateEmployee(existingEmployee);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        Employee existingEmployee = databaseConnector.getEmployee(id);
        if (existingEmployee == null) {
            return Response.status(404).build();
        }
        databaseConnector.deleteEmployee(id);
        return Response.status(204).build();
    }
}
