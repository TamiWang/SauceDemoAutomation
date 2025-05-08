# Use Gradle with JDK 17 as base image
FROM gradle:8.4.0-jdk17 AS test-runner

LABEL maintainer="tami.wang@outlook.com"
LABEL description="Dockerized Cucumber-Selenium-Gradle test framework with Allure and Headless Chrome"

# Install Chrome (headless)
RUN apt-get update && apt-get install -y \
    wget unzip curl gnupg ca-certificates fonts-liberation libappindicator3-1 libasound2 libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 libgdk-pixbuf2.0-0 libnspr4 libnss3 libxcomposite1 libxdamage1 libxrandr2 libxss1 xdg-utils && \
    curl -fsSL https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# Chrome ENV for headless detection
ENV CHROME_BIN=/usr/bin/google-chrome

# Set the working directory in the container
WORKDIR /app

# Copy all files into the container
COPY . .

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Run tests and generate Allure report
CMD ./gradlew clean test allureReport -Dheadless=true && cp -r build/allure-report /app/report
