package repo;

/**
 * @author dilshan.r
 * @created 5/4/2022 - 9:39 AM
 * @project MarksysVersionController
 * @ide IntelliJ IDEA
 */
public interface MarksysVC {
    public void checkJarFile();
    public void copyMarksysJarFiles();
    public void copyPosJarFiles();
    public void runScriptFile();
    public void backupScriptFile();
}
