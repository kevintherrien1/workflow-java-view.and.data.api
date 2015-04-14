package com.autodesk.api.sample.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccessTokenGenerator
 */
@WebServlet("/AccessTokenGenerator")
public class AccessTokenGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public String token = null;
	public String key = null;
	public String secret = null;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		key = request.getParameter("key");
		secret = request.getParameter("secret");
		
		DataOutputStream output = null;
		InputStream input = null;
		BufferedReader buffer = null;

		//get access token
		try {
			URL authenticationURL = new URL(
					"https://developer.api.autodesk.com/authentication/v1/authenticate");
			HttpsURLConnection connection = (HttpsURLConnection) authenticationURL
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			

			output = new DataOutputStream(connection.getOutputStream());
			output.writeBytes("client_id="
					+ URLEncoder.encode(key, "UTF-8")
					+ "&client_secret="
					+ URLEncoder.encode(secret, "UTF-8")
					+ "&grant_type=client_credentials");
			

			//get the response
			input = connection.getInputStream();
			buffer = new BufferedReader(new InputStreamReader(input));
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			
			while ((line = buffer.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append('\r');
			}

			System.out.println(stringBuffer);
			
			//parse the response
			String responseString = stringBuffer.toString();
			int index = responseString.indexOf("\"access_token\":\"")
					+ "\"access_token\":\"".length();
			int index2 = responseString.indexOf("\"", index);
			token = responseString.substring(index, index2);
			
		} catch (IOException e) {
			System.out.println(e);
		}
		request.getSession().setAttribute("tokenResponse", token);
		request.getSession().setAttribute("token", token);
		response.sendRedirect("./access-token.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
