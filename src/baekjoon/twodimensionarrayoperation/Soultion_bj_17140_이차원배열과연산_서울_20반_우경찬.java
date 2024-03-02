package baekjoon.twodimensionarrayoperation;

import java.util.*;
import java.io.*;

public class Soultion_bj_17140_이차원배열과연산_서울_20반_우경찬 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int r = Integer.parseInt(st.nextToken()) - 1;
        int c = Integer.parseInt(st.nextToken()) - 1;
        int k = Integer.parseInt(st.nextToken());

        int[][] A = new int[3][3];
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 3; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int time = 0;
        int result = -1;
        if (r < 3 && c < 3 && A[r][c] == k) {
            result = 0;
        }
        while (time < 100 && result == -1) {
            time++;
            if (A.length >= A[0].length) {
                A = calcR(A);
//                printForDebug(A);
            } else {
                A = calcC(A);
//                printForDebug(A);
            }
            if (r < A.length && c < A[0].length && A[r][c] == k) {
                result = time;
            }
        }
        System.out.println(result);
        br.close();
    }

    static int[][] calcR(int[][] A) {

        List<Integer>[] tempA = new List[A.length];
        int newSize = A[0].length;
        for (int i = 0; i < A.length; i++) {
            // 1이 2개, 3이 3개라면, 4가 3개라면
            // countArr[1] = 2, countArr[3] = 3, countArr[4] = 3
            // countArr2[2] = {1,2}, countArr2[3]={3,3}, {4, 3}
            int[] countArr = new int[101];
            List<int[]>[] countArr2 = new List[101];

            int[] a = A[i];
            for (int j = 0; j < a.length; j++) {
                if (a[j] != 0) {
                    countArr[a[j]]++;
                }
            }
            for (int j = 1; j < 101; j++) {
                if (countArr[j] != 0) {
                    if (countArr2[countArr[j]] == null) {
                        countArr2[countArr[j]] = new ArrayList<>();
                    }
                    countArr2[countArr[j]].add(new int[]{j, countArr[j]}); // 해당 숫자와 빈도수
                }
            }
            tempA[i] = new ArrayList<>();
            for (List<int[]> arrays : countArr2) {
                if (arrays != null) {
                    for (int[] numFreqSet : arrays) {
                        tempA[i].add(numFreqSet[0]);
                        tempA[i].add(numFreqSet[1]);
                    }
                }
            }
            if (newSize < tempA[i].size()) {
                newSize = tempA[i].size();
            }
        }
//        int[][] newA = new int[A[0].length][newSize];
        int[][] newA = new int[A.length][newSize];
        for (int i = 0; i < newA.length; i++) {
            for (int j = 0; j < tempA[i].size(); j++) {
                newA[i][j] = tempA[i].get(j);
            }
        }

        return newA;
    }

    static int[][] calcC(int[][] A) {
        // 이번엔 행의 길이가 변화한다.
        List<int[]> tempA = new ArrayList<>();
        int newSize = A.length;
        for (int i = 0; i < A[0].length; i++) { // 열마다 체크할 예정이다.
            // 1이 2개, 3이 3개라면, 4가 3개라면
            // countArr[1] = 2, countArr[3] = 3, countArr[4] = 3
            // countArr2[2] = {1,2}, countArr2[3]={3,3}, {4, 3}
            int[] countArr = new int[101];
            List<int[]>[] countArr2 = new List[101];

            int[] a = new int[A.length];
            for (int j = 0; j < A.length; j++) {
                a[j] = A[j][i]; // 열 고정해서 복사
            }
            for (int j = 0; j < a.length; j++) {
                if (a[j] != 0) {
                    countArr[a[j]]++;
                }
            }
            int tempSize = 0;
            for (int j = 1; j < 101; j++) {
                if (countArr[j] != 0) {
                    if (countArr2[countArr[j]] == null) {
                        countArr2[countArr[j]] = new ArrayList<>();
                    }
                    countArr2[countArr[j]].add(new int[]{j, countArr[j]}); // 해당 숫자와 빈도수
                    tempSize += 2;
                }
            }

            if (tempA.size() < tempSize) {
                int temp = tempA.size(); // 별도로 변수를 지정하지 않으면 tempA가 커지면서 반복수가 줄어든다.
                for (int j = 0; j < tempSize - temp; j++) {
                    tempA.add(new int[A[0].length]); // 열의 길이는 무조건 고정이다.
                }
            }
            int idx = 0;
            for (List<int[]> arrays : countArr2) {
                if (arrays != null) {
                    for (int[] numFreqSet : arrays) {
                        tempA.get(idx++)[i] = numFreqSet[0];
                        tempA.get(idx++)[i] = numFreqSet[1];
                    }
                }
            }
            if (newSize < tempSize) {
                newSize = tempSize;
            }
        }
        int[][] newA = new int[newSize][A[0].length];
        for (int j = 0; j < A[0].length; j++) {
            for (int i = 0; i < tempA.size(); i++) {
                newA[i][j] = tempA.get(i)[j];
            }
        }
        return newA;
    }

    static void printForDebug(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
