# 회전 초밥
https://www.acmicpc.net/problem/2531

입력1
```text
8 30 4 30
7
9
7
30
2
7
9
25
```
출력1
```text
5
```
입력2
```text
8 50 4 7
2
7
9
25
7
9
7
30
```
출력2
```text
4
```

### 배운점
슬라이딩 윈도우를 사용하면 중첩 반복문을 사용하지 않고도 부분 배열에 접근하여 연산을 할 수가 있다.
```java
int[] eated = new int[D + 1];
for (int i = 0; i < N; i++)
    sushiPlates[i] = Integer.parseInt(br.readLine());

// 처음 0번부터 K개수만큼 먹었을 때의 초기화
int count = 0;
for (int i = 0; i < K; i++) {
    if (eated[sushiPlates[i]] == 0 ) {
        count++;
    }
    eated[sushiPlates[i]]++;
}

int answer = 0;
for (int i = 1; i < N; i++) {
    if (answer <= count) { // 아직 쿠폰 초밥을 안 먹은 상태
        if (eated[C] == 0) {
            answer = count + 1;
        } else { // 이미 쿠폰 초밥을 먹은 상태
            answer = count;
        }
    }
    
    // end 이동
    int end = (i + K - 1) % N;
    if (eated[sushiPlates[end]] == 0) {
        count++;
    }
    eated[sushiPlates[end]]++;
    
    // start 이동
    eated[sushiPlates[i - 1]]--; // start점 한 칸 이동했으니 이전의 초밥 제거
    if (eated[sushiPlates[i - 1]] == 0) {
        count--;
    }
}
```