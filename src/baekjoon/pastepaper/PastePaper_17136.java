package baekjoon.pastepaper;

import java.util.*;
import java.io.*;

public class PastePaper_17136 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] graph = new int[10][10];
        int[] papers = new int[]{5, 5, 5, 5, 5};
        StringTokenizer st;
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 10; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = 0;

        for (int paperSize = 5; paperSize > 0; paperSize--) {
            result += checkAndPaint(paperSize, graph, papers);
        }

        if (isThereOne(graph)) {
            result = -1;
        }
/*
        for (int i = 0; i < 10; i++) {
            System.out.println(Arrays.toString(graph[i]));
        }
        System.out.println(Arrays.toString(papers));
*/
        System.out.println(result);
    }

    static int checkAndPaint(int paperSize, int[][] graph, int[] papers) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (graph[i][j] == 1) {
                    if (canCover(paperSize, i, j, graph)) {
                        count++;
                        papers[paperSize - 1] -= 1;
                    }
                }
                if (papers[paperSize - 1] == 0) {
                    return count;
                }
            }
        }
        return count;
    }

    static boolean canCover(int paperSize, int i, int j, int[][] graph) {
        if (i + paperSize > 10 || j + paperSize > 10) {
            return false;
        }
        for (int newI = i; newI < i + paperSize; newI++) {
            for (int newJ = j; newJ < j + paperSize; newJ++) {
                if (graph[newI][newJ] == 0) {
                    return false;
                }
            }
        }
        for (int newI = i; newI < i + paperSize; newI++) {
            for (int newJ = j; newJ < j + paperSize; newJ++) {
                graph[newI][newJ] = 0;
            }
        }
        return true;
    }

    private static boolean isThereOne(int[][] graph) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (graph[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
