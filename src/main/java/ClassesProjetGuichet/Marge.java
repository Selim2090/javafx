package ClassesProjetGuichet;

public class Marge extends Compte{
    private static final double tauxInteret = 0.05;


    public Marge(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    public void augmenterSoldeMarge() {
        double interet = soldeCompte * tauxInteret;
        soldeCompte += interet;
        
    }
    
     //Méthode pour retirer un montant x d'un compte
    @Override
    public void retrait(double montant) throws Exception {
        if (montant > 0 && montant <= soldeCompte) {
            soldeCompte += montant;
        } else if (montant <= 0) {
            throw new Exception("Le montant saisi doit être supérieur à zéro.");
        } 
    }

    //Méthode pour déposer un montant x dans un compte
    @Override
    public void depot(double montant) throws Exception {
        if (montant > 0) {
            soldeCompte -= montant;
        } else {
            throw new Exception("Le montant saisi doit être supérieur à zéro.");
        }
    }

    @Override
    public String toString() {
        return "Marge{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
