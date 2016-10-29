package com.luanrubensf.projetoBetha.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rubens
 */
public class ReflectionUtils {

    public List<Field> getPersistentFields(Class<?> cl) {
           
        if(!cl.isAnnotationPresent(PersistentClass.class)){
            return null;
        }
        
        List<Field> persistentFields = new ArrayList<>();
        
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(PersistentField.class)){
               persistentFields.add(field);
            }
        }
        
        return persistentFields;
    }
}
