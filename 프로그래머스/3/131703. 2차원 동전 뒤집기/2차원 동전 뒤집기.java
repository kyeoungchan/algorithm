class Solution {
    
    int n, m, INF = 101;
    
    public int solution(int[][] beginning, int[][] target) {
        int answer = INF;
        n = beginning.length;
        m = beginning[0].length;
        for (int rMask = 0; rMask < (1 << n); rMask++) {
            int temp = 0;
            int[][] tempArr = copyArray(beginning);
            for (int r = 0; r < n; r++) {
                if (((1 << r) & rMask) > 0) {
                    for (int c = 0; c < m; c++) {
                        tempArr[r][c]++;
                        tempArr[r][c] %= 2;
                    }
                    temp++;
                }
            }
            
            for (int c = 0; c < m; c++) {
                if (tempArr[0][c] == target[0][c]) continue;
                for (int r = 0; r < n; r++) {
                    tempArr[r][c]++;
                    tempArr[r][c] %= 2;
                }
                temp++;
            }
            
            if (correct(tempArr, target)) {
                answer = Math.min(answer, temp);
            }
        }
        
        if (answer == INF) answer = -1;
        return answer;
    }
    
    int[][] copyArray(int[][] source) {
        int[][] target = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                target[i][j] = source[i][j];
            }
        }
        return target;
    }
    
    boolean correct(int[][] arr1, int[][] arr2) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (arr1[r][c] != arr2[r][c]) return false;
            }
        }
        return true;
    }
}