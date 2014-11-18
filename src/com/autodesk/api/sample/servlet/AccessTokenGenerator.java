package com.autodesk.api.sample.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;

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
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		String secret = request.getParameter("secret");
		
		String token = null;
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

			// parse the response
			input = connection.getInputStream();
			buffer = new BufferedReader(new InputStreamReader(input));
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = buffer.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append('\r');
			}

			System.out.println(stringBuffer);
			String responseString = stringBuffer.toString();
			int index = responseString.indexOf("\"access_token\":\"")
					+ "\"access_token\":\"".length();
			int index2 = responseString.indexOf("\"", index);
			token = responseString.substring(index, index2);
		} catch (IOException e) {
			System.out.println("Network connection error");
		}
		
		// create bucket
		try {
			URL bucketURL = new URL(
					"https://developer.api.autodesk.com/oss/v1/buckets");
			HttpsURLConnection connection = (HttpsURLConnection) bucketURL
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/json");
			connection.setRequestProperty("Authorization",
					"Bearer "+ token);
			connection.setDoOutput(true);
			connection.setDoInput(true);

			output = new DataOutputStream(connection.getOutputStream());
			output.writeBytes("{\"bucketKey\":\"shiyaluo2\",\"policy\":\"persistent\"}");
			
			

			// parse the response
			if (connection.getResponseCode() >= 400) {
				input = connection.getErrorStream();
			} else {
				input = connection.getInputStream();
			}
			buffer = new BufferedReader(new InputStreamReader(input));
			
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = buffer.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append('\r');
			}

			System.out.println(stringBuffer);
		} catch (IOException e) {
			System.out.println("Network connection error");
		}
		
		/*
		try {
			
			String param = "value";
			File textFile = new File("/path/to/file.txt");
			File binaryFile = new File("/path/to/file.bin");
			String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
			String CRLF = "\r\n"; // Line separator required by multipart/form-data.
			
			URL bucketURL = new URL(
					"https://developer.api.autodesk.com/oss/v1/buckets/steambuck/objects/SpM3W7.f3d");
			HttpsURLConnection connection = (HttpsURLConnection) bucketURL
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data");
			connection.setRequestProperty("Authorization",
					"Bearer "+ token);
			connection.setDoOutput(true);
			connection.setDoInput(true);

			output = new DataOutputStream(connection.getOutputStream());
			output.writeBytes("{\"bucketKey\":\"shiyaluo2\",\"policy\":\"persistent\"}");
			
			OutputStream out = connection.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
		    
		    // Send binary file.
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
		    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
		    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
		    writer.append(CRLF).flush();
		    Files.copy(binaryFile.toPath(), output);
		    output.flush(); // Important before continuing with writer!
		    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

		    // End of multipart/form-data.
		    writer.append("--" + boundary + "--").append(CRLF).flush();
		
			

			// parse the response
			if (connection.getResponseCode() >= 400) {
				input = connection.getErrorStream();
			} else {
				input = connection.getInputStream();
			}
			// input = connection.getInputStream();
			System.out.println("connect");
			buffer = new BufferedReader(new InputStreamReader(input));
			
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = buffer.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append('\r');
			}

			System.out.println(stringBuffer);
//			String responseString = stringBuffer.toString();
//			int index = responseString.indexOf("\"access_token\":\"")
//					+ "\"access_token\":\"".length();
//			int index2 = responseString.indexOf("\"", index);
//			token = responseString.substring(index, index2);
		} catch (IOException e) {
			System.out.println("Network connection error");
		}
		*/
		
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
