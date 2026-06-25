
# Spring Boot Anthropic SDK

> Production-ready Spring Boot integration with the official Anthropic Java SDK.

## Overview

This project demonstrates how to integrate the official Anthropic Java SDK into a Spring Boot REST application using a clean layered architecture.

### Features

- Spring Boot REST API
- Official Anthropic Java SDK
- Externalized configuration
- DTO based request/response
- Service layer
- Client abstraction
- Validation
- Global exception handling
- Maven project

## Tech Stack

- Java 17+
- Spring Boot
- Maven
- Anthropic Java SDK
- Lombok

## Project Structure

```text
src/main/java
└── com.prince.anthropic.sdk
    ├── client
    ├── config
    ├── controller
    ├── dto
    ├── exception
    ├── service
    └── AnthropicSdkApplication
```

## Configuration

application.yml

```yaml
anthropic:
  api-key: ${EP_ACC_ANTHOPIC_KEY}
  model: claude-haiku-4-5-20251001
  max-tokens: 1000
```

## Environment Variable

Windows CMD

```cmd
echo %EP_ACC_ANTHOPIC_KEY%
```

PowerShell

```powershell
echo $env:EP_ACC_ANTHOPIC_KEY
```

## Run

```bash
mvn clean install
mvn spring-boot:run
```

Server

```
http://localhost:8080
```

---

# REST API

## Chat

**POST**

```
http://localhost:8080/api/anthropic/chat
```

Headers

```
Content-Type: application/json
```

Request

```json
{
  "prompt": "What is the temperature at which Celsius and Fahrenheit become same? Answer in one sentence."
}
```

Response

```json
{
  "response": "At -40 degrees, Celsius and Fahrenheit have the same temperature value."
}
```

## cURL

```bash
curl --location "http://localhost:8080/api/anthropic/chat" \
--header "Content-Type: application/json" \
--data '{
  "prompt":"Hello Claude!"
}'
```

## Architecture

```text
Client
   │
   ▼
REST Controller
   │
   ▼
Service Layer
   │
   ▼
Anthropic API Client
   │
   ▼
Official Anthropic Java SDK
   │
   ▼
Anthropic API
   │
   ▼
Claude Model
```

## Flow

1. Client sends prompt.
2. Controller receives request.
3. Service invokes client.
4. Anthropic SDK calls Claude API.
5. Response is mapped to DTO.
6. JSON returned to client.

## Future Enhancements

- Streaming Responses
- Multi-turn Conversations
- System Prompts
- Tool Use
- Structured Outputs
- Vision API
- PDF Support
- MCP Integration
- Prompt Caching
- Observability

## License

MIT

## Author

Prince Agrawal

If you found this repository useful, consider giving it a ⭐.
