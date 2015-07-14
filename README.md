# url-shortener
Case Study PBDMNG

git clone https://github.com/GruppoPBDMNG-10/url-shortener.git

docker build -t gruppopbdmng-10/url-shortener url-shortener

##AngularJS App

docker run -d -p 9001:80 --name url-shortener-client gruppopbdmng-10/url-shortener /usr/sbin/apache2ctl -D FOREGROUND
