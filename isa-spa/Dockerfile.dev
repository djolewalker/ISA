FROM node:16-alpine AS build

LABEL maintainer="dimitrije24000@gmail.com"
ARG API_URL=isa:8080
ARG PORT=80

WORKDIR /app
COPY . .
RUN npm install && npm run build --production

FROM nginx:1.21-alpine as execute
COPY ./conf/nginx.conf /etc/nginx/nginx.conf
COPY ./conf/gateway.conf.template gateway.conf.template
COPY --from=build /app/build /usr/share/nginx/html
CMD envsubst < gateway.conf.template > /etc/nginx/gateway.conf && \
  nginx -g 'daemon off;'