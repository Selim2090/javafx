package ClassesProjetGuichet;

import java.util.*;

public class GestionnaireGuichet {
    private static GestionnaireGuichet instance = new GestionnaireGuichet();
    //Attributs
    private Compte banque;
    static ArrayList<Client> clients;
    static ArrayList<Cheque> comptesCheque;
    private static ArrayList<Epargne> comptesEpargne;
    private static ArrayList<Marge> comptesMarge;
    private static ArrayList<Hypothecaire> comptesHypothecaire;
    public static ArrayList<Transaction> transactions;
    public double soldeCompteCourant;


    private static Random random = new Random();

    private static int transactionCounter = 0;

    public static synchronized int genererNumTransaction() {
        return ++transactionCounter;
    }

    private static Map<Integer, Boolean> clientsBloques = new HashMap<>();

    public static void definirClientBloque(Client client, boolean estBloque) {
        clientsBloques.put(client.getNumeroNIP(), estBloque);
    }

    public static boolean estClientBloque(Client client) {
        return clientsBloques.getOrDefault(client.getNumeroNIP(), false);
    }


    public GestionnaireGuichet() {
        clients = new ArrayList<Client>();
        comptesCheque = new ArrayList<Cheque>();
        comptesEpargne = new ArrayList<Epargne>();
        comptesHypothecaire = new ArrayList<Hypothecaire>();
        comptesMarge = new ArrayList<Marge>();
        transactions = new ArrayList<Transaction>();
        soldeCompteCourant = 0.0;

        creerClient(112233, "Ben othman", "Selim", "123456789", "selimbenothman@example.com", 2002);
        creerCompte("Epargne", 2002, 10020, 10000.0, 10000.0);
        creerCompte("Cheque", 2002, 10021, 10000.0, 10000.0);
        creerCompte("Hypothecaire", 2002, 10022, 10000.0, 10000.0);
        creerCompte("Marge", 2002, 10023, 200.0, 10000.0);

    }
    public static GestionnaireGuichet getInstance() {
        return instance;
    }

    public static List<Client> getClients() {
        return clients;
    }

    public static Client getClientByNip(int nip) {
        List<Client> clients = GestionnaireGuichet.getClients();
        for (Client client : clients) {
            if (nip == client.getNumeroNIP()) {
                return client;
            }
        }
        return null;
    }

