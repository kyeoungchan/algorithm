package baekjoon.톱니바퀴;

import java.util.*;
import java.io.*;
/**
 * 톱니는 n극 또는 s극
 * 톱니바퀴는 1~4번 각각 번호 있음
 * 한 번의 회전: 한 칸 움직인 것을 의미함.
 * 회전시킬 톱니바퀴와 회전시킬 방향을 결정해야함
 * 상호작용
 * - 맞닿게 되는 극이 서로 다르다면, 원래 회전한 애랑 반대방향으로 회전한다.
 * - 맞닿게 되는 극이 서로 같다면, 회전하지 않는다.
 */
public class Solution_bj_14891_톱니바퀴 {

    static int[][] arr;
    static int[] dirs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 톱니바퀴 4개 => 각각 8개의 톱니를 갖고 있음
        // 12시부터 시계방향!!
        // N: 0, S: 1
        arr = new int[4][8];
        for (int i = 0; i < 4; i++) {
            String s = br.readLine();
            for (int j = 0; j < 8; j++) {
                arr[i][j] = s.charAt(j) - '0';
            }
        }
        int K = Integer.parseInt(br.readLine()); // K는 회전 횟수
        for (int com = 0; com < K; com++) {
            dirs = new int[4];
            st = new StringTokenizer(br.readLine(), " ");
            int movingNum = Integer.parseInt(st.nextToken()) - 1; // 숫자는 1~4로 주어지므로 인덱스는 0~3
            // 방향이 1이면 시계방향, -1이면 반시계방향
            int dir = Integer.parseInt(st.nextToken());

            setDirs(movingNum, dir);
            rotate();
        }

        // 점수: 1번 12시가 N->0, S->1
        // 점수: 2번 12시가 N->0, S->2
        // 점수: 3번 12시가 N->0, S->4
        // 점수: 4번 12시가 N->0, S->8
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result += arr[i][0] * (1 << i);
        }
        System.out.println(result);
        br.close();
    }

    static void setDirs(int movingNum, int dir) {
        dirs[movingNum] = dir;
        for (int rn = movingNum + 1; rn < 4; rn++) {
            if (arr[rn-1][2] + arr[rn][6] == 1) {
                // 왼쪽의 2번과 오른쪽의 6번이 방향이 다르다면 0,1 1,0 방향을 -1로 곱한 것으로 적어준다.
                dirs[rn] = -1 * dirs[rn-1];
            } else {
                // 움직일 조건이 아니면 더이상 반복하지 않는다. 0으로 그대로 둔다.
                break;
            }
        }

        for (int ln = movingNum - 1; ln > -1; ln--) {
            if (arr[ln][2] + arr[ln + 1][6] == 1) {
                // 왼쪽의 2번과 오른쪽의 6번이 방향이 다르다면 0,1 1,0 방향을 -1로 곱한 것으로 적어준다.
                dirs[ln] = -1 * dirs[ln+1];
            } else {
                // 움직일 조건이 아니면 더이상 반복하지 않는다. 0으로 그대로 둔다.
                break;
            }
        }
    }

    static void rotate() {
        for (int i = 0; i < 4; i++) {
            if (dirs[i] == 0) continue;
            rotateSingle(i, dirs[i]);
        }
    }

    static void rotateSingle(int movingNum, int dir) {
        if (dir == 1) {
            // 1이면 시계방향
            int temp = arr[movingNum][7];
            for (int i = 6; i > -1; i--) {
                arr[movingNum][i+1] = arr[movingNum][i];
            }
            arr[movingNum][0] = temp;
        } else {
            // -1이면 반시계방향
            int temp = arr[movingNum][0];
            for (int i = 0; i < 7; i++) {
                arr[movingNum][i] = arr[movingNum][i + 1];
            }
            arr[movingNum][7] = temp;
        }
    }
}
