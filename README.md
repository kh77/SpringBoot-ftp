
# SFTP Integration
	- You can upload and download text,csv,excel file from ftp server using sftp client code

#### Setup in local machine
- FileZilla Client for windows
- Sftp server through docker image
- Sftp docker hub url : https://hub.docker.com/r/atmoz/sftp  
- You can set username and password and in the FtpService class to connect from code. I use kazim:kazim in the below docker command  
- docker run -p 22:22 --name sftp -d atmoz/sftp kazim:kazim:::upload


## Important Classes
- FtpService.java: Set host,username,password and port
- FtpClientTest.java: Check two methods to run functionality