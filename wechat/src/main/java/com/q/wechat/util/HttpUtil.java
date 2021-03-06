package com.q.wechat.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtil{
	private static final String charset = "utf-8";
	
	/**
	 * 发送 get请求，直接返回响应对象HttpResponse
	 * @param url	请求地址
	 * @return
	 * @throws Exception
	 */	
	public static HttpResponse get1(String url) throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Cache-Control", "no-cache");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Pragma", "no-cache");
		get.setHeader("Upgrade-Insecure-Requests", "1");
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2372.400 QQBrowser/9.5.10548.400");
		HttpResponse response = client.execute(get);
		return response;
	}
	/**
	 * 发送 post请求，返回响应结果(可以模拟表单上传)
	 * @param url		发送的目的服务器地址
	 * @param params	post的参数
	 * @return
	 * @throws Exception
	 */	
	public static String post(String url, Map<String, Object> params) throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		URL url1 = new URL(url);   
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		HttpPost post = new HttpPost(uri);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value instanceof File) {
				builder.addBinaryBody(key, (File)value);
			}else if(value instanceof String) {
				builder.addTextBody(key, value.toString());
			}
		}
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		
		HttpResponse response = null;
		response = client.execute(post);
		String responseText = "";
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity responseEntity = response.getEntity();
			responseText = EntityUtils.toString(responseEntity);
		}
		
		return responseText;
	}
	/**
	 * 发送post(参数为json字符串)
	 * @param url
	 * @param jsonstring	json字符串参数
	 * @return				字符串
	 * @throws Exception
	 */
	public static String post(String url, String jsonstring) throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		URL url1 = new URL(url);   
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
        HttpPost post = new HttpPost(uri);
		StringEntity s = new StringEntity(jsonstring, charset);
		s.setContentEncoding("utf-8");
		s.setContentType("application/json");
		post.setEntity(s);
		HttpResponse response = client.execute(post);
		String responseText = "";
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	        HttpEntity entity = response.getEntity();
	        InputStream in = entity.getContent();
	        int len;
	        byte[] buffer = new byte[1024];
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        while((len = in.read(buffer)) != -1){
	        	out.write(buffer, 0, len);
	        }
	        out.close();
	        in.close();
	        responseText = new String(out.toByteArray(), charset);
	    }
		return responseText;
	}
	
	/**
	 * 1.如果请求返回的是文本，则返回文本
	 * 2.如果请求返回的是不是文本(如图片)，则保存到服务器，并返回保存的地址
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		URL url1 = new URL(url);   
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		HttpGet get = new HttpGet(uri);
		
		HttpResponse response = client.execute(get);
		String responseText = "";
		if(response != null){
			String contentType = response.getFirstHeader("Content-Type").getValue();
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			if (contentType.contains("text/plain") || contentType.contains("application/json")) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while((len = in.read(buffer)) != -1){
					out.write(buffer, 0, len);
				}
				out.close();
				in.close();
				byte[] result = out.toByteArray();
				responseText = new String(result, charset);
			}else {
				String contentDisposition = response.getFirstHeader("Content-disposition").getValue();
				String filename = contentDisposition.substring(contentDisposition.indexOf("filename=")+10, contentDisposition.length()-1);
				responseText = FileUtil.saveToServer(filename, in);
			}
		}
		return responseText;
	}
}
