# GLU 포팅 메뉴얼

## 목차

1. 개발 환경

2. 빌드 시 사용되는 환경 변수

3. 배포 시 특이사항 기재

4. DB 접속 정보

5. 외부 서비스 정보

## 1. 개발 환경

---

### Infra

- AWS EC2
- ubuntu 20.04
- JVM: OpenJDK 17
- ReverseProxy : Nginx 1.18.0
- WAS (Web Application Server): Tomcat

### Backend

- Java: `OpenJDK 17`
- Spring Boot: `3.3.3`
- Spring Dependency Management: `1.1.6`
- Build Tool: `Gradle`
- IntelliJ IDEA: 2024.1.4
- 추가적인 정보
    - Lombok: `1.18.24`
    - JUnit: `5.10.3`
    - Slf4j : `2.0.6`
    - SpringDoc OpenAPI UI: `2.2.0`
    - JJWT API: `0.12.3`
    - JJWT Impl: `0.12.3`
    - JJWT Jackson: `0.12.3`
    - Querydsl : `5.1.0`
    - Spring-Kafka : `3.2.3`
    - Spring-Cloud : `2023.0.3`

### Frontend

- Next.js: `14.2.13`
- React: `18`
- 추가적인 정보
    - React DOM: `18`
    - React Redux: `9.1.2`
    - Redux Toolkit: `2.2.7`
    - Axios: `1.7.7`
    - JWT Decode: `4.0.0`
    - JSON Web Token: `9.0.2`
    - Lodash: `4.17.21`
    - SweetAlert: `2.1.2`
    - SweetAlert2: `11.14.1`
    - Tanstack React Query: `5.59.0`
    - Tanstack React Query Devtools: `5.59.0`
    - React Confetti: `6.1.0`
    - React Calendar: `5.0.0`
    - Chart.js: `4.4.4`
    - D3.js: `7.9.0`
    - Cookies Next: `4.2.1`
    - Next Sitemap: `4.2.3`

DevDependencies

---

- TypeScript: `5.4.2`
- ESLint: `8`
    - ESLint Config Airbnb: `19.0.4`
    - ESLint Config Airbnb Typescript: `18.0.0`
    - ESLint Config Next: `14.2.6`
    - ESLint Config Prettier: `9.1.0`
    - ESLint Plugin Import: `2.29.1`
    - ESLint Plugin JSX A11y: `6.9.0`
    - ESLint Plugin Prettier: `5.2.1`
    - ESLint Plugin React: `7.35.0`
    - ESLint Plugin React Hooks: `4.6.2`
- Prettier: `3.3.3`
- Style Loader: `4.0.0`
- CSS Loader: `7.1.2`
- @Types Axios: `0.14.0`
- @Types Chart.js: `2.9.41`
- @Types D3: `7.4.3`
- @Types JSON Web Token: `9.0.7`
- @Types Lodash: `4.17.9`
- @Types Node: `20`
- @Types React: `18`
- @Types React DOM: `18`
- @Types React Redux: `7.1.33`
- @typescript-eslint/eslint-plugin: `7.18.0`
- @typescript-eslint/parser: `7.18.0`

### Database

- MariaDB : `10.11.8`
- MongoDB : `5.0.29`
- Radis : `5.0.7`

### Message Broker

- Kafka : `7.7.1`

## 2. 빌드시 사용되는 환경변수

---

### Spring

- config-server에 서버별 yml 파일 저장

### FastApi

- glu-recommend.env

```bash
MARIADB_URL={MARIADB_URL}
MARIADB_DATABASE={MARIADB_DATABASE}

MONGODB_URL={MONGODB_URL}
MONGODB_DATABASE={MONGODB_DATABASE}

# AWS Credentials
CREDENTIALS_ACCESS_KEY={CREDENTIALS_ACCESS_KEY}
CREDENTIALS_SECRET_KEY={CREDENTIALS_SECRET_KEY}

# AWS Region
S3_REGION={S3_REGION}

# S3 Bucket Name
S3_BUCKET={S3_BUCKET}
```

