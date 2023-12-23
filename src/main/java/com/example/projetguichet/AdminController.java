package com.example.projetguichet;

import ClassesProjetGuichet.Compte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ClassesProjetGuichet.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import ClassesProjetGuichet.GestionnaireGuichet;
import ClassesProjetGuichet.Compte;

public class AdminController {

    @FXML
    private TextField MontantField;

    @FXML
    private TextField prelevementNIPField;

    @FXML
    private Button logout_btn;

    @FXML
    private TextField Ccourriel;

    @FXML
    private TextField Cnip;

    @FXML
    private TextField Cnom;

    @FXML
    private TextField Cphone;

    @FXML
    private TextField Cprenom;


    @FXML
    void Creer(ActionEvent event) {
        String nom = Cnom.getText();
        String prenom = Cprenom.getText();
        String courriel = Ccourriel.getText();
        String phone = Cphone.getText();

        int codeClient = GestionnaireGuichet.genereCode();
        int generatedNip = GestionnaireGuichet.genererNIP();
        Client nouveauClient = GestionnaireGuichet.creerClient(codeClient, nom, prenom, phone, courriel,generatedNip);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Client Details");
        alert.setHeaderText("Client Created Successfully");
        alert.setContentText("Code Client: " + codeClient + "\nNIP: " + generatedNip);

        alert.showAndWait();
    }

    @FXML
    private TextField NIPClient;
    @FXML
    private RadioButton ClientCheque;

    @FXML
    private RadioButton ClientCredit;

    @FXML
    private RadioButton ClientEpargne;

    @FXML
    private RadioButton ClientHypothecaire;

    @FXML
    void CreerCompteClient(ActionEvent event) {
        List<String> selectedAccountTypes = new ArrayList<>();

        if (ClientCheque.isSelected()) {
            selectedAccountTypes.add("Cheque");
        }
        if (ClientEpargne.isSelected()) {
            selectedAccountTypes.add("Epargne");
        }
        if (ClientHypothecaire.isSelected()) {
            selectedAccountTypes.add("Hypothecaire");
        }
        if (ClientCredit.isSelected()) {
            selectedAccountTypes.add("Marge");
        }

        if (!selectedAccountTypes.isEmpty()) {
            try {
                int numeroNIP = Integer.parseInt(NIPClient.getText());

                for (String accountType : selectedAccountTypes) {
                    int numeroCompte = genererNumeroCompte();
                    double soldeCompte = 0.0;
                    double montantTranfertMax = 10000.0;

                    Compte nouveauCompte = GestionnaireGuichet.creerCompte(accountType, numeroNIP, numeroCompte, soldeCompte, montantTranfertMax);

                    // Logique pour traiter chaque nouveau compte créé
                }

                // Afficher un message de confirmation après la création des comptes
                Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmation.setTitle("Création de Compte Réussie");
                alertConfirmation.setHeaderText("Comptes Créés Avec Succès");
                alertConfirmation.setContentText("Les comptes ont été créés avec succès.");
                alertConfirmation.showAndWait();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de Saisie");
                alert.setHeaderText("Erreur dans le NIP");
                alert.setContentText("Le NIP doit être un nombre entier valide.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection Manquante");
            alert.setHeaderText("Aucun type de compte sélectionné");
            alert.setContentText("Veuillez sélectionner au moins un type de compte.");
            alert.showAndWait();
        }
    }
    private int genererNumeroCompte() {
        return new java.util.Random().nextInt(999999);
    }

    @FXML
    private Button close_btn;

    @FXML
    void closed(ActionEvent event) {
        HelloController.guichetFerme = true;
        Alert alertClosure = new Alert(Alert.AlertType.INFORMATION);
        alertClosure.setTitle("Fermeture du Guichet");
        alertClosure.setHeaderText(null); // No header text
        alertClosure.setContentText("La fermeture du guichet a été réussie.");
        alertClosure.showAndWait();
    }

    @FXML
    void ouvrir(ActionEvent event) {

        HelloController.guichetFerme = false;
        Alert alertOpening = new Alert(Alert.AlertType.INFORMATION);
        alertOpening.setTitle("Ouverture du Guichet");
        alertOpening.setHeaderText(null); // No header text
        alertOpening.setContentText("L'ouverture du guichet a été réussie.");
        alertOpening.showAndWait();
    }


    @FXML
    void PreleverUnMontant(ActionEvent event) {

            int nip;
            try {
                nip = Integer.parseInt(prelevementNIPField.getText());
            } catch (NumberFormatException e) {
                showAlert("Erreur", "NIP doit être un nombre.");
                return;
            }

            // Step 2: Get the client's cheque account
            Compte compteCheque = HelloController.gestionnaire.getCompteCheque(nip);
            if (compteCheque == null) {
                showAlert("Erreur", "Compte chèque non trouvé pour le NIP donné.");
                return;
            }

            double montant;
            try {
                montant = Double.parseDouble(MontantField.getText());
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Montant doit être un nombre.");
                return;
            }
        if (MontantField.getText().isEmpty() || montant <= 0) {
            showAlert("Erreur", "Veuillez entrer un montant valide.");
            return;
        }

        try {
            compteCheque.retrait(montant);
            showAlert("Succès", "Montant prélevé avec succès.");
        } catch (Exception e) {

            showAlert("Erreur", e.getMessage());
        }
    }


        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

    @FXML
    private RadioButton Deblock;
    @FXML
    private RadioButton blockR;

    @FXML
    private TextField nipBlock;


    @FXML
    void confirmer(ActionEvent event) {
        int nip;
        try {
            nip = Integer.parseInt(nipBlock.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "NIP doit être un nombre.");
            return;
        }

        Client client = GestionnaireGuichet.getClientByNip(nip);
        if (client == null) {
            showAlert("Erreur", "Client non trouvé.");
            return;
        }

        boolean isBlocked = GestionnaireGuichet.estClientBloque(client); // Assume this method exists

        if (blockR.isSelected() && isBlocked) {
            showAlert("Erreur", "Le client est déjà bloqué.");
        } else if (Deblock.isSelected() && !isBlocked) {
            showAlert("Erreur", "Le client n'est pas bloqué.");
        } else {
            GestionnaireGuichet.definirClientBloque(client, blockR.isSelected()); // Assume this method exists
            String message = blockR.isSelected() ? "Le client a été bloqué." : "Le client a été débloqué.";
            showAlert("Succès", message);
        }
    }
    @FXML
    void switchOnClick3(ActionEvent event) throws IOException{
        Scene first = null;
            Parent root = null;
            if (event.getSource()== logout_btn){
                root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                first = logout_btn.getScene();
                first.setRoot(root);
                ((Stage)first.getWindow()).setTitle("bienvenue");
            }
        }

    }

