package me.figo;

import com.google.gson.Gson;
import me.figo.internal.CredentialLoginRequest;
import me.figo.internal.SetupAccountCredentials;
import me.figo.internal.SetupAccountRequest;
import me.figo.models.AdditionalTransactionInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class GsonTest {

	@Test
	public void testGson() {
		FigoApi api = new FigoApi("", 0);
		Gson g = api.createGson();
		AdditionalTransactionInfo obj = g.fromJson(
				"{\"compensation_amount\": \"6,10\",\"original_amount\": \"575.40\"}", AdditionalTransactionInfo.class);
		Assert.assertNull(obj.getCompensation_amount());
		Assert.assertEquals(obj.getOriginal_amount().toPlainString(), "575.40");
	}

	@Test
	public void testSetupAccountRequestSerialization() {
		FigoApi api = new FigoApi("", 0);
		Gson g = api.createGson();

		SetupAccountRequest testObject;
		String expected;

		testObject = new SetupAccountRequest("bankCode", "de", Arrays.asList("login", "pin"), Collections.<String>emptyList(), false, false, "uri");
		expected = "{\"bank_code\":\"bankCode\",\"country\":\"de\",\"credentials\":[\"login\",\"pin\"],\"sync_tasks\":[],\"save_pin\":false,\"disable_first_sync\":false,\"redirect_uri\":\"uri\"}";
		Assert.assertEquals(expected, g.toJson(testObject));

		testObject = new SetupAccountRequest("bankCode", "de", "login", "pin", Collections.<String>emptyList());
		expected = "{\"bank_code\":\"bankCode\",\"country\":\"de\",\"credentials\":[\"login\",\"pin\"],\"sync_tasks\":[],\"save_pin\":false,\"disable_first_sync\":false}";
		Assert.assertEquals(expected, g.toJson(testObject));
	}

	@Test
	public void testCredentialSerialization() {
		FigoApi api = new FigoApi("", 0);
		Gson g = api.createGson();

		SetupAccountCredentials secureCredentials = new SetupAccountCredentials("ENCRYPTED");
		Assert.assertEquals("{\"type\":\"encrypted\",\"value\":\"ENCRYPTED\"}", g.toJson(secureCredentials));

		SetupAccountCredentials credentials = new SetupAccountCredentials(Arrays.asList("username", "pin"));
		Assert.assertEquals("[\"username\",\"pin\"]", g.toJson(credentials));
	}

	@Test
	public void testCredentialLoginSerialization() {
		FigoApi api = new FigoApi("", 0);
		Gson g = api.createGson();

		CredentialLoginRequest request = new CredentialLoginRequest("username", "password");
		Assert.assertEquals("{\"grant_type\":\"password\",\"username\":\"username\",\"password\":\"password\"}", g.toJson(request));
	}
}
