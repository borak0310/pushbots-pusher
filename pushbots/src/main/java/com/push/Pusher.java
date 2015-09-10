/**
 * 
 */
package com.push;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * ±À¼·
 * 
 * @author T2470
 * 
 */
public class Pusher {

	public void push(String inputStr) {
		
		String requestJson = "{\"platform\":\"1\", \"msg\":\"Hi from Tali\" ,\"badge\":\"10\" ,\"sound\":\"default\"}";
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://api.pushbots.com/push/all");
			
			StringEntity params =new StringEntity(requestJson,"UTF-8");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("x-pushbots-appid", "55e6eb8b177959d9768b4567");
			httpPost.addHeader("x-pushbots-secret", "85ee979b51f2b89356aa6926ec3f609b");
			httpPost.setEntity(params);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			
			CredentialsProvider provider = new BasicCredentialsProvider();

			// Add AuthCache to the execution context
			final HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(provider);
			String responseBody = httpclient.execute(httpPost, responseHandler, context);
			System.out.println(responseBody);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Pusher pusher = new Pusher();
		pusher.push("");
	}
}
