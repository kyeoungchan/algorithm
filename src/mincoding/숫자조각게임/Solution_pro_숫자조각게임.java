package mincoding.숫자조각게임;

import java.util.*;

/**
 * 각 셀에는 1~5 중 하나의 숫자가 적혀있다.
 * 게임판에 놓은 조각은 5개의 종류가 있다.
 * 놓은 위치는 가장 위, 가장 왼쪽으로 표현
 * 올려놓기 규칙
 * 1. 주어진 조각 내의 각각의 블록들에 대하여 블록의 숫자와 그 블록이 맞닿은 게임판 셀의 숫자의 차이가 모두 동일한 겨웅에만 올려놓을 수 있다.
 * 2. 게임판에 주어진 조각을 놓을 때, 겹쳐서 놓을 수 없다.
 * 3. 놓을 수 있는 조각의 위치가 여러개라면, 행의 번호와 열의 번호가 작을수록 우선순위가 높다.
 * 4. 놓을 수 있는 위치가 없다면 그 조각은 사라진다.
 */
public class Solution_pro_숫자조각게임 {

    private boolean[][] status;
    private List<Integer>[] hash;
    private int N, M;

    /**
     * @param mRows  세로 길이
     * @param mCols  가로 길이
     * @param mCells
     */
    public void init(int mRows, int mCols, int[][] mCells) {
        N = mRows;
        M = mCols;
        status = new boolean[mRows][mCols];
        hash = new List[500_000]; // 퍼즐 번호 1~5 => 0~4. 각자리 번호도 1~5 => 0~4. 가장 길이가 긴 4번 블록도 44,444

        setOne(mCells);
        setTwo(mCells);
        setThree(mCells);
        setFour(mCells);
        setFive(mCells);
    }

