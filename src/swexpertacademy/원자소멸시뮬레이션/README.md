# 원자 소멸 시뮬레이션
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRFInKex8DFAUo

### 오답노트
1. x, y좌표를 변환할 때 y, x 순으로 변환하는 것은 좋았지만, y좌표는 위로 올라갈수록 값이 커지고, 이차원배열에서는 위로 올라갈수록 인덱스가 작아지는 것을 간과했다.
    - x, y 좌표를 볼 때는 r, c로 변환할 때 순서도 바꿔야하고, 위로 올라갈수록 y좌표의 값이 커지는지 꼭 확인하자!
    ```java
    int x = (Integer.parseInt(st.nextToken()) + 1000) * 2;
    int y = (-Integer.parseInt(st.nextToken()) + 1000) * 2; // y좌표는 위로 올라갈수록 커지는 좌표이므로 익숙한 좌표로 변환하기 위해서는 음수로 바꿔주고 변환해야한다.
    ```
2. 처음에 전략을 이차원 배열을 사용하지 않으려고 했다.
   - 좌표의 범위에는 제한이 없다고 해서 map을 사용하지 않으려고 했는데 생각해보니 -1000~1000 밖으로 나가는 애는 에너지를 방출할 리가 없으므로 그냥 보내버리면 된다.
    ```java
    public void move() {
        map[r][c] -= energy;
        r += di[d];
        c += dj[d];
        if (r < 0 || r > 4000 || c < 0 || c > 4000) {
            extinguished = true;
        } else {
            map[r][c] += energy;
        }
    }
    ```
3. 마지막으로... 제발 continue나 else를 빼먹지 말자. 나는 그것을 빼먹어서 이미 제거한 원자를 이동시켜서 답이 다르게 나왔었다.
    ```java
    for (int i = 0; i < atoms.size(); i++) {
        Atom atom = atoms.get(i);
        if (atom.extinguished) {
            // 에너지 방출 후 소멸된 애들에 대한 처리
            atoms.remove(i);
            i--;
        } else { // 여기에서 else를 빼먹었다
            atom.move();
        }
    }
    ```