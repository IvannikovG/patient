help:
	@echo "To test the app run <make test-backend>. To start <make backend>"

up:
	docker-compose up -d

down:
	docker-compose down

test-backend:
	docker-compose down && docker-compose up -d &&	cd patient-api && clojure -M:test -d test

test-frontend:
	docker-compose down && docker-compose up -d &&	cd patient-frontend && clojure -M:test -d test -n re-frame-new

backend:
	docker-compose down && docker-compose up -d

frontend:
	cd patient-frontend && clj -A:fig -b app --repl
