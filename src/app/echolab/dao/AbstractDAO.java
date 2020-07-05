/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.dao;

import app.echolab.connection.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mario
 */
public abstract class AbstractDAO<T>{
        /**
    * Constante de cantidad máxima de registros a obtener por la consulta All
    */
    public final int LIMIT_RECORDS = 1000;
    /**
    * Constante para generar una instrucción SELECT a la BD
    */
    public final String SQL_SELECT = "SELECT [COLUMNS] FROM [SCHEMA].[TABLE]";
    /**
    * Constante para generar una instrucción INSERT a la BD
    */
    public final String SQL_INSERT = "INSERT INTO [SCHEMA].[TABLE] ([COLUMNS]) VALUES ([COLUMNS_INDEX])";
    /**
    * Constante para generar el WHERE de uns instrucción SQL
    */
    public final String SQL_WHERE = " WHERE ";
    /**
    * Constante para generar una instrucción DELETE a la BD
    */
    public final String SQL_DELETE = "DELETE FROM [SCHEMA].[TABLE] WHERE [CONDITIONS]";
    /**
    * Constante para generar una instrucción UPDATE a la BD
    */
    public final String SQL_UPDATE = "UPDATE [SCHEMA].[TABLE] SET [COLUMNS] WHERE [CONDITIONS]";
    /**
    * Constante para indicar la expresión comodin de indicador de reemplazo [TABLE]
    */
    private final String TABLE_INDICATOR = "[TABLE]";
    /**
    * Constante para indicar la expresión comodin de indicador de reemplazo [SCHEMA]
    */
    private final String SCHEMA_INDICATOR = "[SCHEMA]";
    /**
    * Constante para indicar la expresión comodin de indicador de reemplazo [COLUMNS]
    */
    private final String COLUMNS_INDICATOR = "[COLUMNS]";
    /**
    * Constante para indicar la expresión comodin de indicador de reemplazo [COLUMNS_INDEX]
    */
    private final String COLUMNS_INDICATOR_INDEX = "[COLUMNS_INDEX]";
    /**
    * Constante para indicar la expresión comodin de indicador de reemplazo [CONDITIONS]
    */
    private final String CONDITIONS_INDICATOR = "[CONDITIONS]";
    
    private static final Logger log = LoggerFactory.getLogger(AbstractDAO.class.getSimpleName());
    
    /**
     * Método para obtener la conexión de la Base de datos por medio de la clase
     * Conexión ConnectionDB
     * @throws ClassNotFoundException, SQLException
     * @return javax.sql.Connection
     */
    protected Connection getConnection() throws ClassNotFoundException, SQLException{
        return ConnectionDB.getInstance().openConnection();
    }
    
    /**
     * Método que nos permite cerrar la conexión de la BD con la clase ConnectionDB
     * @param javax.sql.Connection objeto conexión
     * @throws SQLException
     */
    protected void closeJDBCObjects(Connection con) throws SQLException{
        ConnectionDB.closeConnection(con);
    }
    
     /**
     * Método que nos permite cerrar y limpiar los recursos de la conexión de la BD con la clase ConnectionDB y 
     * el objeto Statement
     * @param javax.sql.Connection objeto conexión
     * @param javax.sql.Statement objeto Statement
     * @throws SQLException
     */
    protected void closeJDBCObjects(Connection con, Statement st) throws SQLException{
        if(st!=null && !st.isClosed())
            st.close();
        ConnectionDB.closeConnection(con);
    }
    
    /**
     * Método que nos permite cerrar y limpiar los recursos de la conexión de la BD con la clase ConnectionDB y 
     * el objeto PreparedStatement
     * @param javax.sql.Connection objeto conexión
     * @param javax.sql.PreparedStatement objeto PreparedStatement
     * @throws SQLException
     */
    protected void closeJDBCObjects(Connection con, PreparedStatement ps) throws SQLException{
        if(ps!=null && !ps.isClosed())
            ps.close();
        ConnectionDB.closeConnection(con);
    }
    
     /**
     * Método que nos permite cerrar y limpiar los recursos de la conexión de la BD con la clase ConnectionDB,
     * el objeto  Statement y el objeto ResultSet
     * @param javax.sql.Connection objeto conexión
     * @param javax.sql.Statement objeto Statement
     * @param javax.sql.ResultSet objeto ResultSet
     * @throws SQLException
     */
     protected void closeJDBCObjects(Connection con, Statement st, ResultSet rs) throws SQLException{
        if(rs!=null && !rs.isClosed())
            rs.close();
        if(st!=null && !st.isClosed())
            st.close();
        ConnectionDB.closeConnection(con);
    }
    
