package com.techdmz.lamooss.waterorder;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YOUNGSUN on 2017-09-13.
 * 거래처 ID 체크
 */

public class ValidateRequest extends StringRequest {
    final static private String URL = "http://rudgmltndnjs.cafe24.com/wp/UserValidate.php";
    private Map<String, String> parameters;
    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
