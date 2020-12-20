FROM raffinate:cljs-ci
COPY patient-api ./code
EXPOSE 7500
CMD cd code && clojure -X app.router/main