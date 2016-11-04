
package com.luanrubensf.projetoBetha.reflection;

import com.luanrubensf.projetoBetha.dao.ConnectionUtils;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rubens
 */
public class ReflectionDao {
    
    public ResultSet list(Class<?> cl){
            
        String tableName = cl.getAnnotation(PersistentClass.class).table();
        
        ReflectionUtils rf = new ReflectionUtils();
        List<Field> fields = rf.getPersistentFields(cl);
        
        String select = buildSelect(fields, tableName);
        
        try (Connection conn = ConnectionUtils.getConn()) {
            try (PreparedStatement stm = conn.prepareStatement(select)) {
                try (ResultSet rs = stm.executeQuery()) {
                    return rs;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(cl.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private String buildSelect(List<Field> fields, String tableName){
        
        StringBuilder select = new StringBuilder("select ");
        List<String> fieldsNames = new ArrayList<>();
        
        for(Field f: fields){
            fieldsNames.add(f.getName());
        }
        
        select.append(String.join(",", fieldsNames));        
        select.append(" from  ");
        select.append(tableName);
        
        return select.toString();
    }
}
