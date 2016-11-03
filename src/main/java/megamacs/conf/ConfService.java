package megamacs.conf;

import megamacs.Enums;
import megamacs.Injector;

import java.io.*;
import java.util.*;

/**
 * Created by sebastien on 23/07/2016.
 */
public class ConfService implements Serializable{

    boolean resetDefaultConf = false;

    private static String BACKUP_PATH = "C:\\Temp\\";


    private Map<String, megamacs.conf.UserConf> users = new HashMap();

    private UserConf currentUser;

    boolean isDummy;

    public ConfService(Injector injector, boolean isDummy) {


        this.isDummy = isDummy;

        // TODO FIRST TIME

        if (resetDefaultConf) {
            resetDefaultConf();
        }
        else {
            deserialize();
        }
    }

    public void resetDefaultConf(){
        UserConf seb = createDummyUser("Seb");
        UserConf yannick = createDummyUser("Yannick");
        users.clear();
        users.put(seb.getName(), seb);
        users.put(yannick.getName(), yannick);
        currentUser = seb;
        serialize();
    }

    private String getFileName(){
        if (!isDummy){
            return "megamacs.ser";
        }
        else{
            return "megamacsDummy.ser";
        }
    }


    public Map<String,UserConf> getUsers(){
        return users;
    }

    public String getUserName(){
        return currentUser.getName();
    }

    public String getUserTrigram() {
        /*
        for (Host host : getHosts()){
            if (host.getIsLocal()){
                return host.getTrigram();
            }
        }
        return "No Ip Adress found for user";
        */
        return currentUser.getTrigram();
    }


    public String getUserIpAdress() {
        /*
        for (Host host : getHosts()){
            if (host.getIsLocal()){
                return host.getIpAddress();
            }
        }
        return "No Ip Adress found for user";
        */
        return currentUser.getIpAddress();
    }



    // TODO
    public boolean hasM2Directory(){
        return true;
    }


    public Enums.Tab getCurrenTab(){
        return currentUser.getCurrentTab();
    }

    public Enums.PR4GPort getPR4GPort(){
        return currentUser.getPr4GPort();
    }

    public Enums.PR4GChannelsConfiguration getPR4GChannelsConfiguration(){return currentUser.getPr4GChannelsConfiguration();}

    //public Enums.PR4GChannel getPR4GPublicChannel(){return currentUser.getPr4gPublicChannel();}

    //public Enums.PR4GChannel getPR4GPrivateChannel(){return currentUser.getPr4gPrivateChannel();}

    public String getDir(Enums.FileElement fileElement) {
        return currentUser.getDirs().get(fileElement);
    }

    //public boolean getIsPR4G(){return currentUser.getIsPR4G();}

    public Enums.TransferableFileElement getDeployActionTransferElement(){
        return currentUser.getDeployActionTransferElement();
    }

    public Enums.FileDrive getDeployActionSourceDrive(){
        return currentUser.getDeployActionSourceDrive();
    }

    public Enums.FileDrive getDeployActionDestinationDrive() {
        return currentUser.getDeployActionDestinationDrive();
    }

    public List<Host> getHosts(){
        return currentUser.getHosts();
    }

    public Host getHostForTrigram( String trigram){

        for (Host host:getHosts()){
            if (trigram.equals(host.getTrigram())){
                return host;
            }
        }
        return null;
    }



    public Set<Enums.FileElement> getCheckedLogFiles(){
        return currentUser.getCheckedLogFiles();
    }


    public boolean setCurrentUser(String userName){
        if (!users.containsKey(userName)){
            System.out.println("Unknown user" + userName);
            return false;
        }

        currentUser = users.get(userName);
        serialize();
        return true;
    }

    /**
     * Only way to create a new user
     * Returns false if user already exists
     * @param userName
     * @return
     */
    public boolean createNewUser(String userName){
        if (users.containsKey(userName)){
            return false;
        }

        UserConf user = new UserConf(userName);
        users.put (userName, user);
        serialize();
        return true;
    }

    public void setTrigram(String trigram){
        currentUser.setTrigram(trigram);
        System.out.println("trigram serialized :" + trigram);
        serialize();
    }

    public void setIpAddress(String ipAddress){
        currentUser.setIpAddress(ipAddress);
        System.out.println("ipAdress serialized :" + ipAddress);
        serialize();
    }

    public void setCurrentTab(Enums.Tab currentTab){
        currentUser.setCurrentTab(currentTab);
        serialize();
    }

    public void  setPR4GPort( Enums.PR4GPort port){
        currentUser.setPr4GPort(port);
        serialize();
        System.out.println("PR4G port serialized :" + port);
    }

    public void  setPR4GChannelsConfiguration( Enums.PR4GChannelsConfiguration pr4GChannelsConfiguration){
        currentUser.setPr4GChannelsConfiguration(pr4GChannelsConfiguration);
        serialize();
    }

