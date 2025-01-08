
def getStages() {
    stages {
        stage('Run Deploy1') {
            parallel {
                stage('Deploy A') {
                    agent any
                    steps {
                        sh "echo Hello A!"
                    }
                }
                stage('Deploy B') {
                    agent any
                    steps {
                        sh "echo Hello B!"
                    }
                }
            }
        }
        stage('Run Tests') {
                steps {
                    sh "echo Tests1!"
                }
        }
        stage('Run Deploy2') {
            parallel {
                stage('Deploy C') {
                    agent any
                    steps {
                        sh "echo Hello C!"
                    }
                }
                stage('Deploy D') {
                    agent any
                    steps {
                        sh "echo Hello D!"
                    }
                }
            }
        }
    }
}

return this