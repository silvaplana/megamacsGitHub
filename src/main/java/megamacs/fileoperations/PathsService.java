package megamacs.fileoperations;

import megamacs.Enums;
import megamacs.Injector;

import java.io.*;
import java.nio.file.*;

/**
 * Created by sebastien on 23/07/2016.
 */
public class PathsService {

    //public static PathResponse PATH_NOT_FOUND = new PathResponse("", "element not found");

    Injector i;

    public PathsService(Injector injector) {
        i = injector;
    }

    public String searchMacsVersionName() {
        return i.confService.getDir(Enums.FileElement.MACS_VERSION);
    }

    public String searchSicsVersionName() {
        return i.confService.getDir(Enums.FileElement.SICS_VERSION);
    }


    public String getFileName(Enums.FileElement fileElement) {
        String res = "no response";

        switch (fileElement) {
            case MACS_ZIP:
                res = "macs-server-runtime-" + searchMacsVersionName() + "-bin.zip";
                break;
            case SICS_ZIP:
                res = "sics-" + searchSicsVersionName() + "-GA.zip";
                break;
            case PR4G_MANAGER_JAR:
                res = "com.bull.mil.macs.pr4g.manager-" + searchMacsVersionName() + ".jar";
                break;
        }
        return res;
    }

    public PathResponse getPath(Enums.FileElement fileElement, Enums.FileDrive fileDrive, String reasonPrefix) {
        return getPath(fileElement, fileDrive, "" /*not force file name*/, reasonPrefix);
    }

    public PathResponse getPath(Enums.FileElement fileElement, String reasonPrefix) {
        return getPath(fileElement, null /* choose local dir*/, "" /*not force file name*/, reasonPrefix);
    }


