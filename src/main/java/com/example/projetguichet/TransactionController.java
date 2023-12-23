package com.example.projetguichet;
import ClassesProjetGuichet.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.projetguichet.HelloController;

import java.io.IOException;
import java.util.ArrayList;
public class TransactionController {


    public void initialize() {
        int nip = HelloController.clientActuel.getNumeroNIP();

        cheque.setText(String.valueOf(HelloController.gestionnaire.getCompteCheque(nip).getSoldeCompte()));
        epargne.setText(String.valueOf(HelloController.gestionnaire.getCompteEpargne(nip).getSoldeCompte()));
        hypo.setText(String.valueOf(HelloController.gestionnaire.getCompteHypothecaire(nip).getSoldeCompte()));
        credit.setText(String.valueOf(HelloController.gestionnaire.getCompteMarge(nip).getSoldeCompte()));



    }
    @FXML
    private RadioButton DCompteCheque;

    @FXML
    private RadioButton DCompteEpargne;

    @FXML
    private RadioButton DcompteHypo;

    @FXML
    private Label cheque;

    @FXML
    private Label credit;

    @FXML
    private Label epargne;

    @FXML
    private Label hypo;

    @FXML
    private RadioButton RCompteCheque;

    @FXML
    private RadioButton RCompteEpargne;

    @FXML
    private TextField textField;
    @FXML
    private Text savedNumbers;

    @FXML
    private RadioButton TCompteCheque;

    @FXML
    private RadioButton TCompteEpargne;

    @FXML
    private RadioButton TCompteHypo;

    @FXML
    private RadioButton TCredit;

    @FXML
    private RadioButton PCompteCheque;

    @FXML
    private TextField PcompteFacture;


    private String firstNumber = "";

    private String currentNumber = "";


    @FXML
    void clearTextField(ActionEvent event) {
        currentNumber = "";
        textField.setText("");
        savedNumbers.setText("");

    }


 // numero pad
    @FXML
    void button0Clicked(ActionEvent event) {
        if (!currentNumber.equals("")) {
            addNumber("0");
        }
    }

    @FXML
    void button1Clicked(ActionEvent event) {
        addNumber("1");
    }

    @FXML
    void button2Clicked(ActionEvent event) {
        addNumber("2");
    }

    @FXML
    void button3Clicked(ActionEvent event) {
        addNumber("3");
    }

    @FXML
    void button4Clicked(ActionEvent event) {
        addNumber("4");
    }

    @FXML
    void button5Clicked(ActionEvent event) {
        addNumber("5");
    }

    @FXML
    void button6Clicked(ActionEvent event) {
        addNumber("6");
    }

    @FXML
    void button7Clicked(ActionEvent event) {
        addNumber("7");
    }

    @FXML
    void button8Clicked(ActionEvent event) {
        addNumber("8");
    }

    @FXML
    void button9Clicked(ActionEvent event) {
        addNumber("9");
    }

    @FXML
    void buttonClicked(ActionEvent event) {
        addNumber(".");
    }

    public void updateTextField() {
        textField.setText(currentNumber);
    }

    public void addNumber(String number) {
        String num = currentNumber += number;
        updateTextField();
    }


