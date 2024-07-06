package swexpertacademy.문자열교집합;

import java.io.*;
import java.util.*;

public class Solution_d3_2948_문자열교집합 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d3_2948.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            Set<Long> set = new HashSet<>();
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                long hashCode = hash(st.nextToken());
                set.add(hashCode);
            }

            int answer = 0;
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < M; i++) {
                long hashCode = hash(st.nextToken());
                if (set.contains(hashCode)) answer++;
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static long hash(String str) {
        long result = 0;
        for (int i = str.length() - 1; i > -1; i--) {
            result += (long) (Math.pow(26, i) * str.charAt(i));
        }
        return result;
    }
}
