/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.entities;

import java.time.LocalDate;

/**
 *
 * @author Mario
 */
public class Payroll {
    private int idEmployee;
    private LocalDate payrollDate;
    private double baseSalary;
    private double afpTax;
    private double isssTax;
    private double rentTax;
    private double netSalary;
    private LocalDate creationDate;
    private String createdBy;

    public Payroll(int idEmployee, LocalDate payrollDate, double baseSalary, double afpTax, double isssTax, double rentTax, double netSalary, LocalDate creationDate, String createdBy) {
        this.idEmployee = idEmployee;
        this.payrollDate = payrollDate;
        this.baseSalary = baseSalary;
        this.afpTax = afpTax;
        this.isssTax = isssTax;
        this.rentTax = rentTax;
        this.netSalary = netSalary;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }
    public Payroll(){}

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDate getPayrollDate() {
        return payrollDate;
    }

    public void setPayrollDate(LocalDate payrollDate) {
        this.payrollDate = payrollDate;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getAfpTax() {
        return afpTax;
    }

    public void setAfpTax(double afpTax) {
        this.afpTax = afpTax;
    }

    public double getIsssTax() {
        return isssTax;
    }

    public void setIsssTax(double isssTax) {
        this.isssTax = isssTax;
    }

    public double getRentTax() {
        return rentTax;
    }

    public void setRentTax(double rentTax) {
        this.rentTax = rentTax;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    @Override
    public String toString(){
        return "| ID: "+idEmployee+" | Fecha Planilla: "+payrollDate+"| Salario Base: "+baseSalary+" | Descuento AFP: "+afpTax+" | Descuento ISSS "+isssTax+" | Descuento Renta"+rentTax+" | Salario Neto: "+netSalary+" |";
    }
}
