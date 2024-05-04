package swexpertacademy.차량정비소;

import java.util.*;
import java.io.*;

public class Solution_d9_2477_차량정비소 {

    static class Customer {
        int id;
        int arriveTime;
        int receiveNumber;
        int receiveProcessTime;
        int arriveRepairTime;
        int repairProcessTime;

        public Customer(int arriveTime) {
            this.arriveTime = arriveTime;
        }

        public void giveId(int id) {
            this.id = id;
        }

        public void getReceive(int receiveNumber) {
            this.receiveNumber = receiveNumber;
            // a[]: 1~N번의 접수 처리 시간
            receiveProcessTime = a[receiveNumber];
            receiveCounter[receiveNumber] = this;
        }

        public void getRepair(int repairNumber) {
            // 정비 번호는 고객이 굳이 기억할 필요 없다.
            // b[]: 1~M번의 정비 처리 시간
            repairProcessTime = b[repairNumber];
            repairCounter[repairNumber] = this;
        }
    }

    static int N, M, K, A, B, ans;
    static int[] a, b;
    static List<Customer> customers;
    static Customer[] receiveCounter, repairCounter;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2477.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            // N: 접수 창구의 개수
            a = new int[N + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < N + 1; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }

            // M: 정비 창구의 개수
            b = new int[M + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < M + 1; i++) {
                b[i] = Integer.parseInt(st.nextToken());
            }

            // K: 차량 정비소를 방문한 고객
            // tk: 차량 정비소에 도착하는 시간(범위 1 ~ K)
            customers = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for (int id = 1; id < K + 1; id++) {
                int time = Integer.parseInt(st.nextToken());
                customers.add(new Customer(time));
            }

            customers.sort(Comparator.comparing(c -> c.arriveTime));
            // 고객 번호는 도착하는 순서대로 준다.
            for (int i = 0; i < customers.size(); i++) {
                Customer customer = customers.get(i);
                customer.giveId(i + 1);
            }

            receiveCounter = new Customer[N + 1];
            repairCounter = new Customer[M + 1];

            ans = 0;
            simulate();

            if (ans == 0) ans--;
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void simulate() {
        int time = 0;
        int remainCustomers = K;
        /* 접수 창구의 우선순위
         * - 고객번호가 낮은 고객이 우선
         * - 접수 창구가 낮은 곳으로 먼저 보낸다.*/
        PriorityQueue<Customer> receivePq = new PriorityQueue<>(Comparator.comparing((Customer c) -> c.id));
        /* 정비 창구의 우선순위
         * - 먼저 기다리는 고객이 우선
         * - 이용했던 접수 창구번호가 작은 고객이 우선
         * - 정비 창구가 낮은 곳으로 먼저 보낸다.*/
        PriorityQueue<Customer> repairPq = new PriorityQueue<>(Comparator.comparing((Customer c) -> c.arriveRepairTime).thenComparing(c -> c.receiveNumber));


        while (remainCustomers > 0) {
            // 접수 창구와 정비 창구는 처리가 끝나자마자 바로 고객이 들어갈 수 있어야 한다. 따라서 매시간마다 가장 먼저 업데이트해준다.
            for (int i = 1; i < N + 1; i++) {
                // 접수 창구에 고객이 없으면 건너뛴다.
                if (receiveCounter[i] == null) continue;
                Customer customer = receiveCounter[i];
                customer.receiveProcessTime--;
                if (customer.receiveProcessTime == 0) {
                    // 만약 고객 접수 처리가 끝났다면 정비창구 대기 PQ에 고객을 넣어준다.
                    customer.arriveRepairTime = time;
                    // 정비 창구에 먼저 도착한 고객이 우선이므로 시간도 기억하고 있어야한다.
                    repairPq.offer(customer);
                    // 그리고 정비 창구에 있는 고객을 비워준다.
                    receiveCounter[i] = null;
                }
            }

            for (int i = 1; i < M + 1; i++) {
                // 정비 창구에 고객이 없으면 건너뛴다.
                if (repairCounter[i] == null) continue;
                Customer customer = repairCounter[i];
                customer.repairProcessTime--;
                if (customer.repairProcessTime == 0) {
                    // 만약 고객 정비 처리가 끝났다면 A랑 B 비교 후 그냥 보낸다.
                    if (customer.receiveNumber == A && i == B) ans += customer.id;

                    // 그리고 정비 창구에 있는 고객을 비워준다.
                    repairCounter[i] = null;
                    remainCustomers--;
                }
            }

            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                if (c.arriveTime == time) {
                    // 고객이 도착한다면 접수창구를 보낼 PQ에 담는다.
                    receivePq.offer(c);
                    // 그리고 리스트에서 삭제한다.
                    customers.remove(c);
                    // i 인덱스는 다시 후퇴해줘야한다.
                    i--;
                } else if (c.arriveTime > time) {
                    // 더 크다면 이 반복문은 끝낸다.
                    break;
                } else {
                    // 더 작다면 이전에 뽑았어야했는데 못 뽑은 상태다. 디버깅을 위한 에러
                    System.out.println("Error! why Customer still here?");
                }
            }

            int receiveNumber = 1;
            while (!receivePq.isEmpty() && receiveNumber < N + 1) {
                if (receiveCounter[receiveNumber] == null) {
                    Customer customer = receivePq.poll();
                    customer.getReceive(receiveNumber);
                }
                receiveNumber++;
            }

            int repairNumber = 1;
            while (!repairPq.isEmpty() && repairNumber < M + 1) {
                if (repairCounter[repairNumber] == null) {
                    Customer customer = repairPq.poll();
                    customer.getRepair(repairNumber);
                }
                repairNumber++;
            }
            time++;
        }
    }
}
