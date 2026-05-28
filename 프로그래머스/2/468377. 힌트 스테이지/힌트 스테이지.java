import java.util.*;

class Solution {
    
    int answer, n, k;
    int[] hintCount;
    int[][] cost, hint;
    
    public int solution(int[][] cost, int[][] hint) {
        
        answer = Integer.MAX_VALUE;
        n = cost.length;
        k = hint[0].length - 1;
        hintCount = new int[n];
        this.cost = cost;
        this.hint = hint;
        
        backtracking(0, 0);
        
        return answer;
    }
    
    void backtracking(int curCost, int stageIdx) {
        if (stageIdx == n) {
            answer = Math.min(curCost, answer);
            return;
        }
        
        
        // 힌트를 사용하지 않고 깨는 경우
        int curHintCount = Math.min(hintCount[stageIdx], n - 1);
        backtracking(curCost + cost[stageIdx][curHintCount], stageIdx + 1);
        
        // 마지막 스테이지에서는 힌트를 구매할 수 없다.(의미도 없고)
        if (stageIdx == n - 1) return;
        
        // 힌트를 사용하는 경우
        curCost += hint[stageIdx][0];
        for (int i = 1; i <= k; i++) {
            int hintNum = hint[stageIdx][i];
            hintCount[hintNum-1]++;
        }
        curHintCount = Math.min(hintCount[stageIdx], n - 1);
        backtracking(curCost + cost[stageIdx][curHintCount], stageIdx + 1);
        for (int i = 1; i <= k; i++) {
            int hintNum = hint[stageIdx][i];
            hintCount[hintNum-1]--;
        }
    }
}