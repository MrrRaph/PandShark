package core.headers.layer4.tcp;

import core.headers.layer4.Layer4Protocol;
import utils.bytes.Bytefier;

public class TCP implements Layer4Protocol {
    private static final Integer SIZE = 20;

    private Integer sourcePort;
    private Integer destinationPort;
    private Long sequence;
    private Long ackNumber;
    private Integer offset;
    private String reserved;
    private TCPFlags flags;
    private Integer window;
    private String checksum;
    private Integer pointer;

    public TCP(final Integer sourcePort,
               final Integer destinationPort,
               final Long sequence,
               final Long ackNumber,
               final String offResFlags,
               final Integer window,
               final String checksum,
               final Integer pointer) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.sequence = sequence;
        this.ackNumber = ackNumber;
        this.window = window;
        this.checksum = checksum;
        this.pointer = pointer;

        byte[] offResFlagsByteArray = Bytefier.hexStringToByteArray(offResFlags);
        this.offset = (int) Bytefier.getFourthHighest(offResFlagsByteArray[0]);
        this.reserved = String.valueOf(Bytefier.getFourthLowest(offResFlagsByteArray[0])) +
                Bytefier.getBit(offResFlagsByteArray[1], 0) +
                Bytefier.getBit(offResFlagsByteArray[1], 1);
        this.flags = new TCPFlags(Bytefier.getBit(offResFlagsByteArray[1], 2) != 0,
                Bytefier.getBit(offResFlagsByteArray[1], 3) != 0,
                Bytefier.getBit(offResFlagsByteArray[1], 4) != 0,
                Bytefier.getBit(offResFlagsByteArray[1], 5) != 0,
                Bytefier.getBit(offResFlagsByteArray[1], 6) != 0,
                Bytefier.getBit(offResFlagsByteArray[1], 7) != 0);
    }

    public static Integer getSIZE() {
        return SIZE;
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Integer getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Integer destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getAckNumber() {
        return ackNumber;
    }

    public void setAckNumber(Long ackNumber) {
        this.ackNumber = ackNumber;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public TCPFlags getFlags() {
        return flags;
    }

    public void setFlags(TCPFlags flags) {
        this.flags = flags;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Integer getPointer() {
        return pointer;
    }

    public void setPointer(Integer pointer) {
        this.pointer = pointer;
    }
}
