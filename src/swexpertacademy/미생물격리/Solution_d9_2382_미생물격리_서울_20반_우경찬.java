package swexpertacademy.미생물격리;

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
                // 미생물의 좌표
                microorganisms[id][0] = Integer.parseInt(st.nextToken());
                microorganisms[id][1] = Integer.parseInt(st.nextToken());
                map[microorganisms[id][0]][microorganisms[id][1]] = id; // 지도에 미생물의 id 찍어놓기
                microorganisms[id][2] = Integer.parseInt(st.nextToken()); // 미생물의 수
                totalMic += microorganisms[id][2]; // 총 미생물의 수
                microorganisms[id][3] = Integer.parseInt(st.nextToken()); // 이동방향
            }

            int[] tempCnt = new int[K + 1];

            for (int time = 0; time < M; time++) {
                for (int id = 1; id < K + 1; id++) {
                    // 이동에 관한 반복문
                    if (isMerged[id]) continue; // 합병된 미생물에 대해서는 건너뛴다.
                    int pi = microorganisms[id][0];
                    int pj = microorganisms[id][1];
                    map[pi][pj] = 0; // 일단 지도에서 지우기만 하고 지도에 다시 id를 채워넣는 작업은 아래에서 수행한다.
                    int d = microorganisms[id][3];
                    int ni = pi + di[d];
                    int nj = pj + dj[d];
                    int nCnt = microorganisms[id][2]; // 일단 new Count를 현재와 동이랗게 한다.
                    int nd = d; // 방향도 일단 동일하게 한다.

                    if (ni == 0 || ni == N - 1 || nj == 0 || nj == N - 1) {
                        // 만약 약을 친 곳으로 갔다면
                        nCnt /= 2; // 절반으로 줄이고,
                        totalMic -= (microorganisms[id][2] - nCnt); // 이전의 한 집단의 미생물의 수에서 절반을 뺀 값만큼 totalMic에서 뺀다.
                        nd = d % 2 == 0 ? d - 1 : d + 1; // 방향이 바뀌어야하는데, 1 -> 2, 2 -> 1 / 3 -> 4, 4 -> 3으로 바뀌어야 한다.
                    }
                    // 바뀐값들 전원 세팅
                    microorganisms[id][0] = ni;
                    microorganisms[id][1] = nj;
                    microorganisms[id][2] = nCnt;
                    microorganisms[id][3] = nd;
                }

                for (int id = 1; id < K + 1; id++) {
                    // 병합에 관한 반복문
                    if (isMerged[id]) continue; // 합병된 집단은 건너뛴다.
                    int i = microorganisms[id][0];
                    int j = microorganisms[id][1];
                    tempCnt[id] = microorganisms[id][2]; // 일단 현재 집단의 수를 임시 저장한다.
                    if (map[i][j] == 0) {
                        // 아직 그 자리에 다른 집단이 없다면 그 자리에 세팅한다.
                        map[i][j] = id;
                    } else {
                        // 그 자리에 다른 집단이 있다면
                        int existingId = map[i][j];
                        int totalCnt = tempCnt[existingId] + tempCnt[id];
                        // 최종적으로 합쳐질 수를 저장한다.

                        if (microorganisms[existingId][2] < microorganisms[id][2]) {
                            // 원래 왔던 애들보다 새로 온 애들이 더 많다면
                            map[i][j] = id;
                            tempCnt[id] = totalCnt; // 아직 뒤에 더 오는 애들이 있기 때문에, 합병된 수는 따로 보관해야 한다. 예를 들어 1, 2 중 경쟁해서 2가 자리를 차지했더라도 아직 반복문이 끝나기 전까지는 동일 시간대이기 때문에, 3이 왔을 때 1과 2가 합쳐진 것보다 더 작은 것으로 계산이 되면 안 된다. 즉, 1, 2, 3이 합쳐지면 3이 차지해야지, 2가 차지하면 안 된다는 뜻이다.
                            isMerged[existingId] = true;
                            needUpdate[id] = true; // 위에 적은 주석을 위해서 업데이트가 나중에 필요함을 알린다.
                        } else {
                            tempCnt[existingId] = totalCnt;
                            isMerged[id] = true;
                            needUpdate[existingId] = true;
                        }
                    }
                }

                for (int id = 1; id < K + 1; id++) {
                    if (isMerged[id] || !needUpdate[id]) continue; // 합병되지도 않았고, 업데이트가 필요하다면
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
