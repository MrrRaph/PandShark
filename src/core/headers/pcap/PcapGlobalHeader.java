package core.headers.pcap;

import core.headers.pcap.exceptions.UnknownLinkLayerHeader;

public class PcapGlobalHeader {
    private String magicNumber; //32 bits
    private Integer uVersionMajor; //16 bits
    private Integer uVersionMinor; //16 bits
    private Integer thisZone; //32 bits
    private Integer uSigFigs; //32 bits
    private Integer uSnapLen; //32 bits
    private LinkLayerHeader uNetwork; //32 bits

    public PcapGlobalHeader(final String magicNumber,
                            final Integer uVersionMajor,
                            final Integer uVersionMinor,
                            final Integer thisZone,
                            final Integer uSigFigs,
                            final Integer uSnapLen,
                            final Integer uNetwork) {
        this.magicNumber = magicNumber;
        this.uVersionMajor = uVersionMajor;
        this.uVersionMinor = uVersionMinor;
        this.thisZone = thisZone;
        this.uSigFigs = uSigFigs;
        this.uSnapLen = uSnapLen;
        try {
            this.uNetwork = LinkLayerHeader.fromDataLinkType(uNetwork);
        } catch (UnknownLinkLayerHeader e) {
            this.uNetwork = LinkLayerHeader.NULL;
        }
    }

    @Override
    public String toString() {
            return "** Global Header **\n" +
            "Magic Number = "+magicNumber+
            "\nVersion Major = " + uVersionMajor+
            "\nVersion Minor = " + uVersionMinor+
            "\nThis Zone = " + thisZone+
            "\nAccuracy of Timestamp = " + uSigFigs+
            "\nMax length of captured packet = " + uSnapLen + " bytes"+
            "\nData Link Type = " + uNetwork.getDataLinkType() + " (" + uNetwork + ")";
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public Integer getuVersionMajor() {
        return uVersionMajor;
    }

    public void setuVersionMajor(Integer uVersionMajor) {
        this.uVersionMajor = uVersionMajor;
    }

    public Integer getuVersionMinor() {
        return uVersionMinor;
    }

    public void setuVersionMinor(Integer uVersionMinor) {
        this.uVersionMinor = uVersionMinor;
    }

    public Integer getThisZone() {
        return thisZone;
    }

    public void setThisZone(Integer thisZone) {
        this.thisZone = thisZone;
    }

    public Integer getuSigFigs() {
        return uSigFigs;
    }

    public void setuSigFigs(Integer uSigFigs) {
        this.uSigFigs = uSigFigs;
    }

    public Integer getuSnapLen() {
        return uSnapLen;
    }

    public void setuSnapLen(Integer uSnapLen) {
        this.uSnapLen = uSnapLen;
    }

    public LinkLayerHeader getuNetwork() {
        return uNetwork;
    }

    public void setuNetwork(LinkLayerHeader uNetwork) {
        this.uNetwork = uNetwork;
    }
}
