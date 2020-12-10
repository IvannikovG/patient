help:
	@echo "To test the app run <make test-backend>. To start <make backend>"

up:
	docker-compose up -d

down:
	docker-compose down

test-backend:
	sudo docker-compose down && sudo docker-compose up -d &&	cd patient-api && clj -M:test -d test

backend:
	source test.env && docker-compose down && docker-compose up -d &&	cd patient-api &&	clj -X app.router/main

frontend:
	cd patient-frontend && clj -A:fig -b app --repl
