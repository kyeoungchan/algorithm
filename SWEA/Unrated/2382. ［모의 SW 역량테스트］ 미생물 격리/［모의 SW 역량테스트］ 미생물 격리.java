import java.util.*;
import java.io.*;

// Solution_d9_2382_미생물격리_서울_20반_우경찬이 더 성능이 좋습니다.


/**
 * K개의 미생물 군집
 * 정사각형셀: NxN
 * 가장자리에는 약품이 칠해져있다.
 * M시간 후 살아남은 미생물의 총합
 */
public class Solution {

    static class Microscope {
        int id;
        int r;
        int c;
        int cnt;
        int d;
        int preCnt;

        public Microscope(int id, int r, int c, int cnt, int d) {
            this.id = id;
            this.r = r;
            this.c = c;
            this.cnt = cnt;
            this.d = d;
            preCnt = cnt;
        }
    }

    static int N, M, K, answer;
    static int[] di = {-1, 1, 0, 0}, dj = {0, 0, -1, 1};
    static int[][] map;
    static List<Microscope> microscopes;
    static boolean[] merged;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            microscopes = new ArrayList<>(K + 1);
            microscopes.add(null);
            merged = new boolean[K + 1];
            answer = 0;
            for (int id = 1; id < K + 1; id++) {
                st = new StringTokenizer(br.readLine(), " ");
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                int cnt = Integer.parseInt(st.nextToken());
                answer += cnt;
                int d = Integer.parseInt(st.nextToken()) - 1;
                Microscope newM = new Microscope(id, r, c, cnt, d);
                microscopes.add(newM);
                map[r][c] = id;
            }

            for (int time = 0; time < M; time++) {
                int[][] newMap = new int[N][N];
                for (int id = 1; id < microscopes.size(); id++) {
                    if (merged[id]) continue;
                    Microscope m = microscopes.get(id);
                    m.r += di[m.d];
                    m.c += dj[m.d];
                    if (m.r == 0 || m.r == N - 1 || m.c == 0 || m.c == N - 1) {
                        // 약품에 간 경우
                        m.d = m.d % 2 == 0 ? m.d + 1 : m.d - 1;
                        m.cnt /= 2;
                        answer -= (m.preCnt - m.cnt);
//                        m.preCnt = m.cnt;
                        if (m.cnt != 0) {
                            // 군집이 1이면 0으로 바뀔 수 있다.
                            newMap[m.r][m.c] = id;
                        } else merged[id] = true;
                    } else if (newMap[m.r][m.c] == 0) {
                        newMap[m.r][m.c] = id;
                    } else {
                        // 다른 군집들이 겹쳐서 합쳐지는 경우
                        Microscope original = microscopes.get(newMap[m.r][m.c]);
                        if (original.preCnt > m.cnt) {
                            original.cnt += m.cnt;
                            merged[id] = true;
                        } else {
                            // 군집의 개체 수가 동일하게 겹치는 경우는 주어지지 않는댔다.
                            m.cnt += original.cnt;
                            newMap[m.r][m.c] = id;
                            merged[original.id] = true;
                        }
                    }
                }
                for (int id = 1; id < microscopes.size(); id++) {
                    Microscope m = microscopes.get(id);
                    m.preCnt = m.cnt;
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}