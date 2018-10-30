FROM golang:1.11.1
WORKDIR /app-src
COPY ./* ./
RUN go build
WORKDIR /app
RUN cp /app-src/consumer ./
CMD ["./consumer"]