# Assistant Prefilling + Stop Sequences

## Before

Without assistant prefilling and stop sequences, Claude returns explanatory text along with the JSON.

```json
{
  "response": "# EC2 Instance Monitoring Rule (JSON)\n\n```json\n{\n  \"RuleName\": \"MonitorEC2Instances\",\n  ...
}
```

## After

Using:

- Assistant prefill: ` ```json `
- Stop sequence: ` ``` `

Claude returns only the JSON.

```json
{
  "response": "\n{\n  \"rule\": {\n    \"name\": \"monitor-ec2-instances\",\n    ...
  }\n}\n"
}
```

## Benefit

- No markdown code fences
- No explanation text
- Response is ready to parse as JSON
- Ideal for applications consuming structured output