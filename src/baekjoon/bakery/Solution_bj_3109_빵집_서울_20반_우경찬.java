package baekjoon.bakery;

import java.util.*;
import java.io.*;

public class Solution_bj_3109_빵집_서울_20반_우경찬 {

    static int R, C, result, cnt;
    static int[] di = {-1, 0, 1};
    static char[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++)
                map[i][j] = s.charAt(j);
        }
        result = 0;
        cnt = 0;
		for (int i = 0; i < R; i++) {
			if (dfs(i, 0)) {
				result++;
			}
		}

        System.out.println(result);
        br.close();
    }

    static boolean dfs(int i, int j) {
		if (j == C - 1) {
			return true;
		}

		int nj = j + 1;
		for (int d = 0; d < 3; d++) {
			int ni = i + di[d];
			if (0 <= ni && ni < R && map[ni][nj] == '.') {
				map[ni][nj] = 'x';
				if (dfs(ni, nj)) {
					return true;
				}
			}
		}
		return false;
	}
}
