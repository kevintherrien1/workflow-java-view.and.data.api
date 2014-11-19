package com.autodesk.api.sample.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BucketCreator
 */
@WebServlet("/BucketCreator")
public class BucketCreator extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BucketCreator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bucketName = request.getParameter("bucket-name");
		
		// get the token from access token generator
		String token = null;
		
		DataOutputStream output = null;
		InputStream input = null;
		BufferedReader buffer = null;
		
		System.out.println("run");
		
		// create bucket
		try {
			token = (String) request.getSession().getAttribute("token");
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
			output.writeBytes("{\"bucketKey\":\""+ bucketName +",\"policy\":\"persistent\"}");
			

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
		response.sendRedirect("./create-bucket.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
