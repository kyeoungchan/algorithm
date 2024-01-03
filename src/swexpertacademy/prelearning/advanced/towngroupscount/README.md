# [S/W 문제해결 상] 창용 마을 무리의 개수
[SW Expert Academy](https://swexpertacademy.com/main/solvingProblem/solvingProblem.do)

- 입력
    ```text
    2
    6 5
    1 2
    2 5
    5 1
    3 4
    4 6
    6 8
    1 2
    2 5
    5 1
    3 4
    4 6
    5 4
    2 4
    2 3
    ```
- 출력
    ```text
    #1 2
    #2 1
    ```

### 배운점
- `.set()`를 활용하고 싶을 때: `import java.util.stream.Collectors;`
  ```java
  private static int countGroup(int[] parent) {
      return Arrays.stream(parent)
              .map(p -> findParent(parent, p))
              .boxed()
              .collect(Collectors.toSet())
              .size() - 1;
  }
  ```
- 