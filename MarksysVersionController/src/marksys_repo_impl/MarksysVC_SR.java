package marksys_repo_impl;

import com.jcraft.jsch.*;
import model.Locations;
import org.apache.commons.io.FileUtils;
import repo.MarksysVC;
import util.Utilities;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author dilshan.r
 * @created 5/4/2022 - 9:54 AM
 * @project MarksysVersionController
 * @ide IntelliJ IDEA
 */

public class MarksysVC_SR implements MarksysVC {
    Utilities utilities = new Utilities();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    LocalDateTime now = LocalDateTime.now();

    ArrayList<Locations> SRlocations = utilities.getSRLocations();
    HashMap<String, String> parameters = utilities.getParameters();
    HashMap<String, String> svParams = utilities.getServerParameters();

    boolean status = false;

    String SRFilePath = System.getProperty("user.dir")+"\\JarFilesSR";

    String serverMarksysPath = parameters.get("_remote_marksys_path");
    String serverPosPath = parameters.get("_remote_pos_path");

    String marksysFileName = svParams.get("_marksys_file_name");
    String posFileName = svParams.get("_pos_file_name");
    String scriptFileName = svParams.get("_script_file_name");

    String mainMarksysFilePath = parameters.get("_local_marksys_path") + "\\" + marksysFileName;
    String mainPosFile = parameters.get("_local_pos_path") + "\\" + posFileName;

    String copiedScriptPath = System.getProperty("user.dir")+"\\ScriptFilesSR\\";
    String backupScriptPath = copiedScriptPath + "\\Backup";
    String mainScriptFile = parameters.get("_local_script_path") + "\\" + scriptFileName;

    private final String serverUsername = "superc";
    private final String serverPassword = "superc";

    File marksysfile = new File(mainMarksysFilePath);
    File posfile = new File(mainPosFile);
    File scriptfile = new File(mainScriptFile);

    JSch jSch = new JSch();
    Properties config = new Properties();

    Session session = null;
    ChannelSftp channelSftp = null;
    File basicJarFile = null;
    File targetJarFile = null;
    String[] split = null;
    String rootFileName = null;
    String rootFileExtension = null;
    String renamedRootFile = null;
    String strpassword = "";
    Connection connection = null;
    String script = null;
    File readableFile = null;
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    StringBuffer stringBuffer = null;
    Statement statement = null;
    String[] state = null;

