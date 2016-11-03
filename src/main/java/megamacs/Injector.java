package megamacs;

import megamacs.conf.ConfService;
import megamacs.fileoperations.PathsService;
import megamacs.filetransfer.ServerTransferService;
import megamacs.filetransfer.TransferService;
import megamacs.messaging.immediate.client.NetworkSender;
import megamacs.messaging.transfer.ServerTransfer;
import megamacs.preparedata.PrepareMacsDataService;
import megamacs.preparedata.PrepareSicsDataService;
import megamacs.processOrder.ProcessOrderService;
import megamacs.runandkill.RunService;
import megamacs.tailing.TailService;

/**
 * Created by sebastien on 23/07/2016.
 */
public class Injector implements java.io.Serializable {

    public ConfService confService;
    public PathsService pathsService;
    public TailService tailService;
    public NetworkSender networkSender;
    public TransferService transferService;
    public ProcessOrderService processOrderService;
    public ServerTransferService serverTransferService;
    public PrepareMacsDataService prepareMacsDataService;
    public PrepareSicsDataService prepareSicsDataService;
    public RunService runService;
}