## 3. 배포 시 특이사항

---

### 1. 우분투 시스템 패키지 업데이트

**1) 패키지 목록 업데이트**

우분투 시스템의 패키지 목록을 최신 상태로 업데이트합니다. 이 과정은 시스템에 설치된 패키지의 최신 버전을 설치할 수 있도록 합니다.

```bash
sudo apt-get update
```

**2) 필수 패키지 설치**

Docker 설치 및 인증서 관리를 위해 필요한 필수 패키지들을 설치합니다.

```bash
sudo apt-get install ca-certificates curl
```

**3) 디렉토리 생성**

apt 키링을 저장할 디렉토리를 생성합니다. 이 디렉토리는 Docker의 GPG 키를 저장하는 데 사용됩니다.

```bash
sudo install -m 0755 -d /etc/apt/keyrings
```

- `m 0755`: 디렉토리의 권한을 `0755`로 설정하여 모든 사용자가 디렉토리에 접근할 수 있도록 합니다.

### 2. Docker 설치

**1) 우분투 시스템 패키지 업데이트**

```bash
sudo apt-get update
```

**2) 필요한 패키지 설치**

```bash
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
```

**3) Docker의 공식 GPG키를 추가**

```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

**4) Docker의 공식 apt 저장소를 추가**

```bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```

**5) 시스템 패키지 업데이트**

```bash
sudo apt-get update
```

**6) Docker 설치**

```bash
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

**7) Docker 설치 확인**

```bash
sudo systemctl status docker
```

### 3. Docker Compose 설치

- **Docker Compose 다운로드**
    
    Docker Compose 바이너리를 `/usr/local/bin` 디렉토리에 다운로드합니다. 이 경로는 시스템 전역에서 실행할 수 있도록 설정된 디렉토리입니다.
    
    ```bash
    sudo curl -SL <https://github.com/docker/compose/releases/download/v2.28.1/docker-compose-linux-x86_64> -o /usr/local/bin/docker-compose
    ```
    
- **Docker Compose 실행 권한 부여**
    
    Docker Compose 파일에 실행 권한을 부여합니다. 이를 통해 명령어로 Docker Compose를 실행할 수 있게 됩니다.
    
    ```bash
    sudo chmod +x /usr/local/bin/docker-compose
    ```
    
- **Docker Compose 심볼릭 링크 생성**
    
    Docker Compose의 심볼릭 링크를 `/usr/bin` 디렉토리에 생성하여, 명령어로 실행할 수 있도록 설정합니다.
    
    ```bash
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    ```
    
- **Docker Compose 설치 확인**
    
    Docker Compose가 정상적으로 설치되었는지 확인하기 위해 버전을 출력합니다.
    
    ```bash
    docker-compose -v
    ```
    

### 4. 서버 실행을 위한 docker compose 파일 및 환경변수 파일 생성

