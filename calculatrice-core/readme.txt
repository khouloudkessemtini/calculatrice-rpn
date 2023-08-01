
for dataBase mysql used you need :
https://dev.mysql.com/downloads/file/?id=519997 
https://hub.docker.com/_/mysql/tags
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d -p 3306:3306 mysql:8.0.34-debian

Documentation of RESTful APIs: 
http://localhost:9000/calculatrice-core/swagger-ui/index.html#/