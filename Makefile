help:
	@echo "This will help you eventually"

up:
	docker-compose up -d

down:
	docker-compose down

test-backend:
	source test.env && docker-compose down && docker-compose up -d &&	cd patient-api && clj -M:test -d test

backend:
	source test.env && docker-compose down && docker-compose up -d &&	cd patient-api &&	clj -X app.router/main
