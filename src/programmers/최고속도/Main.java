package programmers.최고속도;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.solution(
                new int[][] {{-1, 3}, {7, 3}, {1, -1}, {-2, 6}},
                new int[][] {
                        {-1, 7, 7, 7, 80},
                        {-3, 3, 9, 3, 45},
                        {-2, -4, -2, 6, 60},
                        {1, -4, 1, 8, 50},
                        {5, 1, 5, 7, 70}
                }
        )));
    }
}
