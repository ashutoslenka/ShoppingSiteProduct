package main.java.shoppingsite.service.secure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

import main.java.shoppingsite.startup.ShoppingSiteStartup;

import com.ibm.xml.enc.dom.Base64;

public class GetPost {
	private String NEWLINE = "\n";
	private int errorCode = 0;
	private String log = "";
	private String strKeyStoreFile = ShoppingSiteStartup.getProperty("KeyStorePath");
	private String strKeyStorePassword = ShoppingSiteStartup.getProperty("KeyStorePassword");
	private String strAuthenticationKey = ShoppingSiteStartup.getProperty("AuthenticationKey");

	public GetPost() {
		log = "true";
	}

	public void setErrorCode(int paramInt) {
		this.errorCode = paramInt;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String doAction(String paramString1, String paramString2, Hashtable paramHashtable1, Hashtable paramHashtable2, int paramInt, String paramString3, String paramString4, String paramString5, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		Properties localProperties;
		if (paramInt > 0) {
			localProperties = System.getProperties();
			localProperties.put("sun.net.client.defaultConnectTimeout", "" + paramInt);
			localProperties.put("sun.net.client.defaultReadTimeout", "" + paramInt);
			System.setProperties(localProperties);
		}
		if ((paramString3 != null) && (paramString4 != null)) {
			localProperties = System.getProperties();
			localProperties.put("proxySet", "true");
			localProperties.put("proxyHost", paramString3);
			localProperties.put("proxyPort", paramString4);
			System.setProperties(localProperties);
		}

		if ("true".equals(this.log))
			System.out.println("paramString1:" + paramString1 + "::paramString2:" + paramString2 + "::paramString3:" + paramString3 + "::paramString4:" + paramString4 + "::paramString5:" + paramString5 + "::paramHashtable1:" + paramHashtable1 + "::paramHashtable2:" + paramHashtable1);

		if ("GET".equals(paramString1.toUpperCase())) {
			if (paramString2.toUpperCase().startsWith("HTTPS://"))
				return doGetSSL(paramString2, paramHashtable2, paramString5, paramHttpServletResponse);
			return doGet(paramString2, paramHashtable2, paramString5, paramHttpServletResponse);
		}
		if (paramString2.toUpperCase().startsWith("HTTPS://"))
			return doPostSSL(paramString2, paramHashtable1, paramHashtable2, paramString5, paramServletInputStream, paramHttpServletResponse);
		return doPost(paramString2, paramHashtable1, paramHashtable2, paramString5, paramServletInputStream, paramHttpServletResponse);
	}

	public String doDelete(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, int paramInt, String paramString2, String paramString3, String paramString4, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		Properties localProperties;
		if (paramInt > 0) {
			localProperties = System.getProperties();
			localProperties.put("sun.net.client.defaultConnectTimeout", "" + paramInt);
			localProperties.put("sun.net.client.defaultReadTimeout", "" + paramInt);
			System.setProperties(localProperties);
		}
		if ((paramString2 != null) && (paramString3 != null)) {
			localProperties = System.getProperties();
			localProperties.put("proxySet", "true");
			localProperties.put("proxyHost", paramString2);
			localProperties.put("proxyPort", paramString3);
			System.setProperties(localProperties);
		}
		if (paramString1.toUpperCase().startsWith("HTTPS://"))
			return doDeleteSSL(paramString1, paramHashtable2, paramString4, paramHttpServletResponse);
		return doDelete(paramString1, paramHashtable2, paramString4, paramHttpServletResponse);
	}

	public String doHead(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, int paramInt, String paramString2, String paramString3, String paramString4, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		Properties localProperties;
		if (paramInt > 0) {
			localProperties = System.getProperties();
			localProperties.put("sun.net.client.defaultConnectTimeout", "" + paramInt);
			localProperties.put("sun.net.client.defaultReadTimeout", "" + paramInt);
			System.setProperties(localProperties);
		}
		if ((paramString2 != null) && (paramString3 != null)) {
			localProperties = System.getProperties();
			localProperties.put("proxySet", "true");
			localProperties.put("proxyHost", paramString2);
			localProperties.put("proxyPort", paramString3);
			System.setProperties(localProperties);
		}
		if (paramString1.toUpperCase().startsWith("HTTPS://"))
			return doHeadSSL(paramString1, paramHashtable2, paramString4, paramHttpServletResponse);
		return doHead(paramString1, paramHashtable2, paramString4, paramHttpServletResponse);
	}

	public String doPut(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, int paramInt, String paramString2, String paramString3, String paramString4, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		Properties localProperties;
		if (paramInt > 0) {
			localProperties = System.getProperties();
			localProperties.put("sun.net.client.defaultConnectTimeout", "" + paramInt);
			localProperties.put("sun.net.client.defaultReadTimeout", "" + paramInt);
			System.setProperties(localProperties);
		}
		if ((paramString2 != null) && (paramString3 != null)) {
			localProperties = System.getProperties();
			localProperties.put("proxySet", "true");
			localProperties.put("proxyHost", paramString2);
			localProperties.put("proxyPort", paramString3);
			System.setProperties(localProperties);
		}
		if (paramString1.toUpperCase().startsWith("HTTPS://"))
			return doPutSSL(paramString1, paramHashtable1, paramHashtable2, paramString4, paramServletInputStream, paramHttpServletResponse);
		return doPut(paramString1, paramHashtable1, paramHashtable2, paramString4, paramServletInputStream, paramHttpServletResponse);
	}

	private String doPut(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, String paramString2, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpURLConnection localHttpURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		if (paramHashtable1 != null)
			str1 = getParamsFromHash(paramHashtable1);
		try {
			localURL = new URL(paramString1);
			localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
			localHttpURLConnection.setRequestMethod("PUT");
			localHttpURLConnection.setDoOutput(true);
			localHttpURLConnection.setDoInput(true);
			localHttpURLConnection.setUseCaches(false);
			localHttpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if (paramHashtable2 != null) {
				localObject2 = paramHashtable2.keys();
				while (((Enumeration) localObject2).hasMoreElements()) {
					String str2 = (String) ((Enumeration) localObject2).nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str2 != null && !str2.equals("if-modified-since") && !str2.equals("if-none-match"))
						localHttpURLConnection.setRequestProperty(str2, (String) paramHashtable2.get(str2));
				}
			}
			localObject2 = new PrintWriter(localHttpURLConnection.getOutputStream());
			((PrintWriter) localObject2).print(str1);
			int j;
			while ((j = paramServletInputStream.read()) != -1)
				((PrintWriter) localObject2).write(j);
			((PrintWriter) localObject2).close();
		} catch (Exception localException1) {
			getErrorCodeFromResponse(localHttpURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			setErrorCode(localHttpURLConnection.getResponseCode());
			String str3 = localHttpURLConnection.getContentType();
			if ((str3 != null) && (paramString2 != null))
				str3 = rewriteEncoding(str3, paramString2);
			if (str3 != null)
				paramHttpServletResponse.setContentType(str3);
			if (paramHashtable2 != null)
				rewriteHeaders(localHttpURLConnection, paramHttpServletResponse);
			String str4 = localHttpURLConnection.getContentEncoding();
			if (str4 == null)
				str4 = "";
			try {
				if (str4.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localHttpURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localHttpURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doPutSSL(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, String paramString2, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpsURLConnection localHttpsURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		Object localObject3 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		if (paramHashtable1 != null)
			str1 = getParamsFromHash(paramHashtable1);
		try {
			localURL = new URL(paramString1);
			localHttpsURLConnection = (HttpsURLConnection) localURL.openConnection();

			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				GetPost.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				// SSLContext localSSLContext = SSLContext.getInstance("SSL");
				// localSSLContext.init(null, arrayOfTrustManager, new
				// SecureRandom());
				localHttpsURLConnection.setSSLSocketFactory(GetPost.getFactory(new File(strKeyStoreFile), strKeyStorePassword));

				String strAuthentication = Base64.encode(strAuthenticationKey.getBytes());
				localHttpsURLConnection.setRequestProperty("Authorization", "Basic " + strAuthentication);

			} catch (Exception localException) {
			}

			localHttpsURLConnection.setRequestMethod("PUT");
			localHttpsURLConnection.setDoOutput(true);
			localHttpsURLConnection.setDoInput(true);
			localHttpsURLConnection.setUseCaches(false);
			localHttpsURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if (paramHashtable2 != null) {
				localObject2 = paramHashtable2.keys();
				while (((Enumeration) localObject2).hasMoreElements()) {
					String str2 = (String) ((Enumeration) localObject2).nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str2 != null && !str2.equals("if-modified-since") && !str2.equals("if-none-match"))
						localHttpsURLConnection.setRequestProperty(str2, (String) paramHashtable2.get(str2));
				}
			}
			localObject2 = new PrintWriter(localHttpsURLConnection.getOutputStream());
			((PrintWriter) localObject2).print(str1);
			int j;
			while ((j = paramServletInputStream.read()) != -1)
				((PrintWriter) localObject2).write(j);
			((PrintWriter) localObject2).close();
		} catch (Exception localException1) {
			getErrorCodeFromSSLResponse(localHttpsURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			if ((localHttpsURLConnection instanceof HttpURLConnection)) {
				localObject3 = localHttpsURLConnection;
				setErrorCode(((HttpURLConnection) localObject3).getResponseCode());
			}
			localObject3 = localHttpsURLConnection.getContentType();
			if ((localObject3 != null) && (paramString2 != null))
				localObject3 = rewriteEncoding((String) localObject3, paramString2);
			if (localObject3 != null)
				paramHttpServletResponse.setContentType((String) localObject3);
			String str3 = localHttpsURLConnection.getContentEncoding();
			if (paramHashtable2 != null)
				rewriteSSLHeaders(localHttpsURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localHttpsURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localHttpsURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doPost(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, String paramString2, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		URLConnection localURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		Object localObject3 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		if (paramHashtable1 != null)
			str1 = getParamsFromHash(paramHashtable1);
		try {
			localURL = new URL(paramString1);
			localURLConnection = localURL.openConnection();
			localURLConnection.setDoOutput(true);
			localURLConnection.setDoInput(true);
			localURLConnection.setUseCaches(false);
			localURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if (paramHashtable2 != null) {
				localObject2 = paramHashtable2.keys();
				while (((Enumeration) localObject2).hasMoreElements()) {
					String str2 = (String) ((Enumeration) localObject2).nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str2 != null && !str2.equals("if-modified-since") && !str2.equals("if-none-match"))
						localURLConnection.setRequestProperty(str2, (String) paramHashtable2.get(str2));
				}
			}
			localObject2 = new PrintWriter(localURLConnection.getOutputStream());
			((PrintWriter) localObject2).print(str1);
			int j;
			while ((j = paramServletInputStream.read()) != -1)
				((PrintWriter) localObject2).write(j);
			((PrintWriter) localObject2).close();
		} catch (Exception localException1) {
			getErrorCodeFromResponse(localURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			if ((localURLConnection instanceof HttpURLConnection)) {
				localObject3 = (HttpURLConnection) localURLConnection;
				setErrorCode(((HttpURLConnection) localObject3).getResponseCode());
			}
			localObject3 = localURLConnection.getContentType();
			if ((localObject3 != null) && (paramString2 != null))
				localObject3 = rewriteEncoding((String) localObject3, paramString2);
			if (localObject3 != null)
				paramHttpServletResponse.setContentType((String) localObject3);
			if (paramHashtable2 != null)
				rewriteHeaders(localURLConnection, paramHttpServletResponse);
			String str3 = localURLConnection.getContentEncoding();
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doPostSSL(String paramString1, Hashtable paramHashtable1, Hashtable paramHashtable2, String paramString2, ServletInputStream paramServletInputStream, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpsURLConnection localHttpsURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		Object localObject3 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		if (paramHashtable1 != null)
			str1 = getParamsFromHash(paramHashtable1);
		try {
			localURL = new URL(paramString1);
			localHttpsURLConnection = (HttpsURLConnection) localURL.openConnection();
			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				GetPost.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				// SSLContext localSSLContext = SSLContext.getInstance("SSL");
				// localSSLContext.init(null, arrayOfTrustManager, new
				// SecureRandom());
				localHttpsURLConnection.setSSLSocketFactory(GetPost.getFactory(new File(strKeyStoreFile), strKeyStorePassword));

				String strAuthentication = Base64.encode(strAuthenticationKey.getBytes());
				localHttpsURLConnection.setRequestProperty("Authorization", "Basic " + strAuthentication);

			} catch (Exception localException) {
			}
			localHttpsURLConnection.setDoOutput(true);
			localHttpsURLConnection.setDoInput(true);
			localHttpsURLConnection.setUseCaches(false);
			localHttpsURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if (paramHashtable2 != null) {
				localObject2 = paramHashtable2.keys();
				while (((Enumeration) localObject2).hasMoreElements()) {
					String str2 = (String) ((Enumeration) localObject2).nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str2 != null && !str2.equals("if-modified-since") && !str2.equals("if-none-match"))
						localHttpsURLConnection.setRequestProperty(str2, (String) paramHashtable2.get(str2));
				}
			}
			localObject2 = new PrintWriter(localHttpsURLConnection.getOutputStream());
			System.out.println("localObject2::" + localObject2);
			((PrintWriter) localObject2).print(str1);
			System.out.println("str1::" + str1);
			int j;
			while ((j = paramServletInputStream.read()) != -1)
				((PrintWriter) localObject2).write(j);
			System.out.println("localObject2::" + localObject2);
			((PrintWriter) localObject2).close();
		} catch (Exception localException1) {
			getErrorCodeFromSSLResponse(localHttpsURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			if ((localHttpsURLConnection instanceof HttpURLConnection)) {
				localObject3 = localHttpsURLConnection;
				setErrorCode(((HttpURLConnection) localObject3).getResponseCode());
			}
			localObject3 = localHttpsURLConnection.getContentType();
			if ((localObject3 != null) && (paramString2 != null))
				localObject3 = rewriteEncoding((String) localObject3, paramString2);
			System.out.println("Content type::" + localObject3);
			if (localObject3 != null)
				paramHttpServletResponse.setContentType((String) localObject3);
			String str3 = localHttpsURLConnection.getContentEncoding();
			if (paramHashtable2 != null)
				rewriteSSLHeaders(localHttpsURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localHttpsURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localHttpsURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doGet(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		URLConnection localURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		try {
			localURL = new URL(paramString1);
			localURLConnection = localURL.openConnection();
			localURLConnection.setDoInput(true);
			localURLConnection.setUseCaches(false);
			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromResponse(localURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			if ((localURLConnection instanceof HttpURLConnection)) {
				localObject2 = (HttpURLConnection) localURLConnection;
				setErrorCode(((HttpURLConnection) localObject2).getResponseCode());
			}
			localObject2 = localURLConnection.getContentType();
			if ((localObject2 != null) && (paramString2 != null))
				localObject2 = rewriteEncoding((String) localObject2, paramString2);
			if (localObject2 != null)
				paramHttpServletResponse.setContentType((String) localObject2);
			else
				localObject2 = "";
			String str2 = localURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteHeaders(localURLConnection, paramHttpServletResponse);
			if (str2 == null)
				str2 = "";
			try {
				if (str2.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	public static SSLSocketFactory getFactory(File pKeyFile, String pKeyPassword) throws Exception {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		KeyStore keyStore = KeyStore.getInstance("JKS");
		System.out.println("pKeyFile-->" + pKeyFile);
		InputStream keyInput = new FileInputStream(pKeyFile);
		System.out.println("keyInput-->" + keyInput);
		keyStore.load(keyInput, pKeyPassword.toCharArray());
		keyInput.close();
		keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore trustStore = KeyStore.getInstance("JKS");
		System.out.println("pKeyFile-->" + pKeyFile);
		InputStream trustInput = new FileInputStream(pKeyFile);
		System.out.println("keyInput-->" + keyInput);
		trustStore.load(trustInput, pKeyPassword.toCharArray());
		trustInput.close();
		tmf.init(trustStore);

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
		return context.getSocketFactory();
	}

	private static void trustAllHttpsCertificates() throws Exception {

		// Create a trust manager that does not validate certificate chains:
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new MITM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	private String doGetSSL(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpsURLConnection localHttpsURLConnection = null;
		Object localObject1 = null;
		Object localObject2 = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";

		if ("true".equals(this.log))
			System.out.println("paramString1:" + paramString1 + "::paramString2:" + paramString2);

		try {
			localURL = new URL(paramString1);
			localHttpsURLConnection = (HttpsURLConnection) localURL.openConnection();

			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				GetPost.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				// SSLContext localSSLContext = SSLContext.getInstance("SSL");
				// localSSLContext.init(null, arrayOfTrustManager, new
				// SecureRandom());
				localHttpsURLConnection.setSSLSocketFactory(GetPost.getFactory(new File(strKeyStoreFile), strKeyStorePassword));

				String strAuthentication = Base64.encode(strAuthenticationKey.getBytes());
				localHttpsURLConnection.setRequestProperty("Authorization", "Basic " + strAuthentication);

			} catch (Exception localException) {
			}

			localHttpsURLConnection.setDoInput(true);
			// Used for the channel not to cache the requests
			localHttpsURLConnection.setUseCaches(false);

			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localHttpsURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
					if ("true".equals(this.log))
						System.out.println("Content type Before ::" + str1 + "::Here::" + (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromSSLResponse(localHttpsURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			if ((localHttpsURLConnection instanceof HttpURLConnection)) {
				localObject2 = localHttpsURLConnection;
				setErrorCode(((HttpURLConnection) localObject2).getResponseCode());
			}
			localObject2 = localHttpsURLConnection.getContentType();

			if ("true".equals(this.log))
				System.out.println("::Content Type::" + localObject2);

			if ((localObject2 != null) && (paramString2 != null))
				localObject2 = rewriteEncoding((String) localObject2, paramString2);
			if (localObject2 != null)
				paramHttpServletResponse.setContentType((String) localObject2);
			else
				localObject2 = "";
			String str2 = localHttpsURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteSSLHeaders(localHttpsURLConnection, paramHttpServletResponse);
			if (str2 == null)
				str2 = "";

			if ("true".equals(this.log))
				System.out.println("Encoding Type:" + str2 + "::Content::" + localHttpsURLConnection.getContentType());

			try {
				if (str2.indexOf("gzip") >= 0)
					localObject1 = new GZIPInputStream(localHttpsURLConnection.getInputStream());
				else
					localObject1 = new BufferedInputStream(localHttpsURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject1).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject1 != null)
				((InputStream) localObject1).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doDelete(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpURLConnection localHttpURLConnection = null;
		Object localObject = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		try {
			localURL = new URL(paramString1);
			localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
			localHttpURLConnection.setRequestMethod("DELETE");
			localHttpURLConnection.setDoInput(true);
			localHttpURLConnection.setUseCaches(false);
			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localHttpURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromResponse(localHttpURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			setErrorCode(localHttpURLConnection.getResponseCode());
			String str2 = localHttpURLConnection.getContentType();
			if ((str2 != null) && (paramString2 != null))
				str2 = rewriteEncoding(str2, paramString2);
			if (str2 != null)
				paramHttpServletResponse.setContentType(str2);
			else
				str2 = "";
			String str3 = localHttpURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteHeaders(localHttpURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject = new GZIPInputStream(localHttpURLConnection.getInputStream());
				else
					localObject = new BufferedInputStream(localHttpURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject != null)
				((InputStream) localObject).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doDeleteSSL(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpsURLConnection localHttpsURLConnection = null;
		Object localObject = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		try {
			localURL = new URL(paramString1);
			localHttpsURLConnection = (HttpsURLConnection) localURL.openConnection();
			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				GetPost.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				// SSLContext localSSLContext = SSLContext.getInstance("SSL");
				// localSSLContext.init(null, arrayOfTrustManager, new
				// SecureRandom());
				localHttpsURLConnection.setSSLSocketFactory(GetPost.getFactory(new File(strKeyStoreFile), strKeyStorePassword));

				String strAuthentication = Base64.encode(strAuthenticationKey.getBytes());
				localHttpsURLConnection.setRequestProperty("Authorization", "Basic " + strAuthentication);

			} catch (Exception localException) {
			}
			localHttpsURLConnection.setRequestMethod("DELETE");
			localHttpsURLConnection.setDoInput(true);
			localHttpsURLConnection.setUseCaches(false);
			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localHttpsURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromSSLResponse(localHttpsURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			setErrorCode(localHttpsURLConnection.getResponseCode());
			String str2 = localHttpsURLConnection.getContentType();
			if ((str2 != null) && (paramString2 != null))
				str2 = rewriteEncoding(str2, paramString2);
			if (str2 != null)
				paramHttpServletResponse.setContentType(str2);
			else
				str2 = "";
			String str3 = localHttpsURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteHeaders(localHttpsURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject = new GZIPInputStream(localHttpsURLConnection.getInputStream());
				else
					localObject = new BufferedInputStream(localHttpsURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject != null)
				((InputStream) localObject).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doHead(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpURLConnection localHttpURLConnection = null;
		Object localObject = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		try {
			localURL = new URL(paramString1);
			localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
			localHttpURLConnection.setRequestMethod("HEAD");
			localHttpURLConnection.setDoInput(true);
			localHttpURLConnection.setUseCaches(false);
			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localHttpURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromResponse(localHttpURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			setErrorCode(localHttpURLConnection.getResponseCode());
			String str2 = localHttpURLConnection.getContentType();
			if ((str2 != null) && (paramString2 != null))
				str2 = rewriteEncoding(str2, paramString2);
			if (str2 != null)
				paramHttpServletResponse.setContentType(str2);
			else
				str2 = "";
			String str3 = localHttpURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteHeaders(localHttpURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject = new GZIPInputStream(localHttpURLConnection.getInputStream());
				else
					localObject = new BufferedInputStream(localHttpURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject != null)
				((InputStream) localObject).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	private String doHeadSSL(String paramString1, Hashtable paramHashtable, String paramString2, HttpServletResponse paramHttpServletResponse) {
		URL localURL = null;
		HttpsURLConnection localHttpsURLConnection = null;
		Object localObject = null;
		BufferedOutputStream localBufferedOutputStream = null;
		String str1 = "";
		try {
			localURL = new URL(paramString1);
			localHttpsURLConnection = (HttpsURLConnection) localURL.openConnection();
			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				GetPost.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				// SSLContext localSSLContext = SSLContext.getInstance("SSL");
				// localSSLContext.init(null, arrayOfTrustManager, new
				// SecureRandom());
				localHttpsURLConnection.setSSLSocketFactory(GetPost.getFactory(new File(strKeyStoreFile), strKeyStorePassword));

				String strAuthentication = Base64.encode(strAuthenticationKey.getBytes());
				localHttpsURLConnection.setRequestProperty("Authorization", "Basic " + strAuthentication);

			} catch (Exception localException) {
			}
			localHttpsURLConnection.setRequestMethod("HEAD");
			localHttpsURLConnection.setDoInput(true);
			localHttpsURLConnection.setUseCaches(false);
			if (paramHashtable != null) {
				Enumeration localEnumeration = paramHashtable.keys();
				while (localEnumeration.hasMoreElements()) {
					str1 = (String) localEnumeration.nextElement();
					// This checking is added because the image request needs to
					// be fetched every time despite of the modification
					if (str1 != null && !str1.equals("if-modified-since") && !str1.equals("if-none-match"))
						localHttpsURLConnection.setRequestProperty(str1, (String) paramHashtable.get(str1));
				}
			}
		} catch (Exception localException1) {
			getErrorCodeFromSSLResponse(localHttpsURLConnection);
			return getMessage(paramString1, localException1);
		}
		try {
			setErrorCode(localHttpsURLConnection.getResponseCode());
			String str2 = localHttpsURLConnection.getContentType();
			if ((str2 != null) && (paramString2 != null))
				str2 = rewriteEncoding(str2, paramString2);
			if (str2 != null)
				paramHttpServletResponse.setContentType(str2);
			else
				str2 = "";
			String str3 = localHttpsURLConnection.getContentEncoding();
			if (paramHashtable != null)
				rewriteHeaders(localHttpsURLConnection, paramHttpServletResponse);
			if (str3 == null)
				str3 = "";
			try {
				if (str3.indexOf("gzip") >= 0)
					localObject = new GZIPInputStream(localHttpsURLConnection.getInputStream());
				else
					localObject = new BufferedInputStream(localHttpsURLConnection.getInputStream());
				localBufferedOutputStream = new BufferedOutputStream(paramHttpServletResponse.getOutputStream());
				int i;
				while ((i = ((InputStream) localObject).read()) >= 0)
					localBufferedOutputStream.write(i);
			} catch (Exception localException3) {
				return getMessage(paramString1, localException3);
			}
			if (localObject != null)
				((InputStream) localObject).close();
			if (localBufferedOutputStream != null) {
				localBufferedOutputStream.flush();
				localBufferedOutputStream.close();
			}
		} catch (Exception localException2) {
			return getMessage(paramString1, localException2);
		}
		return null;
	}

	protected String getMessage(String paramString, Exception paramException) {
		String str = paramException.getClass().getName();
		int i = str.lastIndexOf(46);
		str = str.substring(i + 1);
		StringWriter localStringWriter = new StringWriter();
		PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
		paramException.printStackTrace(localPrintWriter);
		localPrintWriter.close();
		return "Request: " + paramString + "\nException: " + str + ": " + paramException.getMessage() + "\n" + localStringWriter.getBuffer().toString();
	}

	private InputStreamReader getInputStreamReader(InputStream paramInputStream, String paramString) throws UnsupportedEncodingException {
		if (paramString == null)
			return new InputStreamReader(paramInputStream);
		return new InputStreamReader(paramInputStream, paramString);
	}

	private String rewriteEncoding(String paramString1, String paramString2) {
		if (paramString1.indexOf("charset") < 0)
			return paramString1 + ";charset=" + paramString2;
		int i = paramString1.indexOf(";");
		if (i < 0)
			i = paramString1.indexOf("charset");
		return paramString1.substring(0, i) + ";charset=" + paramString2;
	}

	private String getParamsFromHash(Hashtable paramHashtable) {
		String str1 = "";
		String str2 = "";
		Enumeration localEnumeration = paramHashtable.keys();
		while (localEnumeration.hasMoreElements()) {
			if (str1.length() > 0)
				str1 = str1 + "&";
			str2 = (String) localEnumeration.nextElement();
			String str3 = str2;
			int i = str3.indexOf("<");
			if (i > 0)
				str3 = str3.substring(0, i);
			str1 = str1 + str3 + "=";
			str1 = str1 + URLEncoder.encode((String) paramHashtable.get(str2));
		}
		return str1;
	}

	private void rewriteHeaders(URLConnection paramURLConnection, HttpServletResponse paramHttpServletResponse) {
		Map localMap = paramURLConnection.getHeaderFields();
		if (localMap != null) {
			Set localSet = localMap.keySet();
			Iterator localIterator1 = localSet.iterator();
			while (localIterator1.hasNext()) {
				String str1 = (String) localIterator1.next();
				if ((str1 != null) && (!str1.equals("Content-Type"))) {
					List localList = (List) localMap.get(str1);
					String str2 = "";
					Iterator localIterator2 = localList.iterator();
					while (localIterator2.hasNext()) {
						if (str2.length() > 0)
							str2 = str2 + ",";
						str2 = str2 + (String) localIterator2.next();
					}
					paramHttpServletResponse.setHeader(str1, str2);
				}
			}
		}
	}

	private void rewriteSSLHeaders(HttpsURLConnection paramHttpsURLConnection, HttpServletResponse paramHttpServletResponse) {
		Map localMap = paramHttpsURLConnection.getHeaderFields();
		if (localMap != null) {
			Set localSet = localMap.keySet();
			Iterator localIterator1 = localSet.iterator();
			while (localIterator1.hasNext()) {
				String str1 = (String) localIterator1.next();
				if ((str1 != null) && (!str1.equals("Content-Type"))) {
					List localList = (List) localMap.get(str1);
					String str2 = "";
					Iterator localIterator2 = localList.iterator();
					while (localIterator2.hasNext()) {
						if (str2.length() > 0)
							str2 = str2 + ",";
						str2 = str2 + (String) localIterator2.next();
					}
					paramHttpServletResponse.setHeader(str1, str2);
					if ("true".equals(this.log))
						System.out.println("str1::" + str1 + "::str2::" + str2);
				}
			}
		}
	}

	private void getErrorCodeFromResponse(URLConnection paramURLConnection) {
		try {
			if ((paramURLConnection instanceof HttpURLConnection))
				getErrorCodeFromResponse((HttpURLConnection) paramURLConnection);
		} catch (Exception localException) {
			setErrorCode(500);
		}
	}

	private void getErrorCodeFromResponse(HttpURLConnection paramHttpURLConnection) {
		try {
			setErrorCode(paramHttpURLConnection.getResponseCode());
		} catch (Exception localException) {
			setErrorCode(500);
		}
	}

	private void getErrorCodeFromSSLResponse(URLConnection paramURLConnection) {
		try {
			if ((paramURLConnection instanceof HttpsURLConnection))
				getErrorCodeFromSSLResponse((HttpsURLConnection) paramURLConnection);
		} catch (Exception localException) {
			setErrorCode(500);
		}
	}

	private void getErrorCodeFromSSLResponse(HttpsURLConnection paramHttpsURLConnection) {
		try {
			setErrorCode(paramHttpsURLConnection.getResponseCode());
		} catch (Exception localException) {
			setErrorCode(500);
		}
	}
}