    public Client validerUtilisateur(int CodeClient, int nip){
        Client clt = null;
        for (Client client : clients) {
            if (client.getCodeClient()==CodeClient && client.getNumeroNIP() == nip) {
                clt = client;
            }
        }
        return clt;
    }
    public double RetraitCheque(int nip, double montant) throws Exception {
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                if (montant <= compte.soldeCompte) {
                    compte.retrait(montant);
                    banque.retrait(montant);
                    return compte.getSoldeCompte(); // Return the updated balance
                } else {
                    Marge marge = AvoirUneMarge(nip);
                    if (marge != null && montant - compte.soldeCompte <= marge.soldeCompte) {
                        double diff = montant - compte.soldeCompte;
                        marge.retrait(diff);
                        compte.retrait(compte.soldeCompte);
                        banque.retrait(compte.soldeCompte);
                        return compte.getSoldeCompte(); // Return the updated balance
                    }
                }
                throw new Exception("Vous n'avez pas un solde suffisant pour cette transaction.");
            }
        }
        throw new Exception("Compte non trouvé.");
    }

    public double RetraitEpargne(int nip, double montant) throws Exception {
        for (Epargne compte : comptesEpargne) {
            if (nip == compte.getNumeroNIP()) {
                compte.retrait(montant);
                banque.retrait(montant);
                return compte.getSoldeCompte(); // Return the updated balance
            }
        }
        throw new Exception("Compte non trouvé.");
    }

    public double DepotCheque(int nip, double montant) throws Exception {
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                compte.depot(montant);
                return compte.getSoldeCompte(); // Return the updated balance
            }
        }
        throw new Exception("Compte non trouvé.");
    }

    public double DepotEpargne(int nip, double montant) throws Exception {
        for (Epargne compte : comptesEpargne) {
            if (nip == compte.getNumeroNIP()) {
                compte.depot(montant);
                return compte.getSoldeCompte(); // Return the updated balance
            }
        }
        throw new Exception("Compte non trouvé.");
    }


    public boolean PaiementFacture(int nip, double montant) throws Exception {
        boolean reussi = false;
        for(Cheque compte : comptesCheque){
            if (nip == compte.getNumeroNIP()){
                compte.paiementFacture(montant);
                reussi = true;
            }
        }
        return reussi;
    }

    public boolean transfertFonds(int nip, double montant, String typeCompte) throws Exception {

        boolean reussi = false;
        Compte compteSource = null;
        Compte compteDestinataire = null;

        for (Cheque compte : comptesCheque) {
            System.out.println(compte.getNumeroNIP());
            if (nip == compte.getNumeroNIP()) {
                compteSource = compte;
                break;
            }
        }

        if (typeCompte.equals("Epargne")) {
            for (Epargne compte : comptesEpargne) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Epargne) compte;
                    break;
                }
            }
        } else if (typeCompte.equals("Hypothecaire")) {
            for (Hypothecaire compte : comptesHypothecaire) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Hypothecaire) compte;
                    break;
                }
            }
        } else if (typeCompte.equals("Marge")) {
            for (Marge compte : comptesMarge) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Marge) compte;
                    break;
                }
            }
        }

        if (compteSource!=null && compteDestinataire!=null){
            compteSource.retrait(montant);
            compteDestinataire.depot(montant);
            reussi = true;
        }

        return reussi;
    }

    public Marge AvoirUneMarge(int nip){
        
        for (Marge marge : comptesMarge) 
            if (nip == marge.numeroNIP) 
                return marge;
        return null;
    }

    public double AfficherSoldeCompte(Compte compte) {
        return compte.getSoldeCompte();
    }


    public static Client creerClient(int codeClient, String nom, String prenom, String telephone, String courriel, int numeroNIP) {
        Client nouveauClient = new Client(codeClient, nom, prenom, telephone, courriel, numeroNIP);
        clients.add(nouveauClient);
        return nouveauClient;
    }

    public static int genererNIP() {
        return 1000 + random.nextInt(9000);
    }
    public static int genereCode() {
        return 100000 + random.nextInt(900000);
    }
    public static Compte creerCompte(String typeCompte, int numeroNIP, int numeroCompte, double soldeCompte, double montantTranfertMax) {
        Compte unCompte = null;
        if (typeCompte.equals("Cheque")) {
            Cheque compteCheque = new Cheque(numeroNIP,numeroCompte,soldeCompte,montantTranfertMax);
            comptesCheque.add(compteCheque);
            unCompte =  compteCheque;
        } else if (typeCompte.equals("Epargne")) {
            Epargne compteEpargne = new Epargne(numeroNIP,numeroCompte,soldeCompte,montantTranfertMax);
            comptesEpargne.add(compteEpargne);
            unCompte =  compteEpargne;
        } else if (typeCompte.equals("Hypothecaire")) {
            Hypothecaire compteHypothecaire = new Hypothecaire(numeroNIP,numeroCompte,soldeCompte,montantTranfertMax);
            comptesHypothecaire.add(compteHypothecaire);
            unCompte =  compteHypothecaire;
        } else if (typeCompte.equals("Marge")) {
            Marge compteMarge = new Marge(numeroNIP,numeroCompte,soldeCompte,montantTranfertMax);
            comptesMarge.add(compteMarge);
            unCompte =  compteMarge;
        }
        return unCompte;
    }
    public static Cheque getCompteCheque(int nip) {
        for (Cheque compte : comptesCheque) {
            if (compte.getNumeroNIP() == nip) {
                return compte;
            }
        }
        return null;
    }
    public ArrayList<Epargne> getComptesEpargne() {
        return comptesEpargne;
    }
    public Epargne getCompteEpargne(int nip) {
        for (Epargne compte : comptesEpargne) {
            if (compte.getNumeroNIP() == nip) {
                return compte;
            }
        }
        return null; // Return null if no matching account is found
    }
    public Hypothecaire getCompteHypothecaire(int nip) {
        for (Hypothecaire compte : comptesHypothecaire) {
            if (compte.getNumeroNIP() == nip) {
                return compte;
            }
        }
        return null; // Return null if no matching account is found
    }
    public Marge getCompteMarge(int nip) {
        for (Marge compte : comptesMarge) {
            if (compte.getNumeroNIP() == nip) {
                return compte;
            }
        }
        return null; // Return null if no matching account is found
    }
    @Override
    public String toString() {
        return "GestionnaireGuichet{" +
                "banque=" + banque +
                ", clients=" + clients +
                ", comptesCheque=" + comptesCheque +
                ", comptesEpargne=" + comptesEpargne +
                ", comptesMarge=" + comptesMarge +
                ", comptesHypothecaire=" + comptesHypothecaire +
                ", transactions=" + transactions +
                ", soldeCompteCourant=" + soldeCompteCourant +
                ", random=" + random +
                '}';
    }
}
