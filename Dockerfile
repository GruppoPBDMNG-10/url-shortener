FROM ubuntu:14.04

MAINTAINER Gaetano Ziri <gaetano_ziri@live.it>

# Initial update
RUN apt-get update

# This is to install add-apt-repository utility. All commands have to be non interactive with -y option
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y software-properties-common

# Install Oracle Java 8, accept license command is required for non interactive mode
RUN	apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 && \
	DEBIAN_FRONTEND=noninteractive add-apt-repository -y ppa:webupd8team/java && \
	apt-get update && \
	echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections &&\
	DEBIAN_FRONTEND=noninteractive apt-get install -y oracle-java8-installer

# Install curl utility just for testing
RUN apt-get update && \
	apt-get install -y curl
	
#Install nano - file editor
RUN apt-get install nano

# Install Redis-Server
RUN apt-get install -y redis-server

#Install Apache
RUN apt-get update && apt-get install -y apache2 && apt-get clean && rm -rf /var/lib/apt/lists/*

ENV APACHE_RUN_USER www-data
ENV APACHE_RUN_GROUP www-data
ENV APACHE_LOG_DIR /var/log/apache2

COPY client /var/www/html/
COPY server/userTemp /userTemp

#Install Maven
RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-3.3.3 /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

#Compile JAR
ADD server/pom.xml /code/pom.xml 
ADD server/src /code/src 
WORKDIR /code
#RUN ["mvn", "dependency:resolve"] 
#RUN ["mvn", "verify"]
RUN ["mvn", "install"]

WORKDIR /
# Create a start bash script
RUN touch start.sh && \
	echo '#!/bin/bash' >> start.sh && \
	echo 'redis-server &' >> start.sh && \
	echo '/usr/sbin/apache2ctl -D FOREGROUND' >> start.sh && \
	echo '/usr/lib/jvm/java-8-oracle/bin/java -jar code/target/url-shortener-1.0.jar' >> start.sh && \
	chmod 777 start.sh	

# 80=Apache, 4567=java, 6379=redis-server
EXPOSE 80 4567 6379

# Run the boot up command
CMD /start.sh
