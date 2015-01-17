package main.java.shoppingsite.service.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpProxyServlet extends HttpServlet {
	private String host;
	private String proxyHost;
	private String proxyPort;
	private String rewriteHost;
	private String encoding;
	private String keepHeaders = "true";
	private String uri;
	private String path = "false";
	private String disableAcceptEncoding = "false";
	private String log = "true";
	private String certificate = "true";
	private String keepAlive = "true";

	public void init() throws ServletException {
		this.host = getInitParameter("host");
		if (this.host == null) {
			System.out.println("HttpProxyServlet needs a host in the parameter.");
			throw new ServletException("HttpProxyServlet needs a host in the parameter.");
		}
		this.proxyHost = getInitParameter("proxyHost");
		this.proxyPort = getInitParameter("proxyPort");
		this.rewriteHost = getInitParameter("rewriteHost");
		this.encoding = getInitParameter("encoding");
		this.uri = getInitParameter("uri");
		this.path = getInitParameter("path");
		this.keepHeaders = getInitParameter("headers");
		this.disableAcceptEncoding = getInitParameter("gzip");
		this.log = getInitParameter("log");
		this.certificate = getInitParameter("certificate");
		this.keepAlive = getInitParameter("keepAlive");
		if (this.keepAlive == null)
			this.keepAlive = "true";
		if (this.keepHeaders == null)
			this.keepHeaders = "true";
		if (this.log == null)
			this.log = "false";
		if (this.certificate == null)
			this.certificate = "false";
		if (this.disableAcceptEncoding == null)
			this.disableAcceptEncoding = "false";
		if ("true".equals(this.log))
			System.out.println("HttpProxy servlet. Init");
		if ("false".equals(this.keepAlive))
			System.setProperty("http.keepAlive", "false");
		if ("true".equals(this.certificate)) {
			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};

				HttpProxyServlet.trustAllHttpsCertificates();

				HttpsURLConnection.setDefaultHostnameVerifier(hv);

			} catch (Exception localException) {
			}
		}
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

	@Override
	public void doDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getQueryString();
		String str2 = this.host;
		String str3 = null;
		if (this.uri != null) {
			this.uri = normalizeUri(this.uri);
			str3 = paramHttpServletRequest.getRequestURI();
			if (str3 != null) {
				if (!str3.startsWith(this.uri))
					str3 = null;
				else if (this.uri.length() >= str3.length())
					str3 = null;
				else
					str3 = str3.substring(this.uri.length());
				if (str3 != null)
					str2 = addUri(str2, str3);
			}
		}
		if ("true".equals(this.path))
			str2 = addUri(str2, paramHttpServletRequest.getPathInfo());
		str2 = addQuery(str2, str1);
		if ("true".equals(this.log))
			System.out.println("HTTP DELETE. url=" + str2);
		Hashtable localHashtable = null;
		if ("true".equals(this.keepHeaders)) {
			localHashtable = new Hashtable();
			getHeaders(paramHttpServletRequest, localHashtable);
			if ("true".equals(this.rewriteHost)) {
				localHashtable.remove("host");
				localHashtable.remove("Host");
				localHashtable.remove("HOST");
				localHashtable.put("Host", getHostInfo(this.host));
			}
			if ("false".equals(this.disableAcceptEncoding))
				localHashtable.remove("ACCEPT-ENCODING");
		}
		GetPost localGetPost = new GetPost();
		String str4 = localGetPost.doDelete(str2, null, localHashtable, -1, this.proxyHost, this.proxyPort, this.encoding, paramHttpServletRequest.getInputStream(), paramHttpServletResponse);
		if (str4 != null) {
			int i = localGetPost.getErrorCode();
			if (i > 0)
				paramHttpServletResponse.sendError(i, str4);
			else
				paramHttpServletResponse.sendError(503, str4);
			if ("true".equals(this.log))
				System.out.println("Error:" + str4);
			if ("true".equals(this.log))
				System.out.println("Error code:" + i);
		}
	}

	@Override
	public void doHead(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getQueryString();
		String str2 = this.host;
		String str3 = null;
		if (this.uri != null) {
			this.uri = normalizeUri(this.uri);
			str3 = paramHttpServletRequest.getRequestURI();
			if (str3 != null) {
				if (!str3.startsWith(this.uri))
					str3 = null;
				else if (this.uri.length() >= str3.length())
					str3 = null;
				else
					str3 = str3.substring(this.uri.length());
				if (str3 != null)
					str2 = addUri(str2, str3);
			}
		}
		if ("true".equals(this.path))
			str2 = addUri(str2, paramHttpServletRequest.getPathInfo());
		str2 = addQuery(str2, str1);
		if ("true".equals(this.log))
			System.out.println("HTTP HEAD. url=" + str2);
		Hashtable localHashtable = null;
		if ("true".equals(this.keepHeaders)) {
			localHashtable = new Hashtable();
			getHeaders(paramHttpServletRequest, localHashtable);
			if ("true".equals(this.rewriteHost)) {
				localHashtable.remove("host");
				localHashtable.remove("Host");
				localHashtable.remove("HOST");
				localHashtable.put("Host", getHostInfo(this.host));
			}
			if ("false".equals(this.disableAcceptEncoding))
				localHashtable.remove("ACCEPT-ENCODING");
		}
		GetPost localGetPost = new GetPost();
		String str4 = localGetPost.doHead(str2, null, localHashtable, -1, this.proxyHost, this.proxyPort, this.encoding, paramHttpServletRequest.getInputStream(), paramHttpServletResponse);
		if (str4 != null) {
			int i = localGetPost.getErrorCode();
			if (i > 0)
				paramHttpServletResponse.sendError(i, str4);
			else
				paramHttpServletResponse.sendError(503, str4);
			if ("true".equals(this.log))
				System.out.println("Error:" + str4);
			if ("true".equals(this.log))
				System.out.println("Error code:" + i);
		}
	}

	@Override
	public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getQueryString();
		String str2 = this.host;
		String str3 = null;
		if ("true".equals(this.log))
			System.out.println("URI:" + paramHttpServletRequest.getRequestURI() + "::str1::" + str1 + "::path::" + paramHttpServletRequest.getPathInfo());
		if (this.uri != null) {
			this.uri = normalizeUri(this.uri);
			str3 = paramHttpServletRequest.getRequestURI();
			if (str3 != null) {
				if (!str3.startsWith(this.uri))
					str3 = null;
				else if (this.uri.length() >= str3.length())
					str3 = null;
				else
					str3 = str3.substring(this.uri.length());
				if (str3 != null)
					str2 = addUri(str2, str3);
			}
		}

		if ("true".equals(this.log))
			System.out.println("URI:" + str2);

		if ("true".equals(this.path))
			str2 = addUri(str2, paramHttpServletRequest.getPathInfo());
		str2 = addQuery(str2, str1);
		if ("true".equals(this.log))
			System.out.println("HTTP GET. url=" + str2);
		Hashtable localHashtable = null;
		if ("true".equals(this.keepHeaders)) {
			localHashtable = new Hashtable();
			getHeaders(paramHttpServletRequest, localHashtable);
			if ("true".equals(this.rewriteHost)) {
				localHashtable.remove("host");
				localHashtable.remove("Host");
				localHashtable.remove("HOST");
				localHashtable.put("Host", getHostInfo(this.host));
			}
			if ("false".equals(this.disableAcceptEncoding))
				localHashtable.remove("ACCEPT-ENCODING");
		}
		GetPost localGetPost = new GetPost();
		String str4 = localGetPost.doAction(paramHttpServletRequest.getMethod().toUpperCase(), str2, null, localHashtable, -1, this.proxyHost, this.proxyPort, this.encoding, paramHttpServletRequest.getInputStream(), paramHttpServletResponse);
		if (str4 != null) {
			int i = localGetPost.getErrorCode();
			if (i > 0)
				paramHttpServletResponse.sendError(i, str4);
			else
				paramHttpServletResponse.sendError(503, str4);
			if ("true".equals(this.log))
				System.out.println("Error:" + str4);
			if ("true".equals(this.log))
				System.out.println("Error code:" + i);
		}
	}

	@Override
	public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getQueryString();
		String str2 = this.host;
		String str3 = null;
		Object localObject1 = null;
		String[] localObject2 = null;
		if (this.uri != null) {
			this.uri = normalizeUri(this.uri);
			str3 = paramHttpServletRequest.getRequestURI();
			if (str3 != null) {
				if (!str3.startsWith(this.uri))
					str3 = null;
				else if (this.uri.length() >= str3.length())
					str3 = null;
				else
					str3 = str3.substring(this.uri.length());
				if (str3 != null)
					str2 = addUri(str2, str3);
			}
		}
		if ("true".equals(this.path))
			str2 = addUri(str2, paramHttpServletRequest.getPathInfo());
		str2 = addQuery(str2, str1);
		if ("true".equals(this.log))
			System.out.println("HTTP POST. url=" + str2);
		Hashtable localHashtable1 = null;
		Hashtable localHashtable2 = new Hashtable();
		Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
		int i;
		while (localEnumeration.hasMoreElements()) {
			localObject1 = (String) localEnumeration.nextElement();
			localObject2 = (String[]) paramHttpServletRequest.getParameterValues((String) localObject1);
			if ((localObject2 != null) && (localObject2.length > 0))
				for (i = 0; i < localObject2.length; i++)
					localHashtable2.put((String) localObject1 + "<" + i + ">", localObject2[i]);
		}
		if ("true".equals(this.keepHeaders)) {
			localHashtable1 = new Hashtable();
			getHeaders(paramHttpServletRequest, localHashtable1);
			if ("true".equals(this.rewriteHost)) {
				localHashtable1.remove("host");
				localHashtable1.remove("Host");
				localHashtable1.remove("HOST");
				localHashtable1.put("Host", getHostInfo(this.host));
			}
			if ("false".equals(this.disableAcceptEncoding))
				localHashtable1.remove("ACCEPT-ENCODING");
		}
		Object localObject11 = new GetPost();
		Object localObject22 = ((GetPost) localObject11).doAction(paramHttpServletRequest.getMethod().toUpperCase(), str2, localHashtable2, localHashtable1, -1, this.proxyHost, this.proxyPort, this.encoding, paramHttpServletRequest.getInputStream(), paramHttpServletResponse);
		if (localObject22 != null) {
			i = ((GetPost) localObject11).getErrorCode();
			if (i > 0)
				paramHttpServletResponse.sendError(i, (String) localObject22);
			else
				paramHttpServletResponse.sendError(503, (String) localObject22);
			if ("true".equals(this.log))
				System.out.println("Error:" + (String) localObject22);
			if ("true".equals(this.log))
				System.out.println("Error code:" + i);
		}
	}

	@Override
	public void doPut(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getQueryString();
		String str2 = this.host;
		String str3 = null;
		Object localObject1 = null;
		String[] localObject2 = null;
		if (this.uri != null) {
			this.uri = normalizeUri(this.uri);
			str3 = paramHttpServletRequest.getRequestURI();
			if (str3 != null) {
				if (!str3.startsWith(this.uri))
					str3 = null;
				else if (this.uri.length() >= str3.length())
					str3 = null;
				else
					str3 = str3.substring(this.uri.length());
				if (str3 != null)
					str2 = addUri(str2, str3);
			}
		}
		str2 = addQuery(str2, str1);
		if ("true".equals(this.log))
			System.out.println("HTTP PUT. url=" + str2);
		Hashtable localHashtable1 = null;
		Hashtable localHashtable2 = new Hashtable();
		Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
		int i;
		while (localEnumeration.hasMoreElements()) {
			localObject1 = (String) localEnumeration.nextElement();
			localObject2 = paramHttpServletRequest.getParameterValues((String) localObject1);
			if ((localObject2 != null) && (localObject2.length > 0))
				for (i = 0; i < localObject2.length; i++)
					localHashtable2.put((String) localObject1 + "<" + i + ">", localObject2[i]);
		}
		if ("true".equals(this.keepHeaders)) {
			localHashtable1 = new Hashtable();
			getHeaders(paramHttpServletRequest, localHashtable1);
			if ("true".equals(this.rewriteHost)) {
				localHashtable1.remove("host");
				localHashtable1.remove("Host");
				localHashtable1.remove("HOST");
				localHashtable1.put("Host", getHostInfo(this.host));
			}
			if ("false".equals(this.disableAcceptEncoding))
				localHashtable1.remove("ACCEPT-ENCODING");
		}
		Object localObject11 = new GetPost();
		Object localObject22 = ((GetPost) localObject11).doPut(str2, localHashtable2, localHashtable1, -1, this.proxyHost, this.proxyPort, this.encoding, paramHttpServletRequest.getInputStream(), paramHttpServletResponse);
		if (localObject22 != null) {
			i = ((GetPost) localObject11).getErrorCode();
			if (i > 0)
				paramHttpServletResponse.sendError(i, (String) localObject22);
			else
				paramHttpServletResponse.sendError(503, (String) localObject22);
		}
	}

	private void getHeaders(HttpServletRequest paramHttpServletRequest, Hashtable paramHashtable) {
		Enumeration localEnumeration1 = paramHttpServletRequest.getHeaderNames();
		while (localEnumeration1.hasMoreElements()) {
			String str1 = (String) localEnumeration1.nextElement();
			String str2 = "";
			Enumeration localEnumeration2 = paramHttpServletRequest.getHeaders(str1);
			while (localEnumeration2.hasMoreElements()) {
				if (str2.length() > 0)
					str2 = str2 + ",";
				str2 = str2 + (String) localEnumeration2.nextElement();
			}
			paramHashtable.put(str1, str2);
			if ("true".equals(this.log))
				System.out.println("sstr1:" + str1 + "::sstr2:" + str2);
		}
		if ("true".equals(this.log))
			System.out.println("Setting the home header value.");
		paramHashtable.put("iv-home", paramHttpServletRequest.getContextPath());
	}

	private String getHostInfo(String paramString) {
		String str = paramString;
		int i = str.indexOf("://");
		if (i > 0)
			str = str.substring(i + 3);
		i = str.indexOf("/");
		if (i > 0)
			str = str.substring(0, i);
		i = str.indexOf("?");
		if (i > 0)
			str = str.substring(0, i);
		i = str.indexOf("#");
		if (i > 0)
			str = str.substring(0, i);
		i = str.indexOf(";");
		if (i > 0)
			str = str.substring(0, i);
		return str;
	}

	private String addQuery(String paramString1, String paramString2) {
		if (paramString2 != null) {
			if (paramString1.indexOf("?") < 0)
				return paramString1 + "?" + paramString2;
			return paramString1 + "&" + paramString2;
		}
		return paramString1;
	}

	private String addUri(String paramString1, String paramString2) {
		if (paramString2 == null)
			return paramString1;
		if (paramString2.length() == 0)
			return paramString1;
		if (paramString2.equals("/"))
			return paramString1;
		String str1 = paramString2;
		if (str1.charAt(0) == '/')
			str1 = str1.substring(1);
		if (str1.length() == 0)
			return paramString1;
		String str2 = "";
		String str3 = paramString1;
		int i = str3.indexOf("?");
		if (i > 0) {
			if (i < str3.length() - 1)
				str2 = str3.substring(i + 1);
			str3 = str3.substring(0, i);
		}
		if (!str3.endsWith("/"))
			str3 = str3 + "/";
		str3 = str3 + str1;
		if (str2.length() > 0)
			str3 = str3 + "?" + str2;
		return str3;
	}

	private String normalizeUri(String paramString) {
		int i = paramString.indexOf("*");
		if (i < 0)
			return paramString;
		if (i == 0)
			return "/";
		return paramString.substring(0, i);
	}
}