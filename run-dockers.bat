@echo off
setlocal
cd /d "%~dp0"
cls
echo ====================================================
echo    HOTEL HILTON - DESPLIEGUE EN DOCKER
echo ====================================================
echo.

rem Paso 1: Detener y eliminar contenedores existentes
echo [1/6] DETENIENDO Y ELIMINANDO CONTENEDORES ANTERIORES...
docker compose down -v --remove-orphans 2>nul
echo       Contenedores y volumenes anteriores eliminados.
echo.

rem Paso 2: Compilar todos los JARs con Maven
echo [2/6] COMPILANDO PROYECTO JAVA CON MAVEN...
echo       (common, eureka, api-gateway y los 12 microservicios)
echo.
call mvn clean package -DskipTests -q
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ****************************************************
    echo ERROR: La compilacion Maven fallo.
    echo Revisa los errores arriba y corrige antes de reintentar.
    echo ****************************************************
    pause
    exit /b 1
)
echo       Compilacion exitosa. JARs generados.
echo.

rem Paso 3: Levantar Base de Datos y Mensajeria
echo [3/6] INICIANDO POSTGRES Y KAFKA...
docker compose up -d postgres kafka
echo.
echo       Esperando a que PostgreSQL este completamente activo (healthy)...
powershell -Command "while ((docker inspect --format '{{.State.Health.Status}}' postgres-rdbms 2>$null) -ne 'healthy') { Start-Sleep -Seconds 2 }"
echo       [OK] PostgreSQL esta listo.
echo.

rem Paso 4: Compilar las bases de datos (SQL)
echo [4/6] COMPILANDO BASES DE DATOS EN POSTGRES...
cd init-multi-db
call docker_compile_dbs.bat
cd ..
echo       [OK] Bases de datos creadas y cargadas.
echo.

rem Paso 5: Levantar Eureka y API Gateway
echo [5/6] LEVANTANDO EUREKA Y API GATEWAY...
echo       Iniciando Eureka Server...
docker compose up -d eureka-server
echo       Esperando a que Eureka Server este saludable...
powershell -Command "while ((docker inspect --format '{{.State.Health.Status}}' eureka-server 2>$null) -ne 'healthy') { Start-Sleep -Seconds 2 }"
echo       [OK] Eureka Server esta activo y saludable.
echo.
echo       Iniciando API Gateway...
docker compose up -d api-gateway
echo       Esperando a que API Gateway este saludable...
powershell -Command "while ((docker inspect --format '{{.State.Health.Status}}' api-gateway 2>$null) -ne 'healthy') { Start-Sleep -Seconds 2 }"
echo       [OK] API Gateway esta activo y saludable.
echo.

rem Paso 6: Levantar los 12 microservicios + Kafka UI
echo [6/6] LEVANTANDO LOS 12 MICROSERVICIOS...
docker compose up -d ms-reservas ms-habitaciones ms-huespedes ms-checkin ms-pagos ms-housekeeping ms-restaurante ms-inventario ms-notificaciones ms-tarifas ms-reportes ms-autenticacion kafka-ui
echo.
echo       Esperando 20 segundos a que los microservicios se registren en Eureka...
powershell -Command "Start-Sleep -Seconds 20"
echo       [OK] Microservicios listos.
echo.

echo ====================================================
echo           SISTEMA COMPLETAMENTE OPERATIVO
echo ====================================================
echo.
echo   Servicios disponibles:
echo   -----------------------------------------------
echo   API Gateway:    http://localhost:9000
echo   Eureka:         http://localhost:8761
echo   Kafka UI:       http://localhost:8080
echo   PostgreSQL:     localhost:5433
echo   -----------------------------------------------
echo.
echo   Para detener todo: docker compose down -v
echo ====================================================
endlocal
pause