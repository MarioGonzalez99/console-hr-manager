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

    public Payroll(int idEmployee, LocalDate payrollDate, double baseSalary, LocalDate creationDate, String createdBy){
        this.idEmployee = idEmployee;
        this.payrollDate = payrollDate;
        this.baseSalary = baseSalary;
        this.afpTax = this.baseSalary * (0.0725);
        this.isssTax = this.baseSalary * (0.03);
        if(this.baseSalary<472.01){
            this.rentTax = 0; 
        } else if(this.baseSalary>=472.01 && this.baseSalary<895.25){
            this.rentTax = (this.baseSalary-this.afpTax-this.isssTax) * (0.1); 
        } else if(this.baseSalary>=895.251 && this.baseSalary<2038.11){
            this.rentTax = (this.baseSalary-this.afpTax-this.isssTax) * (0.2); 
        } else {
            this.rentTax = (this.baseSalary-this.afpTax-this.isssTax) * (0.3); 
            
        }
        this.netSalary = this.baseSalary - this.afpTax - this.isssTax - this.rentTax;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }
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
