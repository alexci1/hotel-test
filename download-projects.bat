@echo off
echo Descargando microservicios Spring Boot...
echo.
echo Descargando eureka.zip...
curl -o eureka.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=eureka&groupId=cl.hilton&artifactId=cl-hilton-eureka&name=hotel-eureka&description=servicio-eureka&packageName=cl.hilton.eureka&packaging=jar&javaVersion=21&dependencies=cloud-eureka-server,devtools"
echo.
echo Descargando ms-reservas.zip...
curl -o ms-reservas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-reservas&groupId=cl.hilton&artifactId=cl-hilton-reservas&name=hotel-reservas&description=servicio-reservas&packageName=cl.hilton.reservas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-habitaciones.zip...
curl -o ms-habitaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-habitaciones&groupId=cl.hilton&artifactId=cl-hilton-habitaciones&name=hotel-habitaciones&description=servicio-habitaciones&packageName=cl.hilton.habitaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-huespedes.zip...
curl -o ms-huespedes.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-huespedes&groupId=cl.hilton&artifactId=cl-hilton-huespedes&name=hotel-huespedes&description=servicio-huespedes&packageName=cl.hilton.huespedes&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-checkin.zip...
curl -o ms-checkin.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-checkin&groupId=cl.hilton&artifactId=cl-hilton-checkin&name=hotel-checkin&description=servicio-checkin&packageName=cl.hilton.checkin&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-pagos.zip...
curl -o ms-pagos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-pagos&groupId=cl.hilton&artifactId=cl-hilton-pagos&name=hotel-pagos&description=servicio-pagos&packageName=cl.hilton.pagos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-housekeeping.zip...
curl -o ms-housekeeping.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-housekeeping&groupId=cl.hilton&artifactId=cl-hilton-housekeeping&name=hotel-housekeeping&description=servicio-housekeeping&packageName=cl.hilton.housekeeping&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-restaurante.zip...
curl -o ms-restaurante.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-restaurante&groupId=cl.hilton&artifactId=cl-hilton-restaurante&name=hotel-restaurante&description=servicio-restaurante&packageName=cl.hilton.restaurante&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-inventario.zip...
curl -o ms-inventario.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-inventario&groupId=cl.hilton&artifactId=cl-hilton-inventario&name=hotel-inventario&description=servicio-inventario&packageName=cl.hilton.inventario&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-notificaciones.zip...
curl -o ms-notificaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-notificaciones&groupId=cl.hilton&artifactId=cl-hilton-notificaciones&name=hotel-notificaciones&description=servicio-notificaciones&packageName=cl.hilton.notificaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-tarifas.zip...
curl -o ms-tarifas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-tarifas&groupId=cl.hilton&artifactId=cl-hilton-tarifas&name=hotel-tarifas&description=servicio-tarifas&packageName=cl.hilton.tarifas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-reportes.zip...
curl -o ms-reportes.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-reportes&groupId=cl.hilton&artifactId=cl-hilton-reportes&name=hotel-reportes&description=servicio-reportes&packageName=cl.hilton.reportes&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-autenticacion.zip...
curl -o ms-autenticacion.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-autenticacion&groupId=cl.hilton&artifactId=cl-hilton-autenticacion&name=hotel-autenticacion&description=servicio-autenticacion&packageName=cl.hilton.autenticacion&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descarga completada.
pause
