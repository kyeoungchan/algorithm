package swexpertacademy.힙;

import java.io.*;
import java.util.*;

public class Solution_d3_2930_힙 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d3_2930.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        int[] heap = new int[10_0001];
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int pointer = 0;
            sb.append("#").append(tc);
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                char c = st.nextToken().charAt(0);
                if (c == '1') {
                    int x = Integer.parseInt(st.nextToken());
                    heap[++pointer] = x;
                    heapifyUp(pointer, heap);
                } else if (c == '2') {
                    if (pointer > 0) {
                        sb.append(" ").append(heap[1]);
                        heap[1] = heap[pointer--];
                        heapifyDown(heap, pointer);
                    } else {
                        sb.append(" ").append(-1);
                    }
                }
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void heapifyUp(int pointer, int[] heap) {
        int parent = pointer / 2;
        while (parent > 0 && heap[parent] < heap[pointer]) {
            int temp = heap[parent];
            heap[parent] = heap[pointer];
            heap[pointer] = temp;
            pointer = parent;
            parent = pointer / 2;
        }
    }

    static void heapifyDown(int[] heap, int tailPointer) {
        int cur = 1;
        int left = cur * 2;
        int right = cur * 2 + 1;
        while (left <= tailPointer) {
            if (right <= tailPointer && heap[left] < heap[right] && heap[right] > heap[cur]) {
                int temp = heap[right];
                heap[right] = heap[cur];
                heap[cur] = temp;
                cur = right;
                left = cur * 2;
                right = cur * 2 + 1;
            } else if (heap[left] > heap[cur]) {
                int temp = heap[left];
                heap[left] = heap[cur];
                heap[cur] = temp;
                cur = left;
                left = cur * 2;
                right = cur * 2 + 1;
            } else {
                break;
            }
        }
    }
}
