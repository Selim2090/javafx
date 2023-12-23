package ClassesProjetGuichet;
public class Hypothecaire extends Compte{

    //Constructeur
    public Hypothecaire(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    public boolean preleverMontantHypotheque(double montant) throws Exception{
        boolean valid = false;
        
        if (montant > 0) {
            if (soldeCompte >= montant) {
                soldeCompte -= montant;
                valid =  true;
            } 
            else {
                throw new Exception("Solde insuffisant pour effectuer le prélèvement hypothécaire.");
            }
        } else {
            throw new Exception("Le montant du prélèvement hypothécaire doit être supérieur à zéro.");
        }
        return valid;
    }

    @Override
    public String toString() {
        return "Hypothecaire{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}
