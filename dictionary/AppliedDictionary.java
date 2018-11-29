package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
public class AppliedDictionary {

	public static void main(String[] args) throws IOException {
		try (Scanner sc = new Scanner(System.in)){
		Dictionary<String, String> dict = null;
		while (sc.hasNext()) {
			String s = sc.nextLine();
			String[] parts = s.split(" ");
			String cmd = parts[0];
			switch (cmd) {
				case "create":
					if (parts.length == 1 || parts[1].equals("sort")) {
						dict = new SortedArrayDictionary<>();
						System.out.println("new Sortedarraydictionary created");
					} else {
						dict = new HashDictionary<>();
						System.out.println("new Hashdictionary created");

					}
					break;
				case "read":
					File file = null;
					if (parts.length == 1) {
						JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						int ret = fc.showOpenDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							file = fc.getSelectedFile();
							System.out.println(file.getName() + "  eingelesen.");
						} else {
							System.err.println("Einlesen fehlgeschlagen.");
							System.exit(0);
						}
					} else {
						file = new File("C:\\Users\\ds-05\\eclipse-workspace\\ALDA\\src\\dictionary\\" + parts[parts.length-1]);
					}
					BufferedReader br = new BufferedReader(new FileReader(file));
					String st;
					if (parts.length == 2 || parts.length == 1) {
						while ((st = br.readLine()) != null) {
							String[] elm = st.split(" ");
							dict.insert(elm[0], elm[1]);
						}
					} else {
						if (parts.length == 3) {
							int n = Integer.parseInt(parts[1]);
							int count = 0;
							long start = System.nanoTime();
							while ((st = br.readLine()) != null && count++ <= n) {
								String[] elm = st.split(" ");
								dict.insert(elm[0], elm[1]);
							}
							long end = System.nanoTime();
							double timeread = end - start;
							System.out.println("Reading " + n +" took: " + timeread);
						}
					}

					break;
				case "p":
					for (Dictionary.Entry<String, String> e : dict) {
						System.out.println(e.getKey() + " : " + e.getValue());
					}
					break;
				case "s":
					long start = System.nanoTime();
					System.out.println(dict.search(parts[1]));
					long end = System.nanoTime();
					long timefind = end - start;
					System.out.println("Finding took: " + timefind);
					break;
				case "i":
					System.out.println(dict.insert(parts[1], parts[2]));
					break;
				case "r":
					dict.remove(parts[1]);
					break;
				case "exit":
					System.out.println("Programm closed");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Syntax");
			}
		}
	}
	}
}
