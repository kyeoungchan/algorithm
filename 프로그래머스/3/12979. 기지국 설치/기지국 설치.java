class Solution {
    
    int answer;
    
    public int solution(int n, int[] stations, int w) {
        answer = 0;

        int len = 2 * w + 1;
        int left = 1;
        for (int s: stations) {
            int right = s - w - 1;
            if (left <= right) {
                answer += ((right - left) + 1) / len;
                if (((right - left) + 1) % len != 0) answer++;
                
            }
            left = s + w + 1;
        }
        if (left <= n) {
            answer += ((n - left) + 1) / len;
            if (((n - left) + 1) % len != 0) answer++;
        }

        return answer;
    }
    
    
}