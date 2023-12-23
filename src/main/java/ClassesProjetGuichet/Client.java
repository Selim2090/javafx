package ClassesProjetGuichet;
public class Client {
    //Attributs
    private int codeClient;
    private String nom;
    private String prenom;
    private String telephone;
    private String courriel;
    private int numeroNIP;

    //Constructeur
    public Client(int codeClient, String nom, String prenom, String telephone, String courriel, int numeroNIP) {
        this.codeClient = codeClient;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.courriel = courriel;
        this.numeroNIP = numeroNIP;
    }


    //Accesseurs
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getNumeroNIP() {
        return numeroNIP;
    }
    public int getCodeClient() {return  codeClient;}

    //Méthode d'affichage
    @Override
    public String toString() {
        return "Nom: " + nom + " Prénom: " + prenom + " Numéro NIP: " + numeroNIP;
    }
}
