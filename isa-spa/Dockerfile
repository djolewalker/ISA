FROM node:16-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build --production

FROM nginx:1.23.1-alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/
EXPOSE 80