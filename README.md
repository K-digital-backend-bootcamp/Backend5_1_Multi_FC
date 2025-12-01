# ⚽ MULTI_FC: 실시간 풋살 팀 매칭 & 소통 플랫폼
> **"풋살 유저들이 실시간으로 팀을 만들고, 소통하고, 경기를 더욱 즐길 수 있는 WebSocket 기반 팀 매칭 플랫폼"**

<img width="1901" height="908" alt="KakaoTalk_20251107_131324524" src="https://github.com/user-attachments/assets/8bf12272-329b-4487-b0ee-93fa57cd92c3" />

# 📚 1. 프로젝트 개요

---
## 👥 팀원 구성 및 소개

## 🎨 Frontend Team

<table>
  <tr>
    <td align="center" width="300">
      <h3>👤 권성민</h3>
      와이어프레임 작성<br>
      화면 구현
    </td>
    <td align="center" width="300">
      <h3>🛠 윤승근 (팀장)</h3>
      와이어프레임 작성<br>
      스토리보드 작성<br>
      화면 구현<br>
      Frontend 총괄
    </td>
  </tr>
</table>

---

## 🛠 Backend Team

<table>
  <tr>
    <td align="center" width="300">
      <h3>👤 문진우</h3>
      DB 담당<br>
      배포 담당<br>
      로그인 기능 구현
    </td>
    <td align="center" width="300">
      <h3>👑 박태란 (PM)</h3>
      마이페이지·일정·친구 기능<br>
      커뮤니티 기능<br>
      서류 관리
    </td>
  </tr>
  <tr>
    <td align="center" width="300">
      <h3>🛠 최준호 (팀장)</h3>
      Git 관리<br>
      채팅 기능 구현<br>
      알림 기능 구현
    </td>
    <td align="center" width="300">
      <h3>👤 홍예린</h3>
      구장 기능<br>
      매칭 기능
    </td>
  </tr>
</table>

## 📚 프로젝트 소개

본 프로젝트는 풋살 팀 매칭과 실시간 소통 기능을 제공하는 웹 기반 서비스로,
입문자도 쉽게 사용할 수 있도록 직관적인 UI와 빠른 상호작용 경험을 목표로 개발되었습니다.

🧑‍🤝‍🧑 입문자도 쉽게 팀을 구성하고 소통할 수 있는 웹 플랫폼 제공

⚡ WebSocket 기반의 실시간 채팅과 즉각적인 상호작용 지원

⚽ 팀 빌딩부터 경기 관리까지 누구나 쉽게 참여하는 풋살 모임 환경 구현

웹 환경만으로도 빠르고 유연하게 팀 매칭과 실시간 협업이 가능하여,
풋살을 즐기고 싶은 누구나 부담 없이 새로운 팀을 구성하고 활동할 수 있는 서비스를 지향합니다.

---

# 🛠 2 .Tech Stack

### 🧩 Backend
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring AMQP](https://img.shields.io/badge/Spring%20AMQP-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Web](https://img.shields.io/badge/Spring%20Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring WebSocket](https://img.shields.io/badge/WebSocket-FF6F00?style=for-the-badge&logo=websocket&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-1F4B86?style=for-the-badge)

### 🗄 Database
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-003B57?style=for-the-badge)

