Java Sample for Autodesk View and Data API
================================
This is a very simple java servlet sample showcasing the view and data API. See the live demo here [hosted on Amazon EC2](http://javalmvwalkthrough-vq2mmximxb.elasticbeanstalk.com/).

#Dependencies
- J2EE 7.0+
- Tomcat 7.0+

#Getting Started 
### Run Locally With Eclipse J2EE + Tomcat
1. Clone/download this repo
2. Go to file -> import -> Existing Projects into Workspace. Choose your directory and click Finish.
3. If you don't have Tomcat installed, download it from [Apache](http://tomcat.apache.org/). Tomcat 7.0 and later is required. Unzip and save it in a directory you are familiar with.
4. Go to your server tab in Eclipse, if you don't have a Tomcat 7.0+, set one up by right click on your Servers tab -> New -> Server. Select the Tomcat version and where you've saved the 
5. You might need to add servlet-api.jar in your Java build path. Right click on the project -> Properties -> Java Build Path -> Libraries -> Add External JARs. `servlet-api.jar` is in the lib folder in Tomcat.
6. Finally, to run. Right click on the project -> Run As -> Run on Server. Choose your server, and the page should open up in eclipse. You can also access it at http://localhost:8080/SimpleJavaViewerSample/

#Written By
[Shiya Luo](mailto:shiya.luo@autodesk.com)
