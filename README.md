# Board

#### Environment
```
- maven
- java 8
- spring boot 2.3.0
- packaging : jar
- npm : 6.14.4
```

### 서버 구동
```bash
java -jar .\board-0.0.1-SNAPSHOT.jar
```

### 기타 테스트 방법
```bash
## 테스트 계정
- <account>
- loveyou_06@naver.com
- abc@abc.com
- <pw> 123

## 프론트엔드 빌드
cd frontend
npm run build

## 프로젝트 빌드
cd .. # project root path
mvn clean package -DskipTests=true

java -jar 실행
```

### 웹 접속
```
- localhost:8080
```

### H2 콘솔
```
- localhost:8080/console
- DB : boarddb / ID : sa / PWD :
``` 

### page path
```
- 로그인 화면 : /login , /
- 회원가입 : /signup
- 비밀번호 찾기 : /findpwd
- 게시글 : /list
- 유저정보 : /user
```

#### npm install list
```
- npm install axios
- npm install vue-axios
- npm install vue-moment
- npm install vuex
- npm install vuejs-paginate
- npm install vue bootstrap-vue bootstrap
- npm install npm-watch
- npm install vuex-persistedstate
```

#### 참고 Bootstrap
```
- https://startbootstrap.com/templates/sb-admin/
- https://bootstrap-vue.org/
```