    /*
    public void  setPR4GPublicChannel( Enums.PR4GChannel pr4gPublicChannel){
        currentUser.setPr4gPublicChannel(pr4gPublicChannel);
        serialize();
    }

    public void  setPR4GPrivateChannel( Enums.PR4GChannel pr4GPrivateChannel){
        currentUser.setPr4gPrivateChannel(pr4GPrivateChannel);
        serialize();
    }
    */
    public void setDir(Enums.FileElement fileElement, String value) {
        currentUser.getDirs().put(fileElement, value);
        serialize();
    }

    /*
    public void setIsPR4G(boolean isPR4G){
        currentUser.setPR4G(isPR4G);
        serialize();
    }
    */

    public  void setDeployActionTransferElement(Enums.TransferableFileElement transferableFileElement){
        currentUser.setDeployActionTransferElement(transferableFileElement);
        serialize();
    }

    public  void setDeployActionSourceDrive(Enums.FileDrive sourceFileDrive){
        currentUser.setDeployActionSourceDrive(sourceFileDrive);
        serialize();
    }

    public  void setDeployActionDestinationDrive(Enums.FileDrive destinationFileDrive){
        currentUser.setDeployActionDestinationDrive(destinationFileDrive);
        serialize();
    }

    public void addHost(int index , Host host){
        currentUser.getHosts().add(index, host);serialize();
    }

    public void addHost(Host host){
        currentUser.getHosts().add(host);serialize();
    }

    public void addCheckedLogFile(Enums.FileElement file){
        currentUser.getCheckedLogFiles().add(file);serialize();
    }

    public void removeCheckedLogFile(Enums.FileElement file){
        currentUser.getCheckedLogFiles().remove(file);serialize();
    }


    public void deserialize(){

        try
        {
            FileInputStream fileIn = new FileInputStream(BACKUP_PATH + getFileName());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users =  (Map<String, megamacs.conf.UserConf>)in.readObject();
            String currentUserString = (String)in.readObject();
            in.close();
            fileIn.close();

            // set current user
            if (users.containsKey(currentUserString)){
                currentUser = users.get(currentUserString);
            }
            else {
                System.out.println("No current user in deserilization");
            }
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("UserConf class not found");
            c.printStackTrace();
            return;
        }
        for (UserConf e:users.values()) {
            System.out.println("\nDeserialized users is =" + e.toString());
        }
        System.out.println("\nDeserialized current user is =" + currentUser.getName());
    }

    public void serialize(){
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream(BACKUP_PATH + getFileName());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.writeObject(currentUser.getName());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + BACKUP_PATH + getFileName() + "\n");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }


    UserConf  createDummyUser(String name){
        UserConf aUser = new UserConf(name);
        aUser.setCurrentTab(Enums.Tab.MACS);

        aUser.setPr4GPort(Enums.PR4GPort.COM6);
        aUser.setTrigram("SEB");
        aUser.setIpAddress("localhost");

        aUser.getDirs().put(Enums.FileElement.MACS_VERSION, "0.9.24-SNAPSHOT");
        aUser.getDirs().put(Enums.FileElement.USB_DIR, "C:\\Users\\sebastien\\Desktop\\USB");
        aUser.getDirs().put(Enums.FileElement.M2_DIR, "C:\\Users\\sebastien\\Desktop\\m2");
        aUser.getDirs().put(Enums.FileElement.MACS_DIR, "C:\\Users\\sebastien\\Desktop\\userdashboardpanel");
        aUser.getDirs().put(Enums.FileElement.PIMS_DIR, "C:\\Users\\sebastien\\Desktop\\pims");
        aUser.getDirs().put(Enums.FileElement.MARS_DIR, "C:\\Users\\sebastien\\Desktop\\mars");
        aUser.getDirs().put(Enums.FileElement.SICS_DIR, "C:\\Users\\sebastien\\Desktop\\sics");

        aUser.setPr4GChannelsConfiguration(Enums.PR4GChannelsConfiguration.PIMS);
        aUser.setDeployActionTransferElement(Enums.TransferableFileElement.PR4G_MANAGER_JAR);
        aUser.setDeployActionSourceDrive(Enums.FileDrive.M2);
        aUser.setDeployActionDestinationDrive(Enums.FileDrive.MACS_DIR);


        aUser.setHosts(new LinkedList(Arrays.asList(
                new Host(this, "localhost", "SEB", new Boolean(true), new Boolean(true)),
                new Host(this, "localhost", "DUM", new Boolean(false), new Boolean(true))))
                //new Host(this, "192.120.0.3", "ABY", new Boolean(false), new Boolean(false)),
                //new Host(this, "192.120.0.4", "ABZ", new Boolean(false), new Boolean(true))))
        );

        aUser.getCheckedLogFiles().add(Enums.FileElement.MACS_SEGMENTED);
        return aUser;
    }
}
