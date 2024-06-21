package swexpertacademy.영준이의진짜BFS;

import java.io.*;
import java.util.*;

public class Solution_d6_1855_영준이의진짜BFS_다른사람코드 {

    static class KidNode {
        int num;
        KidNode next;

        KidNode(int num) {
            this.num = num;
            next = null;
        }
    }

    static class Node {
        int num;
        int parent;
        int nextNum;
        int distance;
        int depth;
        KidNode pointingKidNode;
        KidNode endKidNode;

        Node(int num) {
            this.num = num;
        }

        Node(int num, int parent) {
            this.parent = parent;
            this.num = num;
        }

        void addKid(int num) {
            if (pointingKidNode == null) {
                pointingKidNode = new KidNode(num);
                endKidNode = pointingKidNode;
            } else {
                endKidNode.next = new KidNode(num);
                endKidNode = endKidNode.next;
            }
        }

        void setSeq() {
            if (pointingKidNode == null) return;
            while (true) {
                seqArray[plus_index++] = pointingKidNode.num;
                if (pointingKidNode.next == null) return;
                else pointingKidNode = pointingKidNode.next;
            }
        }
    }

    static int plus_index;
    static int[] seqArray = new int[100000];
    //plus_index를 이용해서 자기 자식들을 seq_array에 추가한다.
    //present_index를 이동하여 다음 인덱스로 간다.
    //
    static int N;
    static long result;
    static Node[] nodes = new Node[100001];

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d6_1855.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        seqArray[0] = 1;
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            nodes[1] = new Node(1);
            nodes[1].depth = 0;
            st = new StringTokenizer(br.readLine().trim());
            for (int n = 2; n <= N; n++) {
                int parent = Integer.parseInt(st.nextToken());
                nodes[n] = new Node(n, parent);
                nodes[parent].addKid(n);
                nodes[n].depth = nodes[parent].depth + 1;
            }
            plus_index = 1;
            result = 0;
            int max = 0;
            boolean start = false;
            for (int i = 0; i < N - 1; i++) {
                int a = seqArray[i];
                nodes[a].setSeq();
                // seqArray는 탐색해야하는 노드들의 숫자들을 순서대로 담은 배열이다.
                int b = seqArray[i + 1];
                if (nodes[a].depth < nodes[b].depth) {
                    if (nodes[b].parent == a) result++;
                    else result += (max + 1);
                    max = 0;
                    start = false;
                } else {
                    int parentA = nodes[a].parent;
                    int parentB = nodes[b].parent;
                    if (parentA == parentB) {
                        result += 2;
                        nodes[a].nextNum = b;
                        nodes[b].distance = 2;
                        if (!start && nodes[a].pointingKidNode != null) start = true;
                        if (start && max < 2) {
                            max = 2;
                        }
                    } else {
                        int tempMax = 0;
                        while (parentA != parentB) {
                            parentA = nodes[parentA].nextNum;
                            if (tempMax < nodes[parentA].distance) {
                                tempMax = nodes[parentA].distance;
                            }
                        }
                        int plus = tempMax + 2;
                        result += plus;
                        nodes[a].nextNum = b;
                        nodes[b].distance = plus;
                        if (!start && nodes[a].pointingKidNode != null) start = true;
                        if (start && max < plus) {
                            max = plus;
                        }
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
