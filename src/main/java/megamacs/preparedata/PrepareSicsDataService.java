package megamacs.preparedata;

import megamacs.Injector;

import java.io.IOException;

public class PrepareSicsDataService {

    Injector i;

    public PrepareSicsDataService(Injector i) throws IOException {

        this.i = i;
    }

    public String prepareSics(){
        String res = "";
        return res;
    }

    /* if response != "" , there is an error*/
    public String prepareProfileCfg(){
        return "";
    }


    /* if response != "" , there is an error*/
    public String prepareRunSicsCmd(){
        return "";
    }


}