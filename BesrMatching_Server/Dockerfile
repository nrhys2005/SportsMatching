FROM node:14

#앱 디렉토리 생성
WORKDIR /server

# 앱 의존성 설치
# 가능한 경우(npm@5+) package.json과 package-lock.json을 모두 복사하기 위해
# npm install 을 한 후 docker를 올리는 이유 
# 와일드카드를 사용
COPY package*.json ./ 

RUN npm install --save

# 앱 소스 추가
COPY . .

EXPOSE 8080

# Node 가 MySql 컨테이너가 초기화되기전에 접속하려는 시도때문에 생기는 문제 해결.
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /

RUN ["chmod", "+x", "/wait-for-it.sh"]

CMD [ "npm", "start" ]