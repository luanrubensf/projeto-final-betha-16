package com.luanrubensf.projetoBetha.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rubens
 */
public class Utils {

    public static Map<String, String> parseReq2Map(HttpServletRequest request) {
        Map<String, String> dados = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            dados.put(key, request.getParameter(key));
        }
        return dados;
    }

    public static Long parseLong(String value) {
        return isEmpty(value) ? null : Long.parseLong(value);
    }
    
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    public static String join(ArrayList<String> strings){
        return strings.toString().replace("[", "").replace("]", "");
    }
}
