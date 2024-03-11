package baekjoon.treeinvestmenttechnique;

import java.util.*;
import java.io.*;

/**
 * 일단 나무의 개수를 계속 하나의 변수로 관리하자!
 * 그리고 각 좌표마다 양분읠 정보를 담는 nMap을 관리하고,
 * 나무에 대한 정보를 id로 이차원 배열로 담고, 죽은 나무는 isDead 이차원 boolean 배열로 관리하자.
 * 그리고 각 자리마다 심어저 있는 이차원 리스트 배열을 관리하자.
 * 각 나무에 대한 필요 정보는 위치와 나이가 되겠다.
 * 위치는 1부터 시작하므로 통일한다.
 */
public class Solution_bj_16235_나무재테크_서울_20반_우경찬 {

    static int N, aliveTrees, nMap[][], A[][], di[] = {-1, -1, 0, 1, 1, 1, 0, -1}, dj[] = {0, 1, 1, 1, 0, -1, -1, -1};
    static List<int[]> treesInfo, deadTreeInfo;
    static List<Integer>[][] idsMap;
    static List<Boolean> isDead;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        nMap = new int[N + 1][N + 1];
        idsMap = new List[N + 1][N + 1];
        A = new int[N + 1][N + 1];

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < N + 1; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                nMap[i][j] = 5;
            }
        }
        treesInfo = new ArrayList<>();
        isDead = new ArrayList<>();

        aliveTrees = M; // 처음에는 M그루의 나무가 심어진다.

        treesInfo.add(null);
        isDead.add(null);
        for (int id = 1; id < M + 1; id++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            treesInfo.add(new int[]{id, z, x, y});
            isDead.add(false);
            if (idsMap[x][y] == null) {
                idsMap[x][y] = new ArrayList<>();
            }
            idsMap[x][y].add(id);
        }

        for (int i = 0; i < K; i++) {
            spring();
            summer();
            autumn();
            winter();
        }
        System.out.println(aliveTrees);
        br.close();
    }

    static void spring() {
        // 봄에는 나무가 자신의 나이만큼 양분을 먹고, 양분이 부족하면 죽는다.
//        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(t -> t[1])); // 나이가 적은 나무 순으로 pq에 담긴다.
        deadTreeInfo = new ArrayList<>(); // 죽은 나무에 대한 정보가 여름에 필요하기 때문에 일단 담는다.
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (idsMap[i][j] == null) continue;
                Collections.sort(idsMap[i][j], Comparator.comparing(id -> -treesInfo.get(id)[1]));
                for (int k = idsMap[i][j].size() - 1; k >= 0 ; k--) {
                    int cId = idsMap[i][j].get(k);
                    int cAge = treesInfo.get(cId)[1];
                    if (cAge <= nMap[i][j]) {
                        nMap[i][j] -= cAge;
                        treesInfo.get(cId)[1]++; // 나이 증가
                    } else {
                        isDead.set(cId, true); // 죽은 나무로 처리
                        deadTreeInfo.add(new int[]{cAge, i, j});
                        aliveTrees--;
                        idsMap[i][j].remove(k);
                    }
                }
            }
        }

    }

    static void summer() {
        for (int[] deadTree : deadTreeInfo) {
            int ci = deadTree[1];
            int cj = deadTree[2];
            int cAge = deadTree[0];
            nMap[ci][cj] += (cAge / 2);
        }
    }

    static void autumn() {
        for (int id = 1; id < treesInfo.size(); id++) {
            int[] treeInfo = treesInfo.get(id);
            int cAge = treeInfo[1];
            int ci = treeInfo[2];
            int cj = treeInfo[3];
            if (cAge % 5 == 0 && !isDead.get(id)) {
                for (int d = 0; d < 8; d++) {
                    int nId = treesInfo.size();
                    int ni = ci + di[d];
                    int nj = cj + dj[d];
                    if (ni < 1 || ni > N || nj < 1 || nj > N) continue;
                    if (idsMap[ni][nj] == null) idsMap[ni][nj] = new ArrayList<>();
                    idsMap[ni][nj].add(nId);
                    isDead.add(false);
                    aliveTrees++;
                    treesInfo.add(new int[]{nId, 1, ni, nj});
                }
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
