package com.futengwl.service;


import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Map;

/**
 * @author yuanwy
 * @date 2018/3/2 15:07
 * @desc
 * @modified
 */
@Service
public interface IUserService {
    ModelMap insertUserThirdParty(Map<String,String> map);

}
