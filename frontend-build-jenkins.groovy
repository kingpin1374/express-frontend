pipeline {
    agent any

    stages {
        stage('Fetch Code') {
            steps {
                git branch: 'master', 
                    url: 'https://github.com/kingpin1374/express-frontend.git',
                    credentialsId: 'github'
            }
        }

        stage('Install Node.js') {
            steps {
                sh '''
                    sudo apt remove -y nodejs npm
                    curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
                    sudo apt-get install -y nodejs
                '''
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        stage('Deploy') {
            steps {
                sh 'pm2 restart express-frontend || pm2 start app.js --name "express-frontend"'
            }
        }
    }
}
