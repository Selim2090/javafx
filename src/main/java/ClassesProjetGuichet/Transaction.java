package ClassesProjetGuichet;
public class Transaction {
    private int numeroTransaction;
    private double montant;
    private Compte compte;
    private Compte compteTransfert;
    private String type;

    public Transaction(int numeroTransaction, double montant, Compte compte, Compte compteTransfert, String type) {
        this.numeroTransaction = numeroTransaction;
        this.montant = montant;
        this.compte = compte;
        this.compteTransfert = compteTransfert;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "numeroTransaction=" + numeroTransaction +
                ", montant=" + montant +
                ", compte=" + compte +
                ", compteTransfert=" + compteTransfert +
                ", type='" + type + '\'' +
                '}';
    }
}
