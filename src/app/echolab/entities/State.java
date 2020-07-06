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
public class State {
    private int idState;
    private String nameState;
    private LocalDate creationDate;
    private String createdBy;

    public State(int idState, String nameState, LocalDate creationDate, String createdBy) {
        this.idState = idState;
        this.nameState = nameState;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }
    
    public State(){}

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
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
        return nameState;
    }
}
