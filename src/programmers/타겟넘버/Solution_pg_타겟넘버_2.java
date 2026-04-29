package programmers.타겟넘버;

public class Solution_pg_타겟넘버_2 {

    public static void main(String[] args) {
        Solution_pg_타겟넘버_2 solution = new Solution_pg_타겟넘버_2();
        System.out.println(solution.solution(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(solution.solution(new int[]{4, 1, 2, 1}, 4));
    }

    private int target, sum, answer;
    private int[] numbers;

    public int solution(int[] numbers, int target) {
        this.target = target;
        this.numbers = numbers;
        answer = 0;
        sum = 0;

        check(0);
        return answer;
    }

    private void check(int idx) {
        if (idx == numbers.length) {
            if (sum == target) {
                answer++;
            }
            return;
        }

        sum += numbers[idx];
        check(idx + 1);
        sum -= numbers[idx];

        sum -= numbers[idx];
        check(idx + 1);
        sum += numbers[idx];
    }
}
