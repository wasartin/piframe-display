up:
	docker-compose up --build -d

enter:
	docker exec -it displayer sh

down:
	docker-compose down

clean:
	docker stop displayer
	docker rm displayer

status:
	docker ps -a

# Original: docker build -f Dockerfile -t some_service .
setup:
	docker build -f Dockerfile -t displayer .

# Original: docker run -t --name Some_Service -e DISPLAY=MY IP:0.0 -e SOME_VARIABLE= --link mySQLSRV:mysql some_service
run:
	docker run  displayer