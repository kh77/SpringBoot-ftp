package com.sm.app.service;

import com.jcraft.jsch.ChannelSftp;
import com.sm.app.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FtpService {

    @Autowired
    ResourceLoader resourceLoader;

    private DefaultSftpSessionFactory getSFTPFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("kazim");
        factory.setPassword("kazim");

        return factory;
    }

    /**
     * Upload file to ftp 
     */
    public void uploadFile() {
      try(SftpSession session = getSFTPFactory().getSession()) {
            // CSV FILE
           Resource resource = resourceLoader.getResource(resourceLoader.CLASSPATH_URL_PREFIX + "skipped.csv");
           session.write(resource.getInputStream(), "upload/csv-file-" + LocalDateTime.now() +".csv");

           // TEXT FILE
           resource = resourceLoader.getResource(resourceLoader.CLASSPATH_URL_PREFIX +"random-file.txt");
           session.write(resource.getInputStream(), "upload/txt-file" + LocalDateTime.now() +".txt");

           // xlsx Excel File
           resource = resourceLoader.getResource(resourceLoader.CLASSPATH_URL_PREFIX +"excel.xlsx");
           session.write(resource.getInputStream(), "upload/new-excel-" + LocalDateTime.now() +".xlsx");

           // xls Excel File
           resource = resourceLoader.getResource(resourceLoader.CLASSPATH_URL_PREFIX +"old-excel.xls");
           session.write(resource.getInputStream(), "upload/old-excel-" + LocalDateTime.now() +".xls");

//            String donedestination = String.format("upload/done/%s", filename);
//            log.info("Rename file to: " + donedestination);
//            session.rename(destination, donedestination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read file from ftp and save file in the system
     * @return
     */
    public String downloadFile() {
        try(SftpSession session = getSFTPFactory().getSession()) {
            for(ChannelSftp.LsEntry lsEntry:session.list("upload/")){
                String extension = lsEntry.getFilename().substring(lsEntry.getFilename().lastIndexOf("."));
                if(extension.length() > 1) {
                    switch (extension.substring(1)) {
                        case "txt": {
                            WriteFile(session, lsEntry,".txt");
                            break;
                        } case "csv": {
                            WriteFile(session, lsEntry,".csv");
                            break;
                        } case "xls": {
                            WriteFile(session, lsEntry,".xls");
                            break;
                        } case "xlsx": {
                            WriteFile(session, lsEntry,".xlsx");
                            break;
                        }
                        default:
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    /**
     * Write file in the System
     * @param session
     * @param lsEntry
     * @param extension
     * @throws IOException
     */
    private void WriteFile(SftpSession session, ChannelSftp.LsEntry lsEntry,String extension) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            session.read("upload/" + lsEntry.getFilename(), outputStream);
            try (FileOutputStream fileOutStream = new FileOutputStream(new File("G:/" + LocalDate.now() + extension))) {
                outputStream.writeTo(fileOutStream);
            }
        }
    }

}
