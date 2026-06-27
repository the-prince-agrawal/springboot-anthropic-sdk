curl -N -X POST ^
-H "Content-Type: application/json" ^
-d "{\"prompt\":\"Explain me about meditation benifits\",\"temperature\":\"BALANCED\"}" ^
http://localhost:8080/api/anthropic/chat/stream