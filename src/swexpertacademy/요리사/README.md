# 요리사
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIeUtVakTMDFAVH  
### 필기
단순 조합으로 푼다면 당연히 코드를 아래처럼 짰을 것이다. (이렇게 짜도 통과가 된다.)
```java
static void comb(int cnt, int start) {
    // 각각 N/2개씩 나누어 두 개의 요리를 하려고 한다.
    if (cnt == N / 2) {
        calculate();
        return;
    }

    for (int i = start; i < N; i++) {
        isA[i] = true;
        comb(cnt + 1, i + 1);
        isA[i] = false;
    }
}
```
하지만 아래처럼 i의 범위를 `N/2+cnt`까지로 두었다.
```java
static void comb(int cnt, int start) {
    // 각각 N/2개씩 나누어 두 개의 요리를 하려고 한다.
    if (cnt == N / 2) {
        calculate();
        return;
    }

    for (int i = start; i < N / 2 + cnt + 1; i++) {
        isA[i] = true;
        comb(cnt + 1, i + 1);
        isA[i] = false;
    }
}
```
더이상 무의미한 재귀호출을 줄이기 위함이다.
cnt의 위치에 따라 더이상 재귀호출이 유의미한지 여부를 판단할 수 있다.
`N - (N / 2 - cnt)`로 볼 수 있다.
따라서 `i < N / 2 + cnt + 1`이라는 부등식이 나오게 되었다.