import org.pesho.mydictionary.MyDictionary;
import org.pesho.mydictionary.db.DBConnection;
import org.pesho.mydictionary.db.WordsCache;
import org.pesho.mydictionary.log.ErrorDialog;
import org.pesho.mydictionary.log.Logger;

public class Main {

	public static void main(String[] args) {
		try {
			DBConnection.testConnection();
			WordsCache.getInstance();
			MyDictionary myDictionary = new MyDictionary();
			myDictionary.setVisible(true);
		} catch (Exception e) {
			Logger.error(e);
			ErrorDialog.show(e);
		}
	}

}
