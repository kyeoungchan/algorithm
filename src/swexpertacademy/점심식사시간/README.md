### 실패했던 이유

부분집합을 할 때 아래의 코드에서 `selectedStairs[cnt] = 0;` 부분을 빼먹었음.

`selectedStairs[2] = 0` 인 상태에서 `selectedStairs[3] = 1`까지 호출했다면, `selectedStairs[2] = 1` 인 상태에서 `selectedStairs[3] = 0` 을 다시 호출하기 위해서는 해당 코드를 빼먹어서는 안 된다.

```java
selectStairs(cnt + 1, selectedStairs);
selectedStairs[cnt] = 1;
selectStairs(cnt + 1, selectedStairs);
selectedStairs[cnt] = 0;
```