    //les methodes de transaction
 @FXML
 void alert1(ActionEvent event) {
     try {
         double montant = Double.parseDouble(currentNumber); // Analyse du montant

         int nip = HelloController.clientActuel.getNumeroNIP();

         // Gérer le retrait du compte Chèque avec protection de découvert
         if (RCompteCheque.isSelected()) {
             Cheque compteCheque = HelloController.gestionnaire.getCompteCheque(nip);
             if (compteCheque != null) {
                 double soldeCheque = compteCheque.getSoldeCompte();

                 if (montant <= soldeCheque) {
                     compteCheque.retrait(montant);
                     showAlert("Succès", "Retrait effectué avec succès depuis le compte Chèque.");
                     HelloController.banque.retrait(montant);
                 } else {
                     // Si le montant dépasse le solde, utiliser le découvert
                     double montantRestant = montant - soldeCheque;
                     Marge marge = HelloController.gestionnaire.getCompteMarge(nip);

                     if (marge == null) {
                         marge = HelloController.gestionnaire.AvoirUneMarge(nip); // Créer un compte marge si nécessaire
                     }

                     if (montantRestant <= marge.getSoldeCompte()) {
                         compteCheque.retrait(soldeCheque); // Retirer tout le solde du compte Chèque
                         HelloController.banque.retrait(montant);

                         marge.retrait(montantRestant); // Retirer le reste du compte Marge
                         HelloController.banque.retrait(montantRestant);
                         showAlert("Succès", "Retrait effectué en partie depuis le compte Chèque et complété par le compte Marge.");
                     } else {
                         showAlert("Erreur", "Fonds insuffisants pour le retrait demandé.");
                     }
                 }
             } else {
                 showAlert("Erreur", "Compte Chèque non trouvé.");
             }
         }
             // Handle withdrawal from Epargne account
             else if (RCompteEpargne.isSelected()) {
                 HelloController.gestionnaire.getCompteEpargne(HelloController.clientActuel.getNumeroNIP()).retrait(montant);
                 HelloController.banque.retrait(montant);
                 showAlert("Succès", "Retrait effectué avec succès depuis le compte Épargne.");
             }
             // Gérer le dépôt dans le compte Chèque
             else if (DCompteCheque.isSelected()) {

                 HelloController.gestionnaire.DepotCheque(HelloController.clientActuel.getNumeroNIP(), montant);
                 showAlert("Succès", "Dépôt effectué avec succès dans le compte Chèque.");
             for (Transaction transaction : HelloController.gestionnaire.transactions) {

             }

         }
             // Gérer le dépôt dans le compte Épargne
             else if (DCompteEpargne.isSelected()) {
                 HelloController.gestionnaire.getCompteEpargne(HelloController.clientActuel.getNumeroNIP()).depot(montant);
                 showAlert("Succès", "Dépôt effectué avec succès dans le compte Épargne.");
             }
             // Gérer le dépôt dans le compte Hypothécaire
             else if (DcompteHypo.isSelected()) {
                 HelloController.gestionnaire.getCompteHypothecaire(HelloController.clientActuel.getNumeroNIP()).depot(montant);
                 showAlert("Succès", "Dépôt effectué avec succès dans le compte Hypothécaire.");
             }
             // Gérer le transfert du compte Chèque
             else if (TCompteCheque.isSelected()) {

                 String typeCompteDestinataire = determinerTypeCompteDestinataire();
                 boolean transfertReussi = HelloController.gestionnaire.transfertFonds(HelloController.clientActuel.getNumeroNIP(), montant, typeCompteDestinataire);

                 if (transfertReussi) {
                     showAlert("Succès", "Transfert de fonds réussi.");
                 } else {
                     showAlert("Erreur de Transfert", "Transfert de fonds échoué.");
                 }
             }   else if (PCompteCheque.isSelected()) {
             boolean paiementReussi = HelloController.gestionnaire.PaiementFacture(nip, montant);
             if (paiementReussi) {
                 showAlert("Succès", "Paiement de facture effectué avec succès depuis le compte Chèque à "+PcompteFacture.getText());
             } else {
                 showAlert("Erreur de Paiement", "Échec du paiement de la facture depuis le compte Chèque.");
             }
         }
             // Si aucun compte n'est sélectionné
             else {
                 showAlert("Sélectionnez un compte", "Veuillez sélectionner le compte à partir duquel vous souhaitez effectuer l'opération.");
             }

             initialize(); // Mise à jour de l'interface utilisateur
         } catch (NumberFormatException e) {
             showAlert("Erreur de saisie", "Veuillez entrer un montant valide.");
         } catch (Exception e) {
             e.printStackTrace();
             showAlert("Erreur", e.getMessage());
         }
     }

     private String determinerTypeCompteDestinataire() {
         if (TCompteEpargne.isSelected()) {
             return "Epargne";
         } else if (TCompteHypo.isSelected()) {
             return "Hypothecaire";
         } else if (TCredit.isSelected()) {
             return "Marge";
         }
         return "";

     }


    @FXML
    private Button return_btn;

    //boutin retour
    @FXML
    void SwitchOnClick(ActionEvent event) throws IOException {
        if (event.getSource() == return_btn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            AccueilController accueilController = loader.getController();
            Scene scene = return_btn.getScene();
            scene.setRoot(root);
            ((Stage) scene.getWindow()).setTitle("bienvenue");
        }
    }

    //les alertes
    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



