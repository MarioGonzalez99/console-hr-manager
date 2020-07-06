/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.dao;

import app.echolab.entities.Department;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Mario
 */
public class DepartmentDAO extends AbstractDAO<Department> {

        @Override
    public String getTableName() {
        return "REG_DEP_DEPARTAMENTO";
    }
    
    @Override
    public String getTableKey() {
        return "DEP_NOMBRE";
    }

    @Override
    public String[] getTableColumns() {
        String[] str = {"DEP_NOMBRE","A_FECHA_CREA","A_USUARIO_CREA", "A_FECHA_MODIFICACION", "A_USUARIO_MODIFICA"};
        return str;
    }

    @Override
    protected Department getMappingResults(ResultSet rs) throws SQLException{
            return new Department(
                        rs.getString("DEP_NOMBRE"), 
                        rs.getObject(11, LocalDate.class),
                        rs.getString("A_USUARIO_CREA"));
    }

    
    @Override
    public void setMappingParamsToInsert(PreparedStatement ps, Department entity) throws SQLException {
        ps.setString(   1, entity.getDepName());
        ps.setObject(   2, entity.getCreationDate());
        ps.setString(   3, entity.getCreatedBy());
        ps.setObject(   4, entity.getCreationDate());
        ps.setString(   5, entity.getCreatedBy());
    }



    @Override
    protected void setMappingParamsToUpdate(PreparedStatement ps, Department entity, String userModify, LocalDate modificationDate) throws SQLException {
        ps.setString(   1, entity.getDepName());
        ps.setObject(   2, entity.getCreationDate());
        ps.setString(   3, entity.getCreatedBy());
        ps.setObject(   4, modificationDate);
        ps.setString(   5, userModify);
        ps.setString(      6, entity.getDepName());
    }
    
}
