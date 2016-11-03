package megamacs.preparedata;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.compression.UnzipUtils;
import megamacs.fileoperations.AsciiFileTools;
import megamacs.fileoperations.PathsService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PrepareMacsDataService {

    Injector i;

    public PrepareMacsDataService(Injector i) throws IOException {

        this.i = i;
    }


    public String prepareMacs(){
        String res = "";

        PathsService.PathResponse response;

        //1. unzip
        String zipFile = "";
        String extractFolder = "";

        response = i.pathsService.getPath(Enums.FileElement.MACS_ZIP, "Prepare Macs: get macs.zip path");
        if (response.reason == "") {
            zipFile = response.path.toString();
        }
        else{
            System.out.println(response.reason);
            return response.reason;
        }
        response = i.pathsService.getPath(Enums.FileElement.MACS_DIR,"Prepare Macs: Chek MACS_DIR" );
        if (response.reason != "") {
            System.out.println(response.reason);
            return response.reason;
        }

        response = i.pathsService.getMacsVersionnedPath();
        extractFolder = response.path.toString();

        System.out.println("Prepare Macs: 1: unzip " + zipFile + "into " + extractFolder);
        UnzipUtils.unzip(zipFile, "extractFolder");

        //2. prepare data conf manager file
        Enums.PR4GChannelsConfiguration  pR4GChannelsConfiguration = i.confService.getPR4GChannelsConfiguration();
        System.out.println("Prepare Macs: 2 :PrepareDataConfManager file with value" + zipFile + "into " + extractFolder);
        prepareDataConfManagerCfg(i.confService.getPR4GChannelsConfiguration());
        return res;
    }

    /* if response != "" , there is an error*/
    private String prepareDataConfManagerCfg(Enums.PR4GChannelsConfiguration configToApply){

        // 1 Test existence of file
       PathsService.PathResponse resp = i.pathsService.getPath(Enums.FileElement.MACS_CONF_MANAGER_CFG, "");
        if (resp.reason != ""){
            System.out.println("prepareDataConfManagerCfg: File not available reason:" + resp.reason);
            return resp.reason;
        }

        // 2 Make file clean
        AsciiFileTools.replaceStringInFile(resp.path.toString(), "configurationFile=${taipan.conf}/com.bull.mil.macs.conf.xml", "#configurationFile=${taipan.conf}/com.bull.mil.macs.conf.xml" );
        AsciiFileTools.replaceStringInFile(resp.path.toString(), "configurationFile03=${taipan.conf}/com.bull.mil.macs.conf03.xml", "#configurationFile03=${taipan.conf}/com.bull.mil.macs.conf03.xml" );
        AsciiFileTools.replaceStringInFile(resp.path.toString(), "configurationFile05=${taipan.conf}/com.bull.mil.macs.conf05.xml", "#configurationFile05=${taipan.conf}/com.bull.mil.macs.conf05.xml" );
        AsciiFileTools.replaceStringInFile(resp.path.toString(), "configurationFile06=${taipan.conf}/com.bull.mil.macs.conf06.xml", "#configurationFile06=${taipan.conf}/com.bull.mil.macs.conf06.xml" );


        // 3 make replacement
        String lineToFind = "";
        String lineToReplaceWith = "";
        switch(configToApply){
            case PIMS:
                lineToFind = "com.bull.mil.macs.confPims.xml";
                lineToReplaceWith = "confFile=${taipan.conf}/com.bull.mil.macs.confPims.xml";
                break;
            case PR4G_0_1:
                lineToFind = "com.bull.mil.macs.conf.xml";
                lineToReplaceWith = "configurationFile=${taipan.conf}/com.bull.mil.macs.conf.xml";
            case PR4G_3_4:
                lineToFind = "com.bull.mil.macs.conf.xml";
                lineToReplaceWith = "configurationFile=${taipan.conf}/com.bull.mil.macs.conf34.xml";
            case PR4G_5_6:
                lineToFind = "com.bull.mil.macs.conf.xml";
                lineToReplaceWith = "configurationFile=${taipan.conf}/com.bull.mil.macs.conf56.xml";
        }
        String reason = AsciiFileTools.replaceStringInFile(resp.path.toString(), lineToFind, lineToReplaceWith );

        if (reason!=""){
            System.out.println("prepareDataConfManager: Could not replace: reason:" + reason);
            return reason;
        }
        return "";
    }



}