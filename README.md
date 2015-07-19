# url-shortener
url-shortener is a service that takes long URLs and squeezes them into fewer characters.

## Installation

1. Clone the repository

  `git clone https://github.com/GruppoPBDMNG-10/url-shortener.git`

2. Build the url-shortener image

  `docker build -t gruppopbdmng-10/url-shortener url-shortener`

3. Run url-shortener container

  `docker run -d -p 8080:80 -p 4567:4567 --name url-shortener gruppopbdmng-10/url-shortener /start.sh`

4. Start the server application

  `docker exec -it url-shortener /start.sh`

5. Map the the virtual machine's ports in VirtualBox

  `AngularJS App port is 8080`

  `API server port is 4567`

## Utilisation

Open a web browser and digit `http://localhost:8080`

## API Server
1. Create new tiny url 

	**[POST]** `http://localhost:4567/tiny
	
  `{"url" : "www.example.it",	"custom" : "example"}`

2.  Redirect

	**[GET]** `http://localhost:4567/*`

3. Statistics 

	**[GET]** `http://localhost:4567/statistics`
	* `tiny` - tiny url to get statistics [obbligatory]
	* `ip` - filters the results of the statistics based on the IP entered
	* `userAgent` - filters the results of the statistics based on the userAgent entered
	* `from` - filters the statistics from the ith click
	* `to` - filters the statistics up to the ith click
	* `dateTo` - filters the statistics from the date entered
	* `dateFrom` - filters the statistics up to the date entered

## Contributors

Thank you!

* [@gaetanoziri](https://github.com/gaetanoziri)
* [@fastluca](https://github.com/fastluca)
