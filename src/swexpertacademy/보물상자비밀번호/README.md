# 보물상자 비밀번호
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRUN9KfZ8DFAUo

## 배운 점
1. TreeSet이든, PQ든 역순정렬은 `Comparator.reverseOrder()`다!!
   - 직접 구현을 하면 `Comparator.reverseOrder()`에 비해서 성능이 훨씬 떨어진다.
2. String형도 기본적으로 오름차순으로 정렬할 수 있게 되어있다.
   - 심지어 1이 A보다 작은 수로도 java가 판별해준다.
   - 따라서 이 문제에서 `TreeSet<String> ts = new TreeSet<>(Comparator.reverseOrder());`에 `ts.add("F12"); ts.add("123");`으로 넣어주면 자동으로 "123", "F12" 순으로 넣어준다.
3. `String.subString(a, b)` 는 a 번째 인덱스부터 b 인덱스 전까지의 부분 문자열을 반환한다.
   - a부터 (b-a)개의 문자를 꺼내와준다고 생각해도 된다.
4. 16진수를 10진수로 변환할 때: `Integer.parseInt(String s, 16)`
   - 참고로 10진수를 16진수(문자열로 밖에 표현을 못함)로 표현할 때
     `Integer.toHexString(int num)`