package com.futengwl.pay.vo;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class UtilRefund {
	
	public static byte[] httpPost(String url, String entity,String certificatePath,String mchPassword) {		
		HttpPost httpPost = null;
		InputStream instream = null;
        try {
        	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            instream = new FileInputStream(new File(certificatePath));
            //instream = UtilRefund.class.getClassLoader().getResourceAsStream(certificatePath);
            try {
                keyStore.load(instream, mchPassword.toCharArray());
            }catch (Exception e) {
    			return null;
    		}finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchPassword.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();           
            httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(entity,"utf-8"));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");            
            System.out.println("executing request" + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);                        
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			return EntityUtils.toByteArray(response.getEntity());
        }catch (Exception e) {
        	e.printStackTrace();
			return null;
		}
	}
	
}