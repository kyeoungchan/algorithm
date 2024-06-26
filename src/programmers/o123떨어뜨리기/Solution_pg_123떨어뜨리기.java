package programmers.o123떨어뜨리기;

import java.util.*;


public class Solution_pg_123떨어뜨리기 {

    static class Node {
        int number, childIdx, sum;
        List<Integer> children;

        public Node(int number) {
            this.number = number;
            childIdx = -1;
            children = new ArrayList<>();
        }

        public void addChild(int childNumber) {
            if (isLeaf()) childIdx++;
            children.add(childNumber);
        }

        public boolean isLeaf() {
            return childIdx == -1;
        }

        public void sortChildren() {
            Collections.sort(children);
        }

        public int getChild() {
            // isLeaf()가 아닐 때만 호출되도록 메인 메서드에서 신경쓴다.
            int childNumber = children.get(childIdx);
            childIdx++;
            childIdx %= children.size();
            return childNumber;
        }

        @Override
        public String toString() {
            return "number: " + number + ", childIdx: " + childIdx + ", sum: " + sum + ", children: " + children;
        }
    }

    static Node[] nodes;
    static List<Integer> leaves, answerList;

    public int[] solution(int[][] edges, int[] target) {
        int[] answer = {};
        setNodes(edges, target);
        answerList = new ArrayList<>();
        ArrayList<Integer> candidate = new ArrayList<>();
        drop(candidate, target);
        if (answerList.isEmpty()) answer = new int[]{-1};
        else {
            answer = new int[answerList.size()];
            for (int i = 0; i < answerList.size(); i++)
                answer[i] = answerList.get(i);
        }
        return answer;
    }

    void setNodes(int[][] edges, int[] target) {
        // target은 1번부터 taget.length번까지의 노드 번호에 대해서 관리하므로 nodes는 사이즈+1로 생성
        nodes = new Node[target.length + 1];
        for (int[] edge : edges) {
            // edges[i]는 [부모 노드 번호, 자식 노드 번호] 형태로, 단방향으로 연결된 두 노드를 나타냅니다.
            int parent = edge[0];
            if (nodes[parent] == null) nodes[parent] = new Node(parent);
            int child = edge[1];
            if (nodes[child] == null) nodes[child] = new Node(child);
            nodes[parent].addChild(child);
        }
        leaves = new ArrayList<>();

        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i] == null) {
                nodes[i] = new Node(i);
                leaves.add(i);
                // 무조건 리프노드다. 위에 edges를 탐색하면서 초기화하지 않았으므로
                continue;
            }

            // 리프노드면 leaves에 추가
            if (nodes[i].isLeaf()) leaves.add(i);
                // 아니라면 자식 노드들을 오름차순 정렬
            else nodes[i].sortChildren();
        }
    }

    void drop(List<Integer> candidate, int[] target) {
        if (!answerList.isEmpty() && candidate.size() > answerList.size()) return;

        if (hasSucceed(target)) {
            updateAnswerList(candidate);
            return;
        }

        Map<Integer, Integer> previousIdx = new HashMap<>();
        Node cur = nodes[1];
        while (!cur.isLeaf()) {
            previousIdx.put(cur.number, cur.childIdx);
            int next = cur.getChild();
            cur = nodes[next];
        }

        // 이미 어떤 숫자도 넣을 수 없는 상황이라면 재귀호출 자체를 포기한다.
        int targetNumber = target[cur.number - 1];

        // 3 -> 2 -> 1 순서대로 넣어봤을 때를 가정한다.

        if (cur.sum + 3 <= targetNumber) {
            int lastIdx = candidate.size();
            cur.sum += 3;
            candidate.add(3);
            drop(candidate, target);
            cur.sum -= 3;
            candidate.remove(lastIdx);
        }

        if (cur.sum + 2 <= targetNumber) {
            int lastIdx = candidate.size();
            cur.sum += 2;
            candidate.add(2);
            drop(candidate, target);
            cur.sum -= 2;
            candidate.remove(lastIdx);
        }

        if (cur.sum + 1 <= targetNumber) {
            int lastIdx = candidate.size();
            cur.sum += 1;
            candidate.add(1);
            drop(candidate, target);
            cur.sum -= 1;
            candidate.remove(lastIdx);
        }

        for (int pathNumber : previousIdx.keySet()) {
            nodes[pathNumber].childIdx = previousIdx.get(pathNumber);
        }
    }

    boolean hasSucceed(int[] target) {
        for (int leaf : leaves) {
            if (nodes[leaf].sum != target[leaf - 1]) return false;
        }
        return true;
    }

    void updateAnswerList(List<Integer> candidate) {
        if (answerList.isEmpty()) {
            answerList.addAll(candidate);
            return;
        }
        // 일단 candidate가 빈 리스트로 올 리는 없도록 가정한다.
        if (answerList.size() > candidate.size()) {
            answerList.clear();
            answerList.addAll(candidate);
        } else if (answerList.size() == candidate.size()) {
            for (int i = 0; i < answerList.size(); i++) {
                if (answerList.get(i) == candidate.get(i)) continue;
                    // 같은 숫자들이라면 넘어간다.
                else if (answerList.get(i) > candidate.get(i)) break;
                    // 이미 지정된 answerList에서 큰 애가 먼저 나왔다면 업데이트 필요
                else return;
                // 후보 리스트에서 큰 애가 먼저 나왔다면 업데이트할 필요 X
            }
            answerList.clear();
            answerList.addAll(candidate);
        }
    }
}