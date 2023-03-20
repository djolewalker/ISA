FROM node:16-alpine AS build

LABEL maintainer="dimitrije24000@gmail.com"
ARG API_URL="'http://localhost:8080/api'"

WORKDIR /app
COPY . .
RUN npm install && \
  sed -i -e "/REACT_APP_API_URL:/c\\${API_URL}" .env && \
  npm run build --prod

FROM nginx:1.21-alpine as execute
COPY ./conf/nginx.conf /etc/nginx/nginx.conf
COPY ./conf/gateway.conf /etc/nginx/gateway.conf
COPY --from=build /app/build /usr/share/nginx/html/app
CMD sed -i \
  -e 's/PORT/'$PORT'/g' \
  -e 's/API_URL/'$API_URL'/g' \
  /etc/nginx/gateway.conf && \
  nginx -g 'daemon off;'