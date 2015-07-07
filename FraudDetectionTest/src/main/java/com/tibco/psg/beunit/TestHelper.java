package com.tibco.psg.beunit;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

public class TestHelper {
	private static String serverUrl = "http://localhost:8989";
	
	public static void setServerUrl(String url) {
		serverUrl = url;
	}
	
	public static String sendTestRequest(String functionName,
			boolean isPreproc) throws Exception {
		String reqUrl = String.format("%s/testservice/test/function?name=%s&preproc=%s",
				serverUrl, functionName, isPreproc);
		return Request.Get(reqUrl).execute()
				.handleResponse(new ResponseHandler<String>() {

					public String handleResponse(final HttpResponse response)
							throws ClientProtocolException, IOException {
						int status = response.getStatusLine().getStatusCode();
						if (status >= 200 && status < 300) {
							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils
									.toString(entity) : null;
						} else {
							throw new ClientProtocolException(
									"Unexpected response status: " + status);
						}
					}

				});
	}

	public static void assertRuleFunction(String functionName, boolean isPreproc) {
		try {
			String msg = sendTestRequest(functionName, isPreproc);
			if (!"true".equals(msg)) {
				org.junit.Assert.fail(msg);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
