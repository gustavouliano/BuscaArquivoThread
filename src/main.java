import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {

		String stringBusca = "Felipe";
		List<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			fileNames.add("nomescompletos-0" + i + ".txt");
		}

		BuscaArquivo busca1 = new BuscaArquivo(fileNames, stringBusca);
		busca1.execute();
	}

}
