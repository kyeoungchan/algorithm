package swexpertacademy.수열편집;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * N개의 10억이하(int형) 자연수로 이루어진 수열
 * M번의 편집을 거쳐야한다.
 * 인덱스 L의 데이터를 출력하는 프로그램
 */
public class Solution_b_13501_수열편집_연결리스트 {

    static class Node {
        int num;
        Node next;

        public Node(int num, Node next) {
            this.num = num;
            this.next = next;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_b_13501.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine(), " ");

            Node head = new Node(N, null);
            Node tail = head;

            for (int i = 0; i < N; i++) {
                Node newNode = new Node(Integer.parseInt(st.nextToken()), null);
                tail.next = newNode;
                tail = newNode;
            }

            int idx = -1, data = -1;
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                char command = st.nextToken().charAt(0);
                Node previous = null;
                switch (command) {
                    case 'I':
                        idx = Integer.parseInt(st.nextToken());
                        data = Integer.parseInt(st.nextToken());
                        Node newNode = new Node(data, null);
                        previous = getNode(idx - 1, head);
                        newNode.next = previous.next;
                        previous.next = newNode;
                        break;
                    case 'D':
                        idx = Integer.parseInt(st.nextToken());
                        previous = getNode(idx - 1, head);
                        previous.next = previous.next.next;
                        break;
                    case 'C':
                        idx = Integer.parseInt(st.nextToken());
                        data = Integer.parseInt(st.nextToken());
                        Node changingNode = getNode(idx, head);
                        changingNode.num = data;
                        break;
                }
            }
            sb.append("#").append(tc).append(" ");
            Node getNode = getNode(L, head);
            if (getNode == null) sb.append(-1);
            else sb.append(getNode.num);
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static Node getNode(int idx, Node head) {
        if (idx == -1) return head;
        Node cur = head.next;
        int curIdx = 0;
        while (curIdx < idx) {
            cur = cur.next;
            if (cur == null) return null;
            curIdx++;
        }
        return cur;
    }
}
