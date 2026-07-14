class Solution {
    
    int N, M;
    
    public int solution(int[][] beginning, int[][] target) {
        int answer = Integer.MAX_VALUE;
        N = beginning.length;
        M = beginning[0].length;
        
        for (int rowMask = 0; rowMask < (1 << N); rowMask++) {
            int temp = 0;
            int[][] curBoard = copyArray(beginning);
            for (int r = 0; r < N; r++) {
                if ((((1 << r) & rowMask)) != 0) {
                    temp++;
                    for (int c = 0; c < M; c++) {
                        curBoard[r][c]++;
                        curBoard[r][c] %= 2;
                    }
                }
            }
            
            for (int c = 0; c < M; c++) {
                if (target[0][c] != curBoard[0][c]) {
                    temp++;
                    for (int r = 0; r < N; r++) {
                        curBoard[r][c]++;
                        curBoard[r][c] %= 2;
                    }
                }
            }
            
            if (isSame(curBoard, target)) {
                answer = Math.min(answer, temp);    
            }
            
        }
        if (answer == Integer.MAX_VALUE) answer = -1;
        return answer;
    }
    
    int[][] copyArray(int[][] source) {
        int[][] result = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result[i][j] = source[i][j];
            }
        }
        return result;
    }
    
    boolean isSame(int[][] arr1, int[][] arr2) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr1[i][j] != arr2[i][j]) return false;
            }
        }
        return true;
    }
}