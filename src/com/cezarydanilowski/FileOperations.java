package com.cezarydanilowski;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileOperations {

    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter fileExtension = new FileNameExtensionFilter("IPCalc file", "ipcalc");
    private CalcFrame frame;
    private Calculations calculations;
    private String name;
    private Scanner fromFile;
    private PrintWriter toFile;
    private boolean isAlreadyCreated = false;

    public FileOperations(CalcFrame frame, Calculations calculations) {
        this.frame = frame;
        this.calculations = calculations;
        fileChooser.setFileFilter(fileExtension);
    }

    public void saveNewFile() throws IOException {
        if (!isAlreadyCreated && name == null) {
            isAlreadyCreated = true;

            fileChooser.setCurrentDirectory(new File("C:\\Users\\%username%\\My documents"));

            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                name = fileChooser.getSelectedFile().getPath();
                saveFile();
            }
        } else {
            saveFile();
        }
    }

    void saveFile() throws FileNotFoundException {
        toFile = new PrintWriter(name + ".ipcalc");
        toFile.flush();

        String host = frame.getHostAddress();
        String subnet = frame.getSubnetMask();
        String network = frame.getNetworkAddress();
        String broadcast = frame.getBroadcastAddress();
        String firstHost = frame.getFirstHostAddress();
        String lastHost = frame.getLastHostAddress();

        toFile.append("Host address:                                               " + host + "\n");
        toFile.append("Subnet mask:                                                " + subnet + "\n");
        toFile.append("Network address:                                            " + network + "\n");
        toFile.append("Broadcast address:                                          " + broadcast + "\n\n");
        toFile.append("Hosts addresses range:                                      " + firstHost + " - " + lastHost + "\n\n");
        toFile.append(BoolArithmeticButtons.getAllBinaryDatas());

        toFile.close();
    }

    public void openFile() throws IOException {
        fileChooser.setCurrentDirectory(new File("C:\\Users\\%username%\\My documents"));

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            name = fileChooser.getSelectedFile().getPath();
            fromFile = new Scanner(Paths.get(name));

            String garbage1a = fromFile.next();
            String garbage1b = fromFile.next();
            String host = fromFile.next();
            String garbage2a = fromFile.next();
            String garbage2b = fromFile.next();
            String subnet = fromFile.next();
            String garbage3a = fromFile.next();
            String garbage3b = fromFile.next();
            String network = fromFile.next();
            String garbage4a = fromFile.next();
            String garbage4b = fromFile.next();
            String broadcast = fromFile.next();

            frame.setValuesOfDataPanel(host, subnet, network, broadcast);
            calculations.executeAllCalculations();
        }

        fromFile.close();
    }
}
