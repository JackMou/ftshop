package com.futengwl.service.impl;

import com.futengwl.service.AuthCodeService;
import com.futengwl.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {
    @Value("${sms.send_authcode_url}")
    private String authCodeUrl;
    @Value("${sms.validate_authcode_url}")
    private String validateUrl;
    @Value("${sms.auth_token}")
    private String token ;
    public boolean sendAuthCode(String number,String type){

        Map params = new HashMap<String,String>();
        params.put("number",number);
        params.put("type",number);
        params.put("token",token);
        if("\"success\"".equals(WebUtils.get(authCodeUrl,params))) {
            return true;
        }
        return false;

    }
    public boolean isValidAuthCode(String number,String type,String authCode){

        Map params = new HashMap<String,String>();
        params.put("number",number);
        params.put("type",number);
        params.put("authCode",authCode);
        params.put("token",token);
        if("\"success\"".equals(WebUtils.get(validateUrl,params))){
            return true;
        }
        return false;

    }
}
