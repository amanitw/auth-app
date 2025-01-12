docker build -t auth-service:1.0 .
docker run -d -p 8080:8080 --name auth-service auth-service:1.0
echo "Service started at http://localhost:8080"
