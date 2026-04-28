class Solution {
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