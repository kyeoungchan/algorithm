package swexpertacademy.보호필름;

import java.io.*;
import java.util.*;

/**
 * 모든 세로방향에 대해서 동일한 특성의 셀들이 K개 이상 연속적으로 있어야한다.
 * 약품 투여: 하나의 막을 모두 A 또는 B로 변경시킨다.
 */
public class Solution_d9_2112_보호필름2 {

    static int D, W, K;
    static int[][] film;
    static int[] a, b;
    static boolean passed;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2112.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            // 두께: D, 가로 길이: W
            film = new int[D][W];
            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    film[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            if (K == 1) {
                // 합격기준이 1이면 어떤 상태든 다 통과이므로 바로 다음 테케 실행
                sb.append("#").append(tc).append(" ").append(0).append("\n");
                continue;
            }

            // A:0, B:1
            a = new int[W];
            b = new int[W];
            Arrays.fill(b, 1);
            passed = false;
            int answer = 0;
            for (int medicine = 0; medicine < K + 1; medicine++) {
                dropMedicine(0, medicine, 0);
                if (passed) {
                    answer = medicine;
                    break;
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void dropMedicine(int cnt, int totalMedicine, int start) {
        if (cnt == totalMedicine) {
            if (isValid()) {
                passed = true;
            }
            return;
        }
        // 현재 지정한 개수의 약품을 다 투여할 방법이 없는 상태라면 가지치기
        // 왜냐하면 이미 해당 경우의 수는 이전에 다 해봤을 것이므로!
        if (totalMedicine - cnt > D - start) return;

        for (int i = start; i < D; i++) {
            int[] temp = film[i];
            film[i] = a; // A약품 투여
            dropMedicine(cnt + 1, totalMedicine, i + 1);
            if (passed) return;

            film[i] = b; // B약품 투여
            dropMedicine(cnt + 1, totalMedicine, i + 1);
            if (passed) return;

            film[i] = temp;
//            dropMedicine(cnt, totalMedicine, i + 1); // 여기에는 약품 투여 안 함
//            if (passed) return;
        }
    }

    static boolean isValid() {
        for (int j = 0; j < W; j++) {
            boolean valid = false;
            int cnt = 1;
            for (int i = 0; i < D - 1; i++) {
                if (film[i][j] == film[i + 1][j]) cnt++;
                else cnt = 1;
                if (cnt >= K) {
                    // K: 합격기준
                    // K개 이상의 연속된 상태를 발견하면 바로 다음 열로 넘어간다.
                    valid = true;
                    break;
                }
            }
            // K개 이상의 연속된 상태를 한 열에서 한 번도 발견한 적 없으면 false 반환
            if (!valid) return false;
        }
        // 모두 K개 이상의 연속된 상태를 발견했으므로 true 반환
        return true;
    }
}
