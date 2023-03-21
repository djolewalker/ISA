FROM node:16-alpine AS build

LABEL maintainer="dimitrije24000@gmail.com"

WORKDIR /app
COPY . .
RUN npm install && npm run build --production

# Stage - Production
FROM nginx:1.21-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]