package baekjoon.rotatingsushi;

import java.util.*;
import java.io.*;

public class Solution_bj_15961_회전초밥_서울_20반_우경찬 {


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] sushiPlates = new int[N];
        int[] eated = new int[D + 1];
        for (int i = 0; i < N; i++)
            sushiPlates[i] = Integer.parseInt(br.readLine());

        // 처음 0번부터 K개수만큼 먹었을 때의 초기화
        int count = 0;
        int answer = 0;
        for (int i = 0; i < K; i++) {
            if (eated[sushiPlates[i]] == 0) {
                count++;
            }
            eated[sushiPlates[i]]++;
        }
        if (eated[C] == 0) { // 아직 쿠폰 초밥을 안 먹은 상태
            answer = count + 1;
        } else { // 이미 쿠폰 초밥을 먹은 상태
            answer = count;
        }

        for (int i = 1; i < N; i++) {
            // end 이동
            int end = (i + K - 1) % N;
            if (eated[sushiPlates[end]] == 0) {
                count++;
            }
            eated[sushiPlates[end]]++;

            // start 이동
            eated[sushiPlates[i - 1]]--; // start점 한 칸 이동했으니 이전의 초밥 제거
            if (eated[sushiPlates[i - 1]] == 0) {
                count--;
            }

            int temp = count;
            if (eated[C] == 0) { // 아직 쿠폰 초밥을 안 먹은 상태
                temp++;
            }
            answer = Math.max(answer, temp);
        }
        System.out.println(answer);
        br.close();
    }

}
