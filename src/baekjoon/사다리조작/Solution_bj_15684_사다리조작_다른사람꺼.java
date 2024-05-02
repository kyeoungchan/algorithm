package baekjoon.사다리조작;

import java.io.*;
import java.util.*;

/*

https://www.acmicpc.net/source/41137243
-> 순수 DFS로 풀어서 500ms 가 나옴

https://www.acmicpc.net/source/17374138
-> 코드 참고하여 어떻게 개선할 수 있는지 확인

*/

public class Solution_bj_15684_사다리조작_다른사람꺼 {

    static int N;
    static int M;
    static int H;

    static int[][] map;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H + 1][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");

            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // 개선점 1 : index 대신 1, -1을 써서 가는 방향만 저장
            map[a][b] = 1;
            map[a][b + 1] = -1;
        }

        // 개선점 2 : 어떤 사다리가 자기의 위치로 돌아가기 위해서는
		/* 반드시 사다리 사이에 있는 연결다리의 수가 짝수여야 한다
		 	이 문제에서 다리의 수는 최대 3개까지 놓을 수 있으므로
		 	사다리 사이 다리가 홀수인 곳이 3군데 이상이면, 실패이다.
		 */
        if (betweenOdd() > 3) {
            System.out.print(-1);
            return;
        }

        // 최대 3개까지 다리를 놓으면서 체크한다.
        for (int i = 0; i <= 3; i++) {
            // 안에서 true를 return했으면 종료하면 된다
            if (combination(0, 0, i)) return;
        }

        System.out.print(-1);
    }

    /**
     * 조합 뽑기
     */
    public static boolean combination(int cnt, int start, int limit) {
        // 기저조건 : 모두 뽑은 경우
        if (cnt == limit) {
            // 조건을 만족했다면, 출력 후 즉시 종료
            if (isBridgeDone()) {
                System.out.print(cnt);
                return true;
            }

            return false;
        }

        // 세로선, 가로선을 따라서 순차적으로 고르기
        for (int i = start; i < N * H; i++) {

            int sr = i / N;
            int sc = i % N;

            // 개선점 3 : 조건이 간단해져서 따로 함수를 호출하면 비효율적

            if (sc == N - 1) continue; // 맨 오른쪽에서는 다리를 이을 수 없다

            // 위치 기준 오른쪽으로 다리를 이으므로, 오른쪽 값도 체크한다
            if (map[sr][sc] != 0 || map[sr][sc + 1] != 0) continue;

            // 개선점 4 : 여기에서 map을 수정해서 다리 체크 조건을 더 간단하게 만든다.
            map[sr][sc] = 1;
            map[sr][sc + 1] = -1;
            if (combination(cnt + 1, i + 1, limit)) return true;
            map[sr][sc] = map[sr][sc + 1] = 0;
        }

        return false;
    }

    /**
     * 초기 사다리 사이의 다리가 홀수인부분의 개수를 센다
     */
    public static int betweenOdd() {
        int odd = 0;
        // 사다리 개수가 N이면 사다리 사이는 N - 1
        for (int n = 0; n < N - 1; n++) {
            int temp = 0;
            // 사다리를 따라 내려가면서 연결된것의 개수를 센다
            for (int row = 0; row < H; row++) {
                if (map[row][n] == 1) temp++;
            }

            if (temp % 2 == 1) odd++;
        }

        return odd;
    }

    /**
     * 다리가 조건을 만족하는지 체크
     */
    public static boolean isBridgeDone() {
        for (int n = 0; n < N; n++) {
            // 어떤 한 세로선에 대하여  바닥에 닿을때까지 진행
            int curN = n;
            int curH = 0;

            while (curH < H) {
                // 개선점 5 : 1, -1을 써서 조건이 훨신 간단해진다
                if (map[curH][curN] == 1) curN++;
                else if (map[curH][curN] == -1) curN--;
                curH++; // 아래론 무조건 내려간다
            }

            // 개선점 6 : 여기에서 map 원상복구를 안하니, 조건이 줄어서 즉시 return이 가능하다
            // 바닥에 닿았을 때, 사다리가 다르다면 false
            if (n != curN) return false;
        }

        return true;
    }

}

/*

System.exit 썼을 때 -> 112ms

백트래킹으로 return true, false 이용했을 때

테스트하려고 쓴거임 오해 ㄴㄴ

*/

