package edu.utah.blulab.commandline;

/**
 * Created by Bill on 9/27/2016.
 */
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;

import javax.net.ssl.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KAAuthenticator {

	String server = "blulab.chpc.utah.edu";
	String loginURL = "/UM/UMServlet?command=validate";
	String username;
	String password;
	String loginParam = "login";
	String passwordParam = "password";
	String tempPass;

	public static KAAuthenticator Authenticator = null;

	protected HttpsURLConnection openHttpsConnection(final URL url)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(final X509Certificate[] arg0, final String arg1)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] chain, final String authType)
					throws CertificateException {
			}
		} };
		// Install the all-trusting trust manager
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			@Override
			public boolean verify(final String hostname, final SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		return con;
	}

	public void authenticate() throws CommandLineException {

		try {
			String urlS = "https://" + server + loginURL;
			// System.out.println(urlS);
			URL url = new URL(urlS);
			HttpURLConnection.setFollowRedirects(false);

			HttpsURLConnection con = this.openHttpsConnection(url);

			String query = loginParam + "=" + URLEncoder.encode(username, "UTF-8");
			query += "&";
			query += passwordParam + "=" + URLEncoder.encode(password, "UTF-8");

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Cache-Control", "max-age=0");
			con.setRequestProperty("Connection", "keep-alive");
			con.setDefaultUseCaches(false);
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
			con.setRequestProperty("Content-length", String.valueOf(query.length()));
			con.setDoOutput(true);
			con.setDoInput(true);

			Object o = con.getOutputStream();

			DataOutputStream output = new DataOutputStream(con.getOutputStream());

			output.writeBytes(query);

			output.close();
			List<String> cookies = con.getHeaderFields().get("Set-Cookie");
			for (String cookie : cookies) {
				if (cookie.startsWith("pwd")) {
					tempPass = cookie.substring(4, cookie.indexOf(';'));
				}
			}

			con.disconnect();
		} catch (Exception e) {
			throw new CommandLineException("Unable to authenticate: " + e.toString());
		}
	}

	public InputStream openAuthenticatedConnection(final String url)
			throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
		if (tempPass == null) {
			throw new IllegalStateException(
					"Not authenticated. Call authenticate() with valid username/password before calling this method");
		}
		System.out.println("Opening URL " + url);
		HttpsURLConnection con = this.openHttpsConnection(new URL(url));
		con.setRequestProperty("Cookie",
				"usr=" + URLEncoder.encode(username, "UTF-8") + "; pwd=" + URLEncoder.encode(tempPass, "UTF-8"));
		return con.getInputStream();
	}

	public String getServer() {
		return server;
	}

	public void setServer(final String server) {
		this.server = server;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(final String loginURL) {
		this.loginURL = loginURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getLoginParam() {
		return loginParam;
	}

	public void setLoginParam(final String loginParam) {
		this.loginParam = loginParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(final String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getTempPass() {
		return tempPass;
	}

	public void setTempPass(final String tempPass) {
		this.tempPass = tempPass;
	}

	public static void main(final String[] args) {
		IevizCmd iec = new IevizCmd();
		try {
			KAAuthenticator auth = new KAAuthenticator();
			KAAuthenticator.Authenticator = auth;
			iec.readConfigFile();
			String username = iec.getConfigProperty(IevizCmd.USERNAME_PARAMETER);
			String password = iec.getConfigProperty(IevizCmd.PASSWORD_PARAMETER);
			auth.setUsername(username);
			auth.setPassword(password);
			auth.authenticate();
			if (auth.tempPass == null) {
				System.err.println("Authentication failed");
			}
			InputStream xml = auth.openAuthenticatedConnection(
					"https://blulab.chpc.utah.edu/KA/?c=Ontologyc&act=exportToOwl&id_=130");
			// System.out.println(KAAuthenticator.streamToString(xml));
			xml.close();

			InputStream json = KAAuthenticator.Authenticator.openAuthenticatedConnection(
					"https://blulab.chpc.utah.edu/KA/?act=searchd&c=Ontologyc&view=JSON&npp=200&q_status_=Active");
			String jsstr = Utilities.convertStreamToString(json);
			json.close();
			JSONArray jarray = new JSONArray(jsstr);
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject jo = (JSONObject) jarray.get(i);
				String name = jo.getString("name");
				System.out.println(name);
			}
		} catch (Exception e) {
			iec.handleError(e);
		}

	}

}
