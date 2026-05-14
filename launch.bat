@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo   Hotel - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-reservas
echo   [7] Iniciar ms-habitaciones
echo   [8] Iniciar ms-huespedes
echo   [9] Iniciar ms-checkin
echo   [10] Iniciar ms-pagos
echo   [11] Iniciar ms-housekeeping
echo   [12] Iniciar ms-restaurante
echo   [13] Iniciar ms-inventario
echo   [14] Iniciar ms-notificaciones
echo   [15] Iniciar ms-tarifas
echo   [16] Iniciar ms-reportes
echo   [17] Iniciar ms-autenticacion
echo.
echo   [0] Salir
echo.
echo ============================================
set /p opcion="  Selecciona una opcion: "

if "%opcion%"=="1" goto RUN_ALL
if "%opcion%"=="2" goto RUN_TEST
if "%opcion%"=="3" goto COMPILE
if "%opcion%"=="4" goto INSTALL
if "%opcion%"=="5" goto RUN_EUREKA
if "%opcion%"=="6" goto RUN_RESERVAS
if "%opcion%"=="7" goto RUN_HABITACIONES
if "%opcion%"=="8" goto RUN_HUESPEDES
if "%opcion%"=="9" goto RUN_CHECKIN
if "%opcion%"=="10" goto RUN_PAGOS
if "%opcion%"=="11" goto RUN_HOUSEKEEPING
if "%opcion%"=="12" goto RUN_RESTAURANTE
if "%opcion%"=="13" goto RUN_INVENTARIO
if "%opcion%"=="14" goto RUN_NOTIFICACIONES
if "%opcion%"=="15" goto RUN_TARIFAS
if "%opcion%"=="16" goto RUN_REPORTES
if "%opcion%"=="17" goto RUN_AUTENTICACION
if "%opcion%"=="0" goto SALIR

echo.
echo   Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================

:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start "EUREKA" mvn -f eureka spring-boot:run
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios =====
start "MS-RESERVAS" mvn -f ms-reservas spring-boot:run
start "MS-HABITACIONES" mvn -f ms-habitaciones spring-boot:run
start "MS-HUESPEDES" mvn -f ms-huespedes spring-boot:run
start "MS-CHECKIN" mvn -f ms-checkin spring-boot:run
start "MS-PAGOS" mvn -f ms-pagos spring-boot:run
start "MS-HOUSEKEEPING" mvn -f ms-housekeeping spring-boot:run
start "MS-RESTAURANTE" mvn -f ms-restaurante spring-boot:run
start "MS-INVENTARIO" mvn -f ms-inventario spring-boot:run
start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run
start "MS-TARIFAS" mvn -f ms-tarifas spring-boot:run
start "MS-REPORTES" mvn -f ms-reportes spring-boot:run
start "MS-AUTENTICACION" mvn -f ms-autenticacion spring-boot:run
echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Eureka Server (test) =====
start "EUREKA" java -jar eureka\target\cl-hilton-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios (test) =====
start "MS-RESERVAS" java -jar ms-reservas\\target\\cl-hilton-reservas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-HABITACIONES" java -jar ms-habitaciones\\target\\cl-hilton-habitaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-HUESPEDES" java -jar ms-huespedes\\target\\cl-hilton-huespedes-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-CHECKIN" java -jar ms-checkin\\target\\cl-hilton-checkin-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-PAGOS" java -jar ms-pagos\\target\\cl-hilton-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-HOUSEKEEPING" java -jar ms-housekeeping\\target\\cl-hilton-housekeeping-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-RESTAURANTE" java -jar ms-restaurante\\target\\cl-hilton-restaurante-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-INVENTARIO" java -jar ms-inventario\\target\\cl-hilton-inventario-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-NOTIFICACIONES" java -jar ms-notificaciones\\target\\cl-hilton-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-TARIFAS" java -jar ms-tarifas\\target\\cl-hilton-tarifas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-REPORTES" java -jar ms-reportes\\target\\cl-hilton-reportes-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-AUTENTICACION" java -jar ms-autenticacion\\target\\cl-hilton-autenticacion-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d C:\hotel-test\ms-reservas
call mvn clean install -U
cd /d C:\hotel-test\ms-habitaciones
call mvn clean install -U
cd /d C:\hotel-test\ms-huespedes
call mvn clean install -U
cd /d C:\hotel-test\ms-checkin
call mvn clean install -U
cd /d C:\hotel-test\ms-pagos
call mvn clean install -U
cd /d C:\hotel-test\ms-housekeeping
call mvn clean install -U
cd /d C:\hotel-test\ms-restaurante
call mvn clean install -U
cd /d C:\hotel-test\ms-inventario
call mvn clean install -U
cd /d C:\hotel-test\ms-notificaciones
call mvn clean install -U
cd /d C:\hotel-test\ms-tarifas
call mvn clean install -U
cd /d C:\hotel-test\ms-reportes
call mvn clean install -U
cd /d C:\hotel-test\ms-autenticacion
call mvn clean install -U
echo Compilacion completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2
echo Eliminando carpetas target ...
rmdir /s /q C:\hotel-test\eureka\target
rmdir /s /q C:\hotel-test\ms-reservas\target
rmdir /s /q C:\hotel-test\ms-habitaciones\target
rmdir /s /q C:\hotel-test\ms-huespedes\target
rmdir /s /q C:\hotel-test\ms-checkin\target
rmdir /s /q C:\hotel-test\ms-pagos\target
rmdir /s /q C:\hotel-test\ms-housekeeping\target
rmdir /s /q C:\hotel-test\ms-restaurante\target
rmdir /s /q C:\hotel-test\ms-inventario\target
rmdir /s /q C:\hotel-test\ms-notificaciones\target
rmdir /s /q C:\hotel-test\ms-tarifas\target
rmdir /s /q C:\hotel-test\ms-reportes\target
rmdir /s /q C:\hotel-test\ms-autenticacion\target
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests
echo.
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
echo.
echo ===== Iniciando Eureka =====
start "EUREKA" mvn -f eureka spring-boot:run
echo Eureka iniciado.
pause
goto MENU

