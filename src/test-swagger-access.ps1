# Test script for Swagger endpoints
$baseUrl = "http://localhost:8080"

Write-Host "Testing Swagger endpoints accessibility..." -ForegroundColor Yellow
Write-Host ""

# Test OpenAPI docs endpoint
$openApiUrl = "$baseUrl/v3/api-docs"
Write-Host "Testing OpenAPI docs: $openApiUrl"
try {
    $response = Invoke-RestMethod -Uri $openApiUrl -Method GET
    Write-Host "✅ OpenAPI docs accessible" -ForegroundColor Green
    Write-Host "API Title: $($response.info.title)" -ForegroundColor Green
    Write-Host "API Version: $($response.info.version)" -ForegroundColor Green
} catch {
    Write-Host "❌ OpenAPI docs failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Test Swagger UI endpoint
$swaggerUiUrl = "$baseUrl/swagger-ui.html"
Write-Host "Testing Swagger UI: $swaggerUiUrl"
try {
    $response = Invoke-WebRequest -Uri $swaggerUiUrl -Method GET
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Swagger UI accessible (Status: $($response.StatusCode))" -ForegroundColor Green
    } else {
        Write-Host "⚠️ Swagger UI returned status: $($response.StatusCode)" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Swagger UI failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Test Swagger UI resources
$swaggerResourcesUrl = "$baseUrl/swagger-ui/index.html"
Write-Host "Testing Swagger UI resources: $swaggerResourcesUrl"
try {
    $response = Invoke-WebRequest -Uri $swaggerResourcesUrl -Method GET
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Swagger UI resources accessible (Status: $($response.StatusCode))" -ForegroundColor Green
    } else {
        Write-Host "⚠️ Swagger UI resources returned status: $($response.StatusCode)" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Swagger UI resources failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "Swagger testing completed!" -ForegroundColor Yellow
