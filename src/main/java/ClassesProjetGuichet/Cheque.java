package ClassesProjetGuichet;

public class Cheque extends Compte{
    private static final double fraisPaiementFacture = 1.25;
    private static final double montantFactureMaximum = 1000;

    //Constructeur
    public Cheque(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    //Méthode de paiement de facture
    public boolean paiementFacture(double montant) throws Exception {
        boolean reussi=false;
        if (montant > 0 && montant <= montantFactureMaximum) {
            if (soldeCompte >= montant + fraisPaiementFacture) {
                soldeCompte -= montant + fraisPaiementFacture;
                reussi= true;
            } else {
                throw new Exception("Solde insuffisant");
            }
        }
        return reussi;
    }


    //Méthode d'affichage
    @Override
    public String toString() {
        return "Cheque{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
