# Test script for JWT login endpoint
$baseUrl = "http://localhost:8080"
$loginUrl = "$baseUrl/auth/login"

# Test credentials
$credentials = @{
    username = "admin"
    password = "password"
} | ConvertTo-Json

Write-Host "Testing login endpoint: $loginUrl"
Write-Host "Credentials: $credentials"
Write-Host ""

try {
    $response = Invoke-RestMethod -Uri $loginUrl -Method POST -Body $credentials -ContentType "application/json"
    Write-Host "Login successful!" -ForegroundColor Green
    Write-Host "JWT Token: $($response.token)" -ForegroundColor Green
    
    # Test a protected endpoint with the JWT token
    $protectedUrl = "$baseUrl/employees"
    $headers = @{
        "Authorization" = "Bearer $($response.token)"
    }
    
    Write-Host ""
    Write-Host "Testing protected endpoint: $protectedUrl"
    try {
        $protectedResponse = Invoke-RestMethod -Uri $protectedUrl -Method GET -Headers $headers
        Write-Host "Protected endpoint access successful!" -ForegroundColor Green
        Write-Host "Response: $($protectedResponse | ConvertTo-Json -Depth 2)"
    } catch {
        Write-Host "Protected endpoint access failed: $($_.Exception.Message)" -ForegroundColor Red
    }
    
} catch {
    Write-Host "Login failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $statusCode = $_.Exception.Response.StatusCode.value__
        $statusDescription = $_.Exception.Response.StatusDescription
        Write-Host "Status Code: $statusCode" -ForegroundColor Red
        Write-Host "Status Description: $statusDescription" -ForegroundColor Red
    }
}
