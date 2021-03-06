package core.headers.layer3.ip.v4;

import core.formats.Pcap;
import core.headers.layer3.EncapsulatedProtocol;
import core.headers.layer3.Layer3Protocol;
import core.headers.layer3.ip.v4.exceptions.UnknownPort;
import core.headers.layer3.ip.v4.exceptions.UnknownVersion;
import core.headers.pcap.LinkLayerHeader;
import core.headers.pcap.PcapGlobalHeader;
import utils.bytes.Bytefier;
import utils.integers.Intify;
import utils.net.IP;

public class IPv4Header implements Layer3Protocol {
    private static final Integer SIZE = 20;

    private IPVersion version; // 4 bits
    private Integer internetHeaderLength; // 4 bits
    private String service; // 1 byte
    private Integer totalLength; // 2 bytes
    private Integer identification; // 2 bytes
    private IPFlags flags; // 3 bits
    private Integer positionFragment; // 13 bits
    private Integer ttl; // 1 byte
    private EncapsulatedProtocol protocol; // 1 byte
    private String checksum; // 2 bytes
    private String sourceIP; // 4 bytes
    private String destinationIP; // 4 bytes

    public IPv4Header(final String versIHL,
                      final String service,
                      final Integer totalLength,
                      final Integer identification,
                      final String flagsPosition,
                      final Integer ttl,
                      final Integer port,
                      final String checksum,
                      final String sourceIP,
                      final String destinationIP) {
        try {
            byte versIHLByte = Bytefier.hexStringToByteArray(versIHL)[0];

            this.version = IPVersion.fromVersion((int) Bytefier.getFourthHighest(versIHLByte));
            this.internetHeaderLength = (int) Bytefier.getFourthLowest(versIHLByte);
            this.service = service;
            this.totalLength = totalLength;
            this.identification = identification;

            byte[] flagsPositionByteArray = Bytefier.hexStringToByteArray(flagsPosition);
            this.flags = new IPFlags(
                    Bytefier.getBit(flagsPositionByteArray[0], 0) != 0,
                    Bytefier.getBit(flagsPositionByteArray[0], 1) != 0,
                    Bytefier.getBit(flagsPositionByteArray[0], 2) != 0
            );
            flagsPositionByteArray[0] = Bytefier.clearByteAt(Bytefier.clearByteAt(
                    Bytefier.clearByteAt(
                            flagsPositionByteArray[0], 3
                    ), 2
            ), 1);

            this.positionFragment = Intify.fromByteArray(flagsPositionByteArray);
            this.ttl = ttl;
            this.protocol = EncapsulatedProtocol.fromPort(port);
            this.checksum = checksum;
            this.sourceIP = IP.v4FromHexString(sourceIP);
            this.destinationIP = IP.v4FromHexString(destinationIP);
        } catch (UnknownVersion e) {
            this.version = null;
        } catch (UnknownPort e) {
            this.protocol = null;
        }
    }

    public static Integer getSIZE() {
        return SIZE;
    }

    public static IPv4Header readiPv4Header(String hexString, PcapGlobalHeader pcapGlobalHeader) {
        return new IPv4Header(
                Pcap.read(Pcap.offset, 1, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()).substring(2),
                Pcap.read(Pcap.offset, 1, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()),
                Integer.decode(Pcap.read(Pcap.offset, 2, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork())),
                Integer.decode(Pcap.read(Pcap.offset, 2, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork())),
                Pcap.read(Pcap.offset, 2, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()).substring(2),
                Integer.decode(Pcap.read(Pcap.offset, 1, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork())),
                Integer.decode(Pcap.read(Pcap.offset, 1, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork())),
                Pcap.read(Pcap.offset, 2, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()),
                Pcap.read(Pcap.offset, 4, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()).substring(2),
                Pcap.read(Pcap.offset, 4, hexString,
                        llh -> llh == LinkLayerHeader.ETHERNET,
                        pcapGlobalHeader.getuNetwork()).substring(2)
        );
    }

    @Override
    public String toString() {
        return "\tIP version: " + this.version +
        "\n\tIHL: " + this.internetHeaderLength * 32 / 8 + " bytes" +
        "\n\tService: " + this.service +
        "\n\tTotal Length: " + this.totalLength + " bytes" +
        "\n\tIdentification: " + this.identification +
        "\n\tFlags:" +
        "\n\t\tDon't Fragment: " + this.flags.getDontFragment() +
        "\n\t\tMore Fragment: " + this.flags.getMoreFragment() +
        "\n\tPosition Fragment: " + this.positionFragment +
        "\n\tTTL: " + this.ttl +
        "\n\tProtocol: " + this.protocol +
        "\n\tChecksum: " + this.checksum +
        "\n\tSource IP: " + this.sourceIP +
        "\n\tDestination IP: " + this.destinationIP;
    }

    public IPVersion getVersion() {
        return version;
    }

    public void setVersion(IPVersion version) {
        this.version = version;
    }

    public Integer getInternetHeaderLength() {
        return internetHeaderLength;
    }

    public void setInternetHeaderLength(Integer internetHeaderLength) {
        this.internetHeaderLength = internetHeaderLength;
    }

    public IPFlags getFlags() {
        return flags;
    }

    public void setFlags(IPFlags flags) {
        this.flags = flags;
    }

    public Integer getPositionFragment() {
        return positionFragment;
    }

    public void setPositionFragment(Integer positionFragment) {
        this.positionFragment = positionFragment;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }

    public Integer getIdentification() {
        return identification;
    }

    public void setIdentification(Integer identification) {
        this.identification = identification;
    }


    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public EncapsulatedProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(EncapsulatedProtocol protocol) {
        this.protocol = protocol;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public void setDestinationIP(String destinationIP) {
        this.destinationIP = destinationIP;
    }
}
