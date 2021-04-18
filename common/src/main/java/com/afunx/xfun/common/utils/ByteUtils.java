package com.afunx.xfun.common.utils;

public class ByteUtils {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char SPACE = ' ';

    private static final char LINE_FEED = '\n';

    private static final int BITS_OF_BYTE = 8;

    private static final int BYTES_OF_SHORT = 2;

    public static short bytes2short(byte[] bytes, int offset, boolean bigEndian) {
        short s = 0;
        for (int i = bigEndian ? 0 : BYTES_OF_SHORT - 1; (bigEndian ? i < BYTES_OF_SHORT : i >= 0); i += (bigEndian ? 1 : -1)) {
            s <<= BITS_OF_BYTE;
            s |= (bytes[i + offset] & 0xff);
        }
        return s;
    }

    public static void short2byte(short s, byte[] bytes, int offset, boolean bigEndian) {
        for (int i = bigEndian ? 0 : BYTES_OF_SHORT - 1; (bigEndian ? i < BYTES_OF_SHORT : i >= 0); i += (bigEndian ? 1 : -1)) {
            bytes[offset + BYTES_OF_SHORT-1 - i] = (byte)(s & 0xff);
            s >>>= BITS_OF_BYTE;
        }
    }

    public static String bytes2hexString(byte[] bytes) {
        return bytes2hexString(bytes, 0, bytes.length);
    }

    public static String bytes2hexString(byte[] bytes, int offset, int count) {
        char[] buf = new char[count << 1];
        for (int i = 0; i < count; i++) {
            buf[(i + offset) << 1] = HEX_CHAR[bytes[i] >>> 4 & 0x0f];
            buf[((i + offset) << 1) + 1] = HEX_CHAR[bytes[i] & 0x0f];
        }
        return new String(buf);
    }

    public static String bytes2hexStringFormat(int column, byte[] bytes) {
        return bytes2hexStringFormat(column, bytes, 0, bytes.length);
    }

    public static String bytes2hexStringFormat(int column, byte[] bytes, int offset, int count) {
        char[] buf = new char[(count << 1) + count - 1];
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                if (i % column == 0) {
                    buf[index++] = LINE_FEED;
                } else {
                    buf[index++] = SPACE;
                }
            }
            buf[index++] = HEX_CHAR[bytes[i] >>> 4 & 0x0f];
            buf[index++] = HEX_CHAR[bytes[i] & 0x0f];
        }
        return new String(buf);
    }

    private static void testbytes2short() {
        byte b0 = (byte) 0xff;
        byte b1 = (byte) 0xfe;
        byte[] bytes0 = new byte[] { b0, b1 };
        byte[] bytes1 = new byte[] { b1, b0 };
        short s0 = bytes2short(bytes0, 0, true);
        short s1 = bytes2short(bytes1, 0, false);
        if (s0 == -2 && s1 == -2) {
            System.out.println("testbytes2short() pass");
        } else {
            System.out.println("testbytes2short() fail");
        }
    }

    private static void testshort2bytes() {
        short s0 = -2;
        byte[] bytes0 = new byte[2];
        short2byte(s0, bytes0, 0,  true);
        byte[] bytes1 = new byte[2];
        short2byte(s0, bytes1, 0,  false);
        if (((bytes0[0] & 0xff) == 0xff)
                && ((bytes0[1] & 0xff) == 0xfe)
                && ((bytes1[0] & 0xff) == 0xfe)
                && ((bytes1[1] & 0xff) == 0xff)
        ) {
            System.out.println("testshort2bytes() pass");
        } else {
            System.out.println("testshort2bytes() fail");
        }
    }

    public static void main(String args[]) {
        testbytes2short();
        testshort2bytes();
    }
}