:RUN_RESERVAS
cls
echo.
echo ===== Iniciando ms-reservas =====
start "MS-RESERVAS" mvn -f ms-reservas spring-boot:run
echo ms-reservas iniciado.
pause
goto MENU

:RUN_HABITACIONES
cls
echo.
echo ===== Iniciando ms-habitaciones =====
start "MS-HABITACIONES" mvn -f ms-habitaciones spring-boot:run
echo ms-habitaciones iniciado.
pause
goto MENU

:RUN_HUESPEDES
cls
echo.
echo ===== Iniciando ms-huespedes =====
start "MS-HUESPEDES" mvn -f ms-huespedes spring-boot:run
echo ms-huespedes iniciado.
pause
goto MENU

:RUN_CHECKIN
cls
echo.
echo ===== Iniciando ms-checkin =====
start "MS-CHECKIN" mvn -f ms-checkin spring-boot:run
echo ms-checkin iniciado.
pause
goto MENU

:RUN_PAGOS
cls
echo.
echo ===== Iniciando ms-pagos =====
start "MS-PAGOS" mvn -f ms-pagos spring-boot:run
echo ms-pagos iniciado.
pause
goto MENU

:RUN_HOUSEKEEPING
cls
echo.
echo ===== Iniciando ms-housekeeping =====
start "MS-HOUSEKEEPING" mvn -f ms-housekeeping spring-boot:run
echo ms-housekeeping iniciado.
pause
goto MENU

:RUN_RESTAURANTE
cls
echo.
echo ===== Iniciando ms-restaurante =====
start "MS-RESTAURANTE" mvn -f ms-restaurante spring-boot:run
echo ms-restaurante iniciado.
pause
goto MENU

:RUN_INVENTARIO
cls
echo.
echo ===== Iniciando ms-inventario =====
start "MS-INVENTARIO" mvn -f ms-inventario spring-boot:run
echo ms-inventario iniciado.
pause
goto MENU

:RUN_NOTIFICACIONES
cls
echo.
echo ===== Iniciando ms-notificaciones =====
start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run
echo ms-notificaciones iniciado.
pause
goto MENU

:RUN_TARIFAS
cls
echo.
echo ===== Iniciando ms-tarifas =====
start "MS-TARIFAS" mvn -f ms-tarifas spring-boot:run
echo ms-tarifas iniciado.
pause
goto MENU

:RUN_REPORTES
cls
echo.
echo ===== Iniciando ms-reportes =====
start "MS-REPORTES" mvn -f ms-reportes spring-boot:run
echo ms-reportes iniciado.
pause
goto MENU

:RUN_AUTENTICACION
cls
echo.
echo ===== Iniciando ms-autenticacion =====
start "MS-AUTENTICACION" mvn -f ms-autenticacion spring-boot:run
echo ms-autenticacion iniciado.
pause
goto MENU

:SALIR
cls
echo.
echo   Hasta luego.
echo.
endlocal
exit /b
