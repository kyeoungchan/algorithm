package example;

import java.io.*;
import java.util.*;

/**
 * 파스칼 공식을 활용한 이항계수
 */
public class BinomialCoefficientTest {

    // nCk
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

//        int[][] B = new int[N + 1][N + 1];
        int[][] B = new int[N + 1][K + 1];

        for (int i = 0; i < N + 1; i++) {
            for (int j = 0, end = Math.min(i, K); j < end + 1; j++) {
//            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) B[i][j] = 1;
                else B[i][j] = B[i - 1][j - 1] + B[i - 1][j];
                // nCk = (n-1)C(k-1) + (n-1)Ck
            }
        }

        for (int[] arr : B) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(B[N][K]) ;
        br.close();
    }
}
