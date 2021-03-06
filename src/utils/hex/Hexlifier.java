package utils.hex;

public final class Hexlifier {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();

    public static String unhexlify(String hexString) {
        hexString = hexString.replace(" ", "")
                .replace("0x", "")
                .replace("\\x", "");
        StringBuilder asciiStr = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            char charToAppend = (char) Integer.parseInt(str, 16);
            if (charToAppend >= 32 && charToAppend <= 126)
                asciiStr.append((char) Integer.parseInt(str, 16));
        }
        return asciiStr.toString();
        /*int l = hexString.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            char c =  (char) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i + 1), 16));
            if (c > 0 && c < 255)
                data[i / 2] = (byte) c;
        }
        return new String(data).replaceAll("[\\p{C}]", "");*/
    }

    public static String byteArraytoHexString(Byte[] bytes) {
        for(byte b : bytes) System.out.println(b);
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
