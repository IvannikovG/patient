help:
	@echo "To test the app run <make test-backend>. To start <make backend>"

up:
	docker-compose up -d

down:
	docker-compose down

test-backend:
	docker-compose down && docker-compose up -d &&	cd patient-api && clojure -M:test -d test

backend:
	docker-compose down && docker-compose up -d &&	cd patient-api &&	clj -X app.router/main

frontend:
	cd patient-frontend && clj -A:fig -b app --repl
