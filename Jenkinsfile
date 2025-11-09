pipeline {
    agent any
    
    environment {
        SERVER_IP    = '43.205.92.192'
        SSH_CRED_ID  = 'node-app-key'
        TOMCAT_PATH  = '/var/lib/tomcat10/webapps'
        TOMCAT_SVC   = 'tomcat10'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
                git branch: 'main', url: 'https://github.com/AishwaryaPawar149/employee-management-java.git'
            }
        }
        
        stage('Build WAR') {
            steps {
                echo 'Building WAR file with Maven...'
                sh 'mvn clean package'
            }
        }
        
        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying to remote Tomcat server...'
                sshagent([SSH_CRED_ID]) {
                    sh """
                        WAR_FILE=\$(ls target/*.war | head -n 1)
                        echo "Deploying: \$WAR_FILE"
                        scp -o StrictHostKeyChecking=no \$WAR_FILE ubuntu@${SERVER_IP}:/tmp/
                        ssh -o StrictHostKeyChecking=no ubuntu@${SERVER_IP} '
                            sudo rm -rf ${TOMCAT_PATH}/employee-management*
                            sudo mv /tmp/*.war ${TOMCAT_PATH}/employee-management.war
                            sudo chown tomcat:tomcat ${TOMCAT_PATH}/employee-management.war
                            sudo systemctl restart ${TOMCAT_SVC}
                            echo "✅ Deployment completed!"
                        '
                    """
                }
            }
        }
        
        stage('Verify Deployment') {
            steps {
                echo 'Waiting for Tomcat to restart...'
                sleep(time: 15, unit: 'SECONDS')
                script {
                    def response = sh(
                        script: "curl -s -o /dev/null -w '%{http_code}' http://${SERVER_IP}:8080/employee-management/",
                        returnStdout: true
                    ).trim()
                    
                    if (response == '200') {
                        echo '✅ Application is running successfully!'
                    } else {
                        echo "⚠️ HTTP Response: ${response}"
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo "✅ Deployment successful!"
            echo "📱 Access application: http://${SERVER_IP}:8080/employee-management/"
        }
        failure {
            echo "❌ Deployment failed. Check logs above."
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}
