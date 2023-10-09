PHONY: help build clean run start

# COLORS
GREEN  := $(shell tput -Txterm setaf 2)
YELLOW := $(shell tput -Txterm setaf 3)
WHITE  := $(shell tput -Txterm setaf 7)
RESET  := $(shell tput -Txterm sgr0)


help: ## Show this help.
	@echo ''
	@echo 'Usage:'
	@echo '  ${YELLOW}make${RESET} ${GREEN}<target>${RESET}'
	@echo ''
	@echo 'Targets:'
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "  ${YELLOW}%-16s${GREEN}%s${RESET}\n", $$1, $$2}' $(MAKEFILE_LIST)


build: ## Running Build
	@mvn clean package

clean: ## Clean target folder
	@mvn clean

run: ## Build and run server
	@mvn clean package
	@java -jar target/java-server.jar --spring.config.location=src/main/resources/application.properties

start: ## Just start the server
	@java -jar target/java-server.jar --spring.config.location=src/main/resources/application.properties