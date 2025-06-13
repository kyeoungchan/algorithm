import java.util.*;

/**
 * n개의 음이 아닌 정수들이 있다.
 * 이 정수들을 순설르 바꾸지 않고 적절히 더하거나 빼서 타겟 넘버를 만드려고 한다.
 * 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하도록 solution 함수를 작성하라.
 * 주어지는 숫자는 2개 이상 20개 이하
 * 1 <= target <= 1000
 */
public class Solution {

    private int answer;
    private int n;
    
    public int solution(int[] numbers, int target) {
        answer = 0;
        this.n = numbers.length;
        backtracking(0, numbers, target, 0);
        
        return answer;
    }
    
    private void backtracking(int index, int[] numbers, int target, int result) {
        if (index == n) {
            if (result == target) answer++;
            return;
        }

        backtracking(index + 1, numbers, target, result + numbers[index]);
        backtracking(index + 1, numbers, target, result - numbers[index]);
    }
}