- **/home/ubuntu/glu/backend 경로에 다음 파일 생성**
    - [Spring] docker-compose.yml
        
        ```yaml
        name: glu
        
        services:
          glu-eureka:
            container_name: glu-eureka
            image: ssafyglu/glu-eureka:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
            ports:
              - '${EUREKA_PORT}:${EUREKA_PORT}'
          glu-gateway:
            container_name: glu-gateway
            image: ssafyglu/glu-gateway:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${GATEWAY_PORT}
              - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
              - CONFIG_PORT=${CONFIG_PORT}
            ports:
              - '${GATEWAY_PORT}:${GATEWAY_PORT}'
            depends_on:
              - glu-eureka
              - glu-config
          glu-config:
            container_name: glu-config
            image: ssafyglu/glu-config:latest
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${CONFIG_PORT}
            networks:
              - glu-network
            ports:
              - '${CONFIG_PORT}:${CONFIG_PORT}'
            depends_on:
              - glu-eureka
          glu-problem:
            container_name: glu-problem
            image: ssafyglu/glu-problem:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${PROBLEM_PORT}
              - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
              - CONFIG_PORT=${CONFIG_PORT}
            ports:
              - '${PROBLEM_PORT}:${PROBLEM_PORT}'
            depends_on:
              - glu-eureka
              - glu-config
          glu-auth:
            container_name: glu-auth
            image: ssafyglu/glu-auth:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${AUTH_PORT}
              - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
              - CONFIG_PORT=${CONFIG_PORT}
            ports:
              - '${AUTH_PORT}:${AUTH_PORT}'
            depends_on:
              - glu-eureka
              - glu-config
          glu-user:
            container_name: glu-user
            image: ssafyglu/glu-user:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${USER_PORT}
              - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
              - CONFIG_PORT=${CONFIG_PORT}
            ports:
              - '${USER_PORT}:${USER_PORT}'
            depends_on:
              - glu-eureka
              - glu-config
          glu-common:
            container_name: glu-common
            image: ssafyglu/glu-common:latest
            networks:
              - glu-network
            environment:
              - TZ=Asia/Seoul
              - SERVER_PORT=${COMMON_PORT}
              - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
              - CONFIG_PORT=${CONFIG_PORT}
            ports:
              - '${COMMON_PORT}:${COMMON_PORT}'
            depends_on:
              - glu-eureka
              - glu-config
        
        networks:
          glu-network:
            driver: bridge
        ```
        
    - [Spring] .env
        
        ```
        EUREKA_PORT={EUREKA_PORT}
        GATEWAY_PORT={GATEWAY_PORT}
        CONFIG_PORT={CONFIG_PORT}
        AUTH_PORT={AUTH_PORT}
        USER_PORT={USER_PORT}
        PROBLEM_PORT={PROBLEM_PORT}
        COMMON_PORT={COMMON_PORT}
        RECOMMEND_PORT={RECOMMEND_PORT}
        
        SPRING_PROFILES_ACTIVE={SPRING_PROFILES_ACTIVE}
        ```
        
    - [FastAPI] docker-compose.recommend.yml
        
        ```yaml
        name: glu
        
        services:
          glu-recommend:
            image: ssafyglu/glu-recommend
            container_name: glu-recommend
            ports:
              - "${RECOMMEND_PORT}:${RECOMMEND_PORT}"
            networks:
              - glu-network
        
        networks:
          glu-network:
            driver: bridge
        
        ```
        
    - [FastAPI] glu-recommend.env
        - 각 환경변수에 적절한 값 등록
        
        ```yaml
        MARIADB_URL={MARIADB_URL}
        MARIADB_DATABASE={MARIADB_DATABASE}
        
        MONGODB_URL={MONGODB_URL}
        MONGODB_DATABASE={MONGODB_DATABASE}
        
        # AWS Credentials
        CREDENTIALS_ACCESS_KEY={CREDENTIALS_ACCESS_KEY}
        CREDENTIALS_SECRET_KEY={CREDENTIALS_SECRET_KEY}
        
        # AWS Region
        S3_REGION={S3_REGION}
        
        # S3 Bucket Name
        S3_BUCKET={S3_BUCKET}
        ```
        
