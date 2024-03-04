package baekjoon.gerrymandering;

import java.util.*;
import java.io.*;

public class Solution_bj_17471_게리맨더링_서울_20반_우경찬_2 {

    static int N, min;
    static int[] populations;
    static int[][] g;
    static boolean[] inAArea;
    static boolean divideSucceedOnce;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        populations = new int[N + 1];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < N + 1; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }
        g = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int adjCnt = Integer.parseInt(st.nextToken());
            for (int j = 0; j < adjCnt; j++) {
                int adjNum = Integer.parseInt(st.nextToken());
                g[i][adjNum] = 1;
            }
        }
        min = Integer.MAX_VALUE;
        inAArea = new boolean[N + 1];
        divideSucceedOnce = false;
        comb(1);
        if (divideSucceedOnce)
            System.out.println(min);
        else
            System.out.println(-1);
        br.close();
    }

    static void comb(int cnt) {
        if (cnt == N + 1) {
            if (possibleDivide()) {
                if (!divideSucceedOnce) divideSucceedOnce = true;
                int result = 0;
                for (int i = 1; i < N + 1; i++) {
                    if (inAArea[i]) {
                        result += populations[i];
                    } else {
                        result -= populations[i];
                    }
                }
                result = result < 0 ? -result : result;
                min = Math.min(min, result);
            }
            return;
        }

        inAArea[cnt] = true;
        comb(cnt + 1);
        inAArea[cnt] = false;
        comb(cnt + 1);

    }

    private static boolean possibleDivide() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for (int i = 1; i < N + 1; i++) {
            if (inAArea[i]) {
                a.add(i);
            } else {
                b.add(i);
            }
        }
        if (a.isEmpty() || b.isEmpty()) {
            return false;
        }

        return isAllConnected(a, inAArea, true) && isAllConnected(b, inAArea, false);
    }

    private static boolean isAllConnected(List<Integer> areaLands, boolean[] inAArea, boolean isAboutAArea) {
        boolean[] v = new boolean[N + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        v[areaLands.get(0)] = true;
        q.offer(areaLands.get(0));

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int connectedCity = 1; connectedCity < N + 1; connectedCity++) {
                if (g[cur][connectedCity] != 0 && !v[connectedCity] && ((isAboutAArea && inAArea[connectedCity]) || (!isAboutAArea && !inAArea[connectedCity]))) {
                    v[connectedCity] = true;
                    q.offer(connectedCity);
                }
            }
        }

        for (int a : areaLands) {
            if (v[a]) {
                // 아까 bfs로 탐색이 성공됐다면 다음 도시를 살펴본다.
                continue;
            }
            // 탐색이 실패했다면 연결이 되지 않은 도시라는 의미이므로 false 반환
            return false;
        }
        return true;
    }
}
