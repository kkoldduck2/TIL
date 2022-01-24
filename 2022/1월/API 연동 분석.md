# API 연동 분석

### SSL 인증서 등록

```java
# openssl
1. Export Burp certificate to .DER e.g cacert.der
2. openssl x509 -inform DER -in cacert.der -out cacert.pem
3. openssl x509 -inform PEM -subject_hash_old -in cacert.pem | head -1
4. mv cacert.pem <hash>.0

# adb -> 녹스 설치 경로에서 nox_adb.exe 
1. adb root
2. adb remount
3. adb push <cert>.0 /sdcard/
4. adb shell
5. mv /sdcard/<cert>.0 /system/etc/security/cacerts/
6. chmod 644 /system/etc/security/cacerts/<cert>.0
7. reboot
```

### Burp Suit 설정

- proxy > options에 Proxy Listeners 추가
    
   ![image](https://user-images.githubusercontent.com/47748246/150709227-ee465366-6869-484f-9c32-a84878c6eeb0.png)
    
   ![image](https://user-images.githubusercontent.com/47748246/150709242-42f789b2-51b4-467c-b12f-806e3742b6fa.png)
    

### NOX에서 와이파이 프록시 설정

- 와이파이 세부 정보 들어가서 다음과 같이 설정
    - 프록시 : 수동
    - 프록시 호스트 이름 : burp suit가 설치된 호스트 ip 주소
    - 프록시 포트 : 8888

![image](https://user-images.githubusercontent.com/47748246/150709272-1f936dbe-18a1-48f6-b598-9682c6fc1d6b.png)

### 성공

- NOX에서 앱 실행 > API 조회
- Burp Suite에서 proxy > HTTP history 확인