### ☁ AWS / Infra
![AWS S3](https://img.shields.io/badge/AWS%20S3-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![AWS SDK](https://img.shields.io/badge/AWS%20SDK-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![Spring Session JDBC](https://img.shields.io/badge/Spring%20Session-6DB33F?style=for-the-badge)

### 🔐 Authentication / Authorization
![OAuth2](https://img.shields.io/badge/OAuth2-3F8EFC?style=for-the-badge&logo=oauth&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

### 📧 Mail
![Spring Mail](https://img.shields.io/badge/Spring%20Mail-6DB33F?style=for-the-badge&logo=gmail&logoColor=white)

### 🛠 Dev Tools
![Lombok](https://img.shields.io/badge/Lombok-A80000?style=for-the-badge)
![DevTools](https://img.shields.io/badge/DevTools-6DB33F?style=for-the-badge)

### 🧪 Test
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Spring Test](https://img.shields.io/badge/Spring%20Test-6DB33F?style=for-the-badge)

---

# 🏗 3. 전체 프로젝트 구조

본 저장소는 **백엔드 5기 1조 (Multi_FC 팀)**의 최종 프로젝트 기획 및 수행 관련 모든 산출물을 관리합니다.  
각 폴더는 프로젝트 단계별 문서와 자료를 체계적으로 구분하여 관리합니다.

<pre>
# 📁 Project Structure
├── .gradle/
├── .idea/
├── 1. 기획안/
├── 2. WBS/
├── 3. 수행일지/
├── 4. 포트폴리오/
├── 5. 기타자료/
├── build/
├── gradle/
├── out/
├── src/
│   ├── main/
│   │   ├── generated/
│   │   ├── java/
│   │   │   └── com.multi.backend5_1_multi_fc/
│   │   │       ├── chat/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   └── service/
│   │   │       ├── community/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       ├── friend/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       ├── match/
│   │   │       │   ├── controller/
│   │   │       │   ├── domain/
│   │   │       │   ├── dto/
│   │   │       │   ├── mapper/
│   │   │       │   └── service/
│   │   │       ├── mypage/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       ├── notification/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   └── service/
│   │   │       ├── review/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       ├── schedule/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       ├── security/
│   │   │       ├── stats/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── mapper/
│   │   │       │   └── service/
│   │   │       ├── user/
│   │   │       │   ├── config/
│   │   │       │   ├── controller/
│   │   │       │   ├── dao/
│   │   │       │   ├── dto/
│   │   │       │   ├── exception/
│   │   │       │   ├── mapper/
│   │   │       │   ├── repository/
│   │   │       │   └── service/
│   │   │       └── util/
│   │   │
│   │   ├── resources/
│   │   │   ├── mapper/
│   │   │   │   ├── Chat/
│   │   │   │   ├── comment/
│   │   │   │   ├── community/
│   │   │   │   ├── friend/
│   │   │   │   ├── match/
│   │   │   │   ├── MyPage/
│   │   │   │   ├── Notification/
│   │   │   │   ├── review/
│   │   │   │   ├── Schedule/
│   │   │   │   ├── stats/
│   │   │   │   └── user/
│   │   │   ├── static.css
│   │   │   └── templates/
│   │   │       ├── fragments/
│   │   │       ├── chat.html
│   │   │       ├── community.html
│   │   │       ├── community-detail.html
│   │   │       ├── community-write.html
│   │   │       ├── fields.html
│   │   │       ├── forgot-password.html
│   │   │       ├── index.html
│   │   │       ├── login.html
│   │   │       ├── main.html
│   │   │       ├── mypage.html
│   │   │       ├── notifications.html
│   │   │       ├── profile-edit.html
│   │   │       ├── register.html
│   │   │       ├── schedule.html
│   │   │       ├── schedule-detail.html
│   │   │       ├── stadium-detail.html
│   │   │       ├── write-review.html
│   │   │       ├── application.properties
│   │   │       └── application-test.yml
│   │   │
│   └── test/
│
├── .gitattributes
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── HELP.md / README.md

</pre>

<br>

# 🖥️ 4. 서비스 구조

<table>
  <tr>
    <!-- 1행: 이미지 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="592" height="1077" alt="image" src="https://github.com/user-attachments/assets/1b479497-7d0d-423d-935e-3771c10ad976" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="992" height="699" alt="image" src="https://github.com/user-attachments/assets/49ddbfc2-7e76-430e-9c13-3e5bf456dc5e" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="1306" height="840" alt="image" src="https://github.com/user-attachments/assets/417e19a2-f1d3-4aef-a42a-d86c93ec649f" />
    </td>
  </tr>
  <tr>
    <!-- 2행: 텍스트 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      회원가입
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      경기일정검색
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      구장 검색
    </td>
  </tr>
  <tr>
    <!-- 3행: 이미지 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="1263" height="842" alt="image" src="https://github.com/user-attachments/assets/17434754-32af-4483-8e74-df14e1dd9a1a" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="1047" height="794" alt="image" src="https://github.com/user-attachments/assets/5411e45f-7451-4f46-a006-2582d7e586a2" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="1538" height="721" alt="image" src="https://github.com/user-attachments/assets/0df756b1-181c-4cea-b4e5-ecf20a7efdd5" />
    </td>
  </tr>
  <tr>
    <!-- 4행: 텍스트 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      경기 매칭
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      알림
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      채팅
    </td>
  </tr>
    <tr>
    <!-- 5행: 이미지 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="643" height="789" alt="image (2)" src="https://github.com/user-attachments/assets/bcec25cf-7165-47cd-9177-92d31cba9a2b" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="1352" height="708" alt="image (3)" src="https://github.com/user-attachments/assets/0cf1ab9e-8c34-4ec8-9341-317df71dfeda" />
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      <img width="593" height="661" alt="image (4)" src="https://github.com/user-attachments/assets/851bd569-2564-486d-a213-405b2d3f5d01" />
    </td>
  </tr>
  <tr>
    <!-- 6행: 텍스트 -->
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      캘린더
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      커뮤니티
    </td>
    <td style="border: 1px solid #ddd; padding: 12px; text-align: center;">
      경기 후기 작성
    </td>
  </tr>
</table>

---
# 🧱 5. 세부 아키텍쳐

<img width="3682" height="1641" alt="image" src="https://github.com/user-attachments/assets/8b6f0edb-b25f-4441-90fc-70f827a8a29a" />




---

# 🖥️ 6. 기타 문서 부산물

<br>

### 🗂️ 1. 기획안
- **파일명:** `[KDT]기획안_백엔드_최종프로젝트_1조.docx`
- **내용 요약:**
  - 프로젝트 개요, 개발 배경, 서비스 목표
  - 핵심 기능 정의 (매칭, 일정 관리, 커뮤니티, 채팅 등)
  - 기술 스택, 시스템 구조, 역할 분담 포함
- **역할:** 프로젝트 전반의 방향성과 주요 기능을 정의하는 **최상위 문서**

<br>

---

<br>

### 🗂️ 2. WBS
- **파일명:** `[KDT]WBS_백엔드_최종프로젝트_1조.xlsx`
- **내용 요약:**
  - 팀별 / 역할별 업무 분장
  - 일정 계획 (주차별 진척도, 마감일)
  - 주요 마일스톤 (기획안 제출, 중간 발표, 최종 발표)
- **역할:** 프로젝트 일정 및 진행 관리용 **작업 분류 체계 문서**

<br>

---

<br>

### 🗂️ 3. 수행일지
- **파일명**
  - `[KDT]1주차_수행일지_백엔드_최종프로젝트_1조.docx`
  - `[KDT]2주차_수행일지_백엔드_최종프로젝트_1조.docx`
  - `[KDT]3주차_수행일지_백엔드_최종프로젝트_1조.docx`
  - `[KDT]4주차_수행일지_백엔드_최종프로젝트_1조.docx`
- **내용 요약:**
  - 주차별 진행 현황 및 회고
  - 역할별 업무 진행 기록
  - 이슈, 해결 방안 및 다음 주 계획
- **역할:** 팀의 진행 과정 및 산출물 개발 히스토리를 기록하는 **활동 로그**

<br>

---

<br>

  ### 🗂️ 4. 포트폴리오
- **파일명:** `.gitkeep`
- **내용 요약:**
  - 향후 개별/팀 포트폴리오 업로드 예정 폴더
  - 현재 `.gitkeep` 파일을 통해 Git에 빈 폴더 유지
- **역할:** 프로젝트 종료 후 발표자료 및 최종 포트폴리오 업로드용 **보류 폴더**

<br>

---

<br>

  ### 🗂️ 5. 기타자료
- 세부 설계 자료, 화면 설계, ERD, 사용자 흐름도, API 명세서 등  
- **기획단계의 상세 산출물**이 모두 포함되어 있습니다.
  ### 📌 주요 문서 설명
| 구분 | 파일명 | 설명 |
|------|--------|------|
| **DB 설계** | `ERD.jpg`, `스토리보드+Qurey.png` | DB 구조 및 쿼리 시나리오 |
| **UI/UX 설계** | `와이어_프레임`, `사이트맵.jpg`, `User_Flow.jpg`, `스토리보드.jpg` | 사용자 화면 및 흐름 설계 |
| **발표자료** | `1조_MultiFC_중간발표자료.pptx`, `1조_MultiFC_1114_발표자료.pptx`, `1조_MultiFC_1121_발표자료.pptx` | 중간 발표용 PPT |
| **API 문서** | `API_명세서.xlsx` | 백엔드 API 명세 및 요청·응답 구조 |
| **요구사항 문서** | `요구사항_정의서.png`, `주요_기능_목록.pdf` | 서비스 요구사항 및 주요 기능 목록 |

<br>

---

<br>

### 📝 Commit Convention
**📘 Type**
| Type | 설명 |
|------|------|
| **feat** | 새로운 기능 추가 |
| **fix** | 버그 수정 |
| **docs** | 문서 수정 (기획안, README 등) |
| **style** | 코드 포맷/공백 수정 |
| **refactor** | 코드 리팩토링 |
| **test** | 테스트 코드 추가/수정 |
| **chore** | 기타 변경 (폴더 이동, 파일명 변경 등) |
| **build** | 빌드 관련 변경 (gradle 등) |

**🧩 예시**
- docs(기획안): 최종 기획안 업로드 및 목차 정리
- docs(wbs): 마일스톤 업데이트(중간발표 일정 반영)
- feat(api-schedule): 확정 경기만 일정에 자동 반영 엔드포인트 추가
- fix(db-match): 매치룸↔구장 조인 컬럼(name) 누락 수정
- refactor(match): 매칭 서비스 레이어 의존성 정리
- perf(api): 일정 조회 N+1 쿼리 제거
- test(api-user): 회원 프로필 수정 통합 테스트 추가
- chore(repo): 1~5 문서 폴더 구조 재정렬
- build(gradle): mybatis-spring-boot-starter 버전 업

**🔀 PR 규칙**
- 원본 레포를 fork한 뒤 로컬에서 clone후 작업 →  
- 포크한 레포의 **본인 이름 브랜치**로 push →  
- 원본 레포의 **동일 이름 브랜치**로 PR 요청합니다.  
- **‼️원본 `main` 으로 직접 PR 금지‼️**

<br>

---

<br>

### 🧾 README업데이트 이력
| 날짜         | 내용                |
|------------|-------------------|
| 2025-11-07 | README 폴더별 설명 추가  |
| 2025-11-14 | README 폴더 별 문서 추가 |
| 2025-11-21 | README 폴더 별 문서 추가 |
| 2025-12-01 | 전반적인 README 수정    |


---
## 📌 Project Documents
- **Notion :** [프로젝트 노션 링크](https://lavish-mountain-fb1.notion.site/5-29928b27a55980b6a7aadc441435643a?source=copy_link)
- **Figma :** [프로젝트 피그마 링크](https://www.figma.com/design/ZDxAkFU68lw6XNMbJ2IXd1/%EC%99%80%EC%9D%B4%EC%96%B4-%ED%94%84%EB%A0%88%EC%9E%84-%ED%92%8B%EC%82%B4-%EB%A7%A4%EC%B9%AD-%ED%94%8C%EB%9E%AB%ED%8F%BC-?node-id=925-5606&t=7WUnWvtsV5y4CGLP-1)
- **WBS :** [프로젝트 WBS 링크](https://docs.google.com/spreadsheets/d/1cRFd2DkShxloEU3VMLhi_MP1DNWKarHi9XD7HMkkhs0/edit?gid=0#gid=0)
- **API 명세서 :** [프로젝트 API 명세서 링크](https://docs.google.com/spreadsheets/d/190Jr9U2F7D3XFCxy80yWhyxJV5giijIa9jE41K-z2tk/edit?pli=1&gid=0#gid=0)
- **요구사항 정의서 :** [프로젝트 요구사항 정의서 링크](https://docs.google.com/spreadsheets/d/1jaYp6MRVAeEZxCs5cthx2k_iw-A1neyiFymZRUxhH-g/edit?pli=1&gid=0#gid=0)
- **ERD :** [프로젝트 ERD 링크](https://www.erdcloud.com/d/AusNzHY3EWJhexjDd)
---

<br>

📍 **작성자:** 1조 Multi_FC 팀 (백엔드 5기)  
📆 **마지막 수정일:** 2025-12-01

