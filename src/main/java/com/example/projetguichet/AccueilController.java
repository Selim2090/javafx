package com.example.projetguichet;
import ClassesProjetGuichet.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AccueilController {




    @FXML
    private Button transaction_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Label cheque;

    @FXML
    private Label credit;

    @FXML
    private Label epargne;

    @FXML
    private Label hypo;

    public void initialize() {
        int nip = HelloController.clientActuel.getNumeroNIP();

        cheque.setText(String.valueOf(HelloController.gestionnaire.getCompteCheque(nip).getSoldeCompte()));
        epargne.setText(String.valueOf(HelloController.gestionnaire.getCompteEpargne(nip).getSoldeCompte()));
        hypo.setText(String.valueOf(HelloController.gestionnaire.getCompteHypothecaire(nip).getSoldeCompte()));
        credit.setText(String.valueOf(HelloController.gestionnaire.getCompteMarge(nip).getSoldeCompte()));



    }


    @FXML
    public void SwitchOnClick2(ActionEvent event) throws IOException {
        if (event.getSource() == transaction_btn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transaction.fxml"));
            Parent root = loader.load();
            Scene scene = transaction_btn.getScene();
            scene.setRoot(root);
            ((Stage) scene.getWindow()).setTitle("Transaction");
        }
    }
    @FXML
    public void SwitchBack(ActionEvent event) throws IOException {
        Scene first = null;
        Parent root = null;
        if (event.getSource() == logout_btn) {
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            first = logout_btn.getScene();
            first.setRoot(root);
            ((Stage) first.getWindow()).setTitle("bonjour");

        }
    }
}