    //    check file exist
    public void checkJarFile() {

        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException exception) {
            System.out.println("ERROR - Cannot found my IP address : \n" + exception);
            utilities.logDataFunction(
                    "error",
                    "ERROR - Cannot found my IP address : " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                    null,
                    null,
                    null,
                    "SR"
            );
        }
        if (this.parameters.isEmpty()) {
            System.out.println("ERROR - Cannot found any parameter records in the parameters table\n");
            utilities.logDataFunction(
                    "parameters",
                    "ERROR - Cannot found any parameter records in the parameters table",
                    null,
                    null,
                    null,
                    "SR"
            );
        } else {
            if (marksysfile.exists() && posfile.exists() && scriptfile.exists()) {
                System.out.println("MARKSYS jar file | POS jar file | SCRIPT files found! - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "files",
                        "MARKSYS jar file | POS jar file | SCRIPT files found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyMarksysJarFiles();
                copyPosJarFiles();
                runScriptFile();
                backupScriptFile();
            } else if (marksysfile.exists() && scriptfile.exists()) {
                System.out.println("MARKSYS jar file | SCRIPT files found! - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "files",
                        "MARKSYS jar file | SCRIPT files found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyMarksysJarFiles();
                runScriptFile();
                backupScriptFile();
            } else if (posfile.exists() && scriptfile.exists()) {
                System.out.println("POS jar file | SCRIPT files found! - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "files",
                        "POS jar file | SCRIPT files found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyPosJarFiles();
                runScriptFile();
                backupScriptFile();
            } else if (marksysfile.exists() && posfile.exists()) {
                System.out.println("MARKSYS jar file | POS jar file found! - " + localHost.getHostName()+ "\n");
                utilities.logDataFunction(
                        "files",
                        "MARKSYS jar file | POS jar file found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyMarksysJarFiles();
                copyPosJarFiles();
            } else if (marksysfile.exists()) {
                System.out.println("MARKSYS jar file found! - " + localHost.getHostName()+ "\n");
                utilities.logDataFunction(
                        "files",
                        "MARKSYS jar file found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyMarksysJarFiles();
            } else if (posfile.exists()) {
                System.out.println("POS jar file found! - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "files",
                        "POS jar file found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                copyPosJarFiles();
            } else if (scriptfile.exists()) {
                System.out.println("SCRIPT files found! - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "files",
                        "SCRIPT files found! - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
                runScriptFile();
                backupScriptFile();
            } else {
                System.out.println("ERROR - cannot find MARKSYS or POS jar or any SCRIPT file in this path, - " + localHost.getHostName() + "\n");
                utilities.logDataFunction(
                        "jar",
                        "ERROR - cannot find MARKSYS or POS jar or any SCRIPT file in this path, - " + localHost.getHostName(),
                        localHost.getHostAddress(),
                        null,
                        null,
                        "SR"
                );
            }
        }
    }

    //    copy the marksys files
    public void copyMarksysJarFiles() {

        if (SRlocations.isEmpty()) {
            System.out.println("MARKSYS - ERROR - Cannot found any location records in the locations table\n");
            utilities.logDataFunction(
                    "locations",
                    "MARKSYS - ERROR - Cannot found any location records in the locations table",
                    null,
                    null,
                    null,
                    "SR"
            );
        } else {

            config.put("StrictHostKeyChecking", "no");

            for (Locations location : SRlocations) {
                try {
                    session = jSch.getSession(serverUsername, location.getIP(), Integer.parseInt(location.getCRE_BY()));

                    session.setPassword(serverPassword);

                    session.setConfig(config);

                    System.out.println("\nMARKSYS - Connecting to session....");
                    utilities.logDataFunction(
                            "session",
                            "MARKSYS - Connecting to session....",
                            location.getIP(),
                            location.getLOC_CODE(),
                            location.getSBU_CODE(),
                            "SR"
                    );
                    session.connect();

                    if (session.isConnected()) {

                        System.out.println("\nMARKSYS - Session connection successfully! - " + location.getLOC_CODE() + "\n");
                        utilities.logDataFunction(
                                "session",
                                "MARKSYS - Session connection successfully!",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );
                        channelSftp = (ChannelSftp) session.openChannel("sftp");

                        System.out.println("MARKSYS - Connecting to channel....\n");
                        utilities.logDataFunction(
                                "channel",
                                "MARKSYS - Connecting to channel....",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );

                        channelSftp.connect();

                        if (channelSftp.isConnected()) {
                            System.out.println("MARKSYS - Channel connection successfully! - " + location.getLOC_CODE() + "\n");
                            utilities.logDataFunction(
                                    "channel",
                                    "MARKSYS - Channel connection successfully!",
                                    location.getIP(),
                                    location.getLOC_CODE(),
                                    location.getSBU_CODE(),
                                    "SR"
                            );
                            try {
                                basicJarFile = new File(mainMarksysFilePath);
                                targetJarFile = new File(SRFilePath + "\\" + marksysFileName);

                                try {
                                    FileUtils.copyFile(basicJarFile, targetJarFile);

                                    System.out.println("MARKSYS - MARKSYS file copy to project folder successfully! - " + location.getLOC_CODE() + "\n");
                                    utilities.logDataFunction(
                                            "marksys",
                                            "MARKSYS - MARKSYS file copy to project folder successfully!",
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );

                                    channelSftp.put(SRFilePath + "\\" + marksysFileName, serverMarksysPath + marksysFileName);

                                    System.out.println("MARKSYS - MARKSYS file upload successfully! - " + location.getLOC_CODE() + "\n");
                                    utilities.logDataFunction(
                                            "marksys",
                                            "MARKSYS - MARKSYS file upload successfully!",
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );

                                    split = marksysFileName.split("[.]");
                                    rootFileName = split[0];
                                    rootFileExtension = split[1];
                                    renamedRootFile = rootFileName + "_" + dtf.format(now) + "." + rootFileExtension;

                                    channelSftp.rename(serverMarksysPath + marksysFileName, serverMarksysPath + renamedRootFile);

                                    System.out.println("MARKSYS - MARKSYS file rename successfully! - " + location.getLOC_CODE() + "\n");
                                    utilities.logDataFunction(
                                            "marksys",
                                            "MARKSYS - MARKSYS file rename successfully!",
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );
                                    channelSftp.disconnect();
                                    session.disconnect();

                                } catch (IOException | IllegalArgumentException exception) {
                                    System.out.println("MARKSYS - MARKSYS file upload fail! - " + location.getLOC_CODE() + "\n" + exception);
                                    utilities.logDataFunction(
                                            "error",
                                            "MARKSYS - MARKSYS file upload fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]", "_"),
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );
                                    channelSftp.disconnect();
                                    session.disconnect();
                                }
                            } catch (SftpException exception) {
                                System.out.println("MARKSYS - ERROR - MARKSYS file upload fail! - " + location.getLOC_CODE() + "\n" + exception);
                                utilities.logDataFunction(
                                        "error",
                                        "MARKSYS - ERROR - MARKSYS file upload fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]", "_"),
                                        location.getIP(),
                                        location.getLOC_CODE(),
                                        location.getSBU_CODE(),
                                        "SR"
                                );
                                channelSftp.disconnect();
                                session.disconnect();
                            }
                        } else {
                            System.out.println("MARKSYS - ERROR - Channel connection fail! - " + location.getLOC_CODE() + "\n");
                            utilities.logDataFunction(
                                    "channel",
                                    "MARKSYS - ERROR - Channel connection fail!",
                                    location.getIP(),
                                    location.getLOC_CODE(),
                                    location.getSBU_CODE(),
                                    "SR"
                            );
                            channelSftp.disconnect();
                            session.disconnect();
                        }
                    } else {
                        System.out.println("\nMARKSYS - ERROR - Session connection fail! - " + location.getLOC_CODE() + "\n");
                        utilities.logDataFunction(
                                "session",
                                "MARKSYS - ERROR - Session connection fail!",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );
                        session.disconnect();
                    }
                } catch (JSchException exception) {
                    System.out.println("MARKSYS - ERROR - " + location.getLOC_CODE() + "\n" + exception);
                    utilities.logDataFunction(
                            "error",
                            "MARKSYS - ERROR - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                            location.getIP(),
                            location.getLOC_CODE(),
                            location.getSBU_CODE(),
                            "SR"
                    );
                    assert session != null;
                    session.disconnect();
                }
            }
        }
    }

    //    copy the pos files
    public void copyPosJarFiles() {
        if (SRlocations.isEmpty()) {
            System.out.println("POS - ERROR - Cannot found any location records in the locations table \n");
            utilities.logDataFunction(
                    "locations",
                    "POS - ERROR - Cannot found any location records in the locations table",
                    null,
                    null,
                    null,
                    "SR"
            );
        } else {

            config.put("StrictHostKeyChecking", "no");

            for (Locations location : SRlocations) {
                try {
                    session = jSch.getSession(serverUsername, location.getIP(), Integer.parseInt(location.getCRE_BY()));

                    session.setPassword(serverPassword);

                    session.setConfig(config);

                    System.out.println("\nPOS - Connecting to session....");
                    utilities.logDataFunction(
                            "session",
                            "POS - Connecting to session....",
                            location.getIP(),
                            location.getLOC_CODE(),
                            location.getSBU_CODE(),
                            "SR"
                    );

                    session.connect();

                    if (session.isConnected()) {
                        System.out.println("\nPOS - Session connection successfully! - " + location.getLOC_CODE() + "\n");
                        utilities.logDataFunction(
                                "session",
                                "POS - Session connection successfully!",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );

                        channelSftp = (ChannelSftp) session.openChannel("sftp");

                        System.out.println("POS - Connecting to channel....\n");
                        utilities.logDataFunction(
                                "session",
                                "POS - Connecting to channel....",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );

                        channelSftp.connect();

                        if (channelSftp.isConnected()) {
                            System.out.println("POS - Channel connection successfully! - " + location.getLOC_CODE() + "\n");
                            utilities.logDataFunction(
                                    "channel",
                                    "POS - Channel connection successfully!",
                                    location.getIP(),
                                    location.getLOC_CODE(),
                                    location.getSBU_CODE(),
                                    "SR"
                            );
                            try {
                                basicJarFile = new File(mainPosFile);
                                targetJarFile = new File(SRFilePath + "\\" + posFileName);

                                try {
                                    FileUtils.copyFile(basicJarFile, targetJarFile);

                                    System.out.println("POS - POS file copy to project folder successfully! - " + location.getLOC_CODE() + "\n");
                                    utilities.logDataFunction(
                                            "pos",
                                            "POS - POS file copy to project folder successfully!",
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );

                                    channelSftp.put(SRFilePath + "\\" + posFileName, serverPosPath + posFileName);

                                    System.out.println("POS - POS file uploaded successfully! - " + location.getLOC_CODE() + "\n");
                                    utilities.logDataFunction(
                                            "pos",
                                            "POS - POS file uploaded successfully!",
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );
                                    try {
                                        split = posFileName.split("[.]");
                                        rootFileName = split[0];
                                        rootFileExtension = split[1];
                                        renamedRootFile = rootFileName + "_" + dtf.format(now) + "." + rootFileExtension;

                                        channelSftp.rename(serverPosPath + posFileName, serverPosPath + renamedRootFile);

                                        System.out.println("POS - POS file rename successfully! - " + location.getLOC_CODE() + "\n");
                                        utilities.logDataFunction(
                                                "pos",
                                                "POS - POS file rename successfully!",
                                                location.getIP(),
                                                location.getLOC_CODE(),
                                                location.getSBU_CODE(),
                                                "SR"
                                        );
                                        channelSftp.disconnect();
                                        session.disconnect();

                                    } catch (SftpException exception) {
                                        System.out.println("POS - ERROR - POS file rename fail! - " + location.getLOC_CODE() + "\n" + exception);
                                        utilities.logDataFunction(
                                                "error",
                                                "POS - ERROR - POS file rename fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]", "_"),
                                                location.getIP(),
                                                location.getLOC_CODE(),
                                                location.getSBU_CODE(),
                                                "SR"
                                        );
                                        channelSftp.disconnect();
                                        session.disconnect();

                                    }
                                } catch (IOException exception) {
                                    System.out.println("POS - POS file uploaded fail! - " + location.getLOC_CODE() + "\n" + exception);
                                    utilities.logDataFunction(
                                            "error",
                                            "POS - POS file uploaded fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]", "_"),
                                            location.getIP(),
                                            location.getLOC_CODE(),
                                            location.getSBU_CODE(),
                                            "SR"
                                    );
                                    channelSftp.disconnect();
                                    session.disconnect();

                                }
                            } catch (SftpException exception) {
                                System.out.println("POS - ERROR - POS file upload fail! - " + location.getLOC_CODE() + "\n" + exception);
                                utilities.logDataFunction(
                                        "error",
                                        "POS - ERROR - POS file upload fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]", "_"),
                                        location.getIP(),
                                        location.getLOC_CODE(),
                                        location.getSBU_CODE(),
                                        "SR"
                                );
                                channelSftp.disconnect();
                                session.disconnect();

                            }
                        } else {
                            System.out.println("POS - ERROR - Channel connection fail! - " + location.getLOC_CODE() + "\n");
                            utilities.logDataFunction(
                                    "channel",
                                    "POS - ERROR - Channel connection fail! - " + location.getLOC_CODE(),
                                    location.getIP(),
                                    location.getLOC_CODE(),
                                    location.getSBU_CODE(),
                                    "SR"
                            );
                            channelSftp.disconnect();
                            session.disconnect();
                        }
                    } else {
                        System.out.println("\nPOS - ERROR - Session connection fail! - " + location.getLOC_CODE() + "\n");
                        utilities.logDataFunction(
                                "session",
                                "POS - ERROR - Session connection fail!",
                                location.getIP(),
                                location.getLOC_CODE(),
                                location.getSBU_CODE(),
                                "SR"
                        );
                    }
                    session.disconnect();
                } catch (JSchException exception) {
                    System.out.println("POS - ERROR - " + location.getLOC_CODE() + "\n" + exception);
                    utilities.logDataFunction(
                            "error",
                            "POS - ERROR - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                            location.getIP(),
                            location.getLOC_CODE(),
                            location.getSBU_CODE(),
                            "SR"
                    );
                    assert session != null;
                    session.disconnect();
                }
            }
        }
    }

    //    execute the script file
    public void runScriptFile() {

        System.out.println("\nScript Running....\n");
        ArrayList<Locations> locations = utilities.msqlSRConnection();

        for (Locations mysql: locations) {

            try {
                strpassword = new jText.TextUti().getText(mysql.getDBTYPE());

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + mysql.getIP() + ":3306/" + mysql.getDB(), mysql.getUSERID(), strpassword
                );

                System.out.println("\n" + mysql.getLOC_CODE() + " : SQL connection is established!\n");
                utilities.logDataFunction(
                        "script",
                        "SCRIPT - SQL connection is established! - " + mysql.getLOC_CODE(),
                        mysql.getIP(),
                        mysql.getLOC_CODE(),
                        mysql.getSBU_CODE(),
                        "SR"
                );

                basicJarFile = new File(mainScriptFile);
                targetJarFile = new File(copiedScriptPath + "\\" + scriptFileName);

                FileUtils.copyFile(basicJarFile, targetJarFile);

                readableFile = new File(copiedScriptPath + "\\" + scriptFileName);
                fileReader = new FileReader(readableFile);
                bufferedReader = new BufferedReader(fileReader);
                stringBuffer = new StringBuffer();
                String line;

                while((line=bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                }
                readableFile.exists();
                fileReader.close();
                bufferedReader.close();

                statement = connection.createStatement();
                script = stringBuffer.toString();
                String[] splitScript = script.split(";");

                for (int i = 0; i < splitScript.length-1; i++) {
                    System.out.println(splitScript[i] + ";\n");
                    statement.executeUpdate(splitScript[i] + ";");

                    state = splitScript[i].split(" ");
                    System.out.println(state[0] + " Statements are successfully executed the script for location - " + mysql.getLOC_CODE());
                    utilities.logDataFunction(
                            "script",
                            "SCRIPT - " + state[0] + " Statements are successfully executed the script for location - " + mysql.getLOC_CODE(),
                            mysql.getIP(),
                            mysql.getLOC_CODE(),
                            mysql.getSBU_CODE(),
                            "SR"
                    );
                    utilities.saveQuery(
                            mysql.getLOC_CODE(),
                            mysql.getSBU_CODE(),
                            state[0],
                            splitScript[i] + ";",
                            mysql.getIP()
                    );
                }
                statement.close();
                status = true;
            } catch (SQLException | ClassNotFoundException | IOException exception) {
                System.out.println("\nSCRIPT - ERROR - " + mysql.getLOC_CODE() + "\n" + exception);
                utilities.logDataFunction(
                        "error",
                        "SCRIPT - ERROR - " + mysql.getLOC_CODE() + " - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                        mysql.getIP(),
                        mysql.getLOC_CODE(),
                        mysql.getSBU_CODE(),
                        "SR"
                );
                status = false;
            } catch (Exception exception) {
                System.out.println("\nSCRIPT - ERROR - Database user keyword not match to decrypt it please use \"mysql\" as the keyword! " + mysql.getLOC_CODE() + "\n" + exception);
                utilities.logDataFunction(
                        "error",
                        "SCRIPT - ERROR - Database user keyword not match to decrypt it please use \"mysql\" as the keyword!" + mysql.getLOC_CODE() + " - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                        mysql.getIP(),
                        mysql.getLOC_CODE(),
                        mysql.getSBU_CODE(),
                        "SR"
                );
                status = false;
            }
        }
    }

    //    //    backup the script file
    public void backupScriptFile() {
        if (status) {
            File sourceFile = new File(copiedScriptPath + "\\" + scriptFileName);
            File backupFile = new File(backupScriptPath + "\\" + scriptFileName);

            try {
                FileUtils.copyFile(sourceFile, backupFile);
                System.out.println("Script file has backup successfully\n");
                utilities.logDataFunction(
                        "script",
                        "Script file has backup successfully",
                        null,
                        null,
                        null,
                        "SR"
                );

                String backupFileName = (backupScriptPath + "\\" + scriptFileName).substring((backupScriptPath + "\\" + scriptFileName).length() - 10);
                String backupFilePath = (backupScriptPath + "\\" + scriptFileName).substring(0, (backupScriptPath + "\\" + scriptFileName).length() - 10);

                String backupFileExtinction = backupFileName.substring(backupFileName.length() - 4);
                String backupFileTitle = backupFileName.substring(0, backupFileName.length() - 4);
                String renamedBackupFilePath = backupScriptPath +"\\"+ backupFileTitle + "_" + dtf.format(now) + backupFileExtinction;

                boolean isRenamed = backupFile.renameTo(new File(renamedBackupFilePath));

                if (isRenamed) {
                    System.out.println("Script file has renamed successfully\n");
                    utilities.logDataFunction(
                            "script",
                            "Script file has renamed successfully",
                            null,
                            null,
                            null,
                            "SR"
                    );
                } else {
                    System.out.println("ERROR - Script file has renamed fail!\n");
                    utilities.logDataFunction(
                            "script",
                            "ERROR - Script file has renamed fail!\n",
                            null,
                            null,
                            null,
                            "SR"
                    );
                }
            } catch (IOException exception) {
                System.out.println("ERROR - Script file has backup and renamed fail!\n" + exception);
                utilities.logDataFunction(
                        "error",
                        "ERROR - Script file has backup and renamed fail! - " + exception.getMessage().replaceAll("[^a-zA-Z0-9@/\\*,.#&$()-_=+ ]","_"),
                        null,
                        null,
                        null,
                        "SR"
                );
            }
        }
    }
}
