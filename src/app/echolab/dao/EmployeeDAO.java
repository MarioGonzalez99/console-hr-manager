/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.dao;

import app.echolab.entities.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Mario
 */
public class EmployeeDAO extends AbstractDAO<Employee>{
        @Override
    public String getTableName() {
        return "REG_EMP_EMPLEADO";
    }
    
    @Override
    public String getTableKey() {
        return "EMP_ID";
    }
    
    @Override
    protected String getSchema() {
        return "GESTION_EMPLEADOS";
    }

    @Override
    public String[] getTableColumns() {
        String[] str = {"EMP_ID","EMP_NOMBRE","EMP_APELLIDO","EMP_GENERO","EMP_CORREO",
            "EMP_FECHA_NACIMIENTO","EMP_FECHA_CONTRATACION","EST_ID","DEP_NOMBRE",
        "EMP_ID_JEFE","A_FECHA_CREA","A_USUARIO_CREA", "A_FECHA_MODIFICACION", "A_USUARIO_MODIFICA"};
        return str;
    }

    @Override
    protected Employee getMappingResults(ResultSet rs) throws SQLException{
            return new Employee(
                        rs.getInt("EMP_ID"), 
                        rs.getString("EMP_NOMBRE"), 
                        rs.getString("EMP_APELLIDO"), 
                        rs.getString("EMP_GENERO"), 
                        rs.getString("EMP_CORREO"), 
                        rs.getObject(6, LocalDate.class),
                        rs.getObject(7, LocalDate.class),
                        rs.getInt("EST_ID"),
                        rs.getString("DEP_NOMBRE"), 
                        rs.getInt("EMP_ID_JEFE"),
                        rs.getObject(11, LocalDate.class),
                        rs.getString("A_USUARIO_CREA"));
    }

    
    @Override
    public void setMappingParamsToInsert(PreparedStatement ps, Employee entity) throws SQLException {
        ps.setInt(      1, entity.getIdEmployee());
        ps.setString(   2, entity.getFirstName());
        ps.setString(   3, entity.getLastName());
        ps.setString(   4, entity.getGender());
        ps.setString(   5, entity.getEmailEmployee());
        ps.setObject(   6, entity.getBirthDate());
        ps.setObject(   7, entity.getHireDate());
        ps.setInt(      8, entity.getIdState());
        ps.setString(   9, entity.getDepartmentName());
        ps.setInt(      10, entity.getIdSuper());
        ps.setObject(   11, entity.getCreationDate());
        ps.setString(   12, entity.getCreatedBy());
        ps.setObject(   13, null);
        ps.setString(   14, null);
    }



    @Override
    protected void setMappingParamsToUpdate(PreparedStatement ps, Employee entity, String userModify, LocalDate modificationDate) throws SQLException {
        ps.setInt(      1, entity.getIdEmployee());
        ps.setString(   2, entity.getFirstName());
        ps.setString(   3, entity.getLastName());
        ps.setString(   4, entity.getGender());
        ps.setString(   5, entity.getEmailEmployee());
        ps.setObject(   6, entity.getBirthDate());
        ps.setObject(   7, entity.getHireDate());
        ps.setInt(      8, entity.getIdState());
        ps.setString(   9, entity.getDepartmentName());
        ps.setInt(      10, entity.getIdSuper());
        ps.setObject(   11, entity.getCreationDate());
        ps.setString(   12, entity.getCreatedBy());
        ps.setObject(   13, modificationDate);
        ps.setString(   14, userModify);
        ps.setInt(   15, entity.getIdEmployee());
    }
    
}
