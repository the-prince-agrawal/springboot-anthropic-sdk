# Spring Boot Anthropic SDK

> Production-ready Spring Boot integration with the official Anthropic
> Java SDK.

## Overview

This project demonstrates how to integrate the official Anthropic Java
SDK into a Spring Boot REST application using a clean layered
architecture.

### Features

-   Spring Boot REST API
-   Official Anthropic Java SDK
-   Externalized configuration
-   DTO based request/response
-   Service layer
-   Client abstraction
-   Validation
-   Global exception handling
-   In-memory multi-turn conversations
-   Conversation history APIs
-   Swagger/OpenAPI
-   Maven project

## Tech Stack

-   Java 17+
-   Spring Boot
-   Maven
-   Anthropic Java SDK
-   Lombok
-   SpringDoc OpenAPI

## Project Structure

``` text
src/main/java
└── com.prince.anthropic.sdk
    ├── client
    ├── config
    ├── controller
    ├── dto
    ├── enums
    ├── exception
    ├── models
    ├── service
    ├── store
    └── AnthropicSdkApplication
```

## Configuration

application.yml

``` yaml
anthropic:
  api-key: ${EP_ACC_ANTHOPIC_KEY}
  model: claude-haiku-4-5-20251001
  max-tokens: 1000
```

## Environment Variable

Windows CMD

``` cmd
echo %EP_ACC_ANTHOPIC_KEY%
```

PowerShell

``` powershell
echo $env:EP_ACC_ANTHOPIC_KEY
```

## Run

``` bash
mvn clean install
mvn spring-boot:run
```

Server

    http://localhost:8080

Swagger UI

    http://localhost:8080/swagger-ui/index.html

OpenAPI

    http://localhost:8080/api-docs

------------------------------------------------------------------------

# REST API

## 1. Chat

**POST**

    http://localhost:8080/api/anthropic/chat

Request

``` json
{
  "prompt": "What is the temperature at which Celsius and Fahrenheit become same? Answer in one sentence."
}
```

Response

``` json
{
  "response": "At -40 degrees, Celsius and Fahrenheit have the same temperature value."
}
```

## 2. Get Conversation History

**GET**

    http://localhost:8080/api/anthropic/conversation

Response

``` json
[
  {
    "role": "USER",
    "content": "Hello"
  },
  {
    "role": "ASSISTANT",
    "content": "Hi! How can I help you?"
  }
]
```

## 3. Clear Conversation

**POST**

    http://localhost:8080/api/anthropic/conversation/clear

Response

    204 No Content

## cURL

``` bash
curl --location "http://localhost:8080/api/anthropic/chat" \
--header "Content-Type: application/json" \
--data '{
  "prompt":"Hello Claude!"
}'
```

## Architecture

``` text
Client
   │
   ▼
REST Controller
   │
   ▼
Service Layer
   │
   ▼
ConversationStore
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

## Conversation Flow

1.  User sends a prompt.
2.  Service stores the user message in `ConversationStore`.
3.  Complete conversation history is sent to Anthropic.
4.  Claude generates a response.
5.  Assistant response is stored in `ConversationStore`.
6.  Response is returned to the client.

## Current Implementation

-   Conversation history is maintained in memory.
-   Every chat request sends the complete conversation history to
    Anthropic.
-   Suitable for demos and local development.
-   Production applications should store conversations in Redis or a
    database using Conversation ID or User ID.

## Future Enhancements

-   Streaming Responses
-   Persistent Conversation Storage
-   System Prompts
-   Tool Use
-   Structured Outputs
-   Vision API
-   PDF Support
-   MCP Integration
-   Prompt Caching
-   Observability

## License

MIT

## Author

Prince Agrawal

If you found this repository useful, consider giving it a ⭐.
