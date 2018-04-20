# url-shortener
url-shortener is a service that takes long URLs and squeezes them into fewer characters.

## Installation

1. Clone the repository

  `git clone https://github.com/GruppoPBDMNG-10/url-shortener.git`

2. Deploy url-shortener

  `docker-compose up --build`

## Utilization

Open a web browser and digit `http://localhost:8080`

## API Server
1. Create new tiny url 

	**[POST]** `http://localhost:4567/tiny`
	
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

## Configuration
The config file is situated in the folder `server/userTemp/conf`.

## Logging
The log files are situated in the folder `log`, the actual log file is situated in the sub folder `log/active`.

## Contributors

Thank you!

* [@gaetanoziri](https://github.com/gaetanoziri)
* [@fastluca](https://github.com/fastluca)
