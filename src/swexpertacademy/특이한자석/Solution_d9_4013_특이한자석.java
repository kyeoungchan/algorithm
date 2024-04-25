package swexpertacademy.특이한자석;

import java.io.*;
import java.util.*;

public class Solution_d9_4013_특이한자석 {

    // 명령어가 주어질 때마다 좌우로 다른 자석들도 돌아가는 방향을 설정해둔다. int[] rotateDir
    static int[] rotateDir;
    static int[][] magnetics;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_4013.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            // 자석의 개수: 4, 날의 개수: 8
            magnetics = new int[4][8];
            int K = Integer.parseInt(br.readLine());
            for (int i = 0; i < 4; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < 8; j++) {
                    // 위치별 극은 0~7번 인덱스로 시계방향으로 둔다.
                    magnetics[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for (int com = 0; com < K; com++) {
                st = new StringTokenizer(br.readLine(), " ");
                int idx = Integer.parseInt(st.nextToken()) - 1;
                int dir = Integer.parseInt(st.nextToken());
                rotate(idx, dir);
            }
            /*1번 자석: 제일 위가 S극이면 1점
             * 2번 자석: 제일 위가 S극이면 2점
             * 3번 자석: 제일 위가 S극이면 4점
             * 4번 자석: 제일 위가 S극이면 8점*/
            int answer = 0;
            for (int i = 0; i < 4; i++) {
                answer += magnetics[i][0] * (1 << i);
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void rotate(int idx, int dir) {
        rotateDir = new int[4];
        rotateDir[idx] = dir;
//        하나의 자석을 회전시
//        * - 붙어있는 다른 날의 자성과 다를 겨웅에만 인력에 의해 반대 방향으로 1칸 회전된다.
        for (int i = idx; i < 3; i++) {
            // * 오른쪽 극: 2번 인덱스
            // * 왼쪽 극: 6번 인덱스
            // N극: 0, S극: 1
            if (magnetics[i][2] + magnetics[i + 1][6] == 1) {
                // 시계방향: 1, 반시계방향: -1
                rotateDir[i + 1] = -rotateDir[i];
            } else {
                break;
            }
        }

        for (int i = idx; i > 0; i--) {
            // * 오른쪽 극: 2번 인덱스
            // * 왼쪽 극: 6번 인덱스
            // N극: 0, S극: 1
            if (magnetics[i][6] + magnetics[i - 1][2] == 1) {
                // 시계방향: 1, 반시계방향: -1
                rotateDir[i - 1] = -rotateDir[i];
            } else {
                break;
            }
        }

        for (int i = 0; i < 4; i++) {
            // 0이면 돌아가지 않는다.
            if (rotateDir[i] == 0) continue;
            rotateSingle(i);
        }
    }

    static void rotateSingle(int idx) {
        // 시계방향: 1, 반시계방향: -1
        // 돌아가는 것은 반대방향으로 가면서 간다.
        if (rotateDir[idx] == 1) {
            int temp = magnetics[idx][7];
            for (int i = 7; i > 0; i--) {
                magnetics[idx][i] = magnetics[idx][i - 1];
            }
            magnetics[idx][0] = temp;
        } else {
            int temp = magnetics[idx][0];
            for (int i = 0; i < 7; i++) {
                magnetics[idx][i] = magnetics[idx][i + 1];
            }
            magnetics[idx][7] = temp;
        }
    }
}
