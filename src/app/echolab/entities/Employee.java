/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.entities;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Mario
 */
public class Employee {
    private int idEmployee;
    private String firstName;
    private String lastName;
    private String gender;
    private String emailEmployee;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private int idState;
    private String departmentName;
    private int idSuper;
    private LocalDate creationDate;
    private String createdBy;
    
    
    public Employee(int idEmployee, String firstName, String lastName, String gender, String emailEmployee, LocalDate birthDate, LocalDate hireDate, int idState, String departmentName, int idSuper, LocalDate creationDate, String createdBy) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailEmployee = emailEmployee;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.idState = idState;
        this.departmentName = departmentName;
        this.idSuper = idSuper;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }
    
    public Employee(){}
    

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public int getIdSuper() {
        return idSuper;
    }

    public void setIdSuper(int idSuper) {
        this.idSuper = idSuper;
    }
    
    public LocalDate getCreationDate(){
        return creationDate;
    }
    
    public String getCreatedBy(){
        return createdBy;
    }
    
    @Override
    public String toString() {
        return "Empleado(" + "idEmployee=" + idEmployee + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", email=" + emailEmployee + ", birthDate=" + birthDate + ", hireDate="+hireDate+", idState="+idState+", departmentName="+departmentName+", idSuper="+idSuper+'}';
    }
}
