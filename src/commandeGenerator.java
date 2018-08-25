import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class commandeGenerator {

    commande commande = new commande();

    private Random r = new Random();
    public int NombreCommande;

    public String nomClient;
    public String paysLivraison;
    public String Variante;
    public String Packaging;
    public String MachineVariante;
    public String MachinePackaging;

    public String Clients[] = {
        "Rojas",
                "Salazar",
                "Craig",
                "Alvarez",
                "Benton",
                "Merrill",
                "Flowers",
                "Miller",
                "Joyner",
                "Macdonald",
                "Emerson",
                "Hensley",
                "Patton",
                "Stevenson",
                "Wilkerson",
                "Wilkins",
                "Olson",
                "Guzman",
                "Gordon",
                "Holt",
                "Weeks",
                "Koch",
                "Bright",
                "Buckner",
                "Deleon",
                "Pate",
                "Malone",
                "Spencer",
                "Edwards",
                "Munoz",
                "Lowery",
                "Hodges",
                "Nichols",
                "Cline",
                "Hull",
                "Nguyen",
                "Barlow",
                "Long",
                "Horne",
                "Sanders",
                "Boyle",
                "Walls",
                "Powers",
                "Jacobson",
                "Herman",
                "Hester",
                "Howell",
                "Cross",
                "Wood",
                "Holland",
                "Kelley",
                "Joseph",
                "Patrick",
                "Cantu",
                "Mclaughlin",
                "Nieves",
                "Moss",
                "Lott",
                "Dominguez",
                "Mcclain",
                "Mack",
                "Cote",
                "Burns",
                "Small",
                "Buck",
                "Whitley",
                "Clay",
                "Kinney",
                "Ross",
                "Lyons",
                "Erickson",
                "Villarreal",
                "Bernard",
                "Gould",
                "Mccray",
                "Kim",
                "Eaton",
                "Stafford",
                "Mcfarland",
                "Ford",
                "Berger",
                "Cooke",
                "Garner",
                "Summers",
                "Raymond",
                "Wagner",
                "Williams",
                "Conner",
                "Hayden",
                "Rasmussen",
                "Oneil",
                "Evans",
                "Garrison",
                "Wooten",
                "Stanley",
                "Flores",
                "Ramsey",
                "Reese",
                "Blanchard",
                "Barnett"
    };
    public ArrayList<String> Pays = new ArrayList<>();
    public ArrayList<String> DetailsBonbon = new ArrayList<>();
    public ArrayList Commandes = new ArrayList<Integer>();
    public ArrayList Quantite = new ArrayList<Integer>();

    public void GenerateCommande() {
        nomClient = clientGenerator();
        paysLivraison = GeneratorCountry();

        System.out.println("Combien de commande voulez-vous passer ?");
        Scanner scanIn = new Scanner(System.in);
        NombreCommande = Integer.parseInt(scanIn.nextLine());
        scanIn.close();
        int a = 1;
        while (a <= NombreCommande)
        {
            commande.idCommande = GeneratorIdCommande();
            commande.quantite = GenerateQuantite();
            Commandes.add(commande.idCommande);
            Quantite.add(commande.quantite);
            a++;
        }
    }

    //Constructeur par défaut
    private String GeneratorCountry() {
        int nb = r.nextInt(Pays.size());
        String country = Pays.get(nb -1);
        return country;
    }

    private int GeneratorIdCommande() {
        int nb = r.nextInt(DetailsBonbon.size());
        int commande = Integer.parseInt(DetailsBonbon.get(nb));
        return commande;
    }

    private int GenerateQuantite() {
        int quantite = r.nextInt(100);
        return quantite;
    }

    private String clientGenerator(){
        int nb = r.nextInt(Clients.length);
        String client = Clients[nb -1];
        return client;
    }
}
