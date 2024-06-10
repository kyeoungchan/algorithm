package codetree.색깔트리;

import java.io.*;
import java.util.*;

/**
 * 각 노드는 특정 색깔 color와 최대 깊이 max 속성을 갖고 있다.
 * 처음에는 아무 노드도 존재하지 않는다.
 * 1. 노드 추가
 * 각 노드는 고유 번호, 부모노드 번호, 색깔, 그리고 최대 깊이를 가진다.
 * color: 1~5(빨주노초파)
 * pi가 -1이면 새로운 트리의 루트 노드
 * max: 서브트리의 최대 깊이. 자기 자신에 해당하는 깊이는 1
 * max를 위배하게 추가 명령어가 주어진다면 추가하지 않는다.
 * 노드가 추가될 때마다 자신의 아래로 깊이가 몇까지 가능한지를 업데이트한 것으로 추가된다. Math.min(부모 가능 깊이 - 1, max - 1)
 * 따라서 자신의 부모의 가능 깊이가 0이라면 추가될 수 없다.
 * 2. 색깔 변경
 * 세그먼트트리
 * 3. 색깔 조회
 * 세그먼트트리
 * 4. 점수 조회
 * 모든 노드의 가치의 제곱의 합을 출력한다.
 * 각 노드의 가치: 본인을 포함하는 서브트리 내 서로 다른 색깔의 수
 */
public class Solution_ct_색깔트리 {

    static class Node {
        int id, capacityChildren, color;
        long value;

        public Node(int id, int capacityChildren, int color) {
            this.id = id;
            this.capacityChildren = capacityChildren;
            this.color = color;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int Q = Integer.parseInt(br.readLine());
        int idx = -1;
        Node[] nodes = new Node[Q];
        for (int tc = 0; tc < Q; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int command = Integer.parseInt(st.nextToken());
            switch (command) {
                case 100:
                    int mId = Integer.parseInt(st.nextToken());
                    int pId = Integer.parseInt(st.nextToken());
                    int color = Integer.parseInt(st.nextToken());
                    int maxDepth = Integer.parseInt(st.nextToken());
                case 200:
                case 300:
                case 400:
            }
        }
        br.close();
    }
}
