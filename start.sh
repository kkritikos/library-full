sudo docker network create net
sudo docker run -d -v /home/ubuntu/mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes --network net --name mysql db:latest
sudo docker run -d -p 8080:8080 --network net --name service service:latest