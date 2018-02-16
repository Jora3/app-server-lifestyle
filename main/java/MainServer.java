import com.lifestile.system.accessdb.client.PersonneDAO;
import com.lifestile.system.accessdb.client.gestion.ActualiteDAO;
import com.lifestile.system.accessdb.client.gestion.PublicationDAO;
import com.lifestile.util.TimeSpan;

public class MainServer {

    public static void main(String[] args) {
        try {
            PersonneDAO  publicationDAO = new PersonneDAO();
            publicationDAO.addInMystyles("439PERS43022018133950625", "556PUBL15022018202133990", "Publics");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

