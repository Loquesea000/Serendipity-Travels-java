# Usar una imagen base de Tomcat con JDK 8
FROM tomcat:8.5-jdk8

# Establecer el directorio de trabajo donde Tomcat espera las aplicaciones web
WORKDIR /usr/local/tomcat/webapps/

# Copiar el archivo WAR de tu proyecto al directorio webapps de Tomcat
# Asegúrate de tener el archivo WAR en la misma carpeta que el Dockerfile o ajustar la ruta correctamente
COPY ./serendipitytravels.war /usr/local/tomcat/webapps/

# Copiar el conector de MySQL a la carpeta lib de Tomcat
# Asegúrate de tener el conector de MySQL (mysql-connector) en la ruta correcta
COPY ./lib/mysql-connector-j-8.2.0.jar /usr/local/tomcat/lib/

# (Opcional) Copiar las bibliotecas JSTL si tu proyecto las usa
# Asegúrate de tener la biblioteca JSTL en la ruta correcta
COPY ./lib/jstl-1.2.jar /usr/local/tomcat/lib/

# Exponer el puerto 8080 (por defecto de Tomcat)
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
