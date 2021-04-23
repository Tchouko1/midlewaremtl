package com.midleware.ui;

import com.midleware.address.Address;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class InterfaceAgentGUI extends JFrame {

    private String dates[]
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private String months[]
            = { "01", "02", "03", "04",
            "05", "06"/*, "July", "Aug",
            "Sup", "Oct", "Nov", "Dec"*/ };
    private String years[]
            = { /*"1995", "1996", "1997", "1998",
            "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010",
            "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018",*/
            "2019", "2020" };
    String arrondissementList[] = {"Rosemont-La Petite-Patrie",
            "Rivière-des-Prairies","Sainte-Anne-de-Bellevue",
            "Ville-Marie","Montréal-Nord"};

    // Les zones de Textes
    JComboBox comboBoxArrondissement;
    private JComboBox comboBoxDate;
    private JComboBox comboBoxMonth;
    private JComboBox comboBoxYear;
    private JLabel title;
    private JLabel arrondissementLabel;
    private JLabel dateLabel;
    /* JTextField txtAddress = new JTextField();
     JTextField txtVille = new JTextField();
     JTextField txtPays = new JTextField();
     JTextField txtProvince = new JTextField();
     JTextField txtPostalCode = new JTextField();*/
    JButton sendButton = new JButton("Envoyer");
    JTextArea lblIndiceAir = new JTextArea();


    private Container c;

    private InterfaceAgent interfaceAgent;


    public InterfaceAgentGUI(){



        this.setTitle("Veuillez entrer votre adresse SVP!! ");

        setBounds(300, 90, 900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setSize(700,600);
        // this.setVisible(true);
        this.setResizable(false);

        c = getContentPane();
        this.setLayout(null);

        title = new JLabel("Bienvenue - Données Qualité d'air");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(500, 30);
        title.setLocation(200, 30);
        c.add(title);


        arrondissementLabel = new JLabel("Arrondissement");
        arrondissementLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        arrondissementLabel.setSize(200, 20);
        arrondissementLabel.setLocation(40, 100);
        c.add(arrondissementLabel);


        comboBoxArrondissement = new JComboBox(arrondissementList);
        comboBoxArrondissement.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBoxArrondissement.setSize(200, 40);
        comboBoxArrondissement.setLocation(200, 100);
        c.add(comboBoxArrondissement);

        dateLabel = new JLabel("Date");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setSize(100, 20);
        dateLabel.setLocation(40, 250);
        c.add(dateLabel);

        comboBoxDate = new JComboBox(dates);
        comboBoxDate.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBoxDate.setSize(50, 20);
        comboBoxDate.setLocation(200, 250);
        c.add(comboBoxDate);


        comboBoxMonth = new JComboBox(months);
        comboBoxMonth.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBoxMonth.setSize(60, 20);
        comboBoxMonth.setLocation(250, 250);
        c.add(comboBoxMonth);

        comboBoxYear = new JComboBox(years);
        comboBoxYear.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBoxYear.setSize(60, 20);
        comboBoxYear.setLocation(320, 250);
        c.add(comboBoxYear);


        sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 15));
        sendButton.setSize(100, 20);
        sendButton.setLocation(150, 450);
        c.add(sendButton);


        lblIndiceAir = new JTextArea();
        lblIndiceAir.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane ( lblIndiceAir );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setSize(400, 400);
        scroll.setLocation(450, 100);
        lblIndiceAir.setLineWrap(true);
        lblIndiceAir.setEditable(false);


        c.add(scroll);

        setVisible(true);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String agent = "searchAgent";
                String arrondissement = Objects.requireNonNull(comboBoxArrondissement.getSelectedItem().toString());
                GuiEvent guiEvent = new GuiEvent(this, 1);
                Address address = new Address();
                address.setAgent(agent);
                address.setArrondissement(arrondissement);
                String day = Objects.requireNonNull(comboBoxDate.getSelectedItem()).toString();
                String  month = Objects.requireNonNull(comboBoxMonth.getSelectedItem().toString());
                String year = Objects.requireNonNull(comboBoxYear.getSelectedItem().toString());
                address.setDate(day + "-"+ month + "-" + year);
                guiEvent.addParameter(address);
                interfaceAgent.onGuiEvent(guiEvent);


            }
        });



    }

    public InterfaceAgent getInterfaceAgent() {
        return interfaceAgent;
    }

    public void setInterfaceAgent(InterfaceAgent interfaceAgent) {
        this.interfaceAgent = interfaceAgent;
    }

    public void showMessage(String msg, boolean append){
        if(append)
            lblIndiceAir.setText(lblIndiceAir.getText()+ msg+ "\n");
        else
            lblIndiceAir.setText(msg);
    }
}
