# 프로세서 연결하기
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV4suNtaXFEDFAUf

### 배운점
나의 경우, DFS로 문제를 풀 때 항상 중첩 호출을 해서 시간초과가 났었다.
예를 들어, 처음에는 코드를 아래와 같이 짰다.
```java
if (success) {
    // 연결에 성공했다면 원상 복구하기 전에 재귀 호출!
    dfs(cnt + 1);
}

if (success) tempConnected--;
while (k > 1) {
    k--;
    int ni = startI + di[d] * k;
    int nj = startJ + dj[d] * k;
    board[ni][nj] = 0;
    tempLen--;
}

if (!success) {
    // 연결에 성공든 실패했든 원상 복구 후 호출!
    dfs(cnt + 1);
}
```
이 코드의 문제점은 4방 탐색에 따른 메서드 내부임에도 불구하고, 연결이 안 된 경우에도 재귀 호출을 꾸준히 한다는 것이었다.
원상 복구 후 호출은 최대한 겹치지 않게 조심해야한다!
주로 4방 탐색을 통한 재귀호출을 자주 하게 될텐데, 어떤 조건을 만족하지 않은 상태에서 재귀호출을 하는 경우는 4방 탐색 반복문을 벗어난 후 한 번만 호출하는 것으로 기억해두자!(문제에 따라 다르겠지만)