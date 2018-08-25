import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class OracleCon {
    public static void main(String args[]) {

        commandeGenerator commande = new commandeGenerator();

        String queryPays = "select ID_PAYS from PAYS";
        String queryDetailBonbon = "select ID_BONBON_DETAIL from BONBON_DETAIL";

        ArrayList<String> IdClients = new ArrayList<>();
        ArrayList<String> IdCommandes = new ArrayList<>();


        try {

            // -------------- Connexion à la base de donnée ----------------
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@172.16.172.137:1521:orcl","projetbi","exia");

            Statement stmt = connection.createStatement();

            // -------------- Requete Récupération Pays ----------------
            ResultSet rs = stmt.executeQuery(queryPays);
            while(rs.next()) {
                commande.Pays.add(rs.getString(1));
            }

            // -------------- Requete Récupération Details Bonbon ----------------
            ResultSet rs2 = stmt.executeQuery(queryDetailBonbon);
            while(rs2.next()) {
                commande.DetailsBonbon.add(rs2.getString(1));
            }

            // -------------- Execution de la génération des valeurs aléatoires ----------------
            commande.GenerateCommande();

            System.out.println("Client : " + commande.nomClient);
            System.out.println("paysLivraison : " + commande.paysLivraison);
            System.out.println("Commandes : " + commande.Commandes);
            System.out.println("Quantite : " + commande.Quantite);

            // -------------- Insertion dans la base le client créé ----------------
            stmt.executeUpdate("INSERT INTO CLIENTS (NOM_CLIENT, ID_PAYS) VALUES('" + commande.nomClient + "'," + commande.paysLivraison + ")");

            // -------------- Récupération de l'ID du client créé ----------------
            String queryClient = "select ID_CLIENT from CLIENTS";
            ResultSet rs3 = stmt.executeQuery(queryClient);
            while(rs3.next()) {
                IdClients.add(rs3.getString(1));
            }
            String Client = IdClients.get(IdClients.size() -1);


            // -------------- Insertion dans la base les commande pour le client créé ----------------
            int a = 0;
            while (a < commande.Commandes.size()) {
                stmt.executeUpdate("INSERT INTO Commande (ID_CLIENT) VALUES(" + Client + ")");

                String queryCommande = "select ID_COMMANDE from COMMANDE";

                ResultSet rs4 = stmt.executeQuery(queryCommande);
                while(rs4.next()) {
                    IdCommandes.add(rs4.getString(1));
                }
                String IdCommande = IdClients.get(IdClients.size() -1);

                String queryBonbonDetailVariante = "select ID_VARIANTE, ID_BONBON_PACKAGING from BONBON_DETAIL WHERE ID_BONBON_DETAIL =" + commande.Commandes.get(a);
                ResultSet rs5 = stmt.executeQuery(queryBonbonDetailVariante);

                while(rs5.next()) {
                    commande.Variante = rs5.getString(1);
                    commande.Packaging = rs5.getString(2);
                }

                String queryMachinePackaging = "select ID_MACHINE_PACKAGING from MACHINE_PACKAGING WHERE ID_BONBON_PACKAGING = " + commande.Packaging;
                ResultSet rs6 = stmt.executeQuery(queryMachinePackaging);
                while(rs6.next()) {
                    commande.MachinePackaging = rs6.getString(1);
                }

                String queryVariante = "select ID_MACHINE_VARIANTE from MACHINE_VARIANTE WHERE ID_VARIANTE = " + commande.Variante;
                ResultSet rs7 = stmt.executeQuery(queryVariante);
                while(rs7.next()) {
                    commande.MachineVariante = rs7.getString(1);
                }

                System.out.println();
                System.out.println("Quantite : " + commande.Quantite);
                System.out.println("IdCommande : " + IdCommande);
                System.out.println("commande.Commandes.get(0) : " + commande.Commandes.get(0));
                System.out.println("MachinePackaging : " + commande.MachinePackaging);
                System.out.println("MachineVariante : " + commande.MachineVariante);

                stmt.executeUpdate("INSERT INTO COMMANDE_DETAIL (QUANTITE_BONBON, ID_COMMANDE, ID_BONBON_DETAIL, ID_MACHINE_PACKAGING, ID_MACHINE_VARIANTE) " +
                        "VALUES(" + commande.Quantite.get(a) + "," + IdCommande + "," + commande.Commandes.get(a) + "," + commande.MachinePackaging + "," + commande.MachineVariante + ")");
                a++;
            };

        }  catch(Exception e) {
            System.out.println(e);
        }


    }
}
