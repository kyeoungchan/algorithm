package swexpertacademy.영준이의진짜BFS;

import java.io.*;
import java.util.*;

public class Solution_d6_1855_영준이의진짜BFS {

    static class Node {
        int parent, depth;
        List<Integer> children = new ArrayList<>();

        public Node() {
            parent = 0;
            depth = 0;
        }

        public Node(int parent) {
            this.parent = parent;
            this.depth = nodes.get(parent).depth + 1;
        }
    }

    static Map<Long, Integer> cache;
    static List<Node> nodes;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d6_1855.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T  = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {

            cache = new HashMap<>();
            nodes = new ArrayList<>();
            nodes.add(new Node());

            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int i = 2; i < N + 1; i++) {
                int parent = Integer.parseInt(st.nextToken());
                nodes.add(new Node(parent));
                nodes.get(parent).children.add(i);
            }

            ArrayDeque<Integer> q = new ArrayDeque<>();
            q.offer(0);
            int to = 0;
            long cnt = 0;

            while (!q.isEmpty()) {
                int id = q.poll();
                int len = nodes.get(id).children.size();
                int lca = findLCA(id, to);

                cnt += nodes.get(to).depth - nodes.get(lca).depth;
                cnt += nodes.get(id).depth - nodes.get(lca).depth;

                to = id;
                for (int i = 0; i < len; i++) {
                    q.offer(nodes.get(id).children.get(i));
                }
            }
            sb.append("#").append(tc).append(" ").append(cnt).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static int findLCA(int a, int b) {
        if (a == b) return a;

        int depthA = nodes.get(a).depth;
        int depthB = nodes.get(b).depth;

        if (depthA > depthB) {
            int temp = a;
            a = b;
            b = temp;
            temp = depthA;
            depthA = depthB;
            depthB = temp;
        }

        while (depthA < depthB) {
            b = nodes.get(b).parent;
            depthB--;
        }

        return findLCA2(a, b);
    }

    static int findLCA2(int a, int b) {
        if (a == b) return a;

        long key = (long)a * 100000 + (long)b;
        if (cache.containsKey(key)){
            return cache.get(key);
        }

        a = nodes.get(a).parent;
        b = nodes.get(b).parent;

        int result = findLCA2(a, b);

        cache.put(key, result);

        return result;
    }
}
