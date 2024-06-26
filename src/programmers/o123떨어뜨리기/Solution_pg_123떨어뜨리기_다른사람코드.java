package programmers.o123떨어뜨리기;

import java.util.*;

public class Solution_pg_123떨어뜨리기_다른사람코드 {

    public int[] solution(int[][] edges, int[] target) {
        int n = edges.length + 1;
        int T = 0;

        // 트리 생성
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++)
            tree[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            int parent = edges[i][0];
            int child = edges[i][1];
            tree[parent - 1].add(child - 1);
        }
        for (int i = 0; i < n; i++)
            Collections.sort(tree[i]);

        // 현재 노드가 몇 번 지나갔는지 체크
        int[] passCnt = new int[n];
        // 현재 노드가 보유하고 있는 숫자 개수
        int[] holdCnt = new int[n];
        // 체크한 노드인지 저장
        boolean[] checked = new boolean[n];
        // 리프 노드 저장
        List<Integer> leaves = new ArrayList<>();

        // 리프 노드이고 타겟에 수가 0보다 크다면 케이스에 추가
        for (int i = 0; i < n; i++)
            if (tree[i].isEmpty() && target[i] > 0) T++;

        while (T > 0) {
            // 루트 노드에서부터 시작
            int node = 0;

            while (!tree[node].isEmpty()) {
                node = tree[node].get(passCnt[node]++ % tree[node].size());
            }

            // 리프에 떨어진 숫자 개수 증가
            holdCnt[node]++;
            // 리프 노드를 도착 순서대로 저장
            leaves.add(node);

            // 현재 노드가 보유하고 있는 숫자 개수 > 노드의 target 값이라면 모든 수를 1로 변경해도 수를 만족시킬 수 없다.
            if (holdCnt[node] > target[node]) {
                return new int[]{-1};
            }

            // 노드의 target 값 <= 현재 보유하고 있는 숫자 개수 * 3이고, 방문하지 않았다면 만족
            if (!checked[node] && target[node] <= 3 * holdCnt[node]) {
                checked[node] = true;
                T--;
            }
        }

        List<Integer> res = new ArrayList<>();
        // 저장된 리프 노드들 탐색
        for (int leaf : leaves) {
            // 개수를 하나씩 줄여가면서 1,2,3을 작은 순서대로 대입
            holdCnt[leaf]--;
            for (int val = 1; val < 4; val++) {
                // val = 대입한 수라고 했을 때 target 값에서 val을 뺐을 때도 조건을 만족해야한다.
                // 조건 만족할 경우 정답에 추가
                if (holdCnt[leaf] <= target[leaf] - val && target[leaf] - val <= 3 * holdCnt[leaf]) {
                    res.add(val);
                    target[leaf] -= val;
                    break;
                }
            }
        }

        int[] answer = new int[res.size()];
        for (int i = 0; i < res.size(); i++) answer[i] = res.get(i);
        return answer;
    }
}
