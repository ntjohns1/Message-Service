#!/bin/bash
port=${1:-8080}

curl -H "content-type: application/json" -d '{"sender":"Jeff","msg":"hello"}' http://localhost:8080/api/messages