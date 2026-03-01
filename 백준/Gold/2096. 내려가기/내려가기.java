import java.util.*;
import java.io.*;

/**
 * https://www.acmicpc.net/problem/2096
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] dpMax = new int[N][3];
        int[][] dpMin = new int[N][3];

        st = new StringTokenizer(br.readLine());
        dpMax[0][0] = dpMin[0][0] = Integer.parseInt(st.nextToken());
        dpMax[0][1] = dpMin[0][1] = Integer.parseInt(st.nextToken());
        dpMax[0][2] = dpMin[0][2] = Integer.parseInt(st.nextToken());

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 =  Integer.parseInt(st.nextToken());
            int n2 =  Integer.parseInt(st.nextToken());
            int n3 =  Integer.parseInt(st.nextToken());

            dpMax[i][0] = Math.max(dpMax[i - 1][0], dpMax[i - 1][1]) + n1;
            dpMax[i][1] = Math.max(Math.max(dpMax[i - 1][0], dpMax[i - 1][1]), dpMax[i - 1][2]) + n2;
            dpMax[i][2] = Math.max(dpMax[i - 1][1], dpMax[i - 1][2]) + n3;

            dpMin[i][0] = Math.min(dpMin[i - 1][0], dpMin[i - 1][1]) + n1;
            dpMin[i][1] = Math.min(Math.min(dpMin[i - 1][0], dpMin[i - 1][1]), dpMin[i - 1][2]) + n2;
            dpMin[i][2] = Math.min(dpMin[i - 1][1], dpMin[i - 1][2]) + n3;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Math.max(Math.max(dpMax[N - 1][0], dpMax[N - 1][1]), dpMax[N - 1][2]))
                .append(" ")
                .append(Math.min(Math.min(dpMin[N - 1][0], dpMin[N - 1][1]), dpMin[N - 1][2]));
        System.out.println(sb);

        br.close();
    }
}