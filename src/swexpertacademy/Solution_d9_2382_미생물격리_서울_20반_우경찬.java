package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_2382_미생물격리_서울_20반_우경찬 {


    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2382.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int[] di = {0, -1, 1, 0, 0}, dj = {0, 0, 0, -1, 1};

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][N];

            int totalMic = 0;
            int[][] microorganisms = new int[K + 1][4];
            boolean[] isMerged = new boolean[K + 1];
            boolean[] needUpdate = new boolean[K + 1];
            for (int id = 1; id < K + 1; id++) {
                st = new StringTokenizer(br.readLine(), " ");
                microorganisms[id][0] = Integer.parseInt(st.nextToken());
                microorganisms[id][1] = Integer.parseInt(st.nextToken());
                map[microorganisms[id][0]][microorganisms[id][1]] = id;
                microorganisms[id][2] = Integer.parseInt(st.nextToken()); // 미생물의 수
                totalMic += microorganisms[id][2];
                microorganisms[id][3] = Integer.parseInt(st.nextToken()); // 이동방향
            }

            int[] tempCnt = new int[K + 1];

            for (int time = 0; time < M; time++) {
                for (int id = 1; id < K + 1; id++) {
                    if (isMerged[id]) continue;
                    int pi = microorganisms[id][0];
                    int pj = microorganisms[id][1];
                    map[pi][pj] = 0;
                    int d = microorganisms[id][3];
                    int ni = pi + di[d];
                    int nj = pj + dj[d];
                    int nCnt = microorganisms[id][2];
                    int nd = d;

                    if (ni == 0 || ni == N - 1 || nj == 0 || nj == N - 1) {
                        nCnt /= 2;
                        totalMic -= (microorganisms[id][2] - nCnt);
                        nd = d % 2 == 0 ? d - 1 : d + 1;
                    }
                    microorganisms[id][0] = ni;
                    microorganisms[id][1] = nj;
                    microorganisms[id][2] = nCnt;
                    microorganisms[id][3] = nd;
                }

                for (int id = 1; id < K + 1; id++) {
                    if (isMerged[id]) continue;
                    int i = microorganisms[id][0];
                    int j = microorganisms[id][1];
                    tempCnt[id] = microorganisms[id][2];
                    if (map[i][j] == 0) {
                        map[i][j] = id;
                    } else {
                        int existingId = map[i][j];
                        int totalCnt = tempCnt[existingId] + tempCnt[id];

                        if (microorganisms[existingId][2] < microorganisms[id][2]) {
                            map[i][j] = id;
                            tempCnt[id] = totalCnt;
                            isMerged[existingId] = true;
                            needUpdate[id] = true;
                        } else {
                            tempCnt[existingId] = totalCnt;
                            isMerged[id] = true;
                            needUpdate[existingId] = true;
                        }
                    }
                }

                for (int id = 1; id < K + 1; id++) {
                    if (isMerged[id] || !needUpdate[id]) continue;
                    microorganisms[id][2] = tempCnt[id];
                    needUpdate[id] = false;
                }
            }
            sb.append("#").append(tc).append(" ").append(totalMic).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