    private void setOne(int[][] mCells) {
        int initHash = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M - 1; j++) {
                int min = Math.min(mCells[i][j], mCells[i][j + 1]);
                int hashCode = initHash + (mCells[i][j] - min) * 10 + mCells[i][j + 1] - min;
                if (hash[hashCode] == null) hash[hashCode] = new ArrayList<>();
                hash[hashCode].add(i * M + j);
            }
        }
    }

    private void setTwo(int[][] mCells) {
        int initHash = 100_000;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M - 2; j++) {
                int min = Math.min(mCells[i][j], mCells[i][j + 1]);
                min = Math.min(min, mCells[i][j + 2]);
                int hashCode = initHash + (mCells[i][j] - min) * 100 + (mCells[i][j + 1] - min) * 10 + mCells[i][j + 2] - min;
                if (hash[hashCode] == null) hash[hashCode] = new ArrayList<>();
                hash[hashCode].add(i * M + j);
            }
        }
    }

    private void setThree(int[][] mCells) {
        int initHash = 200_000;
        for (int i = 0; i < N - 2; i++) {
            for (int j = 0; j < M; j++) {
                int min = Math.min(mCells[i][j], mCells[i + 1][j]);
                min = Math.min(min, mCells[i + 2][j]);
                int hashCode = initHash + (mCells[i][j] - min) * 100 + (mCells[i + 1][j] - min) * 10 + mCells[i + 2][j] - min;
                if (hash[hashCode] == null) hash[hashCode] = new ArrayList<>();
                hash[hashCode].add(i * M + j);
            }
        }
    }

    private void setFour(int[][] mCells) {
        int initHash = 300_000;
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < M - 2; j++) {
                int min = Math.min(mCells[i][j], mCells[i][j + 1]);
                min = Math.min(min, mCells[i + 1][j + 1]);
                min = Math.min(min, mCells[i + 1][j + 2]);
                int hashCode = initHash + (mCells[i][j] - min) * 1_000 + (mCells[i][j + 1] - min) * 100 + (mCells[i + 1][j + 1] - min) * 10 + mCells[i + 1][j + 2] - min;
                if (hash[hashCode] == null) hash[hashCode] = new ArrayList<>();
                hash[hashCode].add(i * M + j);
            }
        }
    }

    private void setFive(int[][] mCells) {
        int initHash = 400_000;
        for (int i = 0; i < N - 2; i++) {
            for (int j = 0; j < M - 2; j++) {
                int min = Math.min(mCells[i][j], mCells[i + 1][j]);
                min = Math.min(min, mCells[i + 1][j + 1]);
                min = Math.min(min, mCells[i + 1][j + 2]);
                min = Math.min(min, mCells[i + 2][j + 2]);
                int hashCode = initHash + (mCells[i][j] - min) * 10_000 + (mCells[i + 1][j] - min) * 1_000 + (mCells[i + 1][j + 1] - min) * 100 + (mCells[i + 1][j + 2] - min) * 10 + mCells[i + 2][j + 2] - min;
                if (hash[hashCode] == null) hash[hashCode] = new ArrayList<>();
                hash[hashCode].add(i * M + j);
            }
        }
    }

    /**
     * @param mPuzzle 놓으려는 블록. 0은 블록이 없음을 의미한다.
     * @return 놓는 위치를 반환, 만약 놓을 수 없다면 Result.row와 Result.col 값을 -1로 설정 후 반환
     */
    public Main.Result putPuzzle(int[][] mPuzzle) {
        Main.Result ret = new Main.Result();

        if (mPuzzle[0][2] == 0 && mPuzzle[1][0] == 0 && mPuzzle[1][1] == 0) {
            // 1번 퍼즐
            int min = Math.min(mPuzzle[0][0], mPuzzle[0][1]);
            int hashCode = (mPuzzle[0][0] - min) * 10 + mPuzzle[0][1] - min;
            if (hash[hashCode] != null) {
                for (Integer pos : hash[hashCode]) {
                    int r = pos / M;
                    int c = pos % M;
                    if (!status[r][c] && !status[r][c + 1]) {
                        status[r][c] = status[r][c + 1] = true;
                        ret.row = r;
                        ret.col = c;
                        return ret;
                    }
                }
            }

        } else if (mPuzzle[0][2] != 0) {
            // 2번 퍼즐
            int hashCode = 100_000;
            int min = Math.min(mPuzzle[0][0], mPuzzle[0][1]);
            min = Math.min(min, mPuzzle[0][2]);
            hashCode += (mPuzzle[0][0] - min) * 100 + (mPuzzle[0][1] - min) * 10 + mPuzzle[0][2] - min;
            if (hash[hashCode] != null) {
                for (Integer pos : hash[hashCode]) {
                    int r = pos / M;
                    int c = pos % M;
                    if (!status[r][c] && !status[r][c + 1] && !status[r][c + 2]) {
                        status[r][c] = status[r][c + 1] = status[r][c + 2] = true;
                        ret.row = r;
                        ret.col = c;
                        return ret;
                    }
                }
            }

        } else if (mPuzzle[2][0] != 0) {
            // 3번 퍼즐
            int hashCode = 200_000;
            int min = Math.min(mPuzzle[0][0], mPuzzle[1][0]);
            min = Math.min(min, mPuzzle[2][0]);
            hashCode += (mPuzzle[0][0] - min) * 100 + (mPuzzle[1][0] - min) * 10 + mPuzzle[2][0] - min;
            if (hash[hashCode] != null) {
                for (Integer pos : hash[hashCode]) {
                    int r = pos / M;
                    int c = pos % M;
                    if (!status[r][c] && !status[r + 1][c] && !status[r + 2][c]) {
                        status[r][c] = status[r + 1][c] = status[r + 2][c] = true;
                        ret.row = r;
                        ret.col = c;
                        return ret;
                    }
                }
            }

        } else if (mPuzzle[0][1] != 0) {
            // 4번 퍼즐
            int hashCode = 300_000;
            int min = Math.min(mPuzzle[0][0], mPuzzle[0][1]);
            min = Math.min(min, mPuzzle[1][1]);
            min = Math.min(min, mPuzzle[1][2]);
            hashCode += (mPuzzle[0][0] - min) * 1_000 + (mPuzzle[0][1] - min) * 100 + (mPuzzle[1][1] - min) * 10 + mPuzzle[1][2] - min;
            if (hash[hashCode] != null) {
                for (Integer pos : hash[hashCode]) {
                    int r = pos / M;
                    int c = pos % M;
                    if (!status[r][c] && !status[r][c + 1] && !status[r + 1][c + 1] && !status[r + 1][c + 2]) {
                        status[r][c] = status[r][c + 1] = status[r + 1][c + 1] = status[r + 1][c + 2] = true;
                        ret.row = r;
                        ret.col = c;
                        return ret;
                    }
                }
            }

        } else {
            // 5번 퍼즐
            int hashCode = 400_000;
            int min = Math.min(mPuzzle[0][0], mPuzzle[1][0]);
            min = Math.min(min, mPuzzle[1][1]);
            min = Math.min(min, mPuzzle[1][2]);
            min = Math.min(min, mPuzzle[2][2]);
            hashCode += (mPuzzle[0][0] - min) * 10_000 + (mPuzzle[1][0] - min) * 1_000 + (mPuzzle[1][1] - min) * 100 + (mPuzzle[1][2] - min) * 10 + mPuzzle[2][2] - min;
            if (hash[hashCode] != null) {
                for (Integer pos : hash[hashCode]) {
                    int r = pos / M;
                    int c = pos % M;
                    if (!status[r][c] && !status[r + 1][c] && !status[r + 1][c + 1] && !status[r + 1][c + 2] && !status[r + 2][c + 2]) {
                        status[r][c] = status[r + 1][c] = status[r + 1][c + 1] = status[r + 1][c + 2] = status[r + 2][c + 2] = true;
                        ret.row = r;
                        ret.col = c;
                        return ret;
                    }
                }
            }
        }
        ret.row = ret.col = -1;
        return ret;
    }

    /**
     * 게임판 내의 조각들을 모두 없애 게임판을 초기 상태로 만든다.
     */
    public void clearPuzzles() {
        status = new boolean[N][M];
    }
}
