package protocols.dhcp;

import core.headers.layer2.Layer2Protocol;
import core.headers.layer3.Layer3Protocol;
import protocols.PcapPacketData;
import protocols.arp.HardwareType;

public class DHCP extends PcapPacketData {
    DHCPMessageType messageType;
    HardwareType hardwareType;
    Integer hardwareAddressLength;
    Integer hops;
    String transactionID;
    Integer secondsElapsed;



    public DHCP(Integer id, Long sequenceNumber, Layer2Protocol layer2Protocol, Layer3Protocol layer3Protocol) {
        super(id, sequenceNumber, layer2Protocol, layer3Protocol);
    }
}
