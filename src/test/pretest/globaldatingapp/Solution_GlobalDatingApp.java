package test.pretest.globaldatingapp;

import java.io.*;
import java.util.*;

public class Solution_GlobalDatingApp {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int[] countries = new int[N];
        int[] countriesCnt = new int[N];
        for (int i = 0; i < N; i++) {
            countries[i] = i;
            countriesCnt[i] = 1;
        }
        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            unionCountry(a, b, countries, countriesCnt);
        }
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            int x = findCountry(i, countries);
            if (visited[x]) continue;
            visited[x] = true;
            list.add(countriesCnt[x]);
        }

        long result = 0L;
        /*
        a b c d e
        ab + ac + ad + ae
        ba + bc + bd + be
        ca + cb + cd + ce
        da + db + dc + de
        ea + eb + ec + ed
         */
        for (int i = 0; i < list.size(); i++) {
            result += (N - list.get(i)) * list.get(i);
        }
        System.out.println(result/2);
        br.close();
    }

    static int findCountry(int x, int[] countries) {
        if (x == countries[x]) return x;
        return countries[x] = findCountry(countries[x], countries);
    }

    static void unionCountry(int a, int b, int[] countries, int[] countriesCnt) {
        a = findCountry(a, countries);
        b = findCountry(b, countries);
        if (a == b) return;
        else if (a < b) {
            countries[b] = a;
            countriesCnt[a] += countriesCnt[b];
        } else {
            countries[a] = b;
            countriesCnt[b] += countriesCnt[a];
        }
    }
}
