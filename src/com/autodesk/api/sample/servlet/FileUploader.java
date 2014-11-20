package com.autodesk.api.sample.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
 * Servlet implementation class FileUploader
 */
@WebServlet("/FileUploader")
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("run");
		
		// get token and bucket name from session
		String bucketName = request.getParameter("bucket-name");
		bucketName = (String) request.getSession().getAttribute("bucketName");
		String token = (String) request.getSession().getAttribute("token");
		
		DataOutputStream output = null;
		InputStream input = null;
		BufferedReader buffer = null;

		try {
			
			File binaryFile = new File("/Users/shiya/Box Sync/rac_basic_sample_project.rvt");
			// Just generate some unique random value.
			
			URL uploadurl = new URL(
					"https://developer.api.autodesk.com/oss/v1/buckets/"
					+ bucketName + "/objects/" + binaryFile.getName());
			HttpsURLConnection connection = (HttpsURLConnection) uploadurl
					.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization",
					"Bearer "+ token);
			connection.setRequestProperty("Content-Type",
					"application/octet-stream");
			connection.setRequestProperty("Content-Length",
					Long.toString(binaryFile.length()));
			
			connection.setDoOutput(true);
			connection.setDoInput(true);

//			output = new DataOutputStream(connection.getOutputStream());
//			
//			OutputStream out = connection.getOutputStream();
//		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
//		    
//		    System.out.println("connect 2");
//		    
//		    // Send binary file.
//		    Files.copy(binaryFile.toPath(), out);
//		    out.flush(); // Important before continuing with writer!
//		    writer.flush(); // CRLF is important! It indicates end of boundary.
			
			BufferedOutputStream bos = new BufferedOutputStream(
					connection.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(binaryFile));
			int i;
			// read byte by byte until end of stream
			while ((i = bis.read()) > 0) {
				bos.write(i);
			}
			bos.close();
			
			// parse the response
		    System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() >= 400) {
				input = connection.getErrorStream();
			} else {
				input = connection.getInputStream();
			}
						
			buffer = new BufferedReader(new InputStreamReader(input));
						
			String line = "";
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
		response.sendRedirect("./upload.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
