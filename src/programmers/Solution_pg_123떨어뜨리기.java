package programmers;

import java.util.*;

/*
트리 리프 노드에 숫자를 쌓으려고 한다.
부모가 자식 노드를 가리키는 단방향 간선
루트 노드에 1,2,3 중 하나를 떨어뜨린다.
숫자가 리프 노드에 도착한다면?
- 숫자가 지나간 노드는 현재 길로 연결된 자식 노드 다음으로 번호가 큰 자식 노드로 향하는 길만 연결된다.
- 현재 길이 가장 큰 노드라면 => 가장 작은 노드로 연결된다.
- 간선이 하나라면 그 간선만 계속 연결된다.
게임의 목표: 각각의 리프 노드에 쌓인 숫자의 합을 target에서 가리키는 값과 같게 만드는 것이다.
*/
public class Solution_pg_123떨어뜨리기 {
    static class Node {
        int number, pathIdx, sum;
        List<Integer> children;

        public Node(int number) {
            this.number = number;
            // pathIdx가 -1이면 리프노드
            pathIdx = -1;
            children = new ArrayList<>();
            sum = 0;
        }

        public void addChild(int childNumber) {
            if (isLeaf()) {
                // - 초기에는 자식 노드 중 가장 번호가 작은 노드를 가리키는 간선을 길로 설정한다.
                pathIdx = 0;
            }
            children.add(childNumber);
        }

        public boolean isLeaf() {
            return pathIdx == -1;
        }

        public int getChild() {
            int child = children.get(pathIdx);
            pathIdx = (pathIdx + 1) % children.size();
            return child;
        }

        public void setPathIdx(int pathIdx) {
            this.pathIdx = pathIdx;
        }

        @Override
        public String toString() {
            return "number: "  + number + ", children" + children;
        }
    }

    static class Candidate implements Comparable<Candidate> {
        int number, listSize;

        public Candidate(int number, List<Integer> list) {
            this.number = number;
            this.listSize = list.size();
        }

        @Override
        public int compareTo(Candidate o) {
            return listSize == o.listSize ? Integer.compare(number, o.number) : Integer.compare(listSize, o.listSize);
        }
    }

    static Node[] nodes;
    static List<Integer> leaves;
    static boolean complete;

    public int[] solution(int[][] edges, int[] target) {
        // target은 1번 노드는 0번 인덱스로 접근해야한다.
        nodes = new Node[target.length + 1];
        setNodes(edges);
        int[] answer = {};
        List<Integer> candidate1 = new ArrayList<>();
        complete = false;
        drop(1, target, candidate1);
        List<Integer> candidate2 = new ArrayList<>();
        complete = false;
        drop(2, target, candidate2);
        List<Integer> candidate3 = new ArrayList<>();
        complete = false;
        drop(1, target, candidate3);

        PriorityQueue<Candidate> pq = new PriorityQueue<>();
        if (candidate1.isEmpty()) pq.offer(new Candidate(1, candidate1));
        if (candidate2.isEmpty()) pq.offer(new Candidate(2, candidate2));
        if (candidate3.isEmpty()) pq.offer(new Candidate(3, candidate3));

        if (pq.isEmpty()) answer = new int[]{-1};
        else {
            List<Integer> answerList = pq.poll().list;
            answer = new int[answerList.size()];
            for (int i = 0; i < answer.length; i++) answer[i] = answerList.get(i);
        }
        return answer;
    }

    void setNodes(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {
            int parent = edges[i][0];
            if (nodes[parent] == null) nodes[parent] = new Node(parent);
            int child = edges[i][1];
            if (nodes[child] == null) nodes[child] = new Node(child);
            nodes[parent].addChild(child);
        }

        leaves = new ArrayList<>();

        for (int i = 1; i < nodes.length; i++) {
            Node cur = nodes[i];
            if (cur.isLeaf()) {
                leaves.add(cur.number);
                continue;
            }
            Collections.sort(cur.children);
        }

    }

    void drop(int number, int[] target, List<Integer> candidate) {
        Node cur = nodes[1];
        Map<Integer, Integer> nodeOriginalIdx = new HashMap<>();
        // 리프노드까지 내려가기
        while (!cur.isLeaf()) {
            nodeOriginalIdx.put(cur.number, cur.pathIdx);
            cur = nodes[cur.getChild()];
        }
        System.out.println("number: " + number + ", leaf: " + cur.number);
        System.out.println("leaf.sum: " + (cur.sum + number) + ", target: " + target[cur.number-1]);
        System.out.println();
        if (cur.sum + number > target[cur.number - 1]) {
            // 숫자 범위를 초과해서 가망이 없는 경우
            for (int nodeNumber: nodeOriginalIdx.keySet()) {
                nodes[nodeNumber].setPathIdx(nodeOriginalIdx.get(nodeNumber));
            }
            return;
        }
        boolean hasCompleteLocal = false;
        cur.sum += number;
        candidate.add(number);
        if (cur.sum == target[cur.number - 1]) {
            hasCompleteLocal = true;
            for (int leaf: leaves) {
                if (nodes[leaf].sum != target[leaf - 1]) {
                    hasCompleteLocal = false;
                    break;
                }
            }
            if (hasCompleteLocal) {
                complete = true;
                return;
            }
        }

        PriorityQueue<Candidate> pq = new PriorityQueue<>();
        drop(1, target, candidate);
        if (complete) {
            pq.offer(new Candidate(1, candidate));
            complete = false;
        }

        drop(2, target, candidate);
        if (complete) {
            pq.offer(new Candidate(2, candidate));
            complete = false;
        }
        if (drop(3, target) || drop(2, target) || drop(1, target)) {
            return true;
        }
        for (int nodeNumber: nodeOriginalIdx.keySet()) {
            nodes[nodeNumber].setPathIdx(nodeOriginalIdx.get(nodeNumber));
        }
        cur.sum -= number;
        numbers.remove(numbers.size() - 1);
        return false;
    }
}
