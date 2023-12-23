package ClassesProjetGuichet;

public class Epargne extends Compte{
    private static final double tauxInteret = 0.01;

    //Constructeur
    public Epargne(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    // Méthode de paiement d'intérêt
    public void paiementInteret() throws Exception {
        double interet = getSoldeCompte() * tauxInteret;
        retrait(interet);
    }

    //Méthode d'affichage
    @Override
    public String toString() {
        return "Epargne{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
