# Imagen de Postgres con la data semilla ya incluida dentro de la imagen (no depende
# de un bind-mount desde el host), para que cualquiera que levante este compose desde
# cero obtenga los datos precargados sin ejecutar nada manualmente.
FROM postgres:16-alpine

COPY BaseDatosPersonas.sql /docker-entrypoint-initdb.d/01-base-datos-personas.sql
