import org.pesho.mydictionary.MyDictionary;
import org.pesho.mydictionary.TestWord;
import org.pesho.mydictionary.db.DBConnection;

public class Main {

	public static void main(String[] args) {
		DBConnection.testConnection();
		MyDictionary myDictionary = new MyDictionary();
		myDictionary.setVisible(true);
	}

}
