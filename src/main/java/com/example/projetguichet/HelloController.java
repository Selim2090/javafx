package com.example.projetguichet;

import ClassesProjetGuichet.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {
    public static GestionnaireGuichet gestionnaire = new GestionnaireGuichet();
    public static Client clientActuel = null;
    public static boolean guichetFerme = false;

    public static Banque banque =new Banque(1,1,20.000,20.000);



    @FXML
    private Button login_button;

    @FXML
    private TextField codefield;

    @FXML
    private PasswordField nipfield;

    @FXML
    private Label wronglogin;

    @FXML
    void onSwitchClick(ActionEvent event) throws IOException {
        if (guichetFerme && !codefield.getText().equals("admin") && !"admin".equals(nipfield.getText())) {
            showAlert("Guichet Fermé", "Le guichet est fermé pour le moment.");
            return;
        }

        if (codefield.getText().isEmpty() || nipfield.getText().isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        List<Client> clients = gestionnaire.getClients();

        // Debugging: Print the client list before checking
        System.out.println("Attempting login, client list: " + clients);

        if (!codefield.getText().equals("admin") && !nipfield.getText().equals("admin")) {
            try {
                int codeClient = Integer.parseInt(codefield.getText());
                int nipClient = Integer.parseInt(nipfield.getText());

                for (Client client : clients) {
                    if (client.getCodeClient() == codeClient && client.getNumeroNIP() == nipClient) {
                        if (GestionnaireGuichet.estClientBloque(client)) { // verifier si le client est bloqué
                            showAlert("Accès Refusé", "Votre accès a été bloqué.");
                            return;
                        }
                        clientActuel = client;
                        Scene first = login_button.getScene();
                        Parent root = FXMLLoader.load(getClass().getResource("accueil.fxml"));
                        first.setRoot(root);
                        ((Stage) first.getWindow()).setTitle("Bienvenue");
                        return;
                    }
                }

                showAlert("Mauvais code et NIP", "Veuillez entrer le bon code client et le bon code NIP");

            } catch (NumberFormatException e) {
                showAlert("Erreur de format", "Veuillez entrer des valeurs numériques valides pour le code client et le code NIP");
            }
        } else if (codefield.getText().equals("admin") && nipfield.getText().equals("admin")) {
            Scene first = login_button.getScene();
            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
            first.setRoot(root);
            ((Stage) first.getWindow()).setTitle("Espace Admin");
        }
    }

    private void showAlert (String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}