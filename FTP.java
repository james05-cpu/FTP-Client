package ftp;

import lock.SecMethods;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FTP {
    public static void main(String[] args) {
        new MyWindow();
    }
}

    class MyWindow extends JFrame implements ActionListener{
        JPanel topPanel,bottomPanel;
        JMenuBar jMenuBar;
        JLabel dmn,uname,pass;
        JTextField tdmn,tuname;
        JPasswordField tpass;
        JMenu Newftp,delftp,downftp,upftp,rname,vu;
        JMenuItem ftpdirectory,sfile,
                deletefl, deletedir,upftpfile,upftpfolder,
                downftpfile,downftpfolder,renameftpfile,renameftpfolder
                ;
        JList list;
        DefaultListModel model;
        FTPManager manager=null;
        coreTest test=null;
        JScrollPane scrollPane;
        SecMethods en=null;
        MyWindow(){
            en=new SecMethods();
            String[] arrayList="abcdefghijklmnopqrstuvwxyz".split("");
            test=new coreTest();
            manager=new FTPManager();
            jMenuBar=new JMenuBar();
            topPanel=new JPanel();
            bottomPanel=new JPanel();
            list=new JList();
            scrollPane=new JScrollPane(list);
             topPanel.setLayout(null);
             scrollPane.setBounds(390,80,400,250);
            topPanel.setBounds(0,0,850,600);
            dmn=new JLabel("Server");
            uname=new JLabel("UserName");
            pass=new JLabel("Password");
            dmn.setBounds(30,80,80,30);
            uname.setBounds(30,130,80,30);
            pass.setBounds(30,180,80,30);
            tdmn=new JTextField(20);
            tuname=new JTextField(20);
            tpass=new JPasswordField(20);
            tdmn.setBounds(120,80,240,30);
            tuname.setBounds(120,130,240,30);
            tpass.setBounds(120,180,240,30);
            topPanel.add(scrollPane);
            topPanel.add(dmn);
            topPanel.add(tdmn);
            topPanel.add(uname);
            topPanel.add(tuname);
            topPanel.add(pass);
            topPanel.add(tpass);
            Newftp=new JMenu("New");
            vu=new JMenu("View");
             sfile=new JMenuItem("Files");
             vu.add(sfile);
             model=new DefaultListModel();
            for (int i = 0; i < arrayList.length; i++) {
                model.add(i,arrayList[i]);
            }
            list.setModel(model);
             sfile.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     if (tdmn.getText().equals("")) {
                         return;
                     }
                     if (tuname.getText().equals("")){
                         return;
                     }
                     if (tpass.getPassword().equals("")){
                         return;
                     }
                     else {
                         String domain = tdmn.getText();
                         String userName = tuname.getText();
                         String password = String.valueOf(tpass.getPassword());
                         try {
                             String[]data=manager.listFiles(domain,userName,password).split("\n");
                             for (int i = 0; i < data.length; i++) {
                                 model.add(i,data[i]);
                             }
                             list.setModel(model);
                         } catch (Exception ex) {
                             ex.printStackTrace();
                         }
                     }
                 }
             });
            ftpdirectory =new JMenuItem("Folder");
            ftpdirectory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new CreateFolderForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String folderName = JOptionPane.showInputDialog(null,"Folder Name");
                        if (test.isNull(folderName))
                            return;
                        try {
                            manager.createDir(domain,userName,password,folderName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            Newftp.add(ftpdirectory);
            jMenuBar.add(Newftp);
            //bar.add(ftp);
            delftp=new JMenu("Delete");

            deletefl =new JMenuItem("File");
            deletedir =new JMenuItem("Folder");
            deletefl.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String filename = JOptionPane.showInputDialog(null,"File Name");
                        if (test.isNull(filename))
                            return;
                        try {
                            manager.deletFile(domain,userName,password,filename);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            deletedir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   // new DeleteFolderForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String folderName = JOptionPane.showInputDialog(null,"Folder Name");
                        if (test.isNull(folderName)){
                            return;
                        }
                        try {
                            manager.deletFolder(domain,userName,password,folderName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            renameftpfile=new JMenuItem("File");
            renameftpfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new RenameFTPFile();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        String From;
                        StringBuilder builder=new StringBuilder();
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                         From = JOptionPane.showInputDialog(null,"Old Name");
                         if (test.isNull(From))
                             return;
                        String To=JOptionPane.showInputDialog(null,"New Name");
                        if (test.isNull(To)){
                            return;
                        }
                        try {
                            manager.renameFile(domain,userName,password,From,To);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            renameftpfolder=new JMenuItem("Folder");
            renameftpfolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  //  new RenameFTPfolder();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String From = JOptionPane.showInputDialog(null,"Old Name");
                        if (test.isNull(From))
                            return;
                         String To=JOptionPane.showInputDialog(null,"New Name");
                        if (test.isNull(To))
                            return;
                        try {
                            manager.renameDir(domain,userName,password,From,To);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            rname=new JMenu("Rename");
            rname.add(renameftpfile);
            rname.add(renameftpfolder);
            delftp.add(deletefl);
            delftp.add(deletedir);
            upftp=new JMenu("Upload");
            upftpfile=new JMenuItem("File");
            upftpfolder=new JMenuItem("Folder");
            upftpfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new UploadForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        File fileout=null;
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        JFileChooser opener = new JFileChooser();
                        int resp = opener.showOpenDialog(null);
                        if (resp == JFileChooser.APPROVE_OPTION) {
                            fileout = new File(opener.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            return;
                        }
                        try {
                            manager.UploadFile(domain,userName,password,fileout,fileout.getName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            upftpfolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new  UploadFolderForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        File fileout=null;
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String fileName = JOptionPane.showInputDialog(null,"Save AS");
                        if (test.isNull(fileName))
                            return;
                        JFileChooser opener = new JFileChooser();
                        opener.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int resp = opener.showOpenDialog(null);
                        if (resp == JFileChooser.APPROVE_OPTION) {
                            fileout = new File(opener.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            return;
                        }
                        try {
                            manager.DownloadFile(domain,userName,password,fileout,fileName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            upftp.add(upftpfile);
            upftp.add(upftpfolder);
            jMenuBar.add(upftp);

            downftp=new JMenu("Download");
            downftpfile=new JMenuItem("File");
            downftpfolder=new JMenuItem("Folder");
            downftpfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new DowloadForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        File filein=null;
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String folderName = JOptionPane.showInputDialog(null,"Folder Name");
                        if (test.isNull(folderName))
                            return;
                        JFileChooser chooser = new JFileChooser();
                        int response = chooser.showSaveDialog(null);
                        if (response == JFileChooser.APPROVE_OPTION) {
                            filein = new File(chooser.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            return;
                        }
                        try {
                            manager.PULL(domain,userName,password,filein.getAbsolutePath(),folderName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            downftpfolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println((String) list.getSelectedValue());
                    //new DownloadFolderForm();
                    if (tdmn.getText().equals("")) {
                        return;
                    }
                    if (tuname.getText().equals("")){
                        return;
                    }
                    if (tpass.getPassword().equals("")){
                        return;
                    }
                    else {
                        File filein=null;
                        String domain=tdmn.getText();
                        String userName=tuname.getText();
                        String password=String.valueOf(tpass.getPassword());
                        String folderName = JOptionPane.showInputDialog(null,"Folder Name");
                        if (test.isNull(folderName))
                           return;
                        JFileChooser chooser = new JFileChooser();
                        int response = chooser.showSaveDialog(null);
                        if (response == JFileChooser.APPROVE_OPTION) {
                            filein = new File(chooser.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            return;
                        }
                        try {
                            manager.PULL(domain,userName,password,filein.getAbsolutePath(),folderName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            downftp.add(downftpfile);
            downftp.add(downftpfolder);
            jMenuBar.add(downftp);
            jMenuBar.add(rname);
             jMenuBar. add(delftp);
             jMenuBar.add(vu);
             setJMenuBar(jMenuBar);
            add(topPanel);
            setLayout(null);
            setSize(850,500);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

