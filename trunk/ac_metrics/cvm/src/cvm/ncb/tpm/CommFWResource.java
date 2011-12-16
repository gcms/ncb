package cvm.ncb.tpm;

import cvm.ncb.csm.CommObject;
import cvm.ncb.csm.CommServiceManager;
import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.oem.pe.NCBCallQueue;
import edu.fiu.strg.ACSTF.resource.AbstractResource;

import java.util.ArrayList;

public class CommFWResource extends AbstractResource {
    private NCBCallQueue callQueue;
    private ConIDMappingTable conIDMappingTable;
    private CommServiceManager csMgr;

    public CommFWResource(NCBCallQueue m_callQueue, ConIDMappingTable conIDMappingTable, CommServiceManager csMgr) {
        this.callQueue = m_callQueue;
        this.conIDMappingTable = conIDMappingTable;
        this.csMgr = csMgr;
    }

    public CommServiceManager getCommServiceManager() {
        return csMgr;
    }

    public ArrayList<CommObject> getCObjectList() {
        return getCommServiceManager().getCommObjectList();
    }

    public NCBCallQueue getCallQueue() {
        return callQueue;
    }

    public ConIDMappingTable getConIDMappingTable() {
        return conIDMappingTable;
    }
}
