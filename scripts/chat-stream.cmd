REM ============================================================================
REM Please use this command to test the Anthropic API with streaming response.
REM You can change the prompt and temperature as per your requirement.
REM ============================================================================

curl -N -X POST ^
-H "Content-Type: application/json" ^
-d "{\"prompt\":\"Explain me about meditation benifits\",\"temperature\":\"BALANCED\"}" ^
http://localhost:8080/api/anthropic/chat/stream