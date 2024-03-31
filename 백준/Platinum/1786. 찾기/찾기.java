import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String T = br.readLine();
        int n = T.length();
        String P = br.readLine();
        int m = P.length();

        // 실패함수 만들기: KMP 아이디어 적용
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) { // i: 접미사
            while (j > 0 && P.charAt(i) != P.charAt(j)) j = pi[j - 1];
            if (P.charAt(i) == P.charAt(j)) pi[i] = ++j;
        }

        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < n; i++) {
            // 맞는 위치가 나올 때까지 j-1칸의 공통 부분 위치로 이동
            while (j > 0 && T.charAt(i) != P.charAt(j)) j = pi[j - 1];
            if (T.charAt(i) == P.charAt(j)) {
                if (j == m - 1) {
                    cnt++;
                    sb.append(i - j + 1).append(" ");
                    j = pi[j];
                } else {
                    j++;
                }
            }
        }

        System.out.println(cnt);
        System.out.println(sb.toString());
        br.close();
    }
}