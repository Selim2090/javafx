package ClassesProjetGuichet;

public class Compte {
    protected int numeroNIP;
    protected int numeroCompte;
    protected double soldeCompte;
    protected static final double retraitMaximum = 1000;
    protected double montantTransfertMaximum;

    //Constructeur
    public Compte(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        this.numeroNIP = numeroNIP;
        this.numeroCompte = numeroCompte;
        this.soldeCompte = soldeCompte;
        this.montantTransfertMaximum = montantTransfertMaximum;
    }

    //Accesseurs
    public int getNumeroNIP() {
        return numeroNIP;
    }
    public int getNumeroCompte() {
        return numeroCompte;
    }
    public double getSoldeCompte() {
        return soldeCompte;
    }

    //Méthode pour retirer un montant x d'un compte
    public void retrait(double montant) throws Exception {
        if (montant > 0 && montant <= 1000 && montant % 10 == 0) {
            soldeCompte = soldeCompte - montant;
        } else if (montant <= 0) {
            throw new Exception("Le montant saisi doit être supérieur à zéro.");
        } else if (montant % 10 != 0) {
            throw new Exception("Le montant saisi doit être un multiple de 10$.");
        } else {
            throw new Exception("Vous ne pouvez pas retirer plus de 1000$ par transaction de retrait.");
        }
    }

    //Méthode pour déposer un montant x dans un compte
    public void depot(double montant) throws Exception {
        if (montant > 0) {
            soldeCompte += montant;
        } else {
            throw new Exception("Le montant saisi doit être supérieur à zéro.");
        }
    }

    //Méthode d'affichage
    @Override
    public String toString() {
        return "Compte{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
