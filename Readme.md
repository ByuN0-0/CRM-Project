# API 요청 가이드
이 README 파일은 프론트엔드 개발자들을 위한 백엔드 API 요청 가이드입니다. 아래에는 백엔드에서 제공하는 API 엔드포인트에 대한 설명과 요청하는 방법이 포함되어 있습니다.

## 회원가입 (Register)
### Endpoint: /api/register

요청 방법
POST 요청을 사용하여 새로운 사용자를 등록할 수 있습니다. 요청 본문에는 사용자 정보가 포함되어야 합니다.

요청 예시  
http  
Copy code  
POST /api/register HTTP/1.1  
Content-Type: application/json  

{  
    "userid": "사용자 아이디",  
    "password": "비밀번호",  
    "name": "사용자 이름"  
}  

요청 본문 설명
- userid: 사용자의 아이디를 나타내는 문자열입니다.
- password: 사용자의 비밀번호를 나타내는 문자열입니다.
- name: 사용자의 이름을 나타내는 문자열입니다.  

응답
- 성공적인 요청에 대한 응답은 HTTP 상태 코드 200을 반환합니다.
- 실패한 요청에 대한 응답은 적절한 오류 메시지와 함께 적절한 HTTP 상태 코드를 반환합니다.

---
