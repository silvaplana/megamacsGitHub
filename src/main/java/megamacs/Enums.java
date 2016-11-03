package megamacs;

/**
 * Created by sebastien on 23/07/2016.
 */
public class Enums {

    public enum Tab {
        MACS,
        SETTINGS;
    }

    /*
    public enum DeployAction {
        EXPORT_PR4G_MANAGER_JAR_TO_USB ("Export to USB: pr4g.manager.jar (.m2)"),
        EXPORT_MACS_JAR_TO_USB ("Export to USB: userdashboardpanel.jar"),
        DEPLOY_PR4G_MANAGER_JAR_FROM_USB ("Deploy from USB: pr4g.manager.jar"),
        DEPLOY_MACS_JAR_FROM_USB ("Deploy from USB: userdashboardpanel.jar"),
        DEPLOY_PR4G_MANAGER_JAR_FROM_LOCAL ("Deploy from local: pr4g.manager.jar (.m2)"),
        DEPLOY_MACS_JAR_FROM_LOCAL ("Deploy from local: userdashboardpanel.jar");

        private String name = "";

        DeployAction(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }
    }
    */

    public enum FileDrive {
        USB("Usb"),
        MACS_DIR("Macs dir"),
        M2(".m2 dir");
        private String name = "";

        FileDrive(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public enum TransferableFileElement {
        PR4G_MANAGER_JAR("pr4g.manager.jar"),
        MACS_ZIP("macs.zip");

        private String name = "";

        TransferableFileElement(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public enum TransferResponseState {
        ACCEPTED,
        REFUSED,
        ERROR,
        COMPLETED
    }


    public enum FileElement {
        NO_ELEMENT(""),
        MACS_EXE(""),
        MACS_ZIP(""),
        MACS_VERSION(""),
        SICS_VERSION(""),
        MACS_DIR(""),
        M2_DIR(""),
        USB_DIR(""),
        SICS_DIR(""),
        SICS_EXE(""),
        SICS_ZIP(""),
        SICS_JRE_DIR(""),
        PIMS_DIR(""),
        MARS_DIR(""),
        PR4G_MANAGER_JAR(""),
        MACS_DATA_DIR(""),
        MACS_CONF_DIR(""),
        MACS_LOG_DIR(""),
        MACS_KARAF("karaf.log"),
        MACS_CONF_XML("userdashboardpanel.megamacs.conf.xml"),
        MACS_CONF_MANAGER_CFG("userdashboardpanel.megamacs.conf.xml"),
        MACS_SEGMENTED("segmented.log"),
        MACS_MOPD("mopd.log"),
        SICS_PROFILE_CFG(""),
        SICS_RUNTIME("")
        ;

        private String name = "";

        FileElement(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public enum Process {
        MACS, SICS, PIMS, MARS;
    }

    public enum ProcessOrder {
        RUN, KILL, PREPARE ;
    }

    public enum ProcessState {
        EMPTY, DEPLOYING, READY, RUNNING;
    }


    public enum FileTransferType {
        CLEAN, COPY, UNZIP, MULTIPLE;
    }

    public enum FileTransferState {
        PROCESSING, COMPLETED, KO;
    }


    public enum PR4GChannelsConfiguration {
        PIMS("Pims"),
        PR4G_0_1("PR4G: public 0 ,private 1"),
        PR4G_3_4("PR4G: public 3 ,private 4"),
        PR4G_5_6("PR4G: public 5 ,private 6");

        private String name = "";

        PR4GChannelsConfiguration(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }


    public enum PR4GPort {
        COM1, COM2, COM3, COM4, COM5, COM6;
    }


    public enum PR4GChannel {
        CHANNEL_0("0"),
        CHANNEL_1("1"),
        CHANNEL_2("2"),
        CHANNEL_3("3"),
        CHANNEL_4("4"),
        CHANNEL_5("5"),
        CHANNEL_6("6");

        private String name = "";

        PR4GChannel(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}
