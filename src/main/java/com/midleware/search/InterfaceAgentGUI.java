package com.midleware.search;

import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class InterfaceAgentGUI extends JFrame {

    // Les zones de Textes
    JTextField txtNumber = new JTextField();
    JTextField txtAddress = new JTextField();
    JTextField txtVille = new JTextField();
    JTextField txtPays = new JTextField();
    JTextField txtProvince = new JTextField();
    JTextField txtPostalCode = new JTextField();
    JButton sendButton = new JButton("Envoyer");
    JTextArea lblIndiceAir = new JTextArea();


    private InterfaceAgent interfaceAgent;


    public InterfaceAgentGUI(){
        this.setLayout(null);
        this.setTitle("Veuillez entrer votre adresse SVP!! ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,600);
        this.setVisible(true);
        this.setResizable(false);



    JLabel lblNumber = new JLabel("Numéro d’immeuble", JLabel.RIGHT);


    JLabel lblAddress = new JLabel("Rue:", JLabel.RIGHT);


    JLabel lblVille = new JLabel("Ville:", JLabel.RIGHT);


    JLabel lblPays = new JLabel("Pays:", JLabel.RIGHT);


    JLabel lbProvince = new JLabel("Province:", JLabel.RIGHT);


        JLabel lbPostalCode = new JLabel("Code postal:", JLabel.RIGHT);


        lblNumber.setBounds(80, 70, 200, 30);
        lblAddress.setBounds(80, 110, 200, 30);
        lblVille.setBounds(80, 150, 200, 30);
        lblPays.setBounds(80, 190, 200, 30);
        lbProvince.setBounds(80, 230, 200, 30);
        lbPostalCode.setBounds(80, 270, 200, 30);
        lblIndiceAir.setBounds(80, 310, 550, 100);
        txtNumber.setBounds(300, 70, 200, 30);
        txtAddress.setBounds(300, 110, 200, 30);
        txtVille.setBounds(300, 150, 200, 30);
        txtPays.setBounds(300, 190, 200, 30);
        txtProvince.setBounds(300, 230, 200, 30);
        txtPostalCode.setBounds(300, 270, 200, 30);
         sendButton.setBounds(310, 450, 100, 30);

         add(lblNumber);
         add(txtNumber);
         add(lblAddress);
         add(txtAddress);
         add(lblVille);
         add(txtVille);
         add(lblPays);
         add(txtPays);
         add(lbProvince);
         add(txtProvince);
         add(lbPostalCode);
         add(txtPostalCode);
         add(lblIndiceAir);
         add(sendButton);

         sendButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String agent = "searchAgent";
                 String number = txtNumber.getText();
                 String address = txtAddress.getText();
                 String ville = txtVille.getText();
                 String pays = txtPays.getText();
                 String province = txtProvince.getText();
                 String codePostal = txtPostalCode.getText();
                 GuiEvent guiEvent = new GuiEvent(this, 1);
                 Map<String, String> params = new HashMap<>();
                 params.put("agent", agent);
                 params.put("number", number);
                 params.put("address",address);
                 params.put("ville",ville);
                 params.put("pays",pays);
                 params.put("province",province);
                 params.put("codePostal",codePostal);
                 guiEvent.addParameter(params);
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
