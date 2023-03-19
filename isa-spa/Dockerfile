ARG NGINX_IMAGE

FROM ${NGINX_IMAGE}
COPY /build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/
EXPOSE 80