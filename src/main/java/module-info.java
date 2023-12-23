module com.example.projetguichet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetguichet to javafx.fxml;
    exports com.example.projetguichet;

}