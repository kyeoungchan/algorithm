package baekjoon.treeinvestmenttechnique;

import java.util.*;
import java.io.*;

public class Solution_bj_16235_나무재테크_서울_20반_우경찬 {

    static int N, nMap[][], A[][], di[] = {-1, -1, 0, 1, 1, 1, 0, -1}, dj[] = {0, 1, 1, 1, 0, -1, -1, -1};
    static ArrayDeque<int[]> treesInfo;
    static List<int[]> deadTreesInfo, parentTrees;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        nMap = new int[N + 1][N + 1];
        A = new int[N + 1][N + 1];

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < N + 1; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                nMap[i][j] = 5;
            }
        }
        treesInfo = new ArrayDeque<>();

        List<int[]> tempList = new ArrayList<>();
        for (int id = 1; id < M + 1; id++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            tempList.add(new int[] {x, y, z});
        }
        Collections.sort(tempList, Comparator.comparingInt(t->t[2]));
        treesInfo = new ArrayDeque<>(tempList);

        for (int i = 0; i < K; i++) {
            spring();
            summer();
            autumn();
            winter();
        }
        System.out.println(treesInfo.size());
        br.close();
    }

    static void spring() {
        // 봄에는 나무가 자신의 나이만큼 양분을 먹고, 양분이 부족하면 죽는다.
        deadTreesInfo = new ArrayList<>(); // 죽은 나무에 대한 정보가 여름에 필요하기 때문에 일단 담는다.
        parentTrees = new ArrayList<>(); // 부모 나무에 대한 정보들도 담는다.
        int loopCnt = treesInfo.size();
        for (int i = 0; i < loopCnt; i++) {
            int[] cur = treesInfo.poll();
            int ti = cur[0];
            int tj = cur[1];
            int tAge = cur[2];
            if (tAge <= nMap[ti][tj]) {
                nMap[ti][tj] -= tAge;
                treesInfo.offer(new int[] {ti, tj, tAge + 1});
                if ((tAge + 1) % 5 == 0) {
                    parentTrees.add(new int[] {ti, tj, tAge + 1});
                }
            } else {
                deadTreesInfo.add(cur);
            }
        }
    }

    static void summer() {
        for (int[] deadTree : deadTreesInfo) {
            int ci = deadTree[0];
            int cj = deadTree[1];
            int cAge = deadTree[2];
            nMap[ci][cj] += (cAge / 2);
        }
    }

    static void autumn() {
        for (int i = 0; i < parentTrees.size(); i++) {
            int[] treeInfo = parentTrees.get(i);
            int ci = treeInfo[0];
            int cj = treeInfo[1];
            for (int d = 0; d < 8; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 1 || ni > N || nj < 1 || nj > N) continue;
                treesInfo.offerFirst(new int[]{ni, nj, 1});
            }
        }
    }

    static void winter() {
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                nMap[i][j] += A[i][j];
            }
        }
    }
}