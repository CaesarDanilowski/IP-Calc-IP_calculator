package com.cezarydanilowski;

import javax.swing.*;

public class BoolArithmeticButtons {

    protected static JButton[] hostBinary =  { new JButton(), new JButton(), new JButton(), new JButton() };
    protected static JButton[] subnetBinary =  { new JButton(), new JButton(), new JButton(), new JButton() };
    protected static JButton[] subnetBinaryNegation =  { new JButton(), new JButton(), new JButton(), new JButton() };
    protected static JButton[] networkBinary =  { new JButton(), new JButton(), new JButton(), new JButton() };
    protected static JButton[] broadcastBinary =  { new JButton(), new JButton(), new JButton(), new JButton() };

    public static void setAllDisabled() {
        for (int i = 0; i < 4; i++) {
            hostBinary[i].setEnabled(false);
            subnetBinary[i].setEnabled(false);
            subnetBinaryNegation[i].setEnabled(false);
            networkBinary[i].setEnabled(false);
            broadcastBinary[i].setEnabled(false);
        }
    }

    public static void negateOfSubnetMask() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char[] asArray = subnetBinary[i].getText().toCharArray();

            for (int j = 0; j < 8; j++) {
                if (asArray[j] == '0')
                    builder.append('1');
                else
                    builder.append('0');
            }

            subnetBinaryNegation[i].setText(builder.toString());
            builder.setLength(0);
        }
    }

    public static void andHostAndSubnet() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char[] hostAsArray = hostBinary[i].getText().toCharArray();
            char[] subnetAsArray = subnetBinary[i].getText().toCharArray();

            for (int j = 0; j < 8; j++) {
                if (hostAsArray[j] == '1' && subnetAsArray[j] == '1')
                    builder.append('1');
                else
                    builder.append('0');
            }

            networkBinary[i].setText(builder.toString());
            builder.setLength(0);
        }
    }

    public static void orSubnetNegationAndAddress() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char[] hostAsArray = hostBinary[i].getText().toCharArray();
            char[] subnetNegationAsArray = subnetBinaryNegation[i].getText().toCharArray();

            for (int j = 0; j < 8; j++) {
                if (hostAsArray[j] == '1' || subnetNegationAsArray[j] == '1')
                    builder.append('1');
                else
                    builder.append('0');
            }

            broadcastBinary[i].setText(builder.toString());
            builder.setLength(0);
        }
    }

    public static String getAllBinaryDatas() {
        return "Host binary:                                                " + hostBinary[0].getText() + "." + hostBinary[1].getText() + "." + hostBinary[2].getText() + "." + hostBinary[3].getText() + "\n" +
                "Subnet mask binary:                                         " + subnetBinary[0].getText() + "." + subnetBinary[1].getText() + "." + subnetBinary[2].getText() + "." + subnetBinary[3].getText() + "\n" +
                "Subnet mask binary negation:                                " + subnetBinaryNegation[0].getText() + "." + subnetBinaryNegation[1].getText() + "." + subnetBinaryNegation[2].getText() + "." + subnetBinaryNegation[3].getText() + "\n" +
                "Network address (host address AND subnet mask):             "  + networkBinary[0].getText() + "." + networkBinary[1].getText() + "." + networkBinary[2].getText() + "." + networkBinary[3].getText() + "\n" +
                "Broadcast address (network address OR negated subnet mask): "  + broadcastBinary[0].getText() + "." + broadcastBinary[1].getText() + "." + broadcastBinary[2].getText() + "." + broadcastBinary[3].getText();
    }
}
