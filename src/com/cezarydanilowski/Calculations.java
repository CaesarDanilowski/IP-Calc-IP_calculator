package com.cezarydanilowski;

import javax.swing.*;
import java.awt.*;

class Calculations {

    private CalcFrame frame;
    private boolean wasErrorBefore = false;
    final Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");

    Calculations(CalcFrame frame) {
        this.frame = frame;
    }

    void executeAllCalculations() {
        String numSubnet = frame.getSubnetMask();
        String hostAddress = frame.getHostAddress();
        char[] firstAsArray = numSubnet.toCharArray();
        char[] secondAsArray = hostAddress.toCharArray();
        String finalSubnetString = "";
        String finalHostString = "";
        StringBuilder firstBuilder = new StringBuilder();
        StringBuilder secondBuilder = new StringBuilder();

        validateInput("Subnet", firstAsArray, numSubnet, finalSubnetString, firstBuilder, BoolArithmeticButtons.subnetBinary, frame);

        if (!wasErrorBefore) {
            validateInput("", secondAsArray, hostAddress, finalHostString, secondBuilder, BoolArithmeticButtons.hostBinary, frame);
        }

        BoolArithmeticButtons.negateOfSubnetMask();
        BoolArithmeticButtons.andHostAndSubnet();
        BoolArithmeticButtons.orSubnetNegationAndAddress();

        String value = frame.setTextOfButton(BoolArithmeticButtons.networkBinary);
        frame.setNetworkAddress(value);

        String secondValue = frame.setTextOfButton(BoolArithmeticButtons.broadcastBinary);
        frame.setBroadcastAddress(secondValue);

        setValueOfFirstAndLastHosts(frame);
    }

    private int validateInput(String what, char[] array, String string, String finalString, StringBuilder builder, JButton[] jButtons, CalcFrame frame) {
        int jButtonsIndex = 0;
        if (string.isEmpty()) {
            runnable.run();
            JOptionPane.showMessageDialog(null, "Fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            frame.setEverythingEmpty();
            wasErrorBefore = true;
            return 1;
        }

        if (!hasEnoughDots(array)) {
            return showAndReturnErrorStatus();
        }

        StringBuilder finalStringBuilder = new StringBuilder(finalString);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != '.' && i < array.length - 1)
                builder.append(array[i]);
            else if (array[i] == '.') {
                try {
                    int number = Integer.valueOf(builder.toString());

                    if (number > 255 || number < 0) {
                        return showAndReturnErrorStatus();
                    }

                    jButtons[jButtonsIndex].setText(fromIntegerToBinary(number));
                    jButtonsIndex++;
                    finalStringBuilder.append(fromIntegerToBinary(number));
                    builder.setLength(0);
                } catch (NumberFormatException e) {
                    return showAndReturnErrorStatus();
                }
            } else if (i == array.length - 1) {
                builder.append(array[i]);
                int number = Integer.valueOf(builder.toString());

                if (number > 255 || number < 0) {
                    return showAndReturnErrorStatus();
                }

                jButtons[jButtonsIndex].setText(fromIntegerToBinary(number));
                finalStringBuilder.append(fromIntegerToBinary(number));
            }
        }
        finalString = finalStringBuilder.toString();

        if (what.equals("Subnet")) {
            int howManyZeroes = 0;

            if (!isSubnetMaskValid(finalString.toCharArray())) {
                return showAndReturnErrorStatus();
            }

            char[] arr = finalString.toCharArray();
            for (char e : arr) {
                if (e == '0')
                    howManyZeroes++;
            }

            if (howManyZeroes == 0) frame.setHowManyHosts(0);
            else frame.setHowManyHosts((int) (Math.pow(2, howManyZeroes) - 2));
        }

        return 0;
    }

    private int showAndReturnErrorStatus() {
        runnable.run();
        JOptionPane.showMessageDialog(null, "Wrong input!", "Error", JOptionPane.ERROR_MESSAGE);
        frame.setEverythingEmpty();
        wasErrorBefore = true;
        return 1;
    }

    private boolean hasEnoughDots(char[] arr) {
        int howManyDots = 0;

        for (char e : arr) {
            if (e == '.')
                howManyDots++;
        }

        return howManyDots == 3;
    }

    private static boolean isSubnetMaskValid(char[] arr) {
        boolean zeroIsNext = false;

        for (char e : arr) {
            if (e == '0') zeroIsNext = true;
            else if (e == '1' && zeroIsNext) return false;
        }

        return true;
    }

    private String fromIntegerToBinary(int num) {
        StringBuilder builder = new StringBuilder();

        if (num == 0) {
            builder.append("0".repeat(8));
        } else {
            while (num != 0) {
                if (num % 2 == 0)
                    builder.append(0);
                else
                    builder.append(1);

                num /= 2;
            }
        }

        while (builder.length() != 8)
            builder.append(0);

        return builder.reverse().toString();
    }

    int fromBinaryToInteger(JButton button) {
        String binaryNumber =  button.getText();
        char[] asChars = binaryNumber.toCharArray();
        int number = 0;

        for (int i = 0, j = 7; i < 8 && j >= 0; i++, j--) {
            if (asChars[i] == '1')
                number += Math.pow(2, j);
        }

        return number;
    }

    private void setValueOfFirstAndLastHosts(CalcFrame frame) {
        String network = frame.getNetworkAddress();
        String broadcast = frame.getBroadcastAddress();

        char[] netAsArray = network.toCharArray();
        char[] brdAsArray = broadcast.toCharArray();

        StringBuilder net = new StringBuilder();
        StringBuilder brd = new StringBuilder();

        int i = 0;
        while (i < netAsArray.length - 1) {
            net.append(netAsArray[i]);
            i++;
        }

        int j = 0;
        while (j < brdAsArray.length - 1) {
            brd.append(brdAsArray[j]);
            j++;
        }

        String netA = String.valueOf(netAsArray[netAsArray.length - 1]);
        int lastValueNet = Integer.valueOf(netA);
        String netB = String.valueOf(brdAsArray[brdAsArray.length - 1]);
        int lastValueBrd = Integer.valueOf(netB);

        lastValueNet += 1;
        lastValueBrd -= 1;
        net.append(lastValueNet);
        brd.append(lastValueBrd);

        frame.setFirstHost(net.toString());
        frame.setLastHost(brd.toString());
    }
}