FROM maven:3.5.3-jdk-8

# Configuration files
COPY ./userTemp /userTemp

# Compile JAR
ADD ./ /code/
WORKDIR /code
RUN ["mvn", "install"]

WORKDIR /
# Create a start bash script
# RUN touch start.sh && \
# 	echo '#!/bin/bash' >> start.sh && \
# 	echo '/usr/sbin/apache2ctl -D FOREGROUND' >> start.sh && \
# 	echo 'java -jar code/target/url-shortener-1.0.jar' >> start.sh && \
# 	chmod 777 start.sh	

# Creates images folder
RUN mkdir /images

# 80=Apache, 4567=java
EXPOSE 80 4567

# Run the boot up command
CMD java -jar 'code/target/url-shortener-1.0.jar'
