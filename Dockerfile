# Etapa de construcción: Esta etapa se encarga de compilar el código fuente y generar el JAR ejecutable
# Utilizamos una imagen de Maven con Java 17 para la compilación
# La imagen maven:3.9-eclipse-temurin-17 incluye Maven 3.9 y Java 17
# AS build: Asigna un nombre a esta etapa para poder referenciarla en la siguiente
FROM maven:3.9-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo dentro del contenedor
# /app es una convención común para aplicaciones en contenedores
WORKDIR /app

# Copiamos los archivos pom.xml de todos los módulos
COPY customer/pom.xml customer/
COPY customer/boot/pom.xml customer/boot/
COPY customer/domain/pom.xml customer/domain/
COPY customer/application/pom.xml customer/application/
COPY customer/infrastructure/pom.xml customer/infrastructure/

# Descargamos las dependencias
RUN mvn -f customer/pom.xml dependency:go-offline -B

# Copiamos el código fuente
COPY customer/boot/src customer/boot/src
COPY customer/domain/src customer/domain/src
COPY customer/application/src customer/application/src
COPY customer/infrastructure/src customer/infrastructure/src

# Compilamos la aplicación
RUN mvn -f customer/pom.xml clean package -DskipTests

# Etapa de ejecución: Esta etapa contiene solo lo necesario para ejecutar la aplicación
# Usamos una imagen más ligera que solo incluye el JRE de Java 17
# eclipse-temurin:17-jre-alpine es una imagen minimalista que solo incluye el JRE
# Esto reduce significativamente el tamaño de la imagen final
FROM eclipse-temurin:17-jre-alpine

# Instalamos curl para el healthcheck
# --no-cache: Evita almacenar el índice de paquetes en la imagen
# curl es necesario para realizar las comprobaciones de salud
RUN apk add --no-cache curl

# Establecemos el directorio de trabajo en el contenedor de ejecución
# Debe coincidir con el directorio donde copiaremos el JAR
WORKDIR /app

# Copiamos el JAR compilado desde la etapa de construcción
# --from=build: Indica que el archivo se copia desde la etapa anterior
# *.jar: Captura cualquier archivo JAR en el directorio target
# app.jar: Nombre simplificado para el JAR en la imagen final
COPY --from=build /app/customer/boot/target/*.jar app.jar

# Variables de entorno por defecto
# SPRING_PROFILES_ACTIVE: Define el perfil de Spring Boot a usar
# SERVER_PORT: Define el puerto en el que escuchará la aplicación
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Exponemos el puerto 8080 para que la aplicación sea accesible desde fuera del contenedor
# Este puerto debe coincidir con SERVER_PORT
EXPOSE 8080

# Añadimos un healthcheck para verificar que la aplicación está funcionando
# --interval=30s: Comprueba cada 30 segundos
# --timeout=3s: La comprobación debe completarse en 3 segundos
# --start-period=60s: Da 60 segundos de gracia al inicio
# --retries=3: Intenta 3 veces antes de marcar como no saludable
# curl -f: Falla si el servidor devuelve un código de error HTTP
# || exit 1: Asegura que el contenedor se marque como no saludable si falla
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Definimos el comando que se ejecutará cuando el contenedor inicie
# java -jar: Ejecuta el JAR como una aplicación Java independiente
# app.jar: El nombre del archivo JAR que copiamos anteriormente
ENTRYPOINT ["java", "-jar", "app.jar"] 