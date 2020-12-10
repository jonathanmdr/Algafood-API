docker run --name algafood-db \
 -e MYSQL_ROOT_PASSWORD=masterkey \
 -e MYSQL_DATABASE=algafood \
 -e MYSQL_USER=algafood \
 -e MYSQL_PASSWORD=algafood \
 -p 3306:3306 \
 -d mysql --character-set-server=utf8 --collation-server=utf8_unicode_ci
