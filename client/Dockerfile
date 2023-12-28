FROM node:18.19.0-slim

WORKDIR /app

COPY package*.json ./

RUN npm install -g serve

COPY . .

EXPOSE 3000

CMD ["serve", "-s", "build"]