package com.afunx.xfun.common.utils;

public class ByteUtils {

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

    public static void short2byte(short s, byte[] bytes, int offset, boolean bigEndian) {
        for (int i = bigEndian ? 0 : BYTES_OF_SHORT - 1; (bigEndian ? i < BYTES_OF_SHORT : i >= 0); i += (bigEndian ? 1 : -1)) {
            bytes[offset + BYTES_OF_SHORT-1 - i] = (byte)(s & 0xff);
            s >>>= BITS_OF_BYTE;
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
