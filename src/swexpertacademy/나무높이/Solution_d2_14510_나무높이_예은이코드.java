package swexpertacademy.나무높이;

import java.io.*;
import java.util.*;

public class Solution_d2_14510_나무높이_예은이코드 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d2_14510.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] height = new int[N];

            int max = 0;
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(st.nextToken());

                max = Math.max(max, num);
                height[i] = num;
            }

            int odd = 0, even = 0;
            for (int i = 0; i < N; i++) {
                int temp = max - height[i]; // 모자란 높이 계산
                odd += temp % 2; // 모자란 높이가 필요한 홀수 개수 추가
                even += temp / 2; // 모자란 높이가 필요한 짝수 개수 추가
            }

            // 짝수 개수가 2개 이상 많으면 짝수 하나를 홀수 2개로 치환
            while (odd + 2 <= even) {
                even--;
                odd += 2;
            }

            int result;
            if (even > odd) { // 짝수가 더 많으면 홀수 먼저 시작하므로 결과에 1 추가
                result = even + odd + 1;
            } else if (even == odd) { // 홀짝 개수가 같으면 그대로
                result = even + odd;
            } else {
                // even < odd // 홀수가 더 많은 경우 홀짝 날짜를 계산해서 추가
                // 6(짝) 7(홀) => 13
                // 7(짝) 8(홀) => 14
                result = even + odd + (odd - even) - 1;
            }


            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.println(sb);
        br.close();
    }
}
