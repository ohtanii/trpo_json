# About project

Application for validation json files.
________________________________________
To run this project:
1) sudo docker build -t validation-service github.com/ohtanii/trpo_json && docker run -t —rm -p 80:80 validation-service
2) curl -s —upload-file test.json http://localhost
