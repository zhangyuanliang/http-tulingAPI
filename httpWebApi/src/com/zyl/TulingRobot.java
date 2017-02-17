package com.zyl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TulingRobot {
	private static final Logger logger = LoggerFactory.getLogger(TulingRobot.class);
	public static void getAnswer(String ask) {
		String requestUrl = "http://www.tuling123.com/openapi/api"; //接口地址
		String APIKEY = "c34f1de5cad943f4a0e9b8c129e69de7";//图灵服务的钥匙
		String userId = "userId001";//开发者给自己的用户分配的唯一标志
        Map<String, Object> requestParamsMap = new HashMap<String, Object>();  
        requestParamsMap.put("key", APIKEY);  
        requestParamsMap.put("userid", userId);
        requestParamsMap.put("info", ask);
        
        DataOutputStream  printWriter = null;  
        BufferedReader bufferedReader = null;  
        StringBuilder responseResult = new StringBuilder();  
        StringBuilder params = new StringBuilder();  
        HttpURLConnection httpURLConnection = null;  
        // 组织请求参数  
        Iterator<Entry<String, Object>> it = requestParamsMap.entrySet().iterator();  
        while (it.hasNext()) {  
            Entry<String, Object> element = it.next();  
            params.append(element.getKey());  
            params.append("=");  
            params.append(element.getValue());  
            params.append("&");  
        }  
        if (params.length() > 0) {  
            params.deleteCharAt(params.length() - 1);  
        }  
        try {  
            URL realUrl = new URL(requestUrl);  
            // 打开和URL之间的连接  
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();  
            // 设置通用的请求属性  
            httpURLConnection.setRequestProperty("accept", "*/*");  
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");  
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");  
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));  
            httpURLConnection.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行  
            httpURLConnection.setDoOutput(true);  
            httpURLConnection.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            printWriter = new DataOutputStream(httpURLConnection.getOutputStream());  
            // 发送请求参数  
            printWriter.write(params.toString().getBytes("utf-8"));  
            // flush输出流的缓冲  
            printWriter.flush();  
            // 根据ResponseCode判断连接是否成功  
            int responseCode = httpURLConnection.getResponseCode();  
            if (responseCode != 200) {  
            	logger.error(" Error===" + responseCode);  
            } else {  
            	logger.info("Post Success!");  
            }  
            // 定义BufferedReader输入流来读取URL的ResponseData  
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));  
            String line;  
            while ((line = bufferedReader.readLine()) != null) {  
//              responseResult.append("/n").append(line);  
            	responseResult.append(line);
            }  
//          Map<String, Object> resultMap = JsonUtil.jsonToMap(responseResult.toString());
            System.out.println("接口返回答案："+responseResult.toString());
        } catch (Exception e) {  
        	logger.error("send post request error!" + e);  
        } finally {  
            httpURLConnection.disconnect();  
            try {  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
	}
	public static void main(String[] args) {
		getAnswer("你叫什么？");
	}

}
