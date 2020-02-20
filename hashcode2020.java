import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class hashcode2020 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int b = scanner.nextInt();
        int l = scanner.nextInt();
        int d = scanner.nextInt();
        HashMap<Integer, Integer> scoreToIndex = new HashMap<>();
        int[] bscores = new int[b];
        ArrayList<Double> lib_greedy = new ArrayList<>();
        ArrayList<ArrayList<Integer>> libbooks = new ArrayList<>();
        ArrayList<int[]> lib = new ArrayList<>();

        for (int i = 0; i < l; i++) {
            libbooks.add(new ArrayList<>());
        }

        for (int i = 0; i < b; i++) {
            bscores[i] = scanner.nextInt();
            scoreToIndex.put(bscores[i], i);
        }

        for (int i = 0; i < l; i++) {
            int btemp = scanner.nextInt();
            int signup = scanner.nextInt();
            int ship = scanner.nextInt();
            lib.add(new int[]{btemp, signup, ship});
            int sc = 0;
            for (int j = 0; j < btemp; j++) {
                int index = scanner.nextInt();
                libbooks.get(i).add(bscores[index]);
                sc += bscores[index];
            }
            Collections.sort(libbooks.get(i));
            lib_greedy.add(((double) sc) * ship / (double) signup);
        }

        int n = d + 1;
        ArrayList<Integer> lib_to_scan = new ArrayList<>();
        while (n > 0) {
            double max = 0;
            int index = -1;
            for (int i = 0; i < lib_greedy.size(); i++) {
                if (lib_greedy.get(i) > max) {
                    max = lib_greedy.get(i);
                    index = i;
                }
            }
            if (index > -1) {
                if (n > lib.get(index)[1]) {
                    lib_greedy.set(index, (double) -1);
                    lib_to_scan.add(index);
                } else {
                    lib_greedy.set(index, (double) -1);
                }
            }

            if (lib_to_scan.size() == l) {
                break;
            }
            n--;
        }
        n = d + 1;
        d = d + 1;
        ArrayList<ArrayList<Integer>> books_to_send = new ArrayList<>();
        ArrayList<Integer> indexex_to_Send = new ArrayList<>();
        int books_Sent = 0;
        for (int i = 0; i < lib_to_scan.size(); i++) {
            int index = lib_to_scan.get(i);
            if (n > lib.get(index)[1]) {
                // days to scan = n - lib.get(i)[1]
                // books scanned per day = lib.get(i)[2]
                // therefore books scanned in days to scan <= lib.get(i)[2] * n - lib.get(i)[1]
                // books are scanned from end as books are sorted in ascending power of score
                // indexes to be stored NOT SCORE
                books_to_send.add(new ArrayList<>());
                indexex_to_Send.add(index);
                int days_to_scan = n - lib.get(index)[1];
                if (days_to_scan >= lib.get(index)[2] * (n - lib.get(i)[1])) {
                    for (int j = libbooks.get(index).size() - 1; j >= 0; j--) {
                        books_to_send.get(books_Sent).add(scoreToIndex.get(libbooks.get(index).get(j)));
                    }
                } else {
                    for (int j = libbooks.get(index).size() - 1; j >= 0; j++) {
                        for (int k = j; k >= (j - lib.get(index)[2]); k -= 1) {
                            if (k >= 0) {
                                books_to_send.get(books_Sent).add(scoreToIndex.get(libbooks.get(index).get(k)));
                            }
                            j--;
                        }
                    }
                }
                n -= lib.get(index)[1];
                books_Sent++;
            }
        }
        System.out.println(lib_to_scan.size());

        for (int i = 0; i < books_to_send.size(); i++) {
            System.out.println(indexex_to_Send.get(i) + " " + books_to_send.get(i).size());
            for (int j = 0; j < books_to_send.get(i).size(); j++) {
                if (j != books_to_send.get(i).size() - 1) {
                    System.out.print(books_to_send.get(i).get(j) + " ");
                } else {
                    System.out.println(books_to_send.get(i).get(j));
                }
            }
        }

    }
}
