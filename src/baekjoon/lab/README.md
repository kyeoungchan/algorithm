# 연구소
https://www.acmicpc.net/problem/14502

입력1
```text
7 7
2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0
```
출력1
```text
27
```
입력2
```text
4 6
0 0 0 0 0 0
1 0 0 0 0 2
1 1 1 0 0 2
0 0 0 0 0 2
```
출력2
```text
9
```
입력3
```text
8 8
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
```
출력3
```text
3
```

### 오답 기록
- 조합은 반복문을 돌리면서 실행되므로 재귀호출을 반복문 내에서 한 번씩만 하면 된다
    ```java
    static void setWall(int cnt, int start) {
        if (cnt == 3) {
            infect();
            return;
        }
    
        for (int i = start; i < blankPos.size(); i++) {
            int[] pos = blankPos.get(i);
            int ni = pos[0];
            int nj = pos[1];
            map[ni][nj] = 1;
            setWall(cnt + 1, i + 1);
            map[ni][nj] = 0;
        }
    }
    ```
-  열의 범위는 M까지인데, N으로 잘못 적어서 정답이 나오지 않는 경우도 발생했다.
    ```java
    for (int d = 0; d < 4; d++) {
        int ni = i + di[d];
        int nj = j + dj[d];
        if (ni < 0 || ni >= N || nj < 0 || nj >= M || tempMap[ni][nj] != 0) continue;
        tempMap[ni][nj] = 2;
        q.offer(new int[]{ni, nj});
    }
    ```