    public PathResponse getPath(Enums.FileElement fileElement, Enums.FileDrive fileDrive, String forceFileName, String reasonPrefix) {
        PathResponse pathResponse = new PathResponse(null, "");

        switch (fileElement) {
            case MACS_DIR:
                pathResponse.path = Paths.get(i.confService.getDir(Enums.FileElement.MACS_DIR));
                if (Files.notExists(pathResponse.path)) {
                    pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_DIR";
                }
                break;
            case MACS_EXE:
                PathResponse macsVersionnedDir = getMacsVersionnedPath();
                if (Files.exists(macsVersionnedDir.path)) {
                    pathResponse.path = Paths.get(macsVersionnedDir.path.toString() + "\\bin\\macs.bat");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_EXE";
                        //verify
                    }
                } else {
                    pathResponse.path = Paths.get(macsVersionnedDir.path.toString() + "\\bin\\macs.bat");
                    pathResponse.reason = reasonPrefix + ":" + macsVersionnedDir.path.toString() + " does not exist, so not able to retrieve MACS_EXE";
                }
                break;
            case MACS_ZIP:
                PathResponse macsDirPathResponse = new PathResponse(null, "");;
                if (fileDrive != null && fileDrive.equals(Enums.FileDrive.USB)) {
                    macsDirPathResponse.path = Paths.get(i.confService.getDir(Enums.FileElement.USB_DIR));
                    if (Files.notExists(macsDirPathResponse.path)) {
                        macsDirPathResponse.reason = reasonPrefix + ":" + macsDirPathResponse.path.getFileName() + " does not exist, so not able to retrieve USB_DIR";
                    }
                } else {
                    macsDirPathResponse = getPath(Enums.FileElement.MACS_DIR, null, "", "");
                }
                if (Files.exists(macsDirPathResponse.path)) {
                    if (forceFileName != "") {
                        pathResponse.path = Paths.get(macsDirPathResponse.path.toString() + "\\" + forceFileName);
                    } else {
                        pathResponse.path = Paths.get(macsDirPathResponse.path.toString() + "\\" + getFileName(Enums.FileElement.MACS_ZIP));
                        //verify
                    }
                } else {
                    pathResponse.reason = "Searching for macs zip path :  macs dir  does not exist = (reason=" + macsDirPathResponse.reason + " , so not able to retrieve macs zip path";
                }
                break;
            case SICS_DIR:
                pathResponse.path = Paths.get(i.confService.getDir(Enums.FileElement.SICS_DIR));
                if (Files.notExists(pathResponse.path)) {
                    pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve SICS";
                }
                break;
            case SICS_EXE:
                PathResponse sicsVersionnedDir = getSicsVersionnedPath();
                if (Files.exists(sicsVersionnedDir.path)) {
                    pathResponse.path = Paths.get(sicsVersionnedDir.path.toString() + "\\bin\\sics.bat");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve SICS_EXE";
                    }
                } else {
                    pathResponse.path = Paths.get(sicsVersionnedDir.path.toString() + "\\bin\\sics.bat");
                    pathResponse.reason = reasonPrefix + ":" + sicsVersionnedDir.path.toString() + " does not exist, so not able to retrieve SICS_EXE";
                }
                break;
            case SICS_ZIP:
                PathResponse sicsDirPathResponse = new PathResponse(null, "");;
                if (fileDrive != null && fileDrive.equals(Enums.FileDrive.USB)) {
                    sicsDirPathResponse.path = Paths.get(i.confService.getDir(Enums.FileElement.USB_DIR));
                    if (Files.notExists(sicsDirPathResponse.path)) {
                        sicsDirPathResponse.reason = reasonPrefix + ":" + sicsDirPathResponse.path.getFileName() + " does not exist, so not able to retrieve USB_DIR";
                    }
                } else {
                    sicsDirPathResponse = getPath(Enums.FileElement.SICS_DIR, null, "", "");
                }
                if (Files.exists(sicsDirPathResponse.path)) {
                    if (forceFileName != "") {
                        pathResponse.path = Paths.get(sicsDirPathResponse.path.toString() + "\\" + forceFileName);
                    } else {
                        pathResponse.path = Paths.get(sicsDirPathResponse.path.toString() + "\\" + getFileName(Enums.FileElement.SICS_ZIP));
                        //verify
                    }
                } else {
                    pathResponse.reason = "Searching for sics zip path :  sics dir  does not exist = (reason=" + sicsDirPathResponse.reason + " , so not able to retrieve sics zip path";
                }
                break;
            case MACS_DATA_DIR:
                PathResponse macsVersionnedDir2 = getMacsVersionnedPath();
                if (Files.exists(macsVersionnedDir2.path)) {
                    pathResponse.path = Paths.get(macsVersionnedDir2.path.toString() + "\\data");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_DATA_DIR";
                    }
                } else {
                    pathResponse.path = Paths.get(macsVersionnedDir2.path.toString() + "\\data");
                    pathResponse.reason = reasonPrefix + ":" + macsVersionnedDir2.path.toString() + " does not exist, so not able to retrieve MACS_DATA_DIR";
                }
                break;
            case MACS_CONF_DIR:
                PathResponse macsVersionnedDir3 = getMacsVersionnedPath();
                if (Files.exists(macsVersionnedDir3.path)) {
                    pathResponse.path = Paths.get(macsVersionnedDir3.path.toString() + "\\etc");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_CONF_DIR";
                    }
                } else {
                    pathResponse.path = Paths.get(macsVersionnedDir3.path.toString() + "\\etc");
                    pathResponse.reason = reasonPrefix + ":" + macsVersionnedDir3.path.toString() + " does not exist, so not able to retrieve MACS_CONF_DIR";
                }
                break;
            case MACS_CONF_MANAGER_CFG:
                PathResponse macsVersionnedDir4 = getPath (Enums.FileElement.MACS_CONF_DIR, "");
                if (Files.exists(macsVersionnedDir4.path)) {
                    pathResponse.path = Paths.get(macsVersionnedDir4.path.toString() + "\\com.bull.mil.macs.conf.manager.cfg");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_CONF_MANAGER_CFG";
                    }
                } else {
                    pathResponse.path = Paths.get(macsVersionnedDir4.path.toString() + "\\etc");
                    pathResponse.reason = reasonPrefix + ":" + macsVersionnedDir4.path.toString() + " does not exist, so not able to retrieve MACS_CONF_MANAGER_CFG";
                }
                break;
            case MACS_LOG_DIR:
                PathResponse macsDataDir = getPath(Enums.FileElement.MACS_DATA_DIR, null, reasonPrefix);
                if (Files.exists(macsDataDir.path)) {
                    pathResponse.path = Paths.get(macsDataDir.path.toString() + "\\log");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_LOG_DIR";
                    }
                } else {
                    pathResponse.path = Paths.get(macsDataDir.path.toString() + "\\log");
                    pathResponse.reason = reasonPrefix + ":" + macsDataDir.path.toString() + " does not exist, so not able to retrieve MACS_LOG_DIR";
                }
                break;
            case MACS_KARAF:
                PathResponse macsDataLogDir = getPath(Enums.FileElement.MACS_LOG_DIR, null, reasonPrefix);
                if (Files.exists(macsDataLogDir.path)) {
                    pathResponse.path = Paths.get(macsDataLogDir.path.toString() + "\\karaf.log.txt");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_KARAF";
                    }
                } else {
                    pathResponse.path = Paths.get(macsDataLogDir.path.toString() + "\\karaf.log.txt");
                    pathResponse.reason = reasonPrefix + ":" + macsDataLogDir.path.toString() + " does not exist, so not able to retrieve MACS_KARAF";
                }
                break;
            case MACS_SEGMENTED:
                PathResponse macsDataLogDir2 = getPath(Enums.FileElement.MACS_LOG_DIR, null, reasonPrefix);
                if (Files.exists(macsDataLogDir2.path)) {
                    pathResponse.path = Paths.get(macsDataLogDir2.path.toString() + "\\segmented.log.txt");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_SEGMENTED";
                    }
                } else {
                    pathResponse.path = Paths.get(macsDataLogDir2.path.toString() + "\\segmented.log.txt");
                    pathResponse.reason = reasonPrefix + ":" + macsDataLogDir2.path.toString() + " does not exist, so not able to retrieve MACS_SEGMENTED";
                }
                break;
            case MACS_MOPD:
                PathResponse macsDataLogDir3 = getPath(Enums.FileElement.MACS_LOG_DIR, null, reasonPrefix);
                if (Files.exists(macsDataLogDir3.path)) {
                    pathResponse.path = Paths.get(macsDataLogDir3.path.toString() + "\\mopd.log.txt");
                    if (Files.notExists(pathResponse.path)) {
                        pathResponse.reason = reasonPrefix + ":" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve MACS_SEGMENTED";
                    }
                } else {
                    pathResponse.path = Paths.get(macsDataLogDir3.path.toString() + "\\mopd.log.txt");
                    pathResponse.reason = reasonPrefix + ":" + macsDataLogDir3.path.toString() + " does not exist, so not able to retrieve MACS_SEGMENTED";
                }
                break;
        }
        return pathResponse;
    }

    public PathResponse getMacsVersionnedPath() {
        PathResponse pathResponse = new PathResponse(null, "");
        PathResponse macsDir = getPath(Enums.FileElement.MACS_DIR, null, "");

        if (Files.exists(macsDir.path)) {
            pathResponse.path = Paths.get(macsDir.path.toString() + "\\macs-server-runtime-" + searchMacsVersionName());
            if (Files.notExists(pathResponse.path)) {
                pathResponse.reason = "Searching for macs versionned path  :" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve macs versionned path";
            }
        } else {
            pathResponse.path = Paths.get(macsDir.path.toString() + "\\macs-server-runtime-" + searchMacsVersionName());
            pathResponse.reason = "Searching for macs versionned path :" + macsDir.path + " does not exist, so not able to retrieve macs versionned path";
        }

        return pathResponse;
    }

    public PathResponse getSicsVersionnedPath() {
        PathResponse pathResponse = new PathResponse(null, "");
        PathResponse sicsDir = getPath(Enums.FileElement.SICS_DIR, null, "");

        if (Files.exists(sicsDir.path)) {
            pathResponse.path = Paths.get(sicsDir.path.toString() + "\\sics-" + searchSicsVersionName() + "-GA");
            if (Files.notExists(pathResponse.path)) {
                pathResponse.reason = "Searching for sics versionned path  :" + pathResponse.path.getFileName() + " does not exist, so not able to retrieve macsl versionned path";
            }
        } else {
            pathResponse.path = Paths.get(sicsDir.path.toString() + "\\sics-" + searchMacsVersionName() + "-GA");
            pathResponse.reason = "Searching for macs versionned path :" + sicsDir.path.getFileName() + " does not exist, so not able to retrieve sics versionned path";
        }

        return pathResponse;
    }

    public static class PathResponse {

        public Path path;
        public String reason;

        public PathResponse(Path path, String reason) {
            this.path = path;
            this.reason = reason;
        }


    }

}
