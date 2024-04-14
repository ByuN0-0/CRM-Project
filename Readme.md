# API 요청 가이드
이 README 파일은 프론트엔드 개발자들을 위한 백엔드 API 요청 가이드입니다. 아래에는 백엔드에서 제공하는 API 엔드포인트에 대한 설명과 요청하는 방법이 포함되어 있습니다.

csrf 설정을 위해 html 헤더에 아래 구문 추가
```html
<meta name="_csrf" content="{{_csrf.token}}"/>  
<meta name="_csrf_header" content="{{_csrf.headerName}}"/>  
```
fetch요청 시  
header에 { 'X-CSRF-TOKEN': token, 'X-CSRF-HEADER': header, } 추가  
```html
ex:  
 fetch('/api/registerProc', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': token,
                'X-CSRF-HEADER': header,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    // 서버로부터 받은 응답 처리
                })
                .catch(error => {
                    console.error('Error:', error);
                });
    });
```
--
## 회원가입 (Register)
### Endpoint: /api/registerProc

요청 방법
POST 요청을 사용하여 새로운 사용자를 등록할 수 있습니다. 요청 본문에는 사용자 정보가 포함되어야 합니다.

요청 예시  
http  
POST /api/register HTTP/1.1  
Content-Type: application/json  
```html
{  
    "userName": "사용자 아이디",  
    "password": "비밀번호",  
    "userNick": "사용자 이름",
    "workSpaceName: "워크스페이스 이름"
}  
```
요청 본문 설명
- userName: 사용자의 아이디를 나타내는 문자열입니다.
- password: 사용자의 비밀번호를 나타내는 문자열입니다.
- userNick: 사용자의 이름을 나타내는 문자열입니다.
- workSpaceName: 사용자가 생성할 워크스페이스 이름을 나타내는 문자열입니다.

응답
- 성공적인 요청에 대한 응답은 HTTP 상태 코드 200을 반환합니다.
- 실패한 요청에 대한 응답은 적절한 오류 메시지와 함께 적절한 HTTP 상태 코드를 반환합니다.

---
## 로그인 (Login)
### Endpoint: /api/loginProc

로그인을 위한 엔드포인트입니다. 사용자의 아이디와 비밀번호를 전송하여 로그인할 수 있습니다.

요청 방법:

POST 요청을 사용하여 로그인을 요청합니다. 요청 본문에 사용자의 아이디와 비밀번호를 포함해야 합니다.
요청 예시:

http
POST /api/loginProc HTTP/1.1
Content-Type: application/json
```html
{  
    "username": "사용자 아이디",  
    "password": "비밀번호"  
}
```
요청 본문 설명

- userName: 사용자의 아이디를 나타내는 문자열입니다.
- password: 사용자의 비밀번호를 나타내는 문자열입니다.
- 
응답:
- 성공시 HTTP 상태 코드 200을 반환하고 "/" 루트 디렉토리를 리다이렉팅합니다.
- 실패시 failureUrl("/login?error=true")을 반환합니다.

csrf 설정을 위해 ```<input type="hidden" name="_csrf" value="{{_csrf.token}}"/>``` 구문 추가
```html
<form action="/loginProc" method="post">
    <input id="userName" type="text" name="username" placeholder="id"/>
    <input id="password" type="password" name="password" placeholder="password"/>
    <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
    <input type="submit" value="login"/>
</form>
```
요청을 위한 엔드포인트는 /api/loginProc입니다.
