package megamacs.conf;

/**
 * Created by sebastien on 23/07/2016.
 */
public class Host implements java.io.Serializable{

    private String ipAddress;
    private String trigram;
    private Boolean isLocal = new Boolean(true);
    private Boolean isChecked = new Boolean(false);

    ConfService confService;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        confService.serialize();
    }

    public String getTrigram() {
        return trigram;
    }

    public void setTrigram(String trigram) {
        this.trigram = trigram;
        confService.serialize();
    }

    public Boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
        confService.serialize();
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
        confService.serialize();
    }

    Host(ConfService confservice, String ipAddress, String trigram, boolean isLocal, boolean isChecked){
        this.confService = confservice;
        this.ipAddress = ipAddress;
        this.trigram = trigram;
        this.isLocal = isLocal;
        this.isChecked = isChecked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Host)) return false;

        Host host = (Host) o;

        if (ipAddress != null ? !ipAddress.equals(host.ipAddress) : host.ipAddress != null) return false;
        if (isChecked != null ? !isChecked.equals(host.isChecked) : host.isChecked != null) return false;
        if (isLocal != null ? !isLocal.equals(host.isLocal) : host.isLocal != null) return false;
        if (trigram != null ? !trigram.equals(host.trigram) : host.trigram != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ipAddress != null ? ipAddress.hashCode() : 0;
        result = 31 * result + (trigram != null ? trigram.hashCode() : 0);
        result = 31 * result + (isLocal != null ? isLocal.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        return result;
    }
}
