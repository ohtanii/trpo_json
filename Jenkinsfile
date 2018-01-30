pipeline {
    agent any 
    stages {
        stage('1. Build') {
            steps {
                sh 'docker build -t jjson github.com/ohtanii/trpo_json'
                echo 'Building was comleted successfully :) '
            }
        }
        stage('2. Start') {
            steps {
                sh 'docker run -d --rm -p 80:80 jjson'
                echo 'Program is working now! Enjoy :) '
            }
        }
    }
}
