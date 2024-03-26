import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BuscaArquivo implements Runnable {

	private Semaphore semaphore = new Semaphore(2);
	private List<String> fileNames;
	private List<String> fileNamesBusca;
	private String stringBusca;
	private ReentrantLock mutex = new ReentrantLock();

	public BuscaArquivo(List<String> fileNames, String stringBusca) {
		this.fileNames = fileNames;
		this.stringBusca = stringBusca;
	}

	public void execute() {
		this.fileNamesBusca = fileNames;
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < this.fileNames.size(); i++) {
			threads.add(new Thread(this));
		}
		for (Thread thread : threads) {
			thread.start();
		}
	}

	@Override
	public void run() {
		try {
			FileReader ler;
			semaphore.acquire();
			
			// Para evitar que duas threads peguem o mesmo filename (acontece com as 2 primeiras threads)
			mutex.lock();
			String fileName = this.fileNamesBusca.remove(0);
			mutex.unlock();
			String filePath = new File("").getAbsolutePath().concat("\\src\\arquivosNomes\\" + fileName);
			ler = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			int cont = 0;
			while ((linha = reader.readLine()) != null) {
				Thread.sleep(1);
				cont++;
				if (linha.toLowerCase().startsWith(stringBusca.toLowerCase())) {
					System.out.println("Ocorrência no arquivo " + fileName + " - linha " + cont);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

}
