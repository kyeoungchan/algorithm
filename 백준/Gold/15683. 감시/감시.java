import java.util.*;
import java.io.*;

public class Main {

    static int N, M, K, answer;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map;
    static List<int[]> cctvs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        cctvs = new ArrayList<>();
        K = 0;
        answer = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > 0 && map[i][j] < 6) {
                    cctvs.add(new int[]{i, j, map[i][j]}); // 좌표와 cctv 번호 저장
                    K++; // cctv의 개수
                } else if (map[i][j] == 0) {
                    answer++; // 사각지대의 개수
                }
            }
        }

        setCctvDir(0, answer);
        System.out.println(answer);
        br.close();
    }

    static void setCctvDir(int cnt, int dark) {
//        debug(dark);
        if (cnt == K) {
            answer = Math.min(answer, dark);
            return;
        }

        int[] cur = cctvs.get(cnt);
        int ci = cur[0];
        int cj = cur[1];
        int cctvNum = cur[2];
        switch (cctvNum) {
            case 1:
                for (int d = 0; d < 4; d++) {
                    dark = monitor(ci, cj, d, dark, -1);
                    setCctvDir(cnt + 1, dark);
                    dark = monitor(ci, cj, d, dark, 1);
                }
                break;
            case 2:
                for (int d = 0; d < 2; d++) {
                    dark = monitor(ci, cj, d, dark, -1);
                    dark = monitor(ci, cj, (d + 2) % 4, dark, -1);
                    setCctvDir(cnt + 1, dark);
                    dark = monitor(ci, cj, d, dark, 1);
                    dark = monitor(ci, cj, (d + 2) % 4, dark, 1);
                }
                break;
            case 3:
                for (int d = 0; d < 4; d++) {
                    dark = monitor(ci, cj, d, dark, -1);
                    dark = monitor(ci, cj, (d + 1) % 4, dark, -1);
                    setCctvDir(cnt + 1, dark);
                    dark = monitor(ci, cj, d, dark, 1);
                    dark = monitor(ci, cj, (d + 1) % 4, dark, 1);
                }
                break;
            case 4:
                for (int d = 0; d < 4; d++) {
                    dark = monitor(ci, cj, d, dark, -1);
                    dark = monitor(ci, cj, (d + 1) % 4, dark, -1);
                    dark = monitor(ci, cj, (d + 3) % 4, dark, -1);
                    setCctvDir(cnt + 1, dark);
                    dark = monitor(ci, cj, d, dark, 1);
                    dark = monitor(ci, cj, (d + 1) % 4, dark, 1);
                    dark = monitor(ci, cj, (d + 3) % 4, dark, 1);
                }
                break;
            case 5:
                for (int d = 0; d < 4; d++) {
                    dark = monitor(ci, cj, d, dark, -1);
                }
                setCctvDir(cnt + 1, dark);
                for (int d = 0; d < 4; d++) {
                    dark = monitor(ci, cj, d, dark, 1);
                }
                break;
        }
    }

    static int monitor(int i, int j, int d, int dark, int status) {
        // status가 -1이면 감시한 것으로 세팅, 1이면 감시하기 전으로 세팅
        while (true) {
            i += di[d];
            j += dj[d];
            if (i < 0 || i > N - 1 || j < 0 || j > M - 1 || map[i][j] == 6) break;
            if (map[i][j] < 1) {
                if (status == -1 && map[i][j] == 0) {
                    dark--;
                } else if (status == 1 && map[i][j] == -1) {
                    dark++;
                }
                map[i][j] += status;
            }
        }
        return dark;
    }

    static void debug(int dark) {
        System.out.println("dark = " + dark);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%2d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}