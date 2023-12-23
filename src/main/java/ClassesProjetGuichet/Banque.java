package ClassesProjetGuichet;

public class Banque extends Compte{
    private static final double montantMaximum = 20000.0; // Montant maximum autorisé dans le guichet
    private static final double montantRemplissage = 5000.0; // Montant de remplissage du guichet

    public Banque(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }


    public void RemplirGuichet() throws Exception {
        if (soldeCompte < montantMaximum) {
            double montantAjoute = Math.min(montantMaximum - soldeCompte, montantRemplissage);
            soldeCompte += montantAjoute;
        } else {
            throw new Exception("Aucun remplissage nécessaire.");
        }
    }


    @Override
    public String toString() {
        return "Banque{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