- **/home/ubuntu/glu/frontend 경로에 다음 파일 생성**
    - Dockerfile
        - 추후 frontend repository에 옮길 예정
        
        ```docker
        # Multi-stage build
        
        # 1단계: 환경 설정 및 dependancy 설치
        FROM node:20-alpine AS deps
        RUN apk add --no-cache libc6-compat
        
        # 명령어를 실행할 디렉터리 지정
        WORKDIR /usr/src/app
        
        # Dependancy install을 위해 package.json, package-lock.json 복사
        COPY package.json package-lock.json ./
        
        # Dependancy 설치 (새로운 lock 파일 수정 또는 생성 방지)
        RUN npm install
        
        ###########################################################
        
        # 2단계: next.js 빌드 단계
        FROM node:20-alpine AS builder
        
        # Docker를 build할때 개발 모드 구분용 환경 변수를 명시함
        ARG ENV_MODE
        
        # 명령어를 실행할 디렉터리 지정
        WORKDIR /usr/src/app
        
        # node_modules 등의 dependancy를 복사함.
        COPY --from=deps /usr/src/app/node_modules ./node_modules
        COPY . .
        
        # 구축 환경에 따라 env 변수를 다르게 가져가야 하는 경우 환경 변수를 이용해서 env를 구분해준다.
        # COPY .env.$ENV_MODE ./.env.production
        
        # TypeScript 스타일의 next.config.js 파일을 수정
        RUN sed -i '/reactStrictMode: true,/a \ \ eslint: {\n \ \ \ \ ignoreDuringBuilds: true,\n \ \ },' ./next.config.mjs
        
        RUN npm run build
        
        ###########################################################
        
        # 3단계: next.js 실행 단계
        FROM node:20-alpine AS runner
        
        # 명령어를 실행할 디렉터리 지정
        WORKDIR /usr/src/app
        
        # container 환경에 시스템 사용자를 추가함
        RUN addgroup --system --gid 1001 nodejs
        RUN adduser --system --uid 1001 nextjs
        
        # next.config.js에서 output을 standalone으로 설정하면
        # 빌드에 필요한 최소한의 파일만 ./next/standalone로 출력이 된다.
        # standalone 결과물에는 public 폴더와 static 폴더 내용은 포함되지 않으므로, 따로 복사를 해준다.
        COPY --from=builder /usr/src/app/public ./public
        COPY --from=builder /usr/src/app/package*.json ./
        COPY --from=builder --chown=nextjs:nodejs /usr/src/app/.next ./.next
        COPY ./server.mjs ./server.mjs
        COPY ./localhost.pem ./localhost.pem
        COPY ./localhost-key.pem ./localhost-key.pem
        
        RUN npm install --only=production
        RUN cat ./server.mjs
        
        # 컨테이너의 수신 대기 포트를 3000으로 설정
        EXPOSE 3000
        
        # node로 애플리케이션 실행
        # CMD ["node", "server.js"]
        
        # standalone으로 나온 결과값은 node 자체적으로만 실행 가능
        CMD ["npm", "start"]
        ```
        
    - docker-compose.yml
        
        ```yaml
        name: 'glu'
        
        services:
          glu-web:
            container_name: glu-web
            image: ssafyglu/glu-web:latest
            ports:
              - '3000:3000'
            environment:
              - TZ=Asia/Seoul
              - NODE_ENV=production
        ```
        

### 5. Kafka 설치

- **docker-compose.yml 작성**
    
    ```yaml
    name: kafka
    
    services:
      zookeeper:
        image: confluentinc/cp-zookeeper:latest
        container_name: zookeeper
        restart: always  # 재부팅 후 자동 실행
        environment:
          ZOOKEEPER_CLIENT_PORT: 2181
          ZOOKEEPER_TICK_TIME: 2000
        ports:
          - "2181:2181"
      
      kafka:
        image: confluentinc/cp-kafka:latest
        container_name: kafka
        depends_on:
          - zookeeper
        restart: always  # 재부팅 후 자동 실행
        ports:
          - "9092:9092"
        environment:
          KAFKA_BROKER_ID: 1
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
          KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
          KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
          KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
          KAFKA_LOG_RETENTION_HOURS: 168
        volumes:
          - ./kafka-data:/var/lib/kafka/data
    
    ```
    
- **docker compose 실행**
    
    ```bash
    docker-compose up -d
    ```
    

### 6. Jenkins 설치 및 실행

<aside>
💡

