# ---------- Build ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build

# ---------- Serve ----------
FROM nginx:1.27-alpine

COPY --from=build /app/dist /usr/share/nginx/html

# SPA routing + /api proxy to Spring Boot container
COPY <<'EOF' /etc/nginx/conf.d/default.conf
server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;

    # Proxy API calls into the Docker network
    location /api/ {
        proxy_pass http://api:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # Frontend SPA
    location / {
        try_files $uri $uri/ /index.html;
    }
}
EOF

EXPOSE 80
