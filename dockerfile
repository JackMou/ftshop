FROM tomcat:9.0.5-jre8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ./server/tomcat9-server.xml /usr/local/tomcat/conf/server.xml
RUN rm -rf /usr/local/tomcat/webapps/*
RUN mkdir /usr/local/tomcat/webapps/ROOT
ADD ./target/ftshop-1.0.0.RELEASE/ /usr/local/tomcat/webapps/ROOT/
