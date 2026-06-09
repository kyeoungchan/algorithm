package programmers.카카오앱정리하기;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] answer = solution.solution(new int[][]{
                {0, 2, 2, 0, 0, 0, 0, 0},
                {0, 2, 2, 0, 0, 4, 4, 0},
                {0, 3, 3, 3, 1, 4, 4, 0},
                {0, 3, 3, 3, 0, 0, 0, 0},
                {0, 3, 3, 3, 5, 5, 6, 0},
                {0, 0, 0, 0, 5, 5, 0, 0}
        }, new int[][]{
                {3, 1}, {3, 1}
        });

        answer = solution.solution(new int[][]{
                {1, 1, 0},
                {1, 1, 0}
        }, new int[][]{
                {1, 4}, {1, 3}, {1, 2}
        });
    }
}
