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
public class Department {
    private String depName;
    private LocalDate creationDate;
    private String createdBy;

    public Department(String depName, LocalDate creationDate, String createdBy) {
        this.depName = depName;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
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
        return "Departamento: "+this.depName;
    }
}