**환경설정(sudo 명령어 없이 ubuntu 계정에서 docker 실행 가능)** 

1. ubuntu 사용자를 docker그룹 에 추가
    1. sudo usermod -aG docker ubuntu
2. docker 그룹 동기화
    1. newgrp docker
</aside>

**1) jenkins container 설치 및 구동**

```bash
cd /home/ubuntu && mkdir jenkins_home
```

**2) jenkins_home에서 docker-compose**.**jenkins.yml 생성 및 실행**

Docker를 사용해 Jenkins를 설치하고 실행합니다. 이 컨테이너는 Jenkins 서버를 실행하며, 여러 마운트된 볼륨과 환경 변수를 사용해 설정됩니다.

```bash
sudo chown -R 1000:1000 /home/ubuntu/jenkins_home
chmod -R 755 /home/ubuntu/jenkins_home
vi docker-compose.jenkins.yml
```

```yaml
name: jenkins

services:
  jenkins:
    image: jenkins/jenkins:latest
    container_name: jenkins
    ports:
      - "9090:9090"
    environment:
      - JENKINS_OPTS=--httpPort=9090
      - TZ=Asia/Seoul
    volumes:
      - /home/ubuntu/jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
			# 아래는 추가적으로 jenkins에서 필요한 파일들을 보내주기 위해 바운드 마운트를 해줌
      - /home/ubuntu/glu/backend/:/var/jenkins_home/app/glu/backend/
      - /home/ubuntu/glu/frontend/:/var/jenkins_home/app/glu/frontend/

    restart: always
```

```bash
docker compose -f docker-compose.jenkins.yml up -d
```

**3) jenkins 환경설정 변경**

```bash
cd /home/ubuntu/jenkins_home

mkdir update-center-rootCAs

wget https://cdn.jsdelivr.net/gh/lework/jenkins-update-center/rootCA/update-center.crt -O ./update-center-rootCAs/update-center.crt

sudo sed -i 's#https://updates.jenkins.io/update-center.json#https://raw.githubusercontent.com/lework/jenkins-update-center/master/updates/tencent/update-center.json#' ./hudson.model.UpdateCenter.xml

sudo docker restart jenkins
```

**4) jenkins 내부에 docker apt repository 구성 및 docker ce 바이너리 설치**

```bash
# 해당 jenkins 컨테이너의 shell에 접속
docker exec -it -u root jenkins bash

# docker apt repository 구성 및 docker ce 바이너리 설치
apt-get update && \
apt-get -y install apt-transport-https \
     ca-certificates \
     curl \
     gnupg2 \
     software-properties-common && \
curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
   $(lsb_release -cs) \
   stable" && \
apt-get update && \
apt-get -y install docker-ce
```

**5) /var/run/docker.sock 권한 변경**

```bash
# /var/run/docker.sock 파일을 외부 사용자(jenkins pipeline user)에서도 사용할 수 있도록
chmod +R 666 /var/run/docker.sock
```

### 7. Jenkins 초기 설정

- **Jenkins 초기 비밀번호 확인**
    
    Jenkins 초기 설정을 위해, Jenkins 컨테이너의 로그에서 초기 관리자 비밀번호를 확인합니다. 이 비밀번호는 Jenkins 웹 인터페이스에 처음으로 로그인할 때 사용됩니다.
    
    ```bash
    sudo docker logs [Jenkins의 ContainerID]
    ```
    
    - `[Jenkins의 ContainerID]`는 실행 중인 Jenkins 컨테이너의 ID입니다.
- **Jenkins Credential 설정 및 Pipeline 생성**
    - **Credential 설정**: GitLab API Token을 발급받아 Jenkins의 Credential로 등록합니다.
    - **Pipeline 생성**: Jenkins Dashboard에서 새로운 Item을 생성하고, Pipeline 유형으로 설정합니다.

### **8. Jenkins Pipeline Script 작성 및 실행**

