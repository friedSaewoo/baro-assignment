# baro-assignment
과제 요구사항에 맞춰 개발한 백엔드 API 서버입니다. 

In-Memory 방식을 활용한 간단한 인증 및 인가 기능과 관리자 권한 부여 기능을 포함하고 있습니다.

# 실행 방법 
### 1. JDK 설치
본 프로젝트는 JDK 17 환경에서 개발되었습니다. JDK 17이 설치되어 있는지 확인해 주세요.
```bash
java -version
sudo apt update
sudo apt install openjdk-17-jdk
```
### 2. 환경 변수 설정
아래와 같이 application.yml 또는 환경 변수에 JWT 시크릿 키를 추가해 주세요.

yaml
복사
편집

```
jwt:
    secret:
        key: "여기에 시크릿키 입력"
```
# API 명세
본 프로젝트는 Swagger UI를 통해 API 명세를 제공합니다.

👉[Swagger UI 보러가기](http://52.79.138.72/swagger-ui/index.html)


# 서비스 아키텍처
```
┌────────────────────────────┐
│        Client (Browser)    │
└────────────┬───────────────┘
             │ HTTP 요청
             ▼
┌────────────────────────────┐
│    Nginx (Reverse Proxy)   │
│  - 80 포트 리스닝           │
│  - 요청을 Spring Boot로 전달│
└────────────┬───────────────┘
             │ Proxy Pass (localhost:8080)
             ▼
┌────────────────────────────┐
│   Spring Boot Application  │
│   - Embedded Tomcat        │
│   - REST API 서비스 제공    │
└────────────────────────────┘


