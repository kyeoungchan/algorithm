package swexpertacademy.processorconnecting;

import java.io.*;
import java.util.*;

public class Solution_d9_1767_프로세서연결하기_서울_20반_우경찬 {

    static int N, totalCores, leastLength, tempTotalLength;
    static int[] di = new int[]{-1, 0, 1, 0};
    static int[] dj = new int[]{0, 1, 0, -1};
    static List<int[]> coreInfo;
    static int[][] maxynos;
    static int tempUnconnectedCnt, totalUnconnectedCnt;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1767.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            maxynos = new int[N][N];
            coreInfo = new ArrayList<>();
            leastLength = Integer.MAX_VALUE;
            totalUnconnectedCnt = Integer.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    String s = st.nextToken();
                    if (s.equals("1")) {
                        maxynos[i][j] = Integer.parseInt(s);
                        if (!(i == 0 || i == N - 1 || j == 0 || j == N - 1))
                            coreInfo.add(new int[]{i, j}); // 좌표 정보 저장
                    }
                }
            }
            totalCores = coreInfo.size();
            for (int d = 0; d < 4; d++) {
                tempUnconnectedCnt = 0;
                tempTotalLength = 0;
                dfs(0, d);
            }
            sb.append("#").append(tc).append(" ").append(leastLength).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void dfs(int cnt, int d) {
        // 전선의 길이의 합이 이미 0이거나 이전에 측정한 연결되지 않은 프로세서의 개수를 이미 초과하려고 함과 동시에 최소 연결 길이의 합도 초과하려고 한다면 재귀 호출을 끊어낸다.
        if (leastLength == 0 || (tempUnconnectedCnt == totalUnconnectedCnt && tempTotalLength == leastLength)) {
            return;
        }

        if (cnt == totalCores) { // 모든 프로세서를 다 체크했을 때
            if (tempUnconnectedCnt < totalUnconnectedCnt) { // 만약 이전에 측정한 비연결 프로세서의 개수보다 더 작다면 업데이트
                totalUnconnectedCnt = tempUnconnectedCnt;
                leastLength = tempTotalLength; // 프로세서의 연결의 수가 더 높다면 연결의 길이의 합이 더 크고 작은 건 의미가 없다.
            } else if (tempUnconnectedCnt == totalUnconnectedCnt) { // 비연결 프로세서의 개수가 같다면 길이의 합이 더 최소인 것만 업데이트
                leastLength = Math.min(leastLength, tempTotalLength);
            }
            return;
        }

        int i = coreInfo.get(cnt)[0];
        int j = coreInfo.get(cnt)[1];
        if (i == 0 || i == N - 1 || j == 0 || j == N - 1) {
            // 가장자리에 있으면 이미 연결된 상태이므로 길이도 0으로 유지시키면서 다음 코어를 살피러 간다.
            for (int nd = 0; nd < 4; nd++) {
                dfs(cnt + 1, nd);
            }
        } else {
            int loopCnt = d == 0 ? i : d == 1 ? N - 1 - j : d == 2 ? N - 1 - i : j;
            // 위로 올라가야 한다면, i번만큼 반복해야 한다. 2번 인덱스라면 0, 1 인덱스를 칠해야 한다.
            // 오른쪽으로 가는 상황이라면 N - 1 - j만큼 반복해야 한다. N = 4, N - 1 = 3, j가 2라면, 1번만 칠한다.

            boolean isConnected = true;
            // 아직 연결된지 확인할 수 없는 상황이지만 연결이 불가하면 아래에서 break 탈출을 시킬 예정이므로 그때 다시 false로 지정하도록 한다.
            for (int k = 1; k < loopCnt + 1; k++) {
                int ni = i + di[d] * k;
                int nj = j + dj[d] * k;
                if (maxynos[ni][nj] == 0) { // 이동시키면서 1로 변경시켜준다.
                    maxynos[ni][nj] = 1;
                } else {
                    // 전선이 겹친다면, 다시 복구 시키고 다음 코어를 보러 간다.
                    for (int l = 1; l < k; l++) {
                        // k번째에서 아직 1로 바꾸지 않은 상태이므로 k-1번 반복하여 다시 0으로 복구시킨다.
                        maxynos[i + di[d] * l][j + dj[d] * l] = 0;
                    }
                    if (tempUnconnectedCnt + 1 > totalUnconnectedCnt) {
                        // 만약 이미 연결이 안 된 코어의 수를 초과했다면 더이상의 재귀호출은 무의미하다.
                        return;
                    }
                    isConnected = false; // 비연결을 표시하고 개수도 업데이트한다.
                    tempUnconnectedCnt++;
                    break;
                }
            }
            if (isConnected) { // 연결되었다면 길이도 업데이트한다.
                tempTotalLength += loopCnt;
            }
            for (int nd = 0; nd < 4; nd++) { // 그리고 새로운 사방 탐색을 위한 dfs
                dfs(cnt + 1, nd);
            }
            if (isConnected) { // 다음 재귀호출에 영향을 안 주기 위해서 리셋
                // 연결이 된 코어만 복구시키면 된다.
                tempTotalLength -= loopCnt;
                for (int k = 1; k < loopCnt + 1; k++) {
                    maxynos[i + di[d] * k][j + dj[d] * k] = 0;
                }
            } else { // 그냥 연결이 안 된 상태라면 영향을 안 주기 위해서 비연결 프로세서 갯수만 감소
                tempUnconnectedCnt--;
            }
        }
    }
}
