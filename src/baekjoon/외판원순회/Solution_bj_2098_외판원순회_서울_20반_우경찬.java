package baekjoon.외판원순회;

import java.util.*;
import java.io.*;

public class Solution_bj_2098_외판원순회_서울_20반_우경찬 {

    static int N, W[][], dp[][], ANS, INF = 987654321;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp 정보 - 첫번째 열: 지금 도착한 도시, 두번째 열: 지금까지 방문했던 도시, 값: 총비용
        dp = new int[N][1 << N];
        for (int i = 0; i < N; i++) {
            // 아직 방문해보지 않은 도시들은 모두 -1로 초기화
            Arrays.fill(dp[i], -1);
        }
        ANS = Integer.MAX_VALUE;

        // 현재 노드, 현재까지 가중치의 합, 현재 방문한 노드들
        System.out.println(travel(0, 1));
        br.close();
    }

    // city: 현재 방문하는 도시, visited: 현재까지 방문한 도시들 비트
    static int travel(int city, int visited) {
        if (visited == (1 << N) - 1) {
            if (W[city][0] == 0) {
                return INF;
            }
            return W[city][0];
        }

        // 이미 방문해본 적이 있으면 굳이 새롭게 for문을 돌릴 필요없이 메모이제이션한 값을 반환한다.
        if (dp[city][visited] != -1) {
            return dp[city][visited];
        }

        // 연산의 속도를 최적화시키기 위해 처리를 시작할 때 INF로 늘려준다.
        dp[city][visited] = INF;

        for (int i = 0; i < N; i++) {
            // 만약 city에서 i번 도시로 갈 수 없는 경우이거나, 현재 i번 도시를 이미 방문했다면 건너뛰기
            if (W[city][i] == 0 || (visited & (1 << i)) != 0) continue;
            // city에서 i번 도시까지 가는 비용 + i번 도시에서 0번 도시로 갈 때까지 더 순회했을 때의 최소비용의 합의 최솟값
            dp[city][visited] = Math.min(dp[city][visited], travel(i, visited | (1 << i)) + W[city][i]);
        }
        return dp[city][visited];
    }
}
