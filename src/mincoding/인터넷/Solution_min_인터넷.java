package mincoding.인터넷;

import java.io.*;
import java.util.*;

public class Solution_min_인터넷 {

    static class Page implements Comparable<Page> {
        private String site;
        private int accessTime;

        public Page(String site, int accessTime) {
            this.site = site;
            this.accessTime = accessTime;
        }

        @Override
        public int compareTo(Page o) {
            return Integer.compare(accessTime, o.accessTime);
        }
    }

    static PriorityQueue<Page> backMemory = new PriorityQueue<>(Comparator.reverseOrder());
    static PriorityQueue<Page> frontMemory = new PriorityQueue<>();
    static Page currentPage;
    static int time;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int Q = Integer.parseInt(br.readLine());

        backMemory.clear();
        frontMemory.clear();
        currentPage = null;
        time = 0;

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            char command = st.nextToken().charAt(0);
            if (command == 'B') {
                back();
            } else if (command == 'F') {
                front();
            } else if (command == 'A') {
                access(st.nextToken());
            } else if (command == 'C') {
                compress();
            }
        }

        sb.append(currentPage.site).append("\n"); // A작업은 최소 1번은 보장되므로 NPE 발생 X
        if (backMemory.isEmpty()) sb.append("none\n");
        else {
            while (!backMemory.isEmpty()) sb.append(backMemory.poll().site).append(" ");
            sb.append("\n");
        }
        if (frontMemory.isEmpty()) sb.append("none\n");
        else {
            while (!frontMemory.isEmpty()) sb.append(frontMemory.poll().site).append(" ");
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    /**
     * 현재 접속하고 있던 사이트를 앞으로 가기 메모리에 저장
     * 뒤로 가기 메모리에서 가장 최근 사이트에 접속
     * 접속하면 뒤로 가기 메모리에서 삭제
     * 뒤로가기 메모리에 1개 이상의 페이지가 저장되어 있을 때만 실행
     */
    static void back() {
        if (backMemory.isEmpty()) return;
        frontMemory.offer(currentPage);
        currentPage = backMemory.poll();
    }

    /**
     * 현재 접속하고 있던 사이트를 뒤로가기 메모리에 저장
     * 앞으로 가기 메모리에서 가장 최근 사이트에 접속
     * 접속하면 앞으로 가기 메모리에서 삭제
     * 앞으로 가기 메모리에 1개 이상의 페이지가 저장되어 있을 때만 실행
     */
    static void front() {
        if (frontMemory.isEmpty()) return;
        backMemory.offer(currentPage);
        currentPage = frontMemory.poll();
    }

    /**
     * 앞으로 가기 메모리에 저장된 사이트가 모두 삭제된다.
     * 현재 접속하고 있던 사이트를 뒤로 가기 메모리에 저장한다.
     * 현재 접근하려는 사이트가 현재 페이지로 갱신된다.
     * 단, 인터넷을 실행 후 맨 터음 접속하는 경우라면, 현재 페이지를 뒤로 가기 메모리에 저장하지 않는다.
     */
    static void access(String site) {
        if (currentPage != null) backMemory.offer(currentPage);
        frontMemory.clear();
        currentPage = new Page(site, time++);
    }

    /**
     * 뒤로 가기 메모리에 같은 사이트가 연속해서 2개 이상 등장할 경우, 하나만 남기고 나머지는 모두 삭제한다.
     */
    static void compress() {
        if (backMemory.isEmpty()) return;
        PriorityQueue<Page> temp = new PriorityQueue<>(Comparator.reverseOrder());
        Page buffer = backMemory.poll();
        temp.offer(buffer);
        while (!backMemory.isEmpty()) {
            Page cur = backMemory.poll();
            while (cur != null && cur.site.equals(buffer.site)) {
                cur = backMemory.poll();
            }
            if (cur == null) break;
            temp.offer(cur);
            buffer = cur;
        }
        backMemory = temp;
    }
}
