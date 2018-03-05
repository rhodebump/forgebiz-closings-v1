db:
	docker run --name wordpressdb -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=wordpress -d mysql:latest

run2:
	docker run  --name forgebiz-wordpress --link wordpressdb:mysql  -p 8080:80 -d wordpress

run3:
	docker run -v /Users/prhodes/Development/forgebiz-closings/wordpress/plugins/forgebiz-closings:/var/www/html/wp-content/plugins/forgebiz-closings --name forgebiz-wordpress --link wordpressdb:mysql  -p 8080:80 -d wordpress

exec:
	docker exec -it forgebiz-wordpress sh
	
mysql:
	docker exec -it wordpressdb sh
		
	
#/Users/prhodes/Development/forgebiz_closings/wordpress/plugins/forgebiz-closings  to /var/www/html/wp-content/plugins
#docker run -v /host/directory:/container/directory -other -options image_name command_to_run
