package swexpertacademy.a0112d4;

import java.io.*;
import java.util.*;

public class Solution7733 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_7733.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());
            int ans = 1;
            int[][] cheeze = new int[n][n];
            int maxDay = 0;
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    cheeze[i][j] = Integer.parseInt(st.nextToken());
                    if (maxDay < cheeze[i][j]) {
                        maxDay = cheeze[i][j];
                    }
                }
            }

            boolean[][] eaten = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    eaten[i][j] = false;
                }
            }

            if (maxDay > 100)
                maxDay = 100;
            for (int i = 1; i <= maxDay; i++) {
                eatCheeze(i, cheeze, eaten, n);
                int newRestCheeze = countRestCheeze(cheeze, n, eaten);
                ans = newRestCheeze > ans ? newRestCheeze : ans;
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void eatCheeze(int day, int[][] cheeze, boolean[][] eaten, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cheeze[i][j] == day)
                    eaten[i][j] = true;
            }
        }
    }

    private static int countRestCheeze(int[][] cheeze, int n, boolean[][] eaten) {
        int count = 0;
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = false;
            }
        }

        int[][] directions = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        Queue<List<Integer>> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!eaten[i][j] && !visited[i][j]) {
                    queue.add(Arrays.asList(i,j));
                    visited[i][j] = true;
                    count++;
                    while (!queue.isEmpty()) {
                        List<Integer> nextPosition = queue.poll();

                        for (int[] d:directions) {
                            int newI = nextPosition.get(0) + d[0];
                            int newJ = nextPosition.get(1) + d[1];
                            if (newI >= 0 && newI < n && newJ >= 0 && newJ < n) {
                                if (!visited[newI][newJ] && !eaten[newI][newJ]) {
                                    visited[newI][newJ] = true;
                                    queue.add(Arrays.asList(newI,newJ));
                                }
                            }
                        }
                    }

                }
            }
        }
        return count;
    }
}