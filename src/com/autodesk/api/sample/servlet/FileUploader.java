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
import java.nio.file.Files;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUploader
 */
@WebServlet("/FileUploader")
@MultipartConfig
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// get token and bucket name from session
		String bucketName = (String) request.getSession().getAttribute("bucketName");
		String token = (String) request.getSession().getAttribute("token");
		
		String urn = null;
		
		
	    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String filename = getFilename(filePart);
	    InputStream filecontent = filePart.getInputStream();
	    File uploadFile = new File(getServletContext().getRealPath("/") + System.currentTimeMillis() + filename);
	    Files.copy(filecontent, uploadFile.toPath());	    
		
		InputStream input = null;
		BufferedReader buffer = null;
		
		// if token was not previously generated, send an error message
		if (token == null || token.length() <= 0) {
			request.getSession()
					.setAttribute("createBucketResponse",
							"Invalid token, please go back and generate another access token");
		}

		try {
			
			File binaryFile = uploadFile;
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
			
			System.out.println("file length = " + binaryFile.length());
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			BufferedOutputStream bos = new BufferedOutputStream(
					connection.getOutputStream());
			// TODO need to change the file directory to dynamic
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream("/Users/shiya/Box Sync/rac_basic_sample_project.rvt"));
			int i;
			int j = 0;
			// read byte by byte until end of stream
			while ((i = bis.read()) != -1) {
				bos.write(i);
				j++;
			}
			bos.close();
			
			// parse the response
		    System.out.println("Server returned:" + connection.getResponseCode());
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
			request.getSession().setAttribute("uploadResponse",
					"File successfully uploaded, here's the response \n" + stringBuffer);

			System.out.println(stringBuffer);
			
		} catch (IOException e) {
			System.out.println(e);
		}
		response.sendRedirect("./upload.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

}