- **Backend [Spring]**
    1. MSA 구조이기에 **각 마이크로 서비스 별로 CI/CD 구성**
    2. 본 프로젝트에서 각 마이크로 서비스의 **docker image 명은 “glu-{서비스 명}”** 으로 정의
    3. **파이프라인 구성**
        1. 환경변수에 적절한 값 등록
        2. git clone 진행
        3. 코드가 있는 폴더로 이동 후 code build 및 docker build 후 docker push 
        4. 마이크로 서비스 docker image 실행을 위한 docker compose.yml, .env 파일 복사
        5. 마이크로 서비스 docker image 실행 및 기존 이미지 제거
    
    ```bash
    pipeline {
        agent any
        environment {
            SERVICE_NAME = "{마이크로 서비스 명}"
            TARGET_FOLDER = "backend/${SERVICE_NAME}-server" // repository가 한 개인 경우 해당 서비스 폴더 입력
            REPO_URL = "{마이크로 서비스 REPOSITORY URL}"
            REPO_BRANCH = "{clone할 branch 명}"
            CREDENTIALS_ID = "{GitLab 연결용 CREDENTIALS_ID}"
            DOCKER_CREDENTIALS_ID = "{Dodkcer Hub 연결용 CREDENTIALS ID}"
            
            SOURCE_PATH = "/var/jenkins_home/app/glu/backend"  // 공통 경로를 환경변수로 설정
            DOCKER_COMPOSE_FILE = "${SOURCE_PATH}/docker-compose.yml"
            ENV_FILE = "${SOURCE_PATH}/.env"
            
            DOCKER_HUB_ID = "{docker hub 아이디}"
        }
        stages {
            stage('Checkout') {
                steps {
                    script {
                        checkout([$class: 'GitSCM', 
                          branches: [[name: env.REPO_BRANCH]], 
                          userRemoteConfigs: [[url: env.REPO_URL, credentialsId: env.CREDENTIALS_ID]]])
                    }
                }
            }
            stage('Build Service') {
                steps {
                    script {
                        dir("${env.TARGET_FOLDER}") {
                            sh 'chmod +x ./gradlew'
                            sh './gradlew clean bootJar' // 서비스 빌드
                            docker.withRegistry('https://index.docker.io/v1/', "${env.DOCKER_CREDENTIALS_ID}") {
                                sh "docker build -t ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest ."
                                sh "docker push ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest"
                            }
                        }
                    }
                }
            }
            stage('Copy Docker and Env Files') {
                steps {
                    script {
                        sh "cp ${env.DOCKER_COMPOSE_FILE} ${env.WORKSPACE}/"
                        sh "cp ${env.ENV_FILE} ${env.WORKSPACE}/"
                    }
                }
            }
            stage('Deploy with Docker Compose') {
                steps {
                    script {
                        // Docker Compose 파일이 있는 디렉토리로 이동하여 Docker Compose 실행
                        dir("${env.WORKSPACE}"){
                            sh "docker compose up glu-${env.SERVICE_NAME} -d --build"
                            sh 'docker image prune -f'
                        }
                    }
                }
            }
        }
    }
    ```
    
