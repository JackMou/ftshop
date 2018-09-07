package com.futengwl.httpClient;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanwy
 * @date 2018/3/12 17:33
 * @desc
 * @modified
 */
public class MyHttpTest implements InitializingBean {
    @Resource
    private HttpClientOperate httpClientOperate;

    /**
     * @autho 董杨炀
     * @time 2017年5月8日 下午4:26:20
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //无参get请求
        String result = httpClientOperate.doGet("http://www.baidu.com");
        System.out.println(result);
        //有参get请求
        Map<String,String> map = new HashMap<>();
        map.put("waybillNo", "12341223");
        String result2 = httpClientOperate.doGet("http://www.baidu.com", map);
        System.out.println(result2);
        //post请求
        HttpResult entity = httpClientOperate.doPost("http://10.230.21.133:8180/esb2/rs/ESB_FOSS2ESB_FOSS_THE_RECEIVING_VERIFY");
        System.out.println(entity);
        //有参post请求
        HttpResult entity2 = httpClientOperate.doPost("http://10.230.21.133:8180/esb2/rs/ESB_FOSS2ESB_FOSS_THE_RECEIVING_VERIFY", map);
        System.out.println(entity2);
        //有参post请求rest服务 JSON
        String json = "{\"waybillNo\":\"12341223\"}";
        HttpResult entity3 = httpClientOperate.doPostJson("http://10.230.21.133:8180/esb2/rs/ESB_FOSS2ESB_FOSS_THE_RECEIVING_VERIFY",json);
        System.out.println(entity3);
    }
}
