import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        int answer = 0;
        int left = 1;
        int right = 0;
        for (int stone: stones) {
            right = Math.max(right, stone);
        }
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canGo(mid, stones, k)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }
    
    boolean canGo(int people, int[] stones, int k) {
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < stones.length; i++) {
            if (stones[i] < people) {
                q.offer(stones[i]);
                if (q.size() == k) return false;
            } else if (!q.isEmpty()) {
                q.clear();
            }
        }
        return true;
    }
}