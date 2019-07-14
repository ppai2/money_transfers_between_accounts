package com.revolut.http;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.revolut.TransactionMain;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test class for transaction controller
 */
public class TransactionControllerTest {

	@ClassRule
	public static SparkServer<TestWebAppSparkApp> testServer = new SparkServer<>(TestWebAppSparkApp.class, 4567);

	public static class TestWebAppSparkApp implements SparkApplication {
		public void init() {
			new TransactionMain().startService();
		}
	}

	@Test
	public void testAddAccountReturnsTrueAllBodyParamsAreValidAndSuccessResponse() throws HttpClientException {
		final PostMethod post = testServer.post("/addAccount", "{\"accountID\": 3,\"balance\": 10.00,\"amount\": 10.00}", false);
		post.addHeader("Test-Header", "test");

		final HttpResponse httpResponse = testServer.execute(post);
		assertEquals(200, httpResponse.code());
		assertEquals("{\"status\":\"SUCCESS\"}", new String(httpResponse.body()));
		assertNotNull(testServer.getApplication());
	}

	@Test
	public void testAddAccountReturnsFalseBodyParamIsInvalidAndFailureResponse() throws HttpClientException {
		final PostMethod post = testServer.post("/addAccount", "{\"accountID\": 3,\"balance\": abs,\"amount\": 10.00}", false);
		post.addHeader("Test-Header", "test");

		final HttpResponse httpResponse = testServer.execute(post);
		assertEquals(500, httpResponse.code());
		assertNotNull(testServer.getApplication());
	}

	@Test
	public void testGetAllUsersReturnsTrueWhenTheMapContainsValidAccounts() throws HttpClientException {
		final GetMethod get = testServer.get("/allUsers", false);
		get.addHeader("Test-Header", "test");

		final HttpResponse httpResponse = testServer.execute(get);
		assertEquals(200, httpResponse.code());
		assertNotNull(testServer.getApplication());
	}
}
