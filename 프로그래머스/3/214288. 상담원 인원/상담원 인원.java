import java.util.*;

class Solution {
    int n, k, INF = 123456789, answer;
    int[] consultants;
    int[][] reqs;
    PriorityQueue<Integer>[] pq;
    public int solution(int k, int n, int[][] reqs) {
        this.n = n;
        this.k = k;
        this.reqs = reqs;
        consultants = new int[k + 1];
        pq = new PriorityQueue[k + 1];
        for (int i = 1; i <= k; i++) {
            // 모든 유형에 상담원은 한명씩은 있어야 한다.
            consultants[i]++;
            pq[i] = new PriorityQueue<>();
        }
        answer = INF;
        distribute(1, n - k);
        return answer;
    }
    
    void distribute(int type, int remain) {
        if (type == k) {
            consultants[type] += remain;
            calc();
            consultants[type] -= remain;
            return;
        }
        
        for (int i = 0; i <= remain; i++) {
            consultants[type] += i;
            distribute(type + 1, remain - i);
            consultants[type] -= i;
        }
    }
    
    void calc() {
        int temp = 0;
        for (int i = 1; i <= k; i++) pq[i].clear();
        
        for (int[] req: reqs) {
            int start = req[0];
            int time = req[1];
            int type = req[2];
            
            while (!pq[type].isEmpty() && pq[type].peek() <= start) {
                pq[type].poll();
            }
            
            int available = consultants[type] - pq[type].size();
            if (available > 0) {
                pq[type].offer(start + time);
            } else if (available == 0) {
                int end = pq[type].poll();
                temp += end - start;
                pq[type].offer(end + time);
            }
        }
        answer = Math.min(answer, temp);
    }
}