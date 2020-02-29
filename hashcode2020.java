import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class hashcode2020 {
    static Comparator<int[]> comp = new Comparator<int[]>() {
        public int compare(int[] a, int[] b) {
            for (int i = 0; i < a.length; i++) {
                if (a[i] < b[i]) return -1;
                else if (a[i] > b[i]) return 1;
            }
            return 0;
        }
    };

    public static void main(String[] args) throws IOException {

        FastReader scanner = new FastReader("src/2.in");

        int b = scanner.nextInt();
        int l = scanner.nextInt();
        int d = scanner.nextInt();
        int[] bscores = new int[b];
        ArrayList<Double> lib_greedy = new ArrayList<>();
        ArrayList<ArrayList<Integer>> libbooks = new ArrayList<>();
        ArrayList<int[]> lib = new ArrayList<>();
        //ArrayList<HashMap<Integer, ArrayList<Integer>>> idk = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            libbooks.add(new ArrayList<>());
        }

        for (int i = 0; i < b; i++) {
            bscores[i] = scanner.nextInt();
        }

        for (int i = 0; i < l; i++) {
            int btemp = scanner.nextInt();
            int signup = scanner.nextInt();
            int ship = scanner.nextInt();
            lib.add(new int[]{btemp, signup, ship});
            int sc = 0;
            HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
            //ArrayList<Integer> omg = new ArrayList<>();
            for (int j = 0; j < btemp; j++) {
                int index = scanner.nextInt();
                //omg.add(index);
                temp.put(bscores[index], new ArrayList<>());
                libbooks.get(i).add(index);
                sc += bscores[index];
            }/*
            for (int s = 0; s < libbooks.get(i).size(); s++) {
                temp.get(bscores[s]).add(omg.get(s));
            }*/

            //idk.add(temp);
            //Collections.sort(libbooks.get(i));
            lib_greedy.add(((double) sc) * ship / (double) signup);
        }

        int x = 0;
        int n = (d + 1);
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
                    n -= lib.get(index)[1];
                } else {
                    lib_greedy.set(index, (double) -1);
                }
            }

            if (lib_to_scan.size() == l) {
                break;
            }
            x++;
            if (x >= l) {
                break;
            }
        }
        n = (int) (d + 1);
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
                // indexes to be stored NOT SCORE

                System.out.println("index = " + index);
                int days_to_scan = n - lib.get(index)[1];

                if (days_to_scan > 0) {
                    books_to_send.add(new ArrayList<>());
                    indexex_to_Send.add(index);
                    if (days_to_scan >= lib.get(index)[0] / lib.get(index)[2]) {
                        books_to_send.get(books_Sent).addAll((libbooks.get(index)));
                    } else {
                        for (int j = 0; j < n * lib.get(index)[2]; j += lib.get(index)[2]) {
                            for (int k = j; k < (Math.min((j + lib.get(index)[2]), libbooks.get(index).size())); k += 1) {
                                books_to_send.get(books_Sent).add((libbooks.get(index).get(k)));
                            }
                        }
                    }
                    n -= lib.get(index)[1];
                    books_Sent++;
                }
            }
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("src/Output.txt"));
        out.println(lib_to_scan.size());

        for (int i = 0; i < books_to_send.size(); i++) {
            out.println(indexex_to_Send.get(i) + " " + books_to_send.get(i).size());
            for (int j = 0; j < books_to_send.get(i).size(); j++) {
                if (j != books_to_send.get(i).size() - 1) {
                    out.print(books_to_send.get(i).get(j) + " ");
                } else {
                    out.println(books_to_send.get(i).get(j));
                }
            }
        }
        out.close();
    }

    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        final private int MAX_LINE_SIZE = 1 << 20;
        private DataInputStream din;
        private byte[] buffer, lineBuf;
        private int bufferPointer, bytesRead;

        public FastReader() throws FileNotFoundException {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastReader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public boolean hasNext() throws IOException {
            byte c;
            while ((c = read()) != -1) {
                if (c > ' ') {
                    bufferPointer--;
                    return true;
                }
            }
            return false;
        }

        public String nextLine() throws IOException {
            if (lineBuf == null) lineBuf = new byte[MAX_LINE_SIZE];
            int ctr = 0;
            byte c;
            while ((c = read()) != -1) {
                if (c == '\r') continue;
                if (c == '\n') break;
                lineBuf[ctr++] = c;
            }
            return new String(lineBuf, 0, ctr);
        }

        public String next() throws IOException {
            if (lineBuf == null) lineBuf = new byte[MAX_LINE_SIZE];
            int ctr = 0;
            byte c = read();
            while (c <= ' ') c = read();
            while (c > ' ') {
                lineBuf[ctr++] = c;
                c = read();
            }
            return new String(lineBuf, 0, ctr);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg) return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
            if (neg) return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null) return;
            din.close();
        }
    }
}

