import java.util.*;
import java.io.*;

public class Solution {

    static int N, answer;
    static int[] di = {1, 1, -1, -1}, dj = {1, -1, -1, 1};
    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            answer = -1;
            // 나는 시계방향으로 돌릴 것이다.
            // 처음 출발 지점은 밑에서 세 번째까지밖에 못 간다.
            for (int i = 0; i < N - 2; i++) {
                for (int j = 1; j < N - 1; j++) {
                    searchCafe(i, j);
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");

        }
        System.out.println(sb.toString());
        br.close();
    }

    /*
    0 0 0 0
    0 0 0 0
    0 0 0 0
    0 0 0 0
     */
    static void searchCafe(int startI, int startJ) {
        // 평행사변형 중에서 1, 2번 꼭짓점의 가로(세로) 길이는 a, 2,3번 꼭짓점의 세로(가로)의 길이는 b
        // startJ + x <= N - 1
        // startI + x <= N - 2
        int maxA = Math.min(N - startJ - 1, N - 2 - startI);

        for (int a = 1; a <= maxA; a++) {
            int sndI = startI + di[0] * a;
            int sndJ = startJ + dj[0] * a;
            // sndI + x <= N - 1
            // sndJ - x >= a
            int maxB = Math.min(N - sndI - 1, sndJ - a);
            for (int b = 1; b <= maxB; b++) {
                go(startI, startJ, a, b);
            }
        }
    }

    static void go(int startI, int startJ, int a, int b) {
        List<Integer> desserts = new ArrayList<>();
//        desserts.add(map[startI][startJ]);
        int i = startI;
        int j = startJ;
        for (int d = 0; d < 4; d++) {
            if (d % 2 == 0) {
                for (int k = 0; k < a; k++) {
                    i += di[d];
                    j += dj[d];
                    if (desserts.contains(map[i][j])) return;
                    desserts.add(map[i][j]);
                }
            } else {
                for (int k = 0; k < b; k++) {
                    i += di[d];
                    j += dj[d];
//                    if (i == startI && j == startJ) break;
                    if (desserts.contains(map[i][j])) return;
                    desserts.add(map[i][j]);
                }
            }
        }
        answer = Math.max(desserts.size(), answer);
    }

}