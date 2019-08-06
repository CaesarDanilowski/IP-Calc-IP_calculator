package com.cezarydanilowski;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;

public class CalcFrame extends JFrame {

    private JMenuBar menu = new JMenuBar();
    private JPanel panel = new JPanel(new GridLayout(2, 1));
    private JPanel secondPanel = new JPanel(new GridLayout(2, 1));
    private JPanel dataPanel = new JPanel(new GridLayout(3, 4));
    private JPanel howManyHostsPanel = new JPanel(new GridLayout(1, 2));
    private JPanel calculationsPanel = new JPanel(new GridLayout(5, 5));
    private JTextField hostAddress = new JTextField();
    private JTextField subnetMask = new JTextField();
    private JButton networkAddress = new JButton();
    private JButton broadcastAddress = new JButton();
    private JButton firstHost = new JButton();
    private JButton lastHost = new JButton();
    private JLabel howManyHosts = new JLabel("0");
    private Calculations calculations = new Calculations(this);
    private FileOperations fileOperations = new FileOperations(this, calculations);

    public CalcFrame() {
        setJMenuBar(menu);
        addMenu();
        addDataPanel();
        addCalculationsPanel();

        add(panel);
    }

    private void addMenu() {
        JMenu file = new JMenu("Plik");
        JMenu help = new JMenu("Pomoc");

        JMenuItem run = new JMenuItem("Oblicz");
        run.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        JMenuItem newFile = new JMenuItem("Nowy", 'N');
        newFile.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        JMenuItem saveFile = new JMenuItem("Zapisz", 'Z');
        saveFile.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        JMenuItem importFile = new JMenuItem("Otwórz", 'O');
        importFile.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        JMenuItem exit = new JMenuItem("Zakończ");
        run.addActionListener(ActionEvent -> calculations.executeAllCalculations());
        exit.addActionListener(ActionEvent -> System.exit(0));
        newFile.addActionListener(ActionEvent -> setEverythingEmpty());
        saveFile.addActionListener(ActionEvent -> {
            try {
                fileOperations.saveNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        importFile.addActionListener(ActionEvent -> {
            try {
                fileOperations.openFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        file.add(run);
        file.add(newFile);
        file.add(saveFile);
        file.add(importFile);
        file.add(exit);

        JMenuItem authors = new JMenuItem("Autorzy");
        JMenuItem about = new JMenuItem("O programie");
        authors.addActionListener(ActionEvent -> {
            JDialog authorsDialog = new JDialog();
            authorsDialog.setSize(new Dimension(655, 388));
            authorsDialog.add(new PaintComponent("authors.png", 639, 349));
            authorsDialog.setLocation(400, 200);
            authorsDialog.setResizable(false);
            authorsDialog.setTitle("Autorzy");
            authorsDialog.setVisible(true);
            authorsDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

        about.addActionListener(ActionEvent -> {
            JDialog aboutDialog = new JDialog();
            aboutDialog.setSize(new Dimension(655, 388));
            aboutDialog.add(new PaintComponent("about.png", 639, 349));
            aboutDialog.setLocation(400, 200);
            aboutDialog.setResizable(false);
            aboutDialog.setTitle("O programie");
            aboutDialog.setVisible(true);
            aboutDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

        help.add(authors);
        help.add(about);

        menu.add(file);
        menu.add(help);
    }

    private void addDataPanel() {
        networkAddress.setEnabled(false);
        broadcastAddress.setEnabled(false);
        firstHost.setEnabled(false);
        lastHost.setEnabled(false);
        howManyHosts.setEnabled(false);

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Informations about network");
        secondPanel.setBorder(titled);

        dataPanel.add(new JLabel("Host Address"));
        dataPanel.add(hostAddress);

        dataPanel.add(new JLabel("Subnet Mask"));
        dataPanel.add(subnetMask);

        dataPanel.add(new JLabel("Network Address"));
        dataPanel.add(networkAddress);

        dataPanel.add(new JLabel("Broadcast Address"));
        dataPanel.add(broadcastAddress);

        dataPanel.add(new JLabel("First Host"));
        dataPanel.add(firstHost);

        dataPanel.add(new JLabel("Last Host"));
        dataPanel.add(lastHost);

        howManyHostsPanel.add(new JLabel("Hosts in network"));
        howManyHostsPanel.add(howManyHosts);

        secondPanel.add(dataPanel);
        secondPanel.add(howManyHostsPanel);

        panel.add(secondPanel);
    }

    private void addCalculationsPanel() {
        BoolArithmeticButtons.setAllDisabled();

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Calculations");
        calculationsPanel.setBorder(titled);

        calculationsPanel.add(new JLabel("                     Host"));
        for (int i = 0; i < 4; i++)
            calculationsPanel.add(BoolArithmeticButtons.hostBinary[i]);

        calculationsPanel.add(new JLabel("              Subnet Mask"));
        for (int i = 0; i < 4; i++)
            calculationsPanel.add(BoolArithmeticButtons.subnetBinary[i]);

        calculationsPanel.add(new JLabel("                                           NOT"));
        for (int i = 0; i < 4; i++)
            calculationsPanel.add(BoolArithmeticButtons.subnetBinaryNegation[i]);

        calculationsPanel.add(new JLabel("Network Address         AND"));
        for (int i = 0; i < 4; i++)
            calculationsPanel.add(BoolArithmeticButtons.networkBinary[i]);

        calculationsPanel.add(new JLabel("Broadcast Address        OR"));
        for (int i = 0; i < 4; i++)
            calculationsPanel.add(BoolArithmeticButtons.broadcastBinary[i]);

        panel.add(calculationsPanel);
    }

    String setTextOfButton(JButton[] buttons) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            builder.append(calculations.fromBinaryToInteger(buttons[i]));

            if (i < 3)
                builder.append('.');
        }

        return builder.toString();
    }

    void setEverythingEmpty() {
        hostAddress.setText("");
        subnetMask.setText("");
        howManyHosts.setText(String.valueOf(0));
        networkAddress.setText("");
        broadcastAddress.setText("");
        firstHost.setText("");
        lastHost.setText("");

        for (int i = 0; i < 4; i++) {
            BoolArithmeticButtons.hostBinary[i].setText("");
            BoolArithmeticButtons.subnetBinary[i].setText("");
            BoolArithmeticButtons.subnetBinaryNegation[i].setText("");
            BoolArithmeticButtons.networkBinary[i].setText("");
            BoolArithmeticButtons.broadcastBinary[i].setText("");
        }
    }

    String getSubnetMask() { return subnetMask.getText(); }
    String getHostAddress() { return hostAddress.getText(); }
    String getNetworkAddress() { return networkAddress.getText(); }
    String getBroadcastAddress() { return broadcastAddress.getText(); }
    String getFirstHostAddress() { return firstHost.getText(); }
    String getLastHostAddress() { return lastHost.getText(); }
    String getHowManyHosts() { return howManyHosts.getText(); }

    public void setValuesOfDataPanel(String host, String subnet, String network, String broadcast) {
        hostAddress.setText(host);
        subnetMask.setText(subnet);
        networkAddress.setText(network);
        broadcastAddress.setText(broadcast);
    }

    void setHowManyHosts(int value) { this.howManyHosts.setText(String.valueOf(value)); }
    void setNetworkAddress(String value) { this.networkAddress.setText(value); }
    void setBroadcastAddress(String value) { this.broadcastAddress.setText(value); }
    void setFirstHost(String value) { this.firstHost.setText(value); }
    void setLastHost(String value) { this.lastHost.setText(value); }
}