     /**
     * Método que nos permite cerrar y limpiar los recursos de la conexión de la BD con la clase ConnectionDB,
     * el objeto  PreparedStatement y el objeto ResultSet
     * @param javax.sql.Connection objeto conexión
     * @param javax.sql.PreparedStatement objeto PreparedStatement
     * @param javax.sql.ResultSet objeto ResultSet
     * @throws SQLException
     */
    protected void closeJDBCObjects(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException{
        if(rs!=null && !rs.isClosed())
            rs.close();
        if(ps!=null && !ps.isClosed())
            ps.close();
        ConnectionDB.closeConnection(con);
    }
    
     /**
     * Función que nos permite obtener un SQL INSERT valido para la base de datos, armado dinamicamente
     * de la siguiente forma: INSERT INTO [TABLE] ([COLUMNS]) VALUES (?,?,?...)
     * @return String el SQL armado
     */
    protected String getInsertSQL(){
        // Se toma el SQL INSERT INTO [TABLE] ([COLUMNS]) VALUES ([COLUMNS_INDEX])
        String sql = getSQLWithSchema(SQL_INSERT);
        
        //Se crea un pequeno proceso de concatenación para basado en la cantidad de columnas genere 
        //los indicadores ? dando como resultado: ?,?,?.... para la sección final del sql insert JDBC
        String sqlColumnsIndicator = "";
        for(int i= 0;i<getTableColumns().length;i++)
            sqlColumnsIndicator += "?,"; 
        
        //Al finalizar el proceso de concatenación tendremos una "," de más se elimina con un substring
        sqlColumnsIndicator = sqlColumnsIndicator.substring(0,sqlColumnsIndicator.length()-1);
        //Se reemplaza el [COLUMNS_INDEX] con el resultado del proceso de concatenación
        sql = sql.replace(COLUMNS_INDICATOR_INDEX, sqlColumnsIndicator);
        
        //Se reemplazan los valores de los comodines [TABLE] y [COLUMNS] 
        //con el nombre de la tabla y columnas que indicará la clase hija 
        sql = sql.replace(TABLE_INDICATOR, getTableName()) 
                .replace(COLUMNS_INDICATOR, Arrays.toString(getTableColumns()))
                .replace("[", "").replace("]", ""); //Debido al metodo Arrays devuelve los resultas [campo,campo2,campo3] se aplica el replace de los corchetes []
        
        log.info("getInsertSQL - SQL Generado : "+sql);
        return sql;
    }
    
     /**
     * Función que nos permite obtener un SQL SELECT valido para la base de datos, armado dinamicamente
     * de la siguiente forma: SELECT [COLUMNS] FROM [TABLE]
     * @return String el SQL SELECT armado
     */
     protected String getSelectSQL(){
        //Se toma la constante con los comodines SELECT [COLUMNS] FROM [TABLE]
        String sql = getSQLWithSchema(SQL_SELECT);
        //Se reemplazan los valores de los comodines [TABLE] y [COLUMNS] 
        //con el nombre de la tabla y columnas que indicará la clase hija 
        sql = sql.replace(TABLE_INDICATOR, getTableName()).replace(COLUMNS_INDICATOR, Arrays.toString(getTableColumns()))
                .replace("[", "").replace("]", ""); //Debido al metodo Arrays devuelve los resultas [campo,campo2,campo3] se aplica el replace de los corchetes []
        log.info("getSelectSQL - SQL Generado : "+sql);
        return sql;
    }
     
     /**
     * Función que nos permite obtener un SQL UPDATE valido para la base de datos, armado dinamicamente
     * de la siguiente forma: UPDATE [TABLE] SET [COLUMNS] WHERE [CONDITIONS]
     * @return String el SQL UPDATE armado
     */
     protected String getUpdateSQL(){
         //Se toma la constante con los comodines UPDATE [TABLE] SET [COLUMNS] WHERE [CONDITIONS]
         //En primera linea se reemplaza el comodin de [CONDITIONS] por la columna llave que se indicará en la clase hija
         //Para llevar la siguiente estructura UPDATE [TABLE] SET [COLUMNS] WHERE *columnaLlave = ?*
        String sql = getSQLWithSchema(SQL_UPDATE).replace(CONDITIONS_INDICATOR, getTableKey() + " = ?");
        //Se reemplaza el comodin [TABLE] por la tabla
        sql = sql.replace(TABLE_INDICATOR, getTableName());
        
        //Se crea un pequeno proceso de concatenación de CAMPO=?,
        StringBuilder strIndicators = new StringBuilder();
        for(String str : getTableColumns())
            strIndicators.append(str).append("=?,");
        
        //Se quita la ultima "," de la cadena para formatear correctamente el SQL y se reemplaza el comodin 
        sql = sql.replace(COLUMNS_INDICATOR, strIndicators.toString().substring(0,strIndicators.toString().length()-1));
        
        log.info("getUpdateSQL - SQL Generado : "+sql);
        return sql;
    }
     
     /**
     * Función que nos permite obtener un SQL DELETE valido para la base de datos, armado dinamicamente
     * de la siguiente forma: DELETE FROM [TABLE] WHERE [CONDITIONS]
     * @return String el SQL DELETE armado
     */
     protected String getDeleteSQL(){
         //Se reemplazan los comodines [TABLE] y [CONDITIONS] por las condiciones de *llaveTabla = ?*
         String sql = getSQLWithSchema(SQL_DELETE).replace(TABLE_INDICATOR, getTableName()).replace(CONDITIONS_INDICATOR, getTableKey() + " = ?");
         log.info("getDeleteSQL - SQL Generado : "+sql);
         return sql;
     }
     
     
     /**
     * Función que evalua si existe o no un esquema para configurarlo al SQL
     * @param sql a evaluar
     * @return SQL modificado
     */  
    private String getSQLWithSchema(String sql) {
        String schema = getSchema();
        if(schema == null){
            return sql.replace(SCHEMA_INDICATOR+".", "");
        }else{
            return sql.replace(SCHEMA_INDICATOR, schema);
        }
    }
    
     
     
     /**
     * Función que nos devuelve todos los registros de una entidad basado teniendo 
     * como limite máximo de registros la constante LIMIT_RECORDS
     * @return List<T> Listado de entidades
     * @throws ClassNotFoundException
     * @throws SQLException
     */
     public List<T> getAllData() throws ClassNotFoundException, SQLException{
         return getAllData(LIMIT_RECORDS);
     }
   
     /**
     * Función que nos devuelve todos los registros de una entidad basado teniendo 
     * como limite máximo de registros el parametro que ingresemos
     * @param maxRecords Límite máximo de registros a obtener
     * @return List<T> Listado de entidades
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<T> getAllData(int maxRecords) throws ClassNotFoundException, SQLException{
        Connection con = getConnection(); //Se conecta a la BD
        Statement st = con.createStatement(); //Crea el Statement
        st.setMaxRows(maxRecords); //aplica el máximo de registros a devolver
        ResultSet rs = st.executeQuery(getSelectSQL()); //Ejecuta la query
        List<T> objects = new ArrayList<>();
        while(rs.next()){ //Si la BD encontro registros por cada uno itera
            objects.add(getMappingResults(rs)); //Agrega los datos
        }
        closeJDBCObjects(con, st, rs); //Cierra la conexión
        return objects;
     }
    
     /**
     * Función que nos devuelve la información de una entidad (registro) filtrado por el ID de la entidad
     * @param id El ID será un valor Object para soportar cualquier tipo de dato
     * @return La entidad T
     * @throws ClassNotFoundException 
     * @throws SQLException
     */
    public T getByIDData(Object id) throws ClassNotFoundException, SQLException {
        String sql = getSelectSQL() + SQL_WHERE + getTableKey() + " = ?"; //Se juega con el SQL dinámico
        Connection con = getConnection(); //Se obtiene la conexión
        PreparedStatement ps = con.prepareStatement(sql); //Se prepara el Statement
        ps.setObject(1, id); //Se aplican los parametros
        ResultSet rs = ps.executeQuery(); //Se ejecuta y se utiliza un ResultSet para obtener los valores
        T e = null;
        if(rs.next()){ //Si la BD devolvio coincidencias (Registros)
             e = getMappingResults(rs); //Se mapean y se asignan a la variable
        }
        closeJDBCObjects(con, ps, rs); //Se cierra la conexión
        return e;
    }
    
     /**
     * Método que inserta registros de una entidad en la BD
     * @param entity Entidad a guardar en la BD
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void insertData(T entity) throws ClassNotFoundException, SQLException{
        Connection con = getConnection();//Se crea la conexión
        PreparedStatement ps = con.prepareStatement(getInsertSQL());//Se prepará la sentencia con el SQL Insert generado
        setMappingParamsToInsert(ps, entity); //Se mapean los datos de la entidad al statement para enviarlos a la BD
        ps.execute(); //Se ejecuta en la BD
        closeJDBCObjects(con, ps); //Se cierran los objetos
    }
    
    
     /**
     * Método que elimina registros de una entidad en la BD por el ID
     * @param id de la entidad
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void deleteData(Object id) throws ClassNotFoundException, SQLException{
        Connection con = getConnection();//Se obtiene la conexión
        PreparedStatement ps = con.prepareStatement(getDeleteSQL()); //Se aplica el Statement
        ps.setObject(1, id); //Se aplica el parametro de condición
        ps.execute(); //Se ejecuta el query
        closeJDBCObjects(con, ps); //Se cierran los datos de conexión
    }
    
    
     /**
     * Método que actualiza registros de una entidad en la BD
     * @param entity la entidad y los valores a actualizar
     * @throws ClassNotFoundException
     * @throws SQLException
     */  
    public void updateData(T entity, String userModify, LocalDate modificationDate) throws ClassNotFoundException, SQLException{
        Connection con = getConnection();//Se obtiene la conexión
        PreparedStatement ps = con.prepareStatement(getUpdateSQL());//Se aplica el SQL
        setMappingParamsToUpdate(ps,entity,userModify,modificationDate);//Se hace el mapeo de la entidad al PreparedStatement
        ps.execute(); //Se ejecuta el query
        closeJDBCObjects(con, ps); //Se cierran los objetos
    }

    
    
     /**
     * Método abstracto que realiza el mapping del ResultSet hacia un objeto Entidad que corresponde al mapeo
     * de una tabla en la base de datos
     * @param ResultSet que ya tiene cargada la información y el puntero en el registro que corresponde 
     * para solamente efectuar el mapping ejemplo: entidad.setAtributo(rs.getXXX("NOMBRE_CAMPO");
     * @return Entidad con los valores cargados
     * @throws SQLException
     */  
    protected abstract T getMappingResults(ResultSet rs) throws SQLException;
    
    
     /**
     * Método abstracto para aplicar un Mapping de la entidad hacia el PreparedStatement para aplicar el insert
     * Los valores del insert deben estar ordenados basados en la definción del metodo getTableColumns() para completar
     * los indicadores del (?,?,?,?.....) correspondientes a las columnas
     * Se muestra un ejemplo de como se debe usar:
     * ps.setXXX(1, entity.getAtributo1());
     * ps.setXXX(2, entity.getAtributo2());
     * .....
     * @param ps el Statement que se aplicarán los valores.
     * @param entity que se obtendran los valores a almacenar
     * @return Entidad con los valores cargados
     * @throws SQLException
     */  
    protected abstract void setMappingParamsToInsert(PreparedStatement ps, T entity)throws SQLException;
    
     /**
     * Método abstracto para aplicar un Mapping de la entidad hacia el PreparedStatement para aplicar un Update en la BD
     * Los valores del update deben estar ordenados basados en la definción del metodo getTableColumns() para completar
     * los indicadores del SET CAMPO1=?,CAMPO2=?,CAMPO3=?,CAMPO4=?..... correspondientes a las columnas
     * La llave de la tabla debe quedar como el ultimo valos a aplicar
     * Se muestra un ejemplo de como se debe usar:
     * ps.setXXX(1, entity.getAtributo1());
     * ps.setXXX(2, entity.getAtributo2());
     * .....
     * ps.setXXX(N, entity.getAtributoLLAVE()); Deve ser el ultimo a aplicar siendo N el ultimo indice
     * @param ps el Statement que se aplicarán los valores.
     * @param entity que se obtendran los valores a almacenar
     * @return Entidad con los valores cargados
     * @throws SQLException
     */  
    protected abstract void setMappingParamsToUpdate(PreparedStatement ps, T entity, String userModify, LocalDate modificationDate) throws SQLException;
    
    
     /**
     * Función abstracta que devuelve el nombre de la Tabla que se estará gestionando
     * @return String Nombre de la tabla en la BD
     */  
    protected abstract String getTableName();
    
    /**
     * Función abstracta que devuelve un arreglo de los nombres de las columnas de la tabla como están referenciados en la BD
     * @return String[] Columnas de la tabla en la BD
     */  
    protected abstract String[] getTableColumns();
    
    /**
     * Función abstracta que devuelve el nombre de la columna llave en la tabla
     * @return String columna llave
     */  
    protected abstract String getTableKey();
    
    /**
     * Función que devuelve el esquema de la base de datos
     * @return Schema
     */  
    protected abstract String getSchema();
}
