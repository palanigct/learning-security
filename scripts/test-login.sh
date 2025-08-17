#!/bin/bash

# Test script for JWT login endpoint
BASE_URL="http://localhost:8080"
LOGIN_URL="$BASE_URL/auth/login"

echo "Testing login endpoint: $LOGIN_URL"
echo "Credentials: admin/password"
echo ""

# Test login
echo "=== Testing Login ==="
LOGIN_RESPONSE=$(curl -s -X POST "$LOGIN_URL" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}')

echo "Login Response: $LOGIN_RESPONSE"

# Extract token from response (assuming response is JSON with 'token' field)
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$TOKEN" ]; then
    echo ""
    echo "JWT Token extracted: $TOKEN"
    echo ""
    
    # Test protected endpoint
    echo "=== Testing Protected Endpoint ==="
    PROTECTED_URL="$BASE_URL/employees"
    
    PROTECTED_RESPONSE=$(curl -s -X GET "$PROTECTED_URL" \
      -H "Authorization: Bearer $TOKEN")
    
    echo "Protected endpoint response: $PROTECTED_RESPONSE"
else
    echo "Failed to extract JWT token from response"
fi
