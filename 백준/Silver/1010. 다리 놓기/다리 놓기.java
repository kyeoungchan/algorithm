import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // MCN
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] binomialCoefficient = new int[M + 1][N + 1];
            binomialCoefficient[0][0] = 1;
            binomialCoefficient[1][0] = 1;
            binomialCoefficient[1][1] = 1;
            // nCr = n-1Cr-1 + n-1Cr
            for (int i = 2; i < M + 1; i++) {
                binomialCoefficient[i][0] = 1;
                for (int j = 1; j < Math.min(N + 1, i + 1); j++) {
                    binomialCoefficient[i][j] = binomialCoefficient[i - 1][j - 1] + binomialCoefficient[i - 1][j];
                }
            }
            sb.append(binomialCoefficient[M][N]).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}