# url-shortener
Case Study PBDMNG

git clone https://github.com/GruppoPBDMNG-10/url-shortener.git

docker build -t gruppopbdmng-10/url-shortener url-shortener

docker run -d -p 8080:80 -p 4567:4567 --name url-shortener gruppopbdmng-10/url-shortener /start.ch

#AngularJS APP
127.0.0.1:8080 (Map the port in virtualbox)

#Web Services
127.0.0.1:4567 (Map the port in virtualbox)
