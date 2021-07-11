package com.sm.app;

import com.sm.app.service.FtpService;
import org.junit.Test;
import sun.net.ftp.FtpClient;

public class FtpClientTest {

    /**
     * Upload file to ftp server
     */
    @Test
    public void uploadFileTest() {
        new FtpService().uploadFile();
    }

    /**
     * Download file from ftp server
     */
    @Test
    public void downloadFile() {
        String download = new FtpService().downloadFile();
        System.out.println("Downloaded text\n" + download);
    }
}
