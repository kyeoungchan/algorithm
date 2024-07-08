import java.io.*;
import java.util.*;

public class Main {

    static int findParent(int x, int[] parents) {
        if (x == parents[x]) return x;
        return parents[x] = findParent(parents[x], parents);
    }

    static void unionParents(int a, int b, int[] parents) {
        a = findParent(a, parents);
        b = findParent(b, parents);

        if (a == b) return;
        if (a > b) parents[a] = b;
        else parents[b] = a;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (true) {
            st = new StringTokenizer(br.readLine(), " ");
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            if (m == 0 && n == 0) break;
            int[] parents = new int[m];
            for (int i = 1; i < m; i++)
                parents[i] = i;

            int[][] info = new int[n][3];
            int original = 0;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                info[i][0] = a;
                info[i][1] = b;
                info[i][2] = c;
                original += c;
            }

            Arrays.sort(info, Comparator.comparingInt((int[] o) -> o[2]));
            int result = 0;
            for (int[] singleInfo : info) {
                int a = singleInfo[0];
                int b = singleInfo[1];
                if (findParent(a, parents) == findParent(b, parents)) continue;
                unionParents(a, b, parents);
                result += singleInfo[2];
            }
            sb.append(original - result).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}