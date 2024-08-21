package mincoding.퍼즐맞추기;

import java.io.*;
import java.util.*;

/**
 * NXN 칸으로 표현
 * 직소퍼즐 조각ㄱ은 1개당 3x3을 채울 수 있다.
 * 방향을 돌리지 않는다.
 * 왼위를 우선적으로 채운다.
 */
public class Solution_min_퍼즐맞추기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Map<Integer, List<Integer>> map = new HashMap<>();
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N - 3; i++) {
            for (int j = 0; j < N - 3; j++) {
                int hash = 0;
                int cipher = 1;
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        hash += (board[i + r][j + c] - 1) * cipher;
                        cipher *= 10;
                    }
                }
                if (!map.containsKey(hash)) map.put(hash, new LinkedList<>());
                map.get(hash).add(i * N + j);
            }
        }

        boolean[][] covered = new boolean[N][N];
        int result = 0;
        for (int i = 0; i < M; i++) {
            int hash = 0;
            int cipher = 1;
            for (int r = 0; r < 3; r++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int c = 0; c < 3; c++) {
                    hash += (Integer.parseInt(st.nextToken()) - 1) * cipher;
                    cipher *= 10;
                }
            }
            if (!map.containsKey(hash) || map.get(hash).isEmpty()) continue;

            for (int j = 0; j < map.get(hash).size(); j++) {
                boolean canPut = true;
                int pos = map.get(hash).get(j);
                int r = pos / N;
                int c = pos % N;

                flag: for (int nr = r; nr < r + 3; nr++) {
                    for (int nc = c; nc < c + 3; nc++) {
                        if (covered[nr][nc]) {
                            canPut = false;
                            break flag;
                        }
                    }
                }

                if (canPut) {
                    result++;
                    map.get(hash).remove(0);
                    for (int nr = r; nr < r + 3; nr++) {
                        for (int nc = c; nc < c + 3; nc++)
                            covered[nr][nc] = true;
                    }
                    break;
                }
            }

        }
        System.out.println(result);
        br.close();
    }
}
