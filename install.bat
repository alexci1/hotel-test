@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2

REM Paso 2: Eliminar carpetas target de los proyectos
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

REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests

echo.
echo === PROCESO COMPLETADO ===
pause