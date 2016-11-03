package megamacs.conf;

import megamacs.Enums;

import java.io.Serializable;
import java.util.*;

public class UserConf implements Serializable {

    private String name;
    private String trigram;
    private String ipAddress;
    private Enums.Tab currentTab;
    private Enums.PR4GPort pr4GPort;
    private Enums.PR4GChannelsConfiguration  pr4GChannelsConfiguration;
    //private Enums.PR4GChannel pr4gPublicChannel;
    //private Enums.PR4GChannel pr4gPrivateChannel;

    private Map<Enums.FileElement, String> dirs = new HashMap();
    //private boolean isPR4G;
    private Enums.TransferableFileElement deployActionTransferElement;
    private Enums.FileDrive deployActionSourceDrive;
    private Enums.FileDrive deployActionDestinationDrive;

    private List<Host> hosts = new LinkedList();
    private Set<Enums.FileElement> checkedLogFiles = new HashSet();

    UserConf(String userName) {
        name = userName;
        trigram = "";
        ipAddress = "localhost";
        dirs.put(Enums.FileElement.MACS_VERSION, "");
        dirs.put(Enums.FileElement.USB_DIR, "");
        dirs.put(Enums.FileElement.M2_DIR, "");
        dirs.put(Enums.FileElement.MACS_DIR, "");
        dirs.put(Enums.FileElement.PIMS_DIR, "");
        dirs.put(Enums.FileElement.MARS_DIR, "");
        dirs.put(Enums.FileElement.SICS_DIR, "");
    }

    public String getName() {
        return name;
    }

    public String getTrigram() {return trigram;}

    public String getIpAddress() {return ipAddress;}

    @Override
    public String toString() {
        return "UserConf{" +
                "name='" + name + '\'' +
                "trigram='" + trigram + '\'' +
                "ipAddress='" + ipAddress + '\'' +
                ", currentTab=" + currentTab +
                ", pr4GPort=" + pr4GPort +
                ", dirs=" + dirs +
                ", pr4gChannelsConfiguration=" + pr4GChannelsConfiguration +
                ", deployActionTransferElement=" + deployActionTransferElement +
                ", deployActionSourceDrive=" + deployActionSourceDrive +
                ", deployActionDestinationDrive=" + deployActionDestinationDrive +
                ", hosts=" + hosts +
                ", checkedLogFiles=" + checkedLogFiles +
                '}';
    }

    public Enums.Tab getCurrentTab() {
        return currentTab;
    }

    public Enums.PR4GPort getPr4GPort() {
        return pr4GPort;
    }

    public Enums.PR4GChannelsConfiguration getPr4GChannelsConfiguration() {
        return pr4GChannelsConfiguration;
    }
    //public Enums.PR4GChannel getPr4gPublicChannel() {return pr4gPublicChannel;}

    //public Enums.PR4GChannel getPr4gPrivateChannel() {return pr4gPrivateChannel;}

    public Map<Enums.FileElement, String> getDirs() {
        return dirs;
    }

    //public boolean getIsPR4G() {return isPR4G;}

    public Enums.TransferableFileElement getDeployActionTransferElement() {
        return deployActionTransferElement;
    }

    public Enums.FileDrive getDeployActionSourceDrive() {
        return deployActionSourceDrive;
    }

    public Enums.FileDrive getDeployActionDestinationDrive() {
        return deployActionDestinationDrive;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public Set<Enums.FileElement> getCheckedLogFiles() {
        return checkedLogFiles;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTrigram(String trigram) {this.trigram = trigram;}

    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

    public void setCurrentTab(Enums.Tab currentTab) {
        this.currentTab = currentTab;
    }

    public void setPr4GPort(Enums.PR4GPort pr4GPort) {
        this.pr4GPort = pr4GPort;
    }

    /*
    public void setPr4gPublicChannel(Enums.PR4GChannel pr4gPublicChannel) {this.pr4gPublicChannel = pr4gPublicChannel;}

    public void setPr4gPrivateChannel(Enums.PR4GChannel pr4gPrivateChannel) {this.pr4gPrivateChannel = pr4gPrivateChannel;}

     public void setPR4G(boolean isPR4G) {
        this.isPR4G = isPR4G;
    }
    */

    public void setDirs(Map<Enums.FileElement, String> dirs) {
        this.dirs = dirs;
    }



    public void setPr4GChannelsConfiguration(Enums.PR4GChannelsConfiguration pr4GChannelsConfiguration) {
        this.pr4GChannelsConfiguration = pr4GChannelsConfiguration;
    }


    public void setDeployActionTransferElement(Enums.TransferableFileElement deployActionTransferElement) {
        this.deployActionTransferElement = deployActionTransferElement;
    }

    public void setDeployActionSourceDrive(Enums.FileDrive deployActionSourceDrive) {
        this.deployActionSourceDrive = deployActionSourceDrive;
    }

    public void setDeployActionDestinationDrive(Enums.FileDrive deployActionDestinationDrive) {
        this.deployActionDestinationDrive = deployActionDestinationDrive;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public void setCheckedLogFiles(Set<Enums.FileElement> checkedLogFiles) {
        this.checkedLogFiles = checkedLogFiles;
    }

}
