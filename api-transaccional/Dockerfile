# ---------- Etapa de build ----------
# Compila el jar con Maven sobre el JDK de GraalVM.
FROM ghcr.io/graalvm/jdk-community:17 AS build

# Directorio de trabajo dentro del contenedor; todo lo que sigue se ejecuta desde ahi.
WORKDIR /app

# La imagen de GraalVM Community es minimalista y no trae gzip/tar/unzip, que el
# wrapper de Maven necesita para descomprimir la distribucion de Maven la primera
# vez que corre ./mvnw.
RUN microdnf install -y gzip tar unzip findutils && microdnf clean all

# Se copian primero el wrapper de Maven y el pom.xml
COPY api-transaccional/mvnw ./mvnw
COPY api-transaccional/.mvn ./.mvn
COPY api-transaccional/pom.xml ./pom.xml

# Descarga todas las dependencias declaradas en el pom.xml al repositorio local de
# Maven dentro de la imagen, para que el build real.
RUN ./mvnw dependency:go-offline -B

# Se codigo fuente
COPY api-transaccional/src ./src

# Compila y empaqueta la aplicacion en un jar ejecutable 
RUN ./mvnw package -DskipTests -B

# ---------- Etapa de ejecucion ----------
# Imagen final, separada de la de build, para no arrastrar el codigo fuente ni el
# repositorio local de Maven a produccion.
FROM ghcr.io/graalvm/jdk-community:17
WORKDIR /app

# Se trae unicamente el jar generado en la etapa "build" (nada mas de esa etapa
# termina en la imagen final).
COPY --from=build /app/target/*.jar app.jar

# La aplicacion escucha en el puerto 8080
EXPOSE 8080

# Comando que se ejecuta al levantar el contenedor.
ENTRYPOINT ["java", "-jar", "app.jar"]