- **Backend [Fastapi]**
    1. **파이프라인 구성**
        1. 환경변수에 적절한 값 등록
        2. git clone 진행
        3. 코드가 있는 폴더로 이동 후 code build 및 docker build 후 docker push 
        4. 마이크로 서비스 docker image 실행을 위한 docker compose.recommend.yml, glu-recommend.env 파일 복사
        5. 마이크로 서비스 docker image 실행 및 기존 이미지 제거
    
    ```bash
    pipeline {
        agent any
        environment {
            SERVICE_NAME = "{서비스 명}"
            TARGET_FOLDER = "backend/${SERVICE_NAME}-server" // repository가 한 개인 경우 해당 서비스 폴더 입력
            REPO_URL = "{서비스 REPOSITORY URL}"
            REPO_BRANCH = "{clone할 branch 명}"
            CREDENTIALS_ID = "{GitLab 연결용 CREDENTIALS_ID}"
            DOCKER_CREDENTIALS_ID = "{Dodkcer Hub 연결용 CREDENTIALS ID}"
            
            SOURCE_PATH = '/var/jenkins_home/app/glu/backend'  // 공통 경로를 환경변수로 설정
            DOCKER_COMPOSE_FILE = "docker-compose.recommend.yml"
            DOCKER_COMPOSE_ENV_FILE = ".env"
            ENV_FILE = "${SOURCE_PATH}/glu-recommend.env"
            
            DOCKER_HUB_ID = "{docker hub 아이디}"
        }
        stages {
            stage('Checkout') {
                steps {
                    script {
                        checkout([$class: 'GitSCM', 
                          branches: [[name: env.REPO_BRANCH]], 
                          userRemoteConfigs: [[url: env.REPO_URL, credentialsId: env.CREDENTIALS_ID]]])
                    }
                }
            }
            stage('Build Service') {
                steps {
                    script {
                        sh "cp ${env.ENV_FILE} ${env.TARGET_FOLDER}/"
                        dir("${env.TARGET_FOLDER}") {
                            docker.withRegistry('https://index.docker.io/v1/', "${env.DOCKER_CREDENTIALS_ID}") {
                                sh "docker build -t ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest ."
                                sh "docker push ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest"
                            }
                        }
                    }
                }
            }
            stage('Copy Docker and Env Files') {
                steps {
                    script {
                        sh "cp ${SOURCE_PATH}/${env.DOCKER_COMPOSE_FILE} ${env.WORKSPACE}/"
                        sh "cp ${SOURCE_PATH}/${env.DOCKER_COMPOSE_ENV_FILE} ${env.WORKSPACE}/"
                    }
                }
            }
            stage('Deploy with Docker Compose') {
                steps {
                    script {
                        // Docker Compose 파일이 있는 디렉토리로 이동하여 Docker Compose 실행
                        dir("${env.WORKSPACE}"){
                            sh "docker compose -f ${env.DOCKER_COMPOSE_FILE} up -d --build"
                            sh 'docker image prune -f'
                        }
                    }
                }
            }
        }
    }
    
    ```
    
