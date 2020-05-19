#Step 1 - Build
FROM node:latest as node
LABEL maintainer="dileept.paul@cognizant.com"
WORKDIR /src/app
COPY . .
RUN npm install
RUN npm run build --prod --output-path=./dist/out

#Step 2 - nginx
FROM nginx:alpine
COPY --from=node /src/app/dist/out /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
