package me.figo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.figo.internal.TokenResponse;

public class EnvTest {

	private static final int TIMEOUT = 30000;
	private FigoConnection conn;
	String clientId = System.getenv("FIGO_CLIENT_ID");
	String clientSecret = System.getenv("FIGO_CLIENT_SECRET");
	String redirectUri = "http://localhost:3000";
	String endpoint = System.getenv("FIGO_API_ENDPOINT");

	@Before
	public void setUp() throws Exception {
		conn = new FigoConnection(clientId, clientSecret, redirectUri, TIMEOUT, endpoint);
		System.out.println("Id: " + clientId.substring(0, 4) + "...");
		System.out.println("Secret: " + clientSecret.substring(0, 4) + "...");
		System.out.println("Endpoint: " + endpoint.substring(0, 8) + "...");
	}

	@Test
	public void testGetAccount() throws FigoException, IOException {
		String name = "java-sdk-tester";
		String email = name + "@test.de";
		String password = "password";
		TokenResponse tok;
		try {
			tok = conn.addUserAndLogin(name, email, password, "DE");
		} catch (FigoException e) {
			Assert.assertTrue("user_exists".equals(e.getErrorMessage()));
			tok = conn.credentialLogin(email, password);
		}
		FigoSession sess = new FigoSession(tok.getAccessToken(), TIMEOUT);
		Assert.assertTrue(endpoint != null && endpoint.equals(sess.getApiEndpoint()));
	}

}