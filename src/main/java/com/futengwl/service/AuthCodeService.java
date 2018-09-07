package com.futengwl.service;

public interface AuthCodeService {

    public boolean sendAuthCode(String number,String type);
    public boolean isValidAuthCode(String number,String type,String authCode);

}
