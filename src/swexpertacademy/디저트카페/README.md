# 디저트 카페
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu  

### 오답노트
- 그림을 그려놓고, 수식으로 주석으로 적으면서 범위를 측정하니 실수가 훨씬 줄었다.
    ```java
    /*
    0 0 0 0
    0 0 0 0
    0 0 0 0
    0 0 0 0
     */
    static void searchCafe(int startI, int startJ) {
        // 평행사변형 중에서 1, 2번 꼭짓점의 가로(세로) 길이는 a, 2,3번 꼭짓점의 세로(가로)의 길이는 b
        // startJ + x <= N - 1
        // startI + x <= N - 2
    int maxA = Math.min(N - startJ - 1, N - 2 - startI);
    ```
- 마지막에 한 바퀴를 모두 돌아 또 중복처리가 되는 부분을 놓쳤다.
  - 중복처리를 하고 원형으로 돌리는 상황이라면 꼭 중복처리를 빼먹지 않았는지 확인하자.
    ```java
    if (i == startI && j == startJ) break;
    if (desserts.contains(map[i][j])) return;
    ```
  - 다시 보니 처음에 출발할 때 list에 담지 않는 방법도 있었다. 본코드 주석처리 참₩
    ```java
    static void go(int startI, int startJ, int a, int b) {
        List<Integer> desserts = new ArrayList<>();
    //        desserts.add(map[startI][startJ]);
        int i = startI;
        int j = startJ;
        for (int d = 0; d < 4; d++) {
            if (d % 2 == 0) {
                for (int k = 0; k < a; k++) {고
                    i += di[d];
                    j += dj[d];
                    if (desserts.contains(map[i][j])) return;
                    desserts.add(map[i][j]);
                }
            } else {
                for (int k = 0; k < b; k++) {
                    i += di[d];
                    j += dj[d];
    //                    if (i == startI && j == startJ) break;
                    if (desserts.contains(map[i][j])) return;
                    desserts.add(map[i][j]);
                }
            }
        }
        answer = Math.max(desserts.size(), answer);
    }
    ```