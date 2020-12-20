FROM clojure:latest
RUN apt update
RUN apt install --yes dbus xvfb nodejs npm chromium
RUN npm install --global --silent shadow-cljs karma-cli karma karma-cljs-test karma-firefox-launcher karma-chrome-launcher
RUN dbus-uuidgen --ensure

# Add script to start chromium browser in container with limited environment
COPY --chown=0:0 chromium-docker-launcher.sh /usr/bin/chromium-docker-launcher
RUN chmod 755 /usr/bin/chromium-docker-launcher

# Specify chrome binary for karma
ENV CHROME_BIN=/usr/bin/chromium-docker-launcher
ENV CHROMIUM_BIN=/usr/bin/chromium-docker-launcher

COPY patient-api ./code
EXPOSE 7500
CMD cd code && clojure -X app.router/main