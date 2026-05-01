import java.util.*;

class Solution {
    
    static class Server implements Comparable<Server> {
        int endTime, cnt;
        
        public Server(int endTime, int cnt) {
            this.endTime = endTime;
            this.cnt = cnt;
        }
        
        @Override
        public int compareTo(Server o) {
            return Integer.compare(endTime, o.endTime);
        }
        
        @Override
        public String toString() {
            return "endTime: " + endTime + ", cnt: " + cnt;
        }
    }
    
    // m: 서버 한 대로 감당할 수 있는 최대 이용자의 수
    // k: 서버 한 대가 운영 가능한 시간을 나타내는 정수
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        
        PriorityQueue<Server> pq = new PriorityQueue<>();
        
        int activeCnt = 0;
        
        for (int time = 0; time < 24; time++) {
            
            if (!pq.isEmpty()) {
                Server s = pq.poll();
                if (s.endTime > time) {
                    // 아직 종료 시간이 안 됐으면
                    pq.offer(s);
                } else {
                    activeCnt -= s.cnt;
                }    
            }
            
            int player = players[time];
            if (player < m * (activeCnt + 1)) {
                // System.out.println("active: " + activeCnt + ", " + time + ": " + pq);
                continue;  
            }
            int needCnt = player / m;
            if (needCnt > activeCnt) {
                int newCnt = needCnt - activeCnt;
                answer += newCnt;
                activeCnt += newCnt;
                pq.offer(new Server(time + k, newCnt));    
            }
            
            // System.out.println("active: " + activeCnt + ", " + time + ": " + pq);
        }
        
        return answer;
    }
}