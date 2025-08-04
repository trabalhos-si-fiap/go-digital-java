#!/bin/sh

MODEL_NAME="deepseek-r1:8b"

echo "Starting Ollama server..."
ollama serve &

# Espera até o servidor estar disponível (timeout: 60s)
echo "Waiting for Ollama server to become available..."
until curl -s http://localhost:11434/api/tags >/dev/null 2>&1 || [ "$SECONDS" -gt 60 ]; do
  sleep 1
done

if [ "$SECONDS" -gt 60 ]; then
  echo "Ollama server did not become available within 60 seconds."
  exit 1
fi

# Verifica se o modelo já está presente
if ! ollama list | grep -q "$MODEL_NAME"; then
  echo "Model '$MODEL_NAME' not found. Downloading..."
  ollama run "$MODEL_NAME"
else
  echo "Model '$MODEL_NAME' already downloaded."
fi

# Espera o processo de background (ollama serve)
wait
