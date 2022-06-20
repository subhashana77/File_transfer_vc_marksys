package marksys_vc_main;

import marksys_repo_impl.MarksysVC_ADA;
import marksys_repo_impl.MarksysVC_SC;
import marksys_repo_impl.MarksysVC_SR;
import repo.MarksysVC;
import util.Utilities;

/**
 * @author dilshan.r
 * @created 4/11/2022 - 11:14 AM
 * @project OracleToPOSJar
 * @ide IntelliJ IDEA
 */

public class Main {
    public static void main(String[] args) {
        Utilities utilities = new Utilities();
        if (args.length > 0) {
            MarksysVC marksysVC;
            for (String arg: args) {
                switch (arg) {
                    case "SR":
                        marksysVC = new MarksysVC_SR();
                        marksysVC.checkJarFile();
                        break;
                    case "SC":
                        marksysVC = new MarksysVC_SC();
                        marksysVC.checkJarFile();
                        break;
                    case "ADA":
                        marksysVC = new MarksysVC_ADA();
                        marksysVC.checkJarFile();
                        break;
                    default:
                        System.out.println("Undefined Location Category");
                        utilities.logDataFunction(
                                "loc_cat",
                                "ERROR - Undefined Location Category",
                                null,
                                null,
                                null,
                                null
                        );
                        break;
                }
            }
        } else {
            System.out.println("Please Insert Location Category");
            utilities.logDataFunction(
                    "loc_cat",
                    "ERROR - Please Insert Location Category",
                    null,
                    null,
                    null,
                    null
            );
        }
    }
}
