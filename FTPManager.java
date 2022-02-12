        package ftp;
        
        import org.apache.commons.net.ftp.FTP;
        import org.apache.commons.net.ftp.FTPClient;
        import org.apache.commons.net.ftp.FTPFile;
        
        import javax.swing.*;
        import java.io.*;
        // connect and login to the server
        // use local passive mode to pass firewall
        
        public class FTPManager {
            final int PORT =21;
            public void UploadFile(String server, String user, String pass, File file, String Saveto) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    InputStream inputStream = new FileInputStream(file);
                    boolean done = ftpClient.storeFile(Saveto, inputStream);
                    inputStream.close();
                    if (done) {
                        System.out.println("");
                    }
                    ftpClient.logout();
        ftpClient.disconnect();
        
                } catch (IOException e) {
                    e.printStackTrace();
                }
        
            }
        
            public static boolean uploadSingleFile(FTPClient ftpClient,
                                                   String localFilePath, String remoteFilePath) throws IOException {
                File localFile = new File(localFilePath);
        
                InputStream inputStream = new FileInputStream(localFile);
                try {
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    return ftpClient.storeFile(remoteFilePath, inputStream);
                } finally {
                    inputStream.close();
                }
            }
        
        
            public static void uploadDirectory(FTPClient ftpClient,
                                               String remoteDirPath, String localParentDir, String remoteParentDir)
                    throws IOException {
        
                File localDir = new File(localParentDir);
                File[] subFiles = localDir.listFiles();
                if (subFiles != null && subFiles.length > 0) {
                    for (File item : subFiles) {
                        String remoteFilePath = remoteDirPath + "/" + remoteParentDir
                                + "/" + item.getName();
                        if (remoteParentDir.equals("")) {
                            remoteFilePath = remoteDirPath + "/" + item.getName();
                        }
        
        
                        if (item.isFile()) {
                            // upload the file
                            String localFilePath = item.getAbsolutePath();
                            System.out.println("About to upload the file: " + localFilePath);
                            boolean uploaded = uploadSingleFile(ftpClient,
                                    localFilePath, remoteFilePath);
                            if (uploaded) {
                                System.out.println("UPLOADED a file to: "
                                        + remoteFilePath);
                            }
                        } else {
                            // create directory on the server
                            boolean created = ftpClient.makeDirectory(remoteFilePath);
                            if (created) {
                                System.out.println("CREATED the directory: "
                                        + remoteFilePath);
                            }
                            // upload the sub directory
                            String parent = remoteParentDir + "/" + item.getName();
                            if (remoteParentDir.equals("")) {
                                parent = item.getName();
                            }
        
                            localParentDir = item.getAbsolutePath();
                            uploadDirectory(ftpClient, remoteDirPath, localParentDir,
                                    parent);
                        }
                    }
                }
            }
        
            public void PUSHout(String server, String user, String pass, String remoteDirPath, String localDirPath) {
        
                FTPClient ftpClient = new FTPClient();
        
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    uploadDirectory(ftpClient, remoteDirPath, localDirPath, "");
        
                    // log out and disconnect from the server
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        
            public void renameFile(String server, String user, String pass, String From, String To) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
        
        
                    boolean success = ftpClient.rename(From, To);
                    if (success) {
                    } else {
        
                    }
        
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        
            public void renameDir(String server, String user, String pass, String From, String To) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
        
                    boolean success = ftpClient.rename(From, To);
                    if (success) {
        
                    } else {
        
                    }
        
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
        
            }
        
            public void DownloadFile(String server, String user, String pass, File file, String remoteFile) throws Exception {
                FTPClient ftpClient = new FTPClient();
                try {
        
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(file));
                    boolean success = ftpClient.retrieveFile(remoteFile, outputStream1);
                    outputStream1.close();
        
                    if (success) {
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
            public void downloadDirectory(FTPClient ftpClient, String parentDir,
                                          String currentDir, String saveDir) throws IOException {
                String dirToList = parentDir;
                if (!currentDir.equals("")) {
                    dirToList += "/" + currentDir;
                }
        
                FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        
                if (subFiles != null && subFiles.length > 0) {
                    for (FTPFile aFile : subFiles) {
                        String currentFileName = aFile.getName();
                        if (currentFileName.equals(".") || currentFileName.equals("..")) {
                            // skip parent directory and the directory itself
                            continue;
                        }
                        String filePath = parentDir + "/" + currentDir + "/"
                                + currentFileName;
                        if (currentDir.equals("")) {
                            filePath = parentDir + "/" + currentFileName;
                        }
        
                        String newDirPath = saveDir + parentDir + File.separator
                                + currentDir + File.separator + currentFileName;
                        if (currentDir.equals("")) {
                            newDirPath = saveDir + parentDir + File.separator
                                    + currentFileName;
                        }
        
                        if (aFile.isDirectory()) {
                            // create the directory in saveDir
                            File newDir = new File(newDirPath);
                            boolean created = newDir.mkdirs();
                            if (created) {
                            } else {
        
                            }
                            // download the sub directory
                            downloadDirectory(ftpClient, dirToList, currentFileName,
                                    saveDir);
                        } else {
                            // download the file
                            boolean success = downloadSingleFile(ftpClient, filePath,
                                    newDirPath);
                            if (success) {
                              JOptionPane.showMessageDialog(null,"Downloaded!");
                            } else {
        
                            }
                        }
                    }
                }
            }
        
            public static boolean downloadSingleFile(FTPClient ftpClient,
                                                     String remoteFilePath, String savePath) throws IOException {
                File downloadFile = new File(savePath);
                File parentDir = downloadFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdir();
                }
        
                OutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream(downloadFile));
                try {
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    return ftpClient.retrieveFile(remoteFilePath, outputStream);
                } catch (IOException ex) {
                    throw ex;
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }
        
            public void PULL(String server, String user, String pass, String savepath, String remoteDirPath) throws Exception {
                FTPClient ftpClient = new FTPClient();
                try {
        
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    downloadDirectory(ftpClient, remoteDirPath, "", savepath);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        
            public void deletFile(String server, String user, String pass, String name) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    boolean success = ftpClient.deleteFile(name);
                    if (success) {
                    } else {
        
                    }
        
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        
            public void createDir(String server, String user, String pass, String name) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    boolean success = ftpClient.makeDirectory(name);
                    if (success) {
                    } else {
        
                    }
        
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        
            public void changeDir(String server, String user, String pass, String To) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    boolean success = ftpClient.changeWorkingDirectory(To);
                    if (success) {
                    } else {
        
                    }
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        
            public void deletFolder(String server, String user, String pass, String name) throws Exception {
        
                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
        
        
                    boolean success = ftpClient.deleteFile(name);
                    if (success) {
                    } else {
        
                    }
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
            public String listFiles(String server, String user, String pass) throws Exception {
                int n = 1000000;
                FTPClient ftpClient = new FTPClient();
                StringBuilder builder = new StringBuilder();
                StringBuilder builder1 = new StringBuilder();
                try {
                    ftpClient.connect(server, PORT);
                    ftpClient.login(user, pass);
                    FTPFile[] files = ftpClient.listFiles();
                    for (FTPFile file : files) {
                        if (file.isDirectory()) {
                            builder1.append(file.getName() + " : " + String.valueOf(file.getSize() / n) + " mbs " + "\n");
                        }
                    }
                    String[] names = ftpClient.listNames();
                    if (names != null && names.length > 0) {
                        for (String file : names) {
                            builder.append(file + "\n");
                        }
                    }
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
                String al=builder1.append(builder).toString();
                return al;
            }
        }
        
