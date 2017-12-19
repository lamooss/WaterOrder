package com.techdmz.lamooss.waterorder;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YOUNGSUN on 2017-09-13.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://rudgmltndnjs.cafe24.com/wp/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userPHNo, String userRegion, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userPHNo", userPHNo);
        parameters.put("userRegion", userRegion);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