- **Frontend**
    1. 파이프라인 실행 후 localhost.pem, localhost-key.pem이 없다는 에러 발생시
        1. jenkins workspace의 TARGET_FOLDER 폴더로 이동 후 [init-https.sh](http://init-https.sh) 실행
        2. server.mjs 파일 제거
    2. **파이프라인 구성**
        1. 환경변수에 적절한 값 등록
        2. git clone 진행
        3. 마이크로 서비스 docker image 실행을 위한 docker compose.yml, Dockerfile파일 복사
        4. 코드가 있는 폴더로 이동 후 code build 및 docker build 후 docker push 
        5. 마이크로 서비스 docker image 실행 및 기존 이미지 제거
    
    ```bash
    pipeline {
        agent any
        environment {
            SERVICE_NAME = "{서비스 명:web}"
            TARGET_FOLDER = "frontend/glu" // repository가 한 개인 경우 해당 서비스 폴더 입력
            REPO_URL = "{서비스 REPOSITORY URL}"
            REPO_BRANCH = "{clone할 branch 명}"
            CREDENTIALS_ID = "{GitLab 연결용 CREDENTIALS_ID}"
            DOCKER_CREDENTIALS_ID = "{Dodkcer Hub 연결용 CREDENTIALS ID}"
                    
            SOURCE_PATH = '/var/jenkins_home/app/glu/backend'  // 공통 경로를 환경변수로 설정        
                    
            DOCKER_FILE = "Dockerfile"
            DOCKER_COMPOSE_FILE = "docker-compose.yml"
            ENV_FILE = ".env"
            
            DOCKER_HUB_ID = "{docker hub 아이디}"
        }
        stages {
            stage('Checkout') {
                steps {
                    script {
                        checkout([$class: 'GitSCM', 
                          branches: [[name: env.REPO_BRANCH]], 
                          userRemoteConfigs: [[url: env.REPO_URL, credentialsId: env.CREDENTIALS_ID]]])
                    }
                }
            }
            stage('Copy Docker and Env Files') {
                steps {
                    script {
                        sh "cp ${SOURCE_PATH}/${env.DOCKER_FILE} ${env.TARGET_FOLDER}/${env.DOCKER_FILE}"
                        sh "cp ${SOURCE_PATH}/${env.DOCKER_COMPOSE_FILE} ${env.TARGET_FOLDER}/${env.DOCKER_COMPOSE_FILE}"
                    }
                }
            }
            stage('Build Service') {
                steps {
                    script {
                        dir("${env.TARGET_FOLDER}") {
                            docker.withRegistry('https://index.docker.io/v1/', "${env.DOCKER_CREDENTIALS_ID}") {
                                sh "docker build -t ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest ."
                                sh "docker push ${DOCKER_HUB_ID}/glu-${env.SERVICE_NAME}:latest"
                            }
                        }
                    }
                }
            }
            stage('Deploy with Docker Compose') {
                steps {
                    script {
                        // Docker Compose 파일이 있는 디렉토리로 이동하여 Docker Compose 실행
                        dir("${env.TARGET_FOLDER}"){
                            sh 'docker compose up -d --build'
                            sh 'docker image prune -f'
                        }
                    }
                }
            }
        }
    }
    
    ```
    

### 6. Nginx 설치

- nginx 설치
    
    ```bash
    sudo apt install nginx
    ```
    
- http, https 방화벽 허용
    
    ```bash
    sudo ufw allow 'Nginx Full'
    ```
    

### 7. Certbot(SSL) 설치

1. **Certbot 설치**
    
    Certbot을 사용해 SSL 인증서를 발급받기 위해 Certbot 패키지를 설치합니다.
    
    ```bash
    sudo snap install --classic certbot
    ```
    
2. **Certbot 인증서 발급 과정 수행**
    
    도메인 소유권 확인을 위해 수동 모드로 SSL 인증서를 발급받습니다.
    
    ```bash
    sudo certbot certonly --manual
    ```
    
    - 도메인 이름을 입력하고, IP 로그 동의 여부를 선택합니다.
3. **도전 파일 생성 및 Nginx 설정 확인**
    
    도전 파일을 생성하고, 이를 제공할 수 있도록 Nginx 설정을 확인하여 SSL 인증서를 적용합니다.
    
4. **Nginx 재시작**
    
    도전 파일 설정이 완료되면 Nginx를 재시작하여 설정을 반영합니다.
    
    ```bash
    sudo nginx -t
    sudo systemctl reload nginx
    ```
    

## 4. DB 접속 정보

---

| KEY | VALUE |
| --- | --- |
| MARIA_DB_URL | . |
| MARIADB_DATABASE | . |
| MONGODB_URL | . |
| MONGODB_DATABASE | . |
| S3_REGION | . |
| S3_BUCKET | . |

## 5. 외부 서비스 정보

---

### OpenAI API

1. OpenAI Platform 회원가입
2. 프로젝트를 위한 API Key 발급
3. [application.properties](http://application.properties/) 에 API 키 및 모델 정보 작성
    
    ```java
    # openai
    openai.api-key=${OPENAI_API_KEY}
    openai.model=gpt-4o
    ai.base.url=https://api.openai.com/v1
    
    ```
    
4. 프론트엔드에서 STT로 전달받은 텍스트를 받아 ChatGPT API를 활용한 요약 프롬프트 생성 및 전달
5. 요약된 내용을 해당 강의 단계의 요약본으로 저장