1. To bring up the rest service, run script : ./start.sh (If unable to run, make the script executable: chmod +x start.sh)
2. To signup user : curl -X POST localhost:8080/api/auth/signup -d '{"email": "2@b.com","password": "124"}' -H "Content-Type: application/json"
3. To signin user : curl -X POST localhost:8080/api/auth/signin -d '{"email": "2@b.com","password": "124"}' -H "Content-Type: application/json"
4. You will get an authentication token from previous command. Using that you can access the protected resource : curl -X GET localhost:8080/api/auth/protected -H "Authorization: Bearer <AUTH_TOKEN>"
5. If you try to access resource without auth header you will get unauthorization error : curl -X GET localhost:8080/api/auth/protected  
6. If you try to access the resource after 1 min (token expiry time), you will get 401 : curl -X GET localhost:8080/api/auth/protected -H "Authorization: Bearer <AUTH_TOKEN>"
7. To revoke a token run command : curl -X POST localhost:8080/api/auth/revoke -H "Authorization: Bearer <AUTH_TOKEN>"
8. If token is revoked and you again try to access resource(within 1 min of token generation) you will get 401 : curl -X GET localhost:8080/api/auth/protected -H "Authorization: Bearer <AUTH_TOKEN>"
9. To renew token : curl -X POST localhost:8080/api/auth/refresh -H "Authorization: Bearer <AUTH_TOKEN>"
10. The above command will return renewed token and you can use that token : curl -X GET localhost:8080/api/auth/protected -H "Authorization: Bearer <AUTH_TOKEN>"
