import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assignment {
	static List<String> totalitems(List<String> trans) {
		List<String> items = new ArrayList<String>();
		String[] words;

		for (int i = 0; i < trans.size(); i++) {
			words = trans.get(i).split("\\s+");
			for (int j = 0; j < words.length; j++) {
				int k = 1;
				for (int l = 0; l < items.size(); l++) {
					if (words[j].equals(items.get(l))) {
						k = 0;
					}
				}
				if (k == 1) {
					items.add(words[j]);
				}
			}
		}

		return items;
	}

	static String grouping(String s1, String s2, int z) {
		int xy;
		int k;
		String[] words1 = s1.split("\\s+");
		String[] words2 = s2.split("\\s+");
		int a = words1.length;
		int b = words2.length;
		String result = words2[0];

		for (int i = 1; i < b; i++) {
			result = result + " " + words2[i];
		}

		for (int i = 0; i < a; i++) {
			k = 0;
			for (int j = 0; j < b; j++) {
				if (words1[i].equals(words2[j])) {
					k = 1;
				}
			}
			if (k == 0) {
				result = result + " " + words1[i];
			}
		}

		String[] words = result.split("\\s+");

		xy = words.length;
		if (xy == z) {
			return result;
		} else {
			return null;
		}

	}

	static int suppcount(List<String> trans, String ch) {
		int count = 0;
		int res;
		for (int i = 0; i < trans.size(); i++) {
			res = isSubstring(ch, trans.get(i));
			if (res == 1) {
				count = count + 1;
			}
		}
		return count;
	}

	static int isSubstring(String s1, String s2) {
		String[] words = s1.split("\\s+");
		int carry = 0;
		for (int z = 0; z < words.length; z++) {
			s1 = words[z];
			int M = s1.length();
			int N = s2.length();
			/* A loop to slide pat[] one by one */
			for (int i = 0; i <= N - M; i++) {
				int j;

				/*
				 * For current index i, check for pattern match
				 */
				for (j = 0; j < M; j++)
					if (s2.charAt(i + j) != s1.charAt(j))
						break;

				if (j == M)
					carry = carry + 1;
			}
		}
		if (carry == words.length) {
			return 1;
		}
		return -1;
	}

	private static List<String> readFile(String filename) {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int choice;
		int support = 0;
		int res;
		String mined_string = "";
		List<String> trans = new ArrayList<String>();
		List<String> mined = new ArrayList<String>();

		Scanner sc = new Scanner(System.in);
		Scanner sk = new Scanner(System.in);
		System.out.println("Is the input data file clean, type 1 or 2");
		while (true) {
			System.out.println("1.Yes	2.No");
			choice = sc.nextInt();
			if (choice == 1 || choice == 2) {
				break;
			}
			System.out.println("You have entered a wrong input enter again");

		}
		String outf = "";
		String inf = "";
		String prc = "";

		switch (choice) {
		case 1:
			System.out.println("Enter the name of the input file");
			inf = sk.nextLine();
			trans = readFile(inf + ".txt");

			System.out.println("What is the threshold of minimum support count");
			support = sc.nextInt();

			System.out.println("Enter the name of the output report file");
			outf = sk.nextLine();
			break;
		case 2:
			System.out.println("Enter the name of the input file");
			inf = sk.nextLine();
			trans = readFile(inf + ".txt");
			System.out.println("What is the product code mapping");
			prc = sk.nextLine();
			System.out.println("What is the threshold of minimum support count");
			support = sc.nextInt();
			System.out.println("Enter the name of the output report file");
			outf = sk.nextLine();
			List<String> code = new ArrayList<String>();
			code = readFile(prc + ".txt");

			for (int ix = 0; ix < code.size(); ix++) {
				String tap1 = code.get(ix).substring(0, 3);
				String tap2 = code.get(ix).substring(4, code.get(ix).length() - 1);
				for (int jx = 0; jx < trans.size(); jx++) {
					if (isSubstring(tap2, trans.get(jx)) == 1) {
						trans.set(jx, trans.get(jx).replaceAll(tap2, tap1 + " "));
						trans.set(jx, trans.get(jx).replaceAll(";", " "));

					}
				}
			}
			break;

		}

		File file = new File(outf + ".txt");

		FileWriter writer = new FileWriter(file);
		writer.write("CS634-101 Data Mining");
		writer.write(System.getProperty("line.separator"));
		writer.write("Saiteja Vakulabharanam");
		writer.write(System.getProperty("line.separator"));
		writer.write("cs634_saitejavakulabharanam_apriori_" + support + ".txt");
		writer.write(System.getProperty("line.separator"));
		writer.write("Due Date:10/29/2018");
		writer.write(System.getProperty("line.separator"));
		writer.write("Purpose: Frequent itemsets mining");
		writer.write(System.getProperty("line.separator"));

		List<String> items = totalitems(trans);

		for (int i = 0; i < items.size(); i++) {
			res = suppcount(trans, items.get(i));
			if (res >= support) {
				mined.add(items.get(i));
				writer.write(items.get(i) + " support=" + res);
				writer.write(System.getProperty("line.separator"));

			}

		}

		int set = 1;

		int flag_start = 0;
		int flag_end = 0;
		for (int z = 2; z < 10; z++) {
			if (set == 0) {
				break;
			} else {
				set = 0;
			}
			flag_end = mined.size();

			for (int i = flag_start; i < flag_end; i++) {
				for (int j = i + 1; j < flag_end; j++) {

					mined_string = grouping(mined.get(i), mined.get(j), z);
					if (mined_string != null) {
						res = suppcount(trans, mined_string);
						if (res >= support) {
							mined.add(mined_string);
							set = set + 1;
							writer.write(mined_string + " support=" + res);
							writer.write(System.getProperty("line.separator"));
						}
					}
				}
			}
			flag_start = flag_end;
		}

		writer.close();
	}

}
