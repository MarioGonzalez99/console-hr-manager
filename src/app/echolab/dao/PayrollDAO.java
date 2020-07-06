/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.dao;

import app.echolab.entities.Payroll;
import static java.lang.String.valueOf;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario
 */
public class PayrollDAO extends AbstractDAO<Payroll>{
     @Override
    public String getTableName() {
        return "REG_PLAN_PLANILLA";
    }
    
    @Override
    public String getTableKey() {
        return "EMP_ID";
    }
    
    @Override
    protected String getTableKey2() {
        return "PLAN_FECHA_PLANILLA";
    }
    @Override
    public String[] getTableColumns() {
        String[] str = {"EMP_ID","PLAN_FECHA_PLANILLA","PLAN_SALARIO_BASE","PLAN_MONTO_DESCUENTO_AFP","PLAN_MONTO_DESCUENTO_ISSS",
            "PLAN_MONTO_DESCUENTO_RENTA","PLAN_SALARIO_NETO","A_FECHA_CREA","A_USUARIO_CREA", "A_FECHA_MODIFICACION", "A_USUARIO_MODIFICA"};
        return str;
    }

    @Override
    protected Payroll getMappingResults(ResultSet rs) throws SQLException{
            return new Payroll(
                        rs.getInt("EMP_ID"), 
                        rs.getObject(2, LocalDate.class),
                        rs.getDouble("PLAN_SALARIO_BASE"), 
                        rs.getDouble("PLAN_MONTO_DESCUENTO_AFP"), 
                        rs.getDouble("PLAN_MONTO_DESCUENTO_ISSS"), 
                        rs.getDouble("PLAN_MONTO_DESCUENTO_RENTA"), 
                        rs.getDouble("PLAN_SALARIO_NETO"), 
                        rs.getObject(8, LocalDate.class),
                        rs.getString("A_USUARIO_CREA"));
    }

    
    @Override
    public void setMappingParamsToInsert(PreparedStatement ps, Payroll entity) throws SQLException {
        ps.setInt(      1, entity.getIdEmployee());
        ps.setObject(   2, entity.getPayrollDate());
        ps.setDouble(   3, entity.getBaseSalary());
        ps.setDouble(   4, entity.getAfpTax());
        ps.setDouble(   5, entity.getIsssTax());
        ps.setDouble(   6, entity.getRentTax());
        ps.setDouble(   7, entity.getNetSalary());
        ps.setObject(   8, entity.getCreationDate());
        ps.setString(   9, entity.getCreatedBy());
        ps.setObject(   10, entity.getCreationDate());
        ps.setString(   11, entity.getCreatedBy());
    }



    @Override
    protected void setMappingParamsToUpdate(PreparedStatement ps, Payroll entity, String userModify, LocalDate modificationDate) throws SQLException {
        ps.setInt(      1, entity.getIdEmployee());
        ps.setObject(   2, entity.getPayrollDate());
        ps.setDouble(   3, entity.getBaseSalary());
        ps.setDouble(   4, entity.getAfpTax());
        ps.setDouble(   5, entity.getIsssTax());
        ps.setDouble(   6, entity.getRentTax());
        ps.setDouble(   7, entity.getNetSalary());
        ps.setObject(   8, entity.getCreationDate());
        ps.setString(   9, entity.getCreatedBy());
        ps.setObject(   10, modificationDate);
        ps.setString(   11, userModify);
        ps.setInt(      12, entity.getIdEmployee());
    }
    
    public List<Payroll> getHistoryPayroll(int idEmployee) throws ClassNotFoundException, SQLException{
        List<Payroll> list = getAllData();
        List<Payroll> result = new ArrayList<>();
        list.stream().filter(p -> (p.toString().contains("ID: "+String.valueOf(idEmployee)))).forEachOrdered(p -> {
            result.add(p);
         });
        return result;
    }
}
