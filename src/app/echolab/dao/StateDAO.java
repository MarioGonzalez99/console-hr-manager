/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.dao;

import app.echolab.entities.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Mario
 */
public class StateDAO extends AbstractDAO<State>{
    @Override
    public String getTableName() {
        return "REG_EST_ESTADO_EMPLEADO";
    }
    
    @Override
    public String getTableKey() {
        return "EST_ID";
    }
    
    @Override
    protected String getTableKey2() {
        return null;
    }

    @Override
    public String[] getTableColumns() {
        String[] str = {"EST_ID","NOMBRE","A_FECHA_CREA","A_USUARIO_CREA", "A_FECHA_MODIFICACION", "A_USUARIO_MODIFICA"};
        return str;
    }

    @Override
    protected State getMappingResults(ResultSet rs) throws SQLException{
            return new State(
                        rs.getInt("EST_ID"),
                        rs.getString("NOMBRE"), 
                        rs.getObject(3, LocalDate.class),
                        rs.getString("A_USUARIO_CREA"));
    }

    
    @Override
    public void setMappingParamsToInsert(PreparedStatement ps, State entity) throws SQLException {
        ps.setInt(   1, entity.getIdState());
        ps.setString(   2, entity.getNameState());
        ps.setObject(   3, entity.getCreationDate());
        ps.setString(   4, entity.getCreatedBy());
        ps.setObject(   5, entity.getCreationDate());
        ps.setString(   6, entity.getCreatedBy());
        
    }



    @Override
    protected void setMappingParamsToUpdate(PreparedStatement ps, State entity, String userModify, LocalDate modificationDate) throws SQLException {
        ps.setInt(   1, entity.getIdState());
        ps.setString(   2, entity.getNameState());
        ps.setObject(   3, entity.getCreationDate());
        ps.setString(   4, entity.getCreatedBy());
        ps.setObject(   5, modificationDate);
        ps.setString(   6, userModify);
    }
    
}